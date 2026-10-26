public enum MenuItem {
    SALAD(900, 1),
    BURGER(1500, 1),
    PASTA(1800, 2);

    private final int priceCents;
    private final int prepUnits;

    MenuItem(int priceCents, int prepUnits){
        this.priceCents = priceCents;
        this.prepUnits = prepUnits;
    }
    public String getName(){
        return name();
    }
    
    public int getPriceCents(){
        return priceCents;
    }
    
    public int getPrepUnits(){
        return prepUnits;
    }
}
