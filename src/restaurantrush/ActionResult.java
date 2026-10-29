package restaurantrush;

/**
 * Outcome of a manager action. A failed action carries the reason to show
 * the player and must not use up the staff member's task.
 */
public class ActionResult {
    private final boolean success;
    private final String message;

    private ActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public static ActionResult ok() {
        return new ActionResult(true, "");
    }

    public static ActionResult fail(String message) {
        return new ActionResult(false, message);
    }
}
