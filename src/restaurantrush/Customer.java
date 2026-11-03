package restaurantrush;

/**
 * Shared state and behaviour for every customer. Status only changes
 * through the mark...() methods. Subclasses supply getWaitPenalty().
 */
public abstract class Customer {
    private static final int START_SATISFACTION = 100;
    private static final int MIN_SATISFACTION = 0;
    private static final int MAX_SATISFACTION = 100;
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

    /**
     * Happy Hour recovery: a customer still waiting for a table or food gains
     * the given amount, up to 100. Anyone else is unchanged.
     */
    public void recoverSatisfaction(int amount) {
        if (status != CustomerStatus.WAITING
                && status != CustomerStatus.SEATED
                && status != CustomerStatus.ORDERED) {
            return;
        }
        satisfaction = Math.min(MAX_SATISFACTION, satisfaction + amount);
    }

    /** Satisfaction lost per waiting turn for this type of customer. */
    protected abstract int getWaitPenalty();

    /** Type label shown in the UI, e.g. "VIP". */
    public abstract String getTypeName();

    /** Bill for an item at the given base price. Full price unless overridden. */
    public int calculateBill(int baseCents) {
        return baseCents;
    }
    
    /**
     * The customer's own food choice. Customer n's usual item is menu item
     * (n - 1) mod 3. Even-numbered customers order a combo of their usual
     * item and the next item on the menu.
     */
    public Dish chooseItem() {
        MenuItem[] menu = MenuItem.values();
        MenuItem usual = menu[Math.floorMod(id - 1, menu.length)];
        if (id % 2 != 0) {
            return usual;
        }
        MenuItem next = menu[Math.floorMod(id, menu.length)];
        return new ComboMeal(usual, next);
    }
}
