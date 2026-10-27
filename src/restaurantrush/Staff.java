package restaurantrush;

/** Holds the one-action-per-turn rule shared by every staff member. */
public abstract class Staff {
    private boolean hasActed;

    public boolean canAct() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Call only after an action succeeds. */
    protected void markActed() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public void resetForTurn() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
