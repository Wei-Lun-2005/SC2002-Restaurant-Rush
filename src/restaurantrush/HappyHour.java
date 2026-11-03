package restaurantrush;

/**
 * A pricing policy the manager can switch on once per game. It lasts for
 * the turn it is activated and the next. Orders taken while it is active
 * get 20% off, and customers still waiting recover 15 satisfaction at the
 * start of each active turn.
 */
public class HappyHour {
    private static final int DURATION_TURNS = 2;
    private static final int DISCOUNT_PERCENT = 20;
    private static final int RECOVERY = 15;

    private boolean used;
    private int turnsLeft;

    public HappyHour() {
        this.used = false;
        this.turnsLeft = 0;
    }

    public boolean canActivate() {
        return !used;
    }

    public void activate() {
        if (used) {
            throw new IllegalStateException("Happy Hour has already been used this game");
        }
        used = true;
        turnsLeft = DURATION_TURNS;
    }

    public boolean isActive() {
        return turnsLeft > 0;
    }

    public int getTurnsLeft() {
        return turnsLeft;
    }

    public int applyDiscount(int priceCents) {
        if (!isActive()) {
            return priceCents;
        }
        return (int) Math.round(priceCents * (100 - DISCOUNT_PERCENT) / 100.0);
    }

    public int getRecovery() {
        return RECOVERY;
    }

    public void countDown() {
        if (turnsLeft > 0) {
            turnsLeft--;
        }
    }
}
