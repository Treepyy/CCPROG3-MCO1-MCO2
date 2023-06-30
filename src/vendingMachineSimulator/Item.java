package vendingMachineSimulator;

/**
 * An item to be sold in a vending machine.
 * @author Vance Gyan M. Robles
 */

public class Item {
    /**
     * <p>Name of the item.</p>
     */
    private String name;
    /**
     * <p>Amount of the item currently being held.</p>
     */
    private int amount;
    /**
     * <p>Number of calories in one serving of the item.</p>
     */
    private int calories;
    /**
     * <p>Price of the item when being sold at a vending machine.</p>
     */
    private double price;

    /**
     * <p>Constructs a new Item object with the given parameters.</p>
     *
     * @param name     the name of the item
     * @param amount   the amount of the item
     * @param calories the number of calories in the item
     * @param price    the price of the item
     */
    public Item(String name, int amount, int calories, double price) {
        this.name = name;
        this.amount = amount;
        this.calories = calories;
        this.price = price;
    }

    /**
     * <p>Returns the price of the item.</p>
     *
     * @return the price of the item
     */
    public double getPrice() {
        return price;
    }

    /**
     * <p>Sets the price of the item.</p>
     *
     * @param price the new price of the item
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * <p>Returns the name of the item.</p>
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Returns the amount of the item.</p>
     *
     * @return the amount of the item
     */
    public int getAmount() {
        return amount;
    }

    /**
     * <p>Sets the amount of the item.</p>
     *
     * @param amount the new amount of the item
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * <p>Returns the number of calories in the item.</p>
     *
     * @return the number of calories in the item
     */
    public int getCalories() {
        return calories;
    }

}
