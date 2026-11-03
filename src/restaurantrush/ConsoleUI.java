package restaurantrush;

import java.util.InputMismatchException;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Collects the manager's choices and displays the game state.
 * Contains no game rules: every decision goes through GameController.
 */
public class ConsoleUI {
    private final Scanner scanner;
    private final GameController controller;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.controller = new GameController();
    }

    public static void main(String[] args) {
        new ConsoleUI().run();
    }

    public void run() {
        while (controller.isGameOver() == false){
            controller.startTurn();
            displayTurnStart();
            promptHappyHour();
            promptHost();
            promptWaiter();
            promptChef();
            controller.endTurn();
            displayTurnEnd();
        }
        displayResult();
    }

    private void displayTurnStart() {
        System.out.println("Turn " + controller.getCurrentTurn() + ":");

        HappyHour hh = controller.getHappyHour();
        if (hh.isActive()) {
            System.out.println("Happy Hour: ACTIVE (" + hh.getTurnsLeft() + " turn(s) left, 20% off new orders)");
        } else if (hh.canActivate()) {
            System.out.println("Happy Hour: available (lasts 2 turns, once per game)");
        } else {
            System.out.println("Happy Hour: ended");
        }

        Restaurant restaurant = controller.getRestaurant();
        
        List<Customer> queue = restaurant.getWaitingQueue();
        System.out.println("Current queue:");
        for (Customer customer : queue) {
            System.out.println("Customer: " + customer.getId() + " (" + customer.getTypeName() + ") Satisfaction: " + customer.getSatisfaction());
        }

        List<Table> tables = restaurant.getTables();
        System.out.println("Tables:");
        for (Table table : tables){
            System.out.print(table.getTableNumber());
            if (table.isFree()){
                System.out.println(" is empty");
            }
            else{
                Customer customer = table.getCustomer();
                System.out.println(": " + customer.getId() + " (" + customer.getTypeName() + ") " + customer.getStatus() + " " + customer.getSatisfaction());
            }
        }

        List<Order> orders = restaurant.getOrders();
        System.out.println("Orders :");
        for (Order order : orders){
            System.out.print(order.getId() + " " + order.getItem().getName() + " " + order.getStatus());
            System.out.println(": Progress:" + order.getUnitsDone() + "/" + order.getItem().getPrepUnits());
        }

        System.out.println("Total revenue: " + formatDollars(restaurant.getRevenueCents()));

        OptionalDouble avg = restaurant.averageSatisfaction();
        System.out.println("Average satisfaction: " + (avg.isPresent() ? String.format("%.1f", avg.getAsDouble()) : "N/A"));

        List<String> waiterTasks = new ArrayList<>();
        if (restaurant.findFreeTable() != null) {
            for (Customer c : queue) {
                waiterTasks.add("seat customer " + c.getId());
            }
        }
        for (Table t : tables) {
            Customer c = t.getCustomer();
            if (c != null && c.getStatus() == CustomerStatus.SEATED) {
                waiterTasks.add("take order from customer " + c.getId());
            }
        }
        for (Order o : orders) {
            if (o.getStatus() == OrderStatus.READY) {
                waiterTasks.add("serve order " + o.getId());
            }
        }

        List<String> chefTasks = new ArrayList<>();
        for (Order o : orders) {
            if (o.getStatus() == OrderStatus.PREPARING) {
                chefTasks.add("prepare order " + o.getId());
            }
        }

        List<String> hostTasks = new ArrayList<>();
        if (restaurant.findFreeTable() != null) {
            for (Customer c : queue) {
                hostTasks.add("seat customer " + c.getId());
            }
        }

        System.out.println("Staff tasks available:");
        System.out.println("Host: " + (hostTasks.isEmpty() ? "nothing to do" : String.join(", ", hostTasks)));
        System.out.println("Waiter: " + (waiterTasks.isEmpty() ? "nothing to do" : String.join(", ", waiterTasks)));
        System.out.println("Chef: " + (chefTasks.isEmpty() ? "nothing to do" : String.join(", ", chefTasks)));

        System.out.println();

    }

    private void promptHappyHour() {
        if (!controller.getHappyHour().canActivate()) return;
        
        while (true) {
            System.out.print("Activate Happy Hour now? (y/n): ");
            String in = scanner.nextLine().trim().toLowerCase();
            if (in.equals("n")) return;
            if (in.equals("y")) {
                ActionResult r = controller.activateHappyHour();
                System.out.println(r.getMessage());
                return;
            }
            System.out.println("Please enter y or n.");  
        }    
    }

    private void promptHost() {
        Restaurant r = controller.getRestaurant();
        if (!r.getHost().canAct() || r.getWaitingQueue().isEmpty()
            || r.findFreeTable() == null) return;

        System.out.println("Host: choose a waiting customer to seat (0 to skip)");
        for (Customer c : r.getWaitingQueue()) {
            System.out.println("  " + c.getId() + " - " + c.getTypeName() + " (satisfaction " + c.getSatisfaction() + ")");
        }
        while (true) {
            System.out.print("Customer id: ");
            String in = scanner.nextLine().trim();
            int id;
            try { id = Integer.parseInt(in); }
            catch (NumberFormatException e) { System.out.println("Enter a number."); continue; }
            if (id == 0) return;
            ActionResult res = controller.hostSeatCustomer(id);
            System.out.println(res.getMessage());
            if (res.isSuccess()) return;
        }    
    }

    private void promptWaiter() {

        Restaurant restaurant = controller.getRestaurant();
        boolean canAct = false;

        if (!restaurant.getWaitingQueue().isEmpty() && restaurant.findFreeTable() != null) {
            canAct = true;
        }
        for (Table t : restaurant.getTables()) {
            Customer c = t.getCustomer();
            if (c != null && c.getStatus() == CustomerStatus.SEATED) {
            canAct = true;
            }
        }       
        for (Order o : restaurant.getOrders()) {
            if (o.getStatus() == OrderStatus.READY) {
            canAct = true;
            }
        }

        if (!canAct) {
            System.out.println("Waiter has nothing to do this turn.");
            return;
        }

        boolean success = false;
            while (success == false) {
                try {
                    System.out.println("Choose waiter's action: ");
                    System.out.println("Offer Seat (1)");
                    System.out.println("Take Order (2)");
                    System.out.println("Serve (3)");
                    System.out.println("Wait (0)");
                    int choice = scanner.nextInt();

                    ActionResult result;
                    switch (choice) {
                        case 0:
                            System.out.println("Waiter waits this turn.");
                            return;
                        case 1:
                            System.out.println("Enter customer ID:");
                            result = controller.seatCustomer(scanner.nextInt());
                            break;
                        case 2:
                            System.out.println("Enter customer ID:");
                            int customerId = scanner.nextInt();
                            result = controller.takeOrder(customerId);
                            if (result.isSuccess()) {
                                Order order = restaurant.findCustomer(customerId).getOrder();
                                System.out.println("Customer " + customerId + " ordered " + order.getItem().getName()
                                        + " (order " + order.getId() + ").");
                            }
                            break;
                        case 3:
                            System.out.println("Enter order ID:");
                            result = controller.serveOrder(scanner.nextInt());
                            break;
                        default:
                            System.out.println("Invalid option, try again.");
                            continue;
                    }

                    if (result.isSuccess()) {
                        success = true;
                    } else {
                        System.out.println(result.getMessage());
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input, please enter a number.");
                    scanner.next();
                }
            }
    }

    private void promptChef() {
        Restaurant restaurant = controller.getRestaurant();
        boolean canAct = false;

        for (Order o : restaurant.getOrders()) {
            if (o.getStatus() == OrderStatus.PREPARING) {
                canAct = true;
            }
        }
        if (!canAct) {
            System.out.println("Chef has nothing to do this turn.");
            return;
        }

        boolean success = false;

        while (success == false) {
            try {
                System.out.println("Enter order ID to prepare (0 to wait):");
                int orderId = scanner.nextInt();
                if (orderId == 0) {
                    System.out.println("Chef waits this turn.");
                    return;
                }
                ActionResult result = controller.prepareOrder(orderId);

                if (result.isSuccess()) {
                    success = true;
                } else {
                    System.out.println(result.getMessage());
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a number.");
                scanner.next();
                }
        }
    }

    private void displayTurnEnd() {
        Restaurant restaurant = controller.getRestaurant();
        System.out.println("End of turn " + controller.getCurrentTurn());
        System.out.println("Customers served: " + restaurant.getServedCount());
        System.out.println("Unhappy departures: " + restaurant.getUnhappyDepartures());
        System.out.println("Total revenue: " + formatDollars(restaurant.getRevenueCents()));
        OptionalDouble avg = restaurant.averageSatisfaction();
        System.out.println("Average satisfaction: " + (avg.isPresent() ? String.format("%.1f", avg.getAsDouble()) : "N/A"));
        System.out.println("Turns used: " + controller.getCurrentTurn() + "/" + GameController.MAX_TURNS);
        System.out.println();
    }

    private void displayResult() {
        Restaurant restaurant = controller.getRestaurant();
        System.out.println("===== GAME OVER =====");
        if (controller.isVictory()) {
            System.out.println("You win!");
        } else {
            System.out.println("You lose.");
        }
        System.out.println("Customers paid: " + restaurant.getPaidCount());
        System.out.println("Customers served: " + restaurant.getServedCount());
        System.out.println("Unhappy departures: " + restaurant.getUnhappyDepartures());
        System.out.println("Total revenue: " + formatDollars(restaurant.getRevenueCents()));
        OptionalDouble avg = restaurant.averageSatisfaction();
        System.out.println("Average satisfaction: " + (avg.isPresent() ? String.format("%.1f", avg.getAsDouble()) : "N/A"));
        System.out.println("Turns used: " + controller.getCurrentTurn() + "/" + GameController.MAX_TURNS);
    }


    private String formatDollars(int cents) {
        return String.format("$%d.%02d", cents / 100, cents % 100);
    }
}
