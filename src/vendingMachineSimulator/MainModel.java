package vendingMachineSimulator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.TreeMap;

/**
 * Serves as the main model to interact with the different parts of the vending machine
 * @author Vance Gyan M. Robles
 */
public class MainModel {

    /**
     * <p>The current vending machine to be tested/used.</p>
     */
    private RegularVendingMachine currentVM;
    /**
     * <p>Holds the test items for RegularVendingMachine.</p>
     */
    private ArrayList<Item> itemSamples = new ArrayList<Item>();
    private ArrayList<SpecialItem> customItemTemplates = new ArrayList<SpecialItem>();
    /**
     * <p>Accesses the vending feature of the Vending Machine.</p>
     */
    private Customer currentCustomer = new Customer();
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
        itemSamples.add(new Item("Chasu Pork", 301, 54.00, true));
        itemSamples.add(new Item("Shio Broth", 65, 30.00, false));
        itemSamples.add(new Item("Miso Broth", 78, 35.00, false));
        itemSamples.add(new Item("Tonkotsu Broth", 58, 42.00, false));
        itemSamples.add(new Item("Ukokkei Broth", 62, 44.00, false));
        itemSamples.add(new Item("Salted Egg", 62, 26.00, false));
        itemSamples.add(new Item("Fried Egg", 67, 21.00, false));
        itemSamples.add(new Item("Chicken Slices", 256, 45.00, false));
        itemSamples.add(new Item("Coconut Milk", 227, 34.00, false));
        itemSamples.add(new Item("Bacon", 283, 42.00, false));
        itemSamples.add(new Item("Tapa", 212, 41.00, false));
        itemSamples.add(new Item("Tocino", 221, 38.00, false));
        itemSamples.add(new Item("Nuts", 85, 10.00, false));
        itemSamples.add(new Item("Nata de Coco", 120, 12.00, false));
        itemSamples.add(new Item("Macapuno", 137, 12.00, false));
        itemSamples.add(new Item("Leche Flan", 211, 15.00, false));
        itemSamples.add(new Item("Banana Slices", 89, 10.00, false));
        itemSamples.add(new Item("Langka", 167, 10.00, false));
        itemSamples.add(new Item("Black Garlic Oil", 69, 5.00, false));

