package restaurantrush;

/** Holds the one-action-per-turn rule shared by every staff member. */
public abstract class Staff {
    private boolean hasActed;

    public boolean canAct() {
        return !hasActed;
    }

    /** Call only after an action succeeds. */
    protected void markActed() {
        hasActed = true;
    }

    public void resetForTurn() {
        hasActed = false;
    }
}
