package restaurantrush;

/** A VIP customer: loses 5 satisfaction per waiting turn and gets 10% off the bill. */
public class VIPCustomer extends Customer {
    private static final int WAIT_PENALTY = 5;
    private static final int DISCOUNT_PERCENT = 10;

    public VIPCustomer(int id) {
        super(id);
    }

    @Override
    protected int getWaitPenalty() {
        return WAIT_PENALTY;
    }

    @Override
    public String getTypeName() {
        return "VIP";
    }

    @Override
    public int calculateBill(int baseCents) {
        return baseCents * (100 - DISCOUNT_PERCENT) / 100;
    }
}
