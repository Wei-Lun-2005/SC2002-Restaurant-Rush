package restaurantrush;

public class ComboMeal implements Dish {
    private static final int DISCOUNT_PERCENT = 10;
    private final MenuItem first;
    private final MenuItem second;

    public ComboMeal(MenuItem first, MenuItem second){
        this.first = first;
        this.second = second;
    }

    public String getName(){
        return first.getName() + " and " + second.getName();
    }

    public int getPriceCents(){
        int originalPrice = first.getPriceCents() + second.getPriceCents();
        return (int) Math.round(originalPrice * (100 - DISCOUNT_PERCENT) / 100.0);
    }

    public int getPrepUnits(){
        return first.getPrepUnits() + second.getPrepUnits();
    }
}
