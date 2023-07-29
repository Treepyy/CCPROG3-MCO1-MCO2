package vendingMachineSimulator;

import java.util.ArrayList;

public class SpecialItem extends Item{

    Item baseItem;
    ArrayList<Item> addonItemList;

    SpecialItem(String name, int amount, double price, Item baseItem, ArrayList<Item> addonItemList){
        super(name,0, price);
        this.baseItem = baseItem;
        this.addonItemList = addonItemList;
    }
    
    private int getAddonCalories(){
        int totalAddonCalories = 0;
        for (Item i : addonItemList){
            totalAddonCalories += i.getCalories();
        }

        return totalAddonCalories;
    }
    
    @Override
    public int getCalories() {
        return baseItem.getCalories() + getAddonCalories();
    }

    public void addAddon(Item addonItem){
        addonItemList.add(addonItem);
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
