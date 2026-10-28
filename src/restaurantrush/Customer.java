package restaurantrush;

/**
 * Shared state and behaviour for every customer. Status only changes
 * through the mark...() methods. Subclasses supply getWaitPenalty().
 */
public abstract class Customer {
    private static final int START_SATISFACTION = 100;
    private static final int MIN_SATISFACTION = 0;
    private static final int NOT_YET = -1;

    private final int id;
    private int satisfaction;
    private CustomerStatus status;
    private Order order;
    private int servedTurn;
    private int readyToPayTurn;

    protected Customer(int id) {
        this.id = id;
        this.satisfaction = START_SATISFACTION;
        this.status = CustomerStatus.WAITING;
        this.order = null;
        this.servedTurn = NOT_YET;
        this.readyToPayTurn = NOT_YET;
    }

    public int getId() {
        return id;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public Order getOrder() {
        return order;
    }

    public int getServedTurn() {
        return servedTurn;
    }

    public int getReadyToPayTurn() {
        return readyToPayTurn;
    }

    public void markSeated() {
        if (status != CustomerStatus.WAITING) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot be seated while " + status);
        }
        status = CustomerStatus.SEATED;
    }

    public void markOrdered(Order o) {
        if (status != CustomerStatus.SEATED) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot order while " + status);
        }
        if (o == null) {
            throw new IllegalArgumentException("Order must not be null");
        }
        order = o;
        status = CustomerStatus.ORDERED;
    }

    public void markServed(int turn) {
        if (status != CustomerStatus.ORDERED) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot be served while " + status);
        }
        servedTurn = turn;
        status = CustomerStatus.EATING;
    }

    public void markReadyToPay(int turn) {
        if (status != CustomerStatus.EATING) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot become ready to pay while " + status);
        }
        readyToPayTurn = turn;
        status = CustomerStatus.READY_TO_PAY;
    }

    public void markPaid() {
        if (status != CustomerStatus.READY_TO_PAY) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot pay while " + status);
        }
        status = CustomerStatus.PAID;
    }

    public void markLeft() {
        if (status != CustomerStatus.WAITING
                && status != CustomerStatus.SEATED
                && status != CustomerStatus.ORDERED) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot leave while " + status);
        }
        status = CustomerStatus.LEFT;
    }

    public void loseSatisfaction() {
        if (status != CustomerStatus.WAITING
                && status != CustomerStatus.SEATED
                && status != CustomerStatus.ORDERED) {
            return;
        }
        satisfaction = Math.max(MIN_SATISFACTION, satisfaction - getWaitPenalty());
    }

    /** Satisfaction lost per waiting turn for this type of customer. */
    protected abstract int getWaitPenalty();

    /** Bill for an item at the given base price. Full price unless overridden. */
    public int calculateBill(int baseCents) {
        return baseCents;
    }
    
    public MenuItem chooseItem() {
        MenuItem[] menu = MenuItem.values();
        return menu[Math.floorMod(id - 1, menu.length)];
    }
}
