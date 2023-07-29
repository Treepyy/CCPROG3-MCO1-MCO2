package vendingMachineSimulator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

// TODO: Convert item amount to instantiation (not set number), implement SpecialVendingMachine and SpecialItem, ~~finish addDemon~~,

/**
 * Serves as the main hub to access the two main features of the Vending Machine simulator.
 * @author Vance Gyan M. Robles
 */
public class MainModel {

    /**
     * <p>Scanner for getting user inputs.</p>
     */
    private Scanner input = new Scanner(System.in);
    /**
     * <p>The current vending machine to be tested/used.</p>
     */
    private RegularVendingMachine currentVM;
    /**
     * <p>Holds the test items for RegularVendingMachine.</p>
     */
    private Item[] defaultItems = new Item[8];
    private ArrayList<Item> itemSamples = new ArrayList<Item>();
    private ArrayList<SpecialItem> customItemTemplates = new ArrayList<SpecialItem>();
    /**
     * <p>Accesses the vending feature of the Vending Machine.</p>
     */
    private Customer currentCustomer;
    /**
     * <p>Accesses the maintenance feature of the Vending Machine.</p>
     */
    private Maintenance currentManager = new Maintenance();

    public MainModel(){
        itemSamples.add(new Item("Bread",207, 30.00, true));
        itemSamples.add(new Item("Ube Ice Cream",146, 45.50, true));
        itemSamples.add(new Item("Plain Rice", 130, 25.00, true));
        itemSamples.add(new Item("Java Rice", 136, 32.00, true));
        itemSamples.add(new Item("Soba Noodles", 99, 55.50, true));
        itemSamples.add(new Item("Udon Noodles",124, 50.00, true));
        itemSamples.add(new Item("Fried Tofu",77, 27.50, true));
        itemSamples.add(new Item("Fish Cake", 201, 32.00, true));
        itemSamples.add(new Item("Shio Broth", 65, 30.00, false));
        itemSamples.add(new Item("Miso Broth", 78, 35.00, false));
        itemSamples.add(new Item("Tonkotsu Broth", 58, 42.00, false));
        itemSamples.add(new Item("Ukokkei Broth", 62, 44.00, false));
        itemSamples.add(new Item("Salted Egg", 62, 26.00, false));
        itemSamples.add(new Item("Fried Egg", 67, 21.00, false));
        itemSamples.add(new Item("Chasu Pork", 301, 54.00, false));
        itemSamples.add(new Item("Chicken Slices", 256, 45.00, false));
        itemSamples.add(new Item("Ham", 227, 34.00, false));
        itemSamples.add(new Item("Bacon", 283, 42.00, false));
        itemSamples.add(new Item("Tapa", 212, 41.00, false));
        itemSamples.add(new Item("Tocino", 221, 38.00, false));
        itemSamples.add(new Item("Nuts", 85, 10.00, false));
        itemSamples.add(new Item("Nata de coco", 120, 12.00, false));
        itemSamples.add(new Item("Macapuno", 137, 12.00, false));
        itemSamples.add(new Item("Leche Flan", 211, 15.00, false));
        itemSamples.add(new Item("Banana Slices", 89, 10.00, false));

        customItemTemplates.add(new SpecialItem("Ramen",2, 1, 3));
        customItemTemplates.add(new SpecialItem("Silog Meal",2, 1, 2));
        customItemTemplates.add(new SpecialItem("Halo-halo",2, 1, 5));
    }

    public void createRegularVM(){
        currentVM = new RegularVendingMachine(itemSamples);
    }

    public void createSpecialVM(){ currentVM = new SpecialVendingMachine(itemSamples, customItemTemplates); }

    public boolean hasCreatedVM(){
        if (currentVM != null)
            return true;

        return false;
    }

    public ArrayList<String> getItemNameList(){

        ArrayList<String> itemNames = new ArrayList<String>();
        for (Item item : itemSamples){
            itemNames.add(item.getName());
        }

        return itemNames;
    }

    public ArrayList<String> getMachineHistory(){
        return currentVM.getMachineHistory();
    }

    public double getItemPrice(int index){
        return currentVM.getItemPrice(index);
    }

    public int getItemStock(int index){
        return currentVM.getItemAmount(index);
    }

    public void changeItemPrice(int index, double newPrice){
        currentManager.changeItemPrice(currentVM, index, newPrice);
    }

    public boolean addItemStock(int index){
        if ((currentVM.getItemAmount(index) + 1) > 10){
            return false;
        }
        else{
            int previousStock = currentVM.getItemAmount(index);
            currentVM.addItemStock(index, 1);
            currentVM.updateStockHistory(index, 1, previousStock);
            return true;
        }
    }

    public double withdrawAll(){
        double withdrawnFunds = currentManager.withdrawFunds(currentVM);
        return withdrawnFunds;
    }

    public ArrayList<String> getItemInformation(){
        ArrayList<String> itemInformation = new ArrayList<String>();
        for (int i = 0; i < itemSamples.size(); i++)
            itemInformation.add(currentVM.getItemName(i) + "," + currentVM.getItemAmount(i) + "," + currentVM.getItemPrice(i) + "," + currentVM.getItemCalories(i));

        return itemInformation;
    }

    public ArrayList<Integer> getMoneyInformation(){
        return currentVM.getDenominationAmounts();
    }

    public int getDenomAmount(int denomIndex){
        return currentManager.getDenomAmt(currentVM, denomIndex);
    }

    public void addDenom(int denomIndex, int amountToAdd){
        currentManager.addDenominations(currentVM, denomIndex, amountToAdd);
    }

}