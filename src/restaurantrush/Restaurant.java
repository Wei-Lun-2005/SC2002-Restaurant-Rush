package restaurantrush;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Queue;

/**
 * Holds the game state: the waiting queue, the 2 tables, all orders,
 * the staff and the totals used for the result. Creates its tables and staff.
 */
public class Restaurant {
    private static final int TABLE_COUNT = 2;

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
        waitingQueue = new ArrayDeque<>();
        orders = new ArrayList<>();
        tables = new ArrayList<>();
        for (int number = 1; number <= TABLE_COUNT; number++) {
            tables.add(new Table(number));
        }
        waiter = new Waiter();
        chef = new Chef();
        cashier = new Cashier();
        revenueCents = 0;
        paidCount = 0;
        satisfactionSum = 0;
        servedCount = 0;
        unhappyDepartures = 0;
    }

    public void addToQueue(Customer c) {
        waitingQueue.add(c);
    }

    public void removeFromQueue(Customer c) {
        waitingQueue.remove(c);
    }

    public Table findFreeTable() {
        for (Table t : tables) {
            if (t.isFree()) {
                return t;
            }
        }
        return null;
    }

    public void releaseTable(Customer c) {
        for (Table t : tables) {
            if (t.getCustomer() == c) {
                t.release();
                return;
            }
        }
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    public Customer findCustomer(int id) {
        for (Customer c : waitingQueue) {
            if (c.getId() == id) {
                return c;
            }
        }
        for (Table t : tables) {
            Customer c = t.getCustomer();
            if (c != null && c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Order findOrder(int id) {
        for (Order o : orders) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    public void recordServed() {
        servedCount++;
    }

    /** Adds the bill to revenue and records the satisfaction at payment. */
    public void recordPayment(int cents, int satisfaction) {
        revenueCents += cents;
        paidCount++;
        satisfactionSum += satisfaction;
    }   

    public void recordDeparture(Customer c) {
        removeFromQueue(c);
        releaseTable(c);
        unhappyDepartures++;
    }

    /** Average satisfaction of paid customers, or empty if no one has paid. */
    public OptionalDouble averageSatisfaction() {
        if (paidCount == 0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of((double) satisfactionSum / paidCount);
    }

    /** Read-only view of the queue, oldest first. */
    public List<Customer> getWaitingQueue() {
        return Collections.unmodifiableList(new ArrayList<>(waitingQueue));
    }

    public List<Table> getTables() {
        return Collections.unmodifiableList(tables);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    public int getRevenueCents() {
        return revenueCents;
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public Chef getChef() {
        return chef;
    }

    public Cashier getCashier() {
        return cashier;
    }

    public int getPaidCount() {
        return paidCount;
    }

    public int getServedCount() {
        return servedCount;
    }

    public int getUnhappyDepartures() {
        return unhappyDepartures;
    }
}
