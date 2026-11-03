package restaurantrush;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Runs the turn sequence and checks every manager action against the rules.
 * The UI only calls these methods and displays the results.
 */
public class GameController {
    public static final int MAX_TURNS = 18;
    private static final int REQUIRED_PAID = 4;
    private static final int REQUIRED_REVENUE_CENTS = 4500;
    private static final int REQUIRED_AVG_SATISFACTION = 60;
    private static final int[] ARRIVAL_TURNS = {1, 4, 7, 10, 13, 16};
    private static final int VIP_ARRIVAL_TURN = 4;
    private static final int CRITIC_ARRIVAL_TURN = 10;

    private int currentTurn;
    private final Restaurant restaurant;
    private final HappyHour happyHour;

    public GameController() {
        this.currentTurn = 0;
        this.restaurant = new Restaurant();
        this.happyHour = new HappyHour();
    }

    /**
     * Step 1: moves to the next turn, resets staff, gives the Happy Hour
     * recovery while it is active and adds any scheduled arrival.
     */
    public void startTurn() {
        currentTurn++;
        restaurant.getWaiter().resetForTurn();
        restaurant.getChef().resetForTurn();
        restaurant.getCashier().resetForTurn();
        restaurant.getHost().resetForTurn();

        if (happyHour.isActive()) {
            recoverWaitingCustomers();
        }

        for (int arrivalTurn : ARRIVAL_TURNS) {
            if (arrivalTurn == currentTurn) {
                restaurant.addToQueue(createArrival(currentTurn));
            }
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public HappyHour getHappyHour() {
        return happyHour;
    }

    /**
     * Starts Happy Hour for this turn and the next. Allowed once per game,
     * and only at the start of a turn, before any staff member has acted.
     */
    public ActionResult activateHappyHour() {
        if (!happyHour.canActivate()) {
            return ActionResult.fail("Happy Hour has already been used.");
        }
        if (!restaurant.getHost().canAct() || !restaurant.getWaiter().canAct()
                || !restaurant.getChef().canAct()) {
            return ActionResult.fail("Happy Hour can only start at the beginning of a turn.");
        }
        happyHour.activate();
        // startTurn() has already run, so this turn's recovery is given here.
        recoverWaitingCustomers();
        return ActionResult.ok();
    }

    /** The Host seats at most one waiting customer per turn, in addition to the Waiter. */
    public ActionResult hostSeatCustomer(int customerId) {
        Customer customer = restaurant.findCustomer(customerId);
        Table table = restaurant.findFreeTable();

        ActionResult result = restaurant.getHost().seatCustomer(customer, table);
        if (result.isSuccess()) {
            restaurant.removeFromQueue(customer);
        }
        return result;
    }

    // Waiter actions must fail once the Chef has acted this turn,
    // so a dish finished this turn is served next turn at the earliest.

    public ActionResult seatCustomer(int customerId) {
        if (!restaurant.getChef().canAct()) {
            return ActionResult.fail("The Waiter must act before the Chef.");
        }
        Customer customer = restaurant.findCustomer(customerId);
        Table table = restaurant.findFreeTable();

        ActionResult result = restaurant.getWaiter().seatCustomer(customer, table);
        if (result.isSuccess()) {
            restaurant.removeFromQueue(customer);
        }
        return result;
    }

    public ActionResult takeOrder(int customerId) {
        if (!restaurant.getChef().canAct()) {
            return ActionResult.fail("The Waiter must act before the Chef.");
        }
        Customer customer = restaurant.findCustomer(customerId);
        if (customer == null) {
            return ActionResult.fail("No customer with that number.");
        }

        // The customer picks the dish. Each customer orders once, so the order shares their id.
        Dish item = customer.chooseItem();
        Order order = new Order(customer.getId(), customer, item, lockPrice(customer, item));

        ActionResult result = restaurant.getWaiter().takeOrder(customer, order);
        if (result.isSuccess()) {
            restaurant.addOrder(order);
        }
        return result;
    }

    public ActionResult serveOrder(int orderId) {
        if (!restaurant.getChef().canAct()) {
            return ActionResult.fail("The Waiter must act before the Chef.");
        }
        Order order = restaurant.findOrder(orderId);

        ActionResult result = restaurant.getWaiter().serveOrder(order, currentTurn);
        if (result.isSuccess()) {
            restaurant.recordServed();
        }
        return result;
    }

    public ActionResult prepareOrder(int orderId) {
        Order order = restaurant.findOrder(orderId);
        return restaurant.getChef().prepareOrder(order);
    }

    /**
     * Steps 4 and 5: eating, payment, satisfaction loss and departures.
     * Then one turn of Happy Hour is used up.
     */
    public void endTurn() {
        // Copy everyone in the restaurant first, because departures change the queue and tables.
        List<Customer> customers = customersInRestaurant();

        // Step 4: a customer served on an earlier turn finishes eating,
        // then the Cashier takes at most one payment.
        for (Customer customer : customers) {
            if (customer.getStatus() == CustomerStatus.EATING
                    && customer.getServedTurn() < currentTurn) {
                customer.markReadyToPay(currentTurn);
            }
        }
        restaurant.getCashier().collectPayment(restaurant, currentTurn);

        // Step 5: customers not yet served lose satisfaction and leave when it reaches 0.
        for (Customer customer : customers) {
            customer.loseSatisfaction();
            if (customer.getSatisfaction() == 0) {
                customer.markLeft();
                if (customer.getOrder() != null) {
                    customer.getOrder().cancel();
                }
                restaurant.recordDeparture(customer);
            }
        }

        happyHour.countDown();
    }

    public boolean isGameOver() {
        return currentTurn >= MAX_TURNS;
    }

    public boolean isVictory() {
        OptionalDouble average = restaurant.averageSatisfaction();
        return restaurant.getPaidCount() >= REQUIRED_PAID
                && restaurant.getRevenueCents() >= REQUIRED_REVENUE_CENTS
                && average.isPresent()
                && average.getAsDouble() >= REQUIRED_AVG_SATISFACTION;
    }

    /** Customers are numbered 1 to 6 in the order they arrive. */
    private Customer createArrival(int turn) {
        int customerId = 0;
        for (int i = 0; i < ARRIVAL_TURNS.length; i++) {
            if (ARRIVAL_TURNS[i] == turn) {
                customerId = i + 1;
            }
        }
        if (turn == VIP_ARRIVAL_TURN) {
            return new VIPCustomer(customerId);
        }
        if (turn == CRITIC_ARRIVAL_TURN) {
            return new CriticCustomer(customerId);
        }
        return new RegularCustomer(customerId);
    }

    /**
     * The price is fixed when the order is taken: the customer's own discount
     * first (VIP 10% off), then Happy Hour's 20% off if it is active.
     */
    private int lockPrice(Customer customer, Dish item) {
        int price = customer.calculateBill(item.getPriceCents());
        return happyHour.applyDiscount(price);
    }

    /** Happy Hour recovery for everyone still waiting for a table or food. */
    private void recoverWaitingCustomers() {
        for (Customer customer : customersInRestaurant()) {
            customer.recoverSatisfaction(happyHour.getRecovery());
        }
    }

    /** A new list of everyone in the queue or at a table. */
    private List<Customer> customersInRestaurant() {
        List<Customer> customers = new ArrayList<>(restaurant.getWaitingQueue());
        for (Table table : restaurant.getTables()) {
            if (!table.isFree()) {
                customers.add(table.getCustomer());
            }
        }
        return customers;
    }
}
