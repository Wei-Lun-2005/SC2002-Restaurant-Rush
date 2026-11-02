package restaurantrush;

/** A standard customer: loses 8 satisfaction per waiting turn and pays full price. */
public class RegularCustomer extends Customer {
    private static final int WAIT_PENALTY = 8;

    public RegularCustomer(int id) {
        super(id);
    }

    @Override
    protected int getWaitPenalty() {
        return WAIT_PENALTY;
    }

    @Override
    public String getTypeName() {
        return "Regular";
    }
}
