package restaurantrush;

/**
 * Shared state and behaviour for every customer. Status only changes
 * through the mark...() methods. Subclasses supply getWaitPenalty().
 */
public abstract class Customer {
    private final int id;
    private int satisfaction;
    private CustomerStatus status;
    private Order order;
    private int servedTurn;
    private int readyToPayTurn;

    protected Customer(int id) {
        // TODO: also set the starting satisfaction and status
        this.id = id;
    }

    public int getId() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getSatisfaction() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public CustomerStatus getStatus() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Order getOrder() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getServedTurn() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getReadyToPayTurn() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markSeated() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markOrdered(Order o) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markServed(int turn) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markReadyToPay(int turn) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markPaid() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markLeft() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Called at turn end for a customer who is still waiting or not yet served. */
    public void loseSatisfaction() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Satisfaction lost per waiting turn for this type of customer. */
    protected abstract int getWaitPenalty();

    /** Bill for an item at the given base price. Full price unless overridden. */
    public int calculateBill(int baseCents) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** The customer's own food choice, using the rule documented in the README. */
    public MenuItem chooseItem() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
