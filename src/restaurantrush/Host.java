package restaurantrush;

public class Host extends Staff {
    public ActionResult seatCustomer(Customer c, Table t) {
        if(!canAct()){
            return ActionResult.fail("Host has already acted.");
        }
        if(c == null){
            return ActionResult.fail("Customer does not exist.");
        }
        if(c.getStatus() != CustomerStatus.WAITING){
            return ActionResult.fail("This is not a waiting customer.");
        }
        if(t == null || !t.isFree()){
            return ActionResult.fail("Table is not free.");
        }

        t.seat(c);
        c.markSeated();
        markActed();
        return ActionResult.ok();
    }
}
