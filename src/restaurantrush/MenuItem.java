package restaurantrush;

/** The three dishes on the menu. Prices are in cents. */
public enum MenuItem {
    SALAD(900, 1),
    BURGER(1500, 1),
    PASTA(1800, 2);

    private final int priceCents;
    private final int prepUnits;

    MenuItem(int priceCents, int prepUnits) {
        this.priceCents = priceCents;
        this.prepUnits = prepUnits;
    }

    /** Display name, e.g. "Salad". */
    public String getName() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getPriceCents() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }

    public int getPrepUnits() {
        // TODO
        throw new UnsupportedOperationException("TODO");
    }
}
