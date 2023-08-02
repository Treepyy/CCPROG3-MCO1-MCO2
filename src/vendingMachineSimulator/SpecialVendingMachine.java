package vendingMachineSimulator;

import java.util.ArrayList;

public class SpecialVendingMachine extends RegularVendingMachine {

    private ArrayList<SpecialItem> specialItemTemplates;

    public SpecialVendingMachine(ArrayList<Item> itemSamples, ArrayList<SpecialItem> specialItemTemplates) {
        super(itemSamples);
        this.specialItemTemplates = specialItemTemplates;
    }

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

    public double getItemTemplatePrice(int index){
        return this.specialItemTemplates.get(index).getPrice();
    }

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
