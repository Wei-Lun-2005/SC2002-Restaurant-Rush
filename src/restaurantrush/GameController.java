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

    public GameController() {
        this.currentTurn = 0;
        this.restaurant = new Restaurant();
    }

    /** Step 1: moves to the next turn, resets staff and adds any scheduled arrival. */
    public void startTurn() {
        currentTurn++;
        restaurant.getWaiter().resetForTurn();
        restaurant.getChef().resetForTurn();
        restaurant.getCashier().resetForTurn();

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
        Order order = new Order(customer.getId(), customer, item, item.getPriceCents());

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

    /** Steps 4 and 5: eating, payment, satisfaction loss and departures. */
    public void endTurn() {
        // Copy everyone in the restaurant first, because departures change the queue and tables.
        List<Customer> customers = new ArrayList<>(restaurant.getWaitingQueue());
        for (Table table : restaurant.getTables()) {
            if (!table.isFree()) {
                customers.add(table.getCustomer());
            }
        }

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
}
