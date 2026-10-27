package restaurantrush;

/** Automatically takes payment from at most one READY_TO_PAY customer per turn. */
public class Cashier extends Staff {

    /**
     * Charges the oldest READY_TO_PAY customer who became ready before this turn,
     * so a customer who became ready this turn pays next turn at the earliest.
     */
    public void collectPayment(Restaurant r, int turn) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
