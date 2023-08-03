package vendingMachineSimulator;

/**
 * An item to be sold in a vending machine.
 * @author Vance Gyan M. Robles
 */

public class Item {
    /**
     * <p>Name of the item.</p>
     */
    protected String name;

    /**
     * <p>Number of calories in one serving of the item.</p>
     */
    protected int calories;
    /**
     * <p>Price of the item when being sold at a vending machine.</p>
     */
    protected double price;

    /**
     * <p>Determines if the item can be purchased (on its own) or not</p>
     */
    protected boolean isPurchasable;

    /**
     * <p>Constructs a new Item object with the given parameters.</p>
     *
     * @param name     the name of the item
     * @param calories the number of calories in the item
     * @param price    the price of the item
     * @param isPurchasable boolean value determining if the item can be purchased (on its own) or not
     */
    public Item(String name, int calories, double price, boolean isPurchasable) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.isPurchasable = isPurchasable;
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

    public boolean isPurchasable() { return isPurchasable; }

    /**
     * <p>Returns the number of calories in the item.</p>
     *
     * @return the number of calories in the item
     */
    public int getCalories() {
        return calories;
    }

}
