public class Order {
    private int id;
    private Customer customer;
    private MenuItem item;
    private int priceCents;
    private int unitsDone;
    private OrderStatus status;

    public Order(int id, Customer customer, MenuItem item, int priceCents){
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.priceCents = priceCents;
        this.unitsDone = 0;
        this.status = OrderStatus.PREPARING;
    }

    public int getId(){
        return id;
    }

    public Customer getCustomer(){
        return customer;
    }
        
    public MenuItem getItem(){
        return item;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public int getUnitsDone(){
        return unitsDone;
    }
    
    public int getPriceCents(){
        return priceCents;
    }
        
    public void advancePreparation(){
        if(status == OrderStatus.PREPARING){
            //increase unitsDone by 1
            unitsDone++;
            if(unitsDone >= item.getPrepUnits()){ //order is done preparing
                status = OrderStatus.READY;
            }
        }
    }

    public boolean isReady(){
        if (status == OrderStatus.READY){
            return true;
        }
        else{
            return false;
        }
    }
        
    public void markServed(){
        if(status == OrderStatus.READY){ //order is done preparing, can be served
            status = OrderStatus.SERVED;
        }
    }

    public void markPaid(){
        if(status == OrderStatus.SERVED){ //order has been served, ready for payment
            status = OrderStatus.PAID;
        }
    }

    public void cancel(){
        //order can only be cancelled if it is still being prepared or have not been served
        if(status == OrderStatus.PREPARING || status == OrderStatus.READY){
            status = OrderStatus.CANCELLED;
        }
    }
}
