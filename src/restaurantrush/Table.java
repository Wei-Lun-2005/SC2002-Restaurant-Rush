package restaurantrush;

/** Holds at most one customer and stays occupied until that customer pays or leaves. */
public class Table {
    private final int tableNumber;
    private Customer customer;

    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isFree() {
        return customer == null;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void seat(Customer c) {
        this.customer = c;
    }

    public void release() {
        this.customer = null;
    }
}
