package vendingMachineSimulator;

import java.util.ArrayList;

public class SpecialVendingMachine extends RegularVendingMachine {

    private ArrayList<SpecialItem> specialItemTemplates;

    public SpecialVendingMachine(ArrayList<Item> itemSamples, ArrayList<SpecialItem> specialItemTemplates) {
        super(itemSamples);
        this.specialItemTemplates = specialItemTemplates;
    }

    // returns true if item creation was successful
    public boolean createCustomItem(int templateIndex, ArrayList<Integer> baseIndexes, ArrayList<Integer> addonIndexes){

        SpecialItem customItem = specialItemTemplates.get(templateIndex).clone();

        for (int i : baseIndexes){

            if(getItemAmount(i) == 0){
                return false;
            }

            else{
                customItem.addBase(itemSamples.get(i));
                itemSlots[i].pop();
            }

        }

        for (int i : addonIndexes){

            if(getItemAmount(i) == 0){
                return false;
            }

            else {
                customItem.addAddon(itemSamples.get(i));
                itemSlots[i].pop();
            }

        }

        return true;
    }

    public String dispenseItem(ArrayList<String> preparationMessages){

        return null;
    }

}
