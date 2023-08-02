package vendingMachineSimulator;

import java.util.ArrayList;

/**
 * Represents a special vending machine that can create custom special items.
 * Inherits from RegularVendingMachine.
 * @author Vance Gyan M. Robles
 */
public class SpecialVendingMachine extends RegularVendingMachine {
    /** Stores the special item templates available in the vending machine.**/
    private ArrayList<SpecialItem> specialItemTemplates;

    /**
     * Creates a new SpecialVendingMachine with the given item samples and special item templates.
     * @param itemSamples The list of item samples available in the vending machine.
     * @param specialItemTemplates The list of special item templates available in the vending machine.
     */
    public SpecialVendingMachine(ArrayList<Item> itemSamples, ArrayList<SpecialItem> specialItemTemplates) {
        super(itemSamples);
        this.specialItemTemplates = specialItemTemplates;
    }

    /**
     * Creates a custom special item based on the provided template index and lists of base and addon item indexes.
     * @param templateIndex The index of the special item template to use.
     * @param baseIndexes The indexes of the base items to use in the special item.
     * @param addonIndexes The indexes of the addon items to use in the special item.
     * @return The created custom special item.
     */
    public SpecialItem createCustomItem(int templateIndex, ArrayList<Integer> baseIndexes, ArrayList<Integer> addonIndexes){

        SpecialItem customItem = specialItemTemplates.get(templateIndex).clone();

        for (int i : baseIndexes){
            customItem.addBase(itemSamples.get(i));
            dispenseItem(i);
        }

        for (int i : addonIndexes){
            customItem.addAddon(itemSamples.get(i));
            dispenseItem(i);
        }

        return customItem;
    }

    /**
     * Retrieves the price of the special item template at the specified index.
     * @param index The index of the special item template.
     * @return The price of the special item template.
     */
    public double getItemTemplatePrice(int index){
        return this.specialItemTemplates.get(index).getPrice();
    }

    /**
     * Updates the special vending machine's purchase history with the details of a special item purchase.
     * @param insertedCash The amount of cash inserted by the customer.
     * @param templateIndex The index of the special item template purchased.
     * @param change The change provided to the customer.
     * @param baseIndexes The indexes of the base items used in the special item.
     * @param addonIndexes The indexes of the addon items used in the special item.
     */
    public void updateSpecialPurchaseHistory(double insertedCash, int templateIndex,  double change, ArrayList<Integer> baseIndexes, ArrayList<Integer> addonIndexes){

        String itemDetails = "";
        for(int base : baseIndexes){
            itemDetails += itemSamples.get(base).getName() + " ";
        }
        for (int addons : addonIndexes){
            itemDetails += itemSamples.get(addons).getName() + " ";
        }
        String transaction = "User purchased " + specialItemTemplates.get(templateIndex).getName() + " with contents " + itemDetails + ", given P" + insertedCash + " with change P" + change +", total funds as of this purchase: " + machineWallet.getTotal();
        this.machineHistory.add(transaction);
    }

}
