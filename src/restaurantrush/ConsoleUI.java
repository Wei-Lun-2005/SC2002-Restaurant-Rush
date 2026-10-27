package restaurantrush;

import java.util.Scanner;

/**
 * Collects the manager's choices and displays the game state.
 * Contains no game rules: every decision goes through GameController.
 */
public class ConsoleUI {
    private final Scanner scanner;
    private final GameController controller;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.controller = new GameController();
    }

    public static void main(String[] args) {
        new ConsoleUI().run();
    }

    public void run() {
        // TODO: each turn: startTurn, display, prompt Waiter then Chef, endTurn, display
        throw new UnsupportedOperationException("TODO");
    }

    private void displayTurnStart() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    private void promptWaiter() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    private void promptChef() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    private void displayTurnEnd() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    private void displayResult() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    /** Formats cents as dollars with two decimals, e.g. 4500 becomes "$45.00". */
    private String formatDollars(int cents) {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
