package restaurantrush;
import java.util.List;

/** Automatically takes payment from at most one READY_TO_PAY customer per turn. */
public class Cashier extends Staff {

    /**
     * Charges the oldest READY_TO_PAY customer who became ready before this turn,
     * so a customer who became ready this turn pays next turn at the earliest.
     */
    public void collectPayment(Restaurant r, int turn) {
        if(!canAct()){
            return;
        }
        List<Table> tables = r.getTables();
        Customer payingCustomer = null;
        for(int i = 0; i < tables.size(); i++){
            Customer customer = tables.get(i).getCustomer();
            if(customer == null || customer.getStatus() != CustomerStatus.READY_TO_PAY || customer.getReadyToPayTurn() >= turn) {
                continue;
            }
            if(payingCustomer == null || customer.getReadyToPayTurn() < payingCustomer.getReadyToPayTurn()) {
                payingCustomer = customer;
            }
        }

        if(payingCustomer == null){
            return;
        }

        Order order = payingCustomer.getOrder();
        // The price was locked when the order was taken, with any discounts already applied.
        int bill = order.getPriceCents();
        int satisfaction = payingCustomer.getSatisfaction();
        r.recordPayment(bill, satisfaction);
        payingCustomer.markPaid();
        order.markPaid();
        r.releaseTable(payingCustomer);
        markActed();
    }
}
