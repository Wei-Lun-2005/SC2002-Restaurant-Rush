package restaurantrush;

/** A food critic: loses 12 satisfaction per waiting turn, pays full price and always orders Pasta. */
public class CriticCustomer extends Customer {
    private static final int WAIT_PENALTY = 12;

    public CriticCustomer(int id) {
        super(id);
    }

    @Override
    protected int getWaitPenalty() {
        return WAIT_PENALTY;
    }

    @Override
    public String getTypeName() {
        return "Critic";
    }

    @Override
    public MenuItem chooseItem() {
        return MenuItem.PASTA;
    }
}
