package restaurantrush;

/** Holds at most one customer and stays occupied until that customer pays or leaves. */
public class Table {
    private final int tableNumber;
    private Customer customer;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isFree() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getTableNumber() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Customer getCustomer() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void seat(Customer c) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void release() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
