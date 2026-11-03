package restaurantrush;

/** One customer's dish. The price is fixed when the order is created. */
public class Order {
    private final int id;
    private final Customer customer;
    private final Dish item;
    private final int priceCents;
    private int unitsDone;
    private OrderStatus status;

    public Order(int id, Customer c, Dish item, int priceCents) {
        this.id = id;
        this.customer = c;
        this.item = item;
        this.priceCents = priceCents;
        this.unitsDone = 0;
        this.status = OrderStatus.PREPARING;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Dish getItem() {
        return item;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getUnitsDone() {
        return unitsDone;
    }

    public int getPriceCents() {
        return priceCents;
    }

    /** Does one preparation unit. Becomes READY when the last unit is done. */
    public void advancePreparation() {
        if(status == OrderStatus.PREPARING){
            unitsDone++;
            if(unitsDone >= item.getPrepUnits()){
                status = OrderStatus.READY;
            }
        }
    }

    public boolean isReady() {
        return status == OrderStatus.READY;
    }

    public void markServed() {
        if(status == OrderStatus.READY){
            status = OrderStatus.SERVED;
        }
    }

    public void markPaid() {
        if(status == OrderStatus.SERVED){
            status = OrderStatus.PAID;
        }
    }

    /** Cancels an unfinished order when its customer leaves. */
    public void cancel() {
        if(status == OrderStatus.PREPARING || status == OrderStatus.READY){
            status = OrderStatus.CANCELLED;
        }
    }
}
