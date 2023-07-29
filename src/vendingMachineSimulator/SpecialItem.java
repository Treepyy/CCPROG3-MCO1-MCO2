package vendingMachineSimulator;

import java.util.ArrayList;

public class SpecialItem extends Item implements Cloneable{

    ArrayList<Item> baseItemList; // base items are required, the user cannot order a special item without the base
    ArrayList<Item> addonItemList; // addons are optional, the user can choose to skip adding addons, or add ones up to a limit
    int baseItemRequirement; // The amount of base items needed to create the special item
    int addonItemRequirement; // The amount of addons needed to create the special item (at least)
    int addonItemLimit; // The upper limit of the amount of addons the user can add (at most)

    SpecialItem(String name, int baseItemRequirement, int addonItemRequirement, int addonItemLimit){
        super(name,0, 0, true);
        this.baseItemRequirement = baseItemRequirement;
        this.addonItemRequirement = addonItemRequirement;
        this.addonItemLimit = addonItemLimit;
        this.calories = getBaseCalories() + getAddonCalories();
        this.price = getBasePrice() + getBasePrice();
    }
    
    private int getAddonCalories(){
        int totalAddonCalories = 0;
        for (Item i : addonItemList){
            totalAddonCalories += i.getCalories();
        }

        return totalAddonCalories;
    }

    private int getBaseCalories(){
        int totalBaseCalories = 0;
        for (Item i : baseItemList){
            totalBaseCalories += i.getCalories();
        }

        return totalBaseCalories;
    }

    private double getBasePrice(){

        double totalBasePrice = 0;
        for (Item i : baseItemList){
            totalBasePrice += i.getPrice();
        }

        return totalBasePrice;

    }

    private double getAddonPrice(){

        double totalAddonPrice = 0;
        for (Item i : baseItemList){
            totalAddonPrice += i.getPrice();
        }

        return totalAddonPrice;

    }

    @Override
    public int getCalories() {
        return getBaseCalories() + getAddonCalories();
    }

    @Override
    public double getPrice(){
        return getBasePrice() + getAddonPrice();
    }

    public void addBase(Item baseItem){
        baseItemList.add(baseItem);
    }

    public void addAddon(Item addonItem){
        addonItemList.add(addonItem);
    }

    @Override
    public SpecialItem clone() {

        try {
            SpecialItem clonedItem = (SpecialItem) super.clone();
            return clonedItem;

        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    /*

    convert item handling to instantiated
    ItemSlots[][] = {item1[10], item2[10], ... , itemN[10]};

    special items should have a base(needed) and toppings(optional, 1..4)
    implement with priority from bottom to top

    get images needed for gui, design vending machine interface
    adjust code to account for special vending machines and special items

     */
}
