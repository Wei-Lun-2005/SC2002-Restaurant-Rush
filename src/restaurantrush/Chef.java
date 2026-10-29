package restaurantrush;

/** Advances one order by one preparation unit per turn. */
public class Chef extends Staff {

    public ActionResult prepareOrder(Order o) {
        if(!canAct()){
            return ActionResult.fail("Chef has already acted this turn!");
        }

        if (o == null) {
            return ActionResult.fail("No order with that number.");
        }
        if (o.getStatus() != OrderStatus.PREPARING) {
            return ActionResult.fail("That order is not being prepared.");
        }

        o.advancePreparation();
        markActed();
        return ActionResult.ok();

    }
}
