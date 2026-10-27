package restaurantrush;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Queue;

/**
 * Holds the game state: the waiting queue, the 2 tables, all orders,
 * the staff and the totals used for the result. Creates its tables and staff.
 */
public class Restaurant {
    private Queue<Customer> waitingQueue;
    private List<Table> tables;
    private List<Order> orders;
    private Waiter waiter;
    private Chef chef;
    private Cashier cashier;
    private int revenueCents;
    private int paidCount;
    private int satisfactionSum;
    private int servedCount;
    private int unhappyDepartures;

    public Restaurant() {
        // TODO: create the queue, the lists, the 2 tables and the 3 staff
    }

    public void addToQueue(Customer c) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void removeFromQueue(Customer c) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Table findFreeTable() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void releaseTable(Customer c) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void addOrder(Order o) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Customer findCustomer(int id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Order findOrder(int id) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void recordServed() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Adds the bill to revenue and records the satisfaction at payment. */
    public void recordPayment(int cents, int satisfaction) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void recordDeparture(Customer c) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Average satisfaction of paid customers, or empty if no one has paid. */
    public OptionalDouble averageSatisfaction() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Read-only view of the queue, oldest first. */
    public List<Customer> getWaitingQueue() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Table> getTables() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public List<Order> getOrders() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getRevenueCents() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Waiter getWaiter() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Chef getChef() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Cashier getCashier() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getPaidCount() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getServedCount() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getUnhappyDepartures() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
