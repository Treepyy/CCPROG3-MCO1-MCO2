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

    public String dispenseItem(ArrayList<String> preparationMessages){

        return null;
    }

    public double getItemTemplatePrice(int index){
        return this.specialItemTemplates.get(index).getPrice();
    }

    public void updateSpecialPurchaseHistory(double insertedCash, int templateIndex,  double change, ArrayList<Integer> baseIndexes, ArrayList<Integer> addonIndexes){

    }

}
