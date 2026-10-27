package restaurantrush;

/**
 * Runs the turn sequence and checks every manager action against the rules.
 * The UI only calls these methods and displays the results.
 */
public class GameController {
    private static final int MAX_TURNS = 18;
    private static final int REQUIRED_PAID = 4;
    private static final int REQUIRED_REVENUE_CENTS = 4500;
    private static final int REQUIRED_AVG_SATISFACTION = 60;
    private static final int[] ARRIVAL_TURNS = {1, 4, 7, 10, 13, 16};

    private int currentTurn;
    private Restaurant restaurant;

    public GameController() {
        // TODO: create the restaurant
    }

    /** Step 1: moves to the next turn, resets staff and adds any scheduled arrival. */
    public void startTurn() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getCurrentTurn() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public Restaurant getRestaurant() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    // Waiter actions must fail once the Chef has acted this turn,
    // so a dish finished this turn is served next turn at the earliest.

    public ActionResult seatCustomer(int customerId) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public ActionResult takeOrder(int customerId) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public ActionResult serveOrder(int orderId) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public ActionResult prepareOrder(int orderId) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Steps 4 and 5: eating, payment, satisfaction loss and departures. */
    public void endTurn() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean isGameOver() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public boolean isVictory() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    private Customer createArrival(int turn) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
