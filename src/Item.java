public class Item {

    private final String name;
    private int amount;
    private final int calories;
    private double price;

    public Item(String name, int amount, int calories, double price) {
        this.name = name;
        this.amount = amount;
        this.calories = calories;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCalories() {
        return calories;
    }

}
