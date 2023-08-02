package vendingMachineSimulator;

import java.util.ArrayList;

/**
 * Represents a special item in the vending machine.
 * Special items are customizable items that can have base items and optional addons.
 * The user can choose to skip adding addons or add them up to a specified limit.
 * @author Vance Gyan M. Robles
 */
public class SpecialItem extends Item implements Cloneable{

    private ArrayList<Item> baseItemList; // base items are required, the user cannot order a special item without the base
    private ArrayList<Item> addonItemList; // addons are optional, the user can choose to skip adding addons, or add ones up to a limit
    private int baseItemRequirement; // The amount of base items needed to create the special item
    private int addonItemRequirement; // The amount of addons needed to create the special item (at least)
    private int addonItemLimit; // The upper limit of the amount of addons the user can add (at most)


    /**
     * Creates a new SpecialItem with the specified name, price, base item requirement, addon item requirement, and addon item limit.
     * @param name The name of the special item.
     * @param price The price of the special item.
     * @param baseItemRequirement The amount of base items needed to create the special item.
     * @param addonItemRequirement The amount of addons needed to create the special item (at least).
     * @param addonItemLimit The upper limit of the amount of addons the user can add (at most).
     */
    SpecialItem(String name, double price, int baseItemRequirement, int addonItemRequirement, int addonItemLimit){
        super(name,0, price, true);
        this.baseItemList = new ArrayList<>();
        this.addonItemList = new ArrayList<>();
        this.baseItemRequirement = baseItemRequirement;
        this.addonItemRequirement = addonItemRequirement;
        this.addonItemLimit = addonItemLimit;
        this.calories = getBaseCalories() + getAddonCalories();
    }
    
    private int getAddonCalories(){
        int totalAddonCalories = 0;
        try{
            for (Item i : addonItemList){
                totalAddonCalories += i.getCalories();
            }
        }
        catch (NullPointerException e){
            totalAddonCalories = 0;
        }


        return totalAddonCalories;
    }

    private int getBaseCalories(){
        int totalBaseCalories = 0;

        try{
            for (Item i : baseItemList){
                totalBaseCalories += i.getCalories();
            }
        }
        catch (NullPointerException e){
            totalBaseCalories = 0;
        }

        return totalBaseCalories;
    }

    /**
     * Retrieves the total calories of the special item, including both base item calories and addon item calories.
     * @return The total calories of the special item.
     */
    @Override
    public int getCalories() {
        return getBaseCalories() + getAddonCalories();
    }

    /**
     * Retrieves the amount of base items needed to create the special item.
     * @return The amount of base items required.
     */
    public int getBaseItemRequirement() {
        return baseItemRequirement;
    }

    /**
     * Retrieves the amount of addons needed to create the special item (at least).
     * @return The amount of addons required.
     */
    public int getAddonItemRequirement() {
        return addonItemRequirement;
    }

    /**
     * Retrieves the upper limit of the amount of addons the user can add to the special item (at most).
     * @return The addon item limit.
     */
    public int getAddonItemLimit() {
        return addonItemLimit;
    }

    /**
     * Adds a base item to the special item.
     * @param baseItem The base item to add.
     * @return True if the base item was added successfully, false otherwise.
     */
    public boolean addBase(Item baseItem){
        baseItemList.add(baseItem);
        return true;
    }

    /**
     * Adds an addon item to the special item.
     * @param addonItem The addon item to add.
     * @return True if the addon item was added successfully, false if the addon item limit is reached.
     */
    public boolean addAddon(Item addonItem){
        if (addonItemList.size() + 1 > addonItemLimit)
            return false;

        addonItemList.add(addonItem);
        return true;
    }

    /**
     * Creates a clone of the SpecialItem.
     * @return A clone of the SpecialItem.
     */
    @Override
    public SpecialItem clone() {

        try {
            SpecialItem clonedItem = (SpecialItem) super.clone();
            return clonedItem;

        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