        customItemTemplates.add(new SpecialItem("Ramen", 380.00,2, 1, 3));
        customItemTemplates.add(new SpecialItem("Silog Meal", 105.00,2, 1, 2));
        customItemTemplates.add(new SpecialItem("Halo-halo", 95.50,2, 1, 5));
    }

    /**
     * Creates a new regular vending machine using the provided item samples.
     */
    public void createRegularVM(){
        currentVM = new RegularVendingMachine(itemSamples);
    }

    /**
     * Creates a new special vending machine using the provided item samples and custom item templates.
     */
    public void createSpecialVM(){ currentVM = new SpecialVendingMachine(itemSamples, customItemTemplates); }

    /**
     * Checks if a vending machine has been created.
     * @return True if a vending machine has been created, false otherwise.
     */
    public boolean hasCreatedVM(){
        if (currentVM != null)
            return true;

        return false;
    }

    /**
     * Retrieves a list of item names from the item samples.
     * @return An ArrayList containing the names of the items.
     */
    public ArrayList<String> getItemNameList(){

        int i = 0;

        ArrayList<String> itemNames = new ArrayList<String>();
        for (Item item : itemSamples){
            itemNames.add(item.getName());
        }

        return itemNames;
    }

    /**
     * Retrieves a list of item prices from the purchasable item samples.
     * @return An ArrayList containing the prices of the purchasable items.
     */
    public ArrayList<Double> getItemPriceList(){

        int i = 0;

        ArrayList<Double> itemPrices = new ArrayList<>();
        for (Item item : itemSamples){
            if (itemSamples.get(i).isPurchasable())
                itemPrices.add(item.getPrice());
            i++;
        }

        return itemPrices;
    }

    /**
     * Retrieves a list of item calories from the item samples.
     * @return An ArrayList containing the calories of the items.
     */
    public ArrayList<Integer> getItemCalorieList(){

        int i = 0;

        ArrayList<Integer> itemCalories = new ArrayList<>();
        for (Item item : itemSamples){
            itemCalories.add(item.getCalories());
        }

        return itemCalories;

    }

    /**
     * Retrieves a list of item amounts available in the vending machine.
     * @return An ArrayList containing the amounts of the items.
     */
    public ArrayList<Integer> getItemAmountList(){

        int i = 0;

        ArrayList<Integer> itemAmounts = new ArrayList<>();
        for (Item item : itemSamples){
            itemAmounts.add(currentVM.getItemAmount(i));
            i++;
        }

        return itemAmounts;

    }

    /**
     * Retrieves a list of custom item template names from the custom item templates.
     * @return An ArrayList containing the names of the custom item templates.
     */
    public ArrayList<String> getTemplateNames(){

        ArrayList<String> templateNames = new ArrayList<>();
        for (SpecialItem template : customItemTemplates){
            templateNames.add(template.getName());
        }

        return templateNames;
    }

    /**
     * Retrieves a list of custom item template prices from the custom item templates.
     * @return An ArrayList containing the prices of the custom item templates.
     */
    public ArrayList<Double> getTemplatePrices(){

        ArrayList<Double> templatePrices = new ArrayList<>();

        for (Item template : customItemTemplates){
            templatePrices.add(template.getPrice());
        }

        return templatePrices;
    }

    /**
     * Retrieves the vending machine's history.
     * @return An ArrayList containing the history records of the vending machine (String entries).
     */
    public ArrayList<String> getMachineHistory(){
        return currentVM.getMachineHistory();
    }

    /**
     * Retrieves the price of an item at the specified index in the vending machine.
     * @param index The index of the item.
     * @return The price of the item.
     */
    public double getItemPrice(int index){
        return currentVM.getItemPrice(index);
    }

    /**
     * Retrieves the stock amount of an item at the specified index in the vending machine.
     * @param index The index of the item.
     * @return The stock amount of the item.
     */
    public int getItemStock(int index){
        return currentVM.getItemAmount(index);
    }

    /**
     * Changes the price of an item at the specified index in the vending machine.
     * @param index The index of the item.
     * @param newPrice The new price of the item.
     */
    public void changeItemPrice(int index, double newPrice){
        currentManager.changeItemPrice(currentVM, index, newPrice);
    }

    /**
     * Adds one unit of stock to an item at the specified index in the vending machine.
     * @param index The index of the item.
     * @return True if the stock addition was successful, false otherwise.
     */
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

    /**
     * Withdraws all the funds from the vending machine.
     * @return The total amount of funds withdrawn.
     */
    public double withdrawAll(){
        double withdrawnFunds = currentManager.withdrawFunds(currentVM);
        return withdrawnFunds;
    }

    /**
     * Withdraws all the funds from the vending machine.
     * @return The total amount of funds withdrawn.
     */
    public ArrayList<String> getItemInformation(){
        ArrayList<String> itemInformation = new ArrayList<String>();
        for (int i = 0; i < itemSamples.size(); i++)
            itemInformation.add(currentVM.getItemName(i) + "," + currentVM.getItemAmount(i) + "," + currentVM.getItemPrice(i) + "," + currentVM.getItemCalories(i));

        return itemInformation;
    }

    /**
     * Retrieves information about the denominations in the vending machine's money storage.
     * @return An ArrayList containing the amount of each denomination.
     */
    public ArrayList<Integer> getMoneyInformation(){
        return currentVM.getDenominationAmounts();
    }

    /**
     * Retrieves the amount of a given denomination
     * @param denomIndex The index of the denomination.
     * @return The amount of the denomination.
     */
    public int getDenomAmount(int denomIndex){
        return currentManager.getDenomAmt(currentVM, denomIndex);
    }

    /**
     * Adds a specified amount to a denomination
     * @param denomIndex The index of the denomination.
     * @param amountToAdd The amount to add to the denomination.
     */
    public void addDenom(int denomIndex, int amountToAdd){
        currentManager.addDenominations(currentVM, denomIndex, amountToAdd);
    }

    /**
     * Adds a specified amount to the user's wallet.
     * @param key The amount to add to the user's wallet.
     */
    public void addToUserWallet(double key){
        currentCustomer.addCash(key);
    }

    /**
     * Resets the user's wallet by setting its balance to zero.
     */
    public void resetUserWallet(){
        currentCustomer.resetWallet();
    }

    /**
     * Purchases an item at the specified index from the vending machine.
     * @param itemIndex The index of the item to purchase.
     * @return A message indicating the status of the purchase.
     */
    public String purchaseItem(int itemIndex){
        return currentCustomer.purchaseItem(currentVM, itemIndex);
    }

    /**
     * Purchases a special item using the specified template index, base item indexes, and addon item indexes.
     * @param templateIndex The index of the special item template to use.
     * @param baseIndexes The indexes of the base items to use in the special item.
     * @param addonIndexes The indexes of the addon items to use in the special item.
     * @return A message indicating the status of the purchase.
     */
    public String purchaseSpecialItem(int templateIndex, ArrayList<Integer> baseIndexes, ArrayList<Integer> addonIndexes){

        String message = "";

        if (customItemTemplates.get(templateIndex) == null){
            message = "INVALID NUMBER";
        }
        else {
            message =  currentCustomer.purchaseSpecialItem((SpecialVendingMachine) currentVM, templateIndex, baseIndexes, addonIndexes);
        }

        return message;

    }

    /**
     * Checks if the user can purchase a special item with the specified template index.
     * @param templateIndex The index of the special item template.
     * @return True if the user can purchase the special item, false otherwise.
     */
    public boolean canPurchaseSpecialItem(int templateIndex){
        if(currentCustomer.getUserWalletTotal() < customItemTemplates.get(templateIndex).getPrice())
            return false;

        return true;
    }

    /**
     * Retrieves the type of the current vending machine.
     * @return The type of the current vending machine.
     */
    public String getCurrentVMType(){
        return currentVM.getClass().getName();
    }

    /**
     * Retrieves the message for withdrawing cash (the machine returning the inserted cash to the user)
     * @return The corresponding message containing denominations and their amounts
     */
    public String getWithdrawMessage(){
        return currentCustomer.getWithdrawMessage();
    }

}