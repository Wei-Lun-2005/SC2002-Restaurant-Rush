package restaurantrush;

/** One customer's dish. The price is fixed when the order is created. */
public class Order {
    private final int id;
    private final Customer customer;
    private final MenuItem item;
    private final int priceCents;
    private int unitsDone;
    private OrderStatus status;

    public Order(int id, Customer c, MenuItem item, int priceCents) {
        // TODO: also set unitsDone and status
        this.id = id;
        this.customer = c;
        this.item = item;
        this.priceCents = priceCents;
    }

    public int getId() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Customer getCustomer() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public MenuItem getItem() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public OrderStatus getStatus() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getUnitsDone() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getPriceCents() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Does one preparation unit. Becomes READY when the last unit is done. */
    public void advancePreparation() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean isReady() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markServed() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void markPaid() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Cancels an unfinished order when its customer leaves. */
    public void cancel() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
