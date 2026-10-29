package restaurantrush;

/** Does one task per turn: seat a customer, take an order or serve a READY order. */
public class Waiter extends Staff {

    public ActionResult seatCustomer(Customer c, Table t) {
        if(!canAct()){
            return ActionResult.fail("Waiter has already acted.");
        }
        if(c == null){
            return ActionResult.fail("Customer does not exist");
        }
        if(c.getStatus() != CustomerStatus.WAITING){
            return ActionResult.fail("This is not a waiting customer.");
        }
        if(t == null || !t.isFree()){
            return ActionResult.fail("Table is not free");
        }

        t.seat(c);
        c.markSeated();
        markActed();
        return ActionResult.ok();
    }

    public ActionResult takeOrder(Customer c, Order o) {
        if(!canAct()){
            return ActionResult.fail("Waiter has already acted.");
        }
        if(c == null){
            return ActionResult.fail("Customer does not exist");
        }
        if(c.getStatus() != CustomerStatus.SEATED){
            return ActionResult.fail("This is not a seated customer.");
        }
        if(o == null){
            return ActionResult.fail("Order is invalid");
        }
        if(o.getCustomer() != c){
            return ActionResult.fail("Order does not belong to this customer.");
        }

        c.markOrdered(o);
        markActed();
        return ActionResult.ok();
    }

    public ActionResult serveOrder(Order o, int turn) {
        if(!canAct()){
            return ActionResult.fail("Waiter has already acted.");
        }
        if(o == null){
            return ActionResult.fail("Order is invalid");
        }
        if(o.getStatus() != OrderStatus.READY){
            return ActionResult.fail("Order is not ready.");
        }
        o.markServed();
        o.getCustomer().markServed(turn);
        markActed();
        return ActionResult.ok();
    }
}
