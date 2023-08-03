package vendingMachineSimulator;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Deque;
import java.util.LinkedList;

/**
 * A regular vending machine, can hold up to 40 item slots with a max capacity of 10 each. Supports denomination management through a Wallet and machine history recording.
 * @author Vance Gyan M. Robles
 */
public class RegularVendingMachine {
    private final int maxItemSlots = 40;
    protected ArrayList<Item> itemSamples = new ArrayList<>();
    /**
     * <p>Holds the items available for purchase.</p>
     */
    protected Deque<Item>[] itemSlots = new LinkedList[maxItemSlots];

    /**
     * <p>Wallet which holds the funds of the machine.</p>
     */
    protected Wallet machineWallet = new Wallet();
    /**
     * <p>ArrayList for keeping the history of machine transactions.</p>
     */
    protected ArrayList<String> machineHistory = new ArrayList<String>();

    /**
     * <p>Constructs a new RegularVendingMachine object with the given parameters.</p>
     *
     * @param itemSamples the Item array which contains a sample of the products of the vending machine, the information of each item will be used for the stock
     */
    public RegularVendingMachine(ArrayList<Item> itemSamples){
        // initializes the stacks
        for (int i = 0; i < maxItemSlots; i++) {
            itemSlots[i] = new LinkedList<>();
        }
        this.itemSamples = itemSamples;
    }

    /**
     * <p>Returns the price of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the price of the item
     */
    public double getItemPrice(int itemIndex){ return itemSamples.get(itemIndex).getPrice(); }

    /**
     * <p>Returns the name of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the name of the item
     */
    public String getItemName(int itemIndex) { return itemSamples.get(itemIndex).getName(); }

    /**
     * <p>Returns the amount value of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the amount of the item
     */
    public int getItemAmount(int itemIndex) {
        return itemSlots[itemIndex].size();
    }

    /**
     * <p>Returns the calories of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the calories of the item
     */
    public double getItemCalories(int itemIndex) { return itemSamples.get(itemIndex).getCalories(); }

    /**
     * <p>Returns the amount of a given denomination in the machineWallet.</p>
     *
     * @param key the denomination key to find the amount of
     * @return the amount of the denomination
     */
    public int getDenominationAmount(double key) { return machineWallet.getDenominations().get(key); }

    /**
     * <p>Sets the price of an item in an item slot.</p>
     *
     * @param itemIndex the index of the item to be modified
     * @param newPrice the new price of the item
     */
    public void setItemPrice(int itemIndex, double newPrice){

        // sets the price of the item within the samples to the new price so that new items created that are of this type will have the updated price
        itemSamples.get(itemIndex).setPrice(newPrice);

        // then, sets the price of all already created items to the new price
        for (Item item : itemSlots[itemIndex]){
            System.out.println(item.getPrice());
        }
    }

    /**
     * <p>Returns the total amount of the denominations in the machineWallet</p>
     *
     * @return the total funds in the machineWallet
     */
    public double getCurrentFunds(){ return machineWallet.getTotal(); }

    /**
     * <p>Withdraws all funds from the machineWallet</p>
     */
    public void withdrawCurrentFunds(){ machineWallet.withdrawAll(); }

    /**
     * <p>Inserts a certain amount of a given denomination into the machineWallet</p>
     *
     * @param key the denomination value to add
     * @param amount the amount of bills/coins to add
     */
    public void insertMachineFunds(double key, int amount){ machineWallet.insertDenomination(key, amount); }

    /**
     * <p>Adds the denominations of a given Wallet into the machineWallet</p>
     *
     * @param addedWallet the wallet whose denominations are to be added to the machine
     */
    public void addFunds(Wallet addedWallet){ machineWallet.addCash(addedWallet); }

    /**
     * <p>Subtracts the denominations of a given Wallet from the machineWallet</p>
     *
     * @param subtractedWallet the wallet whose denominations are to be subtracted from the machine
     */
    public void subtractFunds(Wallet subtractedWallet){ machineWallet.subtractCash(subtractedWallet);}

    /**
     * <p>Subtracts the denominations of a given double value from the machineWallet</p>
     *
     * @param cash the double value to subtract from the machineWallet
     */
    public void subtractFunds(double cash){ machineWallet.subtractCash(cash); }

    /**
     * <p>Dispenses an item from the machine given its index (pops the corresponding item in the item stack)</p>
     *
     * @param itemIndex the index of the item to be dispensed
     */
    public String dispenseItem(int itemIndex){
        itemSlots[itemIndex].pop();
        return "Successfully dispensed!\nReceived: 1x " + itemSamples.get(itemIndex).getName();

    }

    /**
     * <p>Adds a given amount of items into the item slot (pushes the item into the stack)</p>
     *
     * @param itemIndex the index of the item to be restocked
     * @param addedAmount the amount of items to be added
     */
    public void addItemStock(int itemIndex, int addedAmount){
        for (int i = 0; i < addedAmount; i++){
            itemSlots[itemIndex].push(itemSamples.get(itemIndex));
        }
    }

    /**
     * <p>Validates a converted change treemap to see if the machine has enough denominations to return it to the user</p>
     *
     * @param change is the treemap of a given double change value
     * @return true if valid (no negative values after map subtraction), false if not
     */
    public boolean hasEnoughDenominations(TreeMap<Double, Integer> change){

        // should return false if there is at least 1 negative value for a treemap obtained after subtracting each denomination, else returns true
        for (double key : machineWallet.getDenominations().descendingMap().keySet()){
            int denominationsRemaining = machineWallet.getDenominations().get(key) - change.get(key);
            if (denominationsRemaining < 0){
                return false;
            }
        }
        
        return true;
    }

    /**
     * <p>Creates a summary of a user's transaction and adds it into the machineHistory ArrayList</p>
     *
     * @param insertedCash is the amount of cash given by the user
     * @param itemIndex is the itemIndex of the item dispensed
     * @param change is the change received by the user
     */
    public void updatePurchaseHistory(double insertedCash, int itemIndex, double change){
        String itemName = itemSamples.get(itemIndex).getName();
        String givenMoney = Double.toString(insertedCash);
        String givenChange = Double.toString(change);
        String totalFunds = Double.toString(getCurrentFunds());
        
        String transaction = "User purchased " + itemName + ", given P" + givenMoney + " with change P" + givenChange +", total funds as of this purchase: " + totalFunds;
        machineHistory.add(transaction);
    }

    /**
     * <p>Creates a summary of a user's addition to the item stock and adds it into the machineHistory ArrayList</p>
     *
     * @param itemIndex is the itemIndex of the item whose stock has been updated
     * @param addedStock is the amount of items added
     * @param previousStock is the amount of items that the machine had before the action
     */
    public void updateStockHistory(int itemIndex, int addedStock, int previousStock){
        String itemName = itemSamples.get(itemIndex).getName();
        String added = Integer.toString(addedStock);
        String previous = Integer.toString(previousStock);
        String newStock = Integer.toString(getItemAmount(itemIndex));

        String record = "Added " + added + "x " + itemName + " to the machine. " + "Amount went from " + previous + " to " + newStock;
        machineHistory.add(record);

    }
    /**
     * <p>Creates a summary of a user's addition to denomination stock and adds it into the machineHistory ArrayList</p>
     *
     * @param key is the denomination whose stock has been updated
     * @param addedStock is the amount added
     * @param previousStock is the amount that the machine had before the action
     * @param previousTotal is the previous total funds in the machine before the action
     */
    public void updateStockHistory(double key, int addedStock, int previousStock, double previousTotal){
        String denomination = Double.toString(key);
        String added = Integer.toString(addedStock);
        String previous = Integer.toString(previousStock);
        String newStock = Integer.toString(machineWallet.getDenominations().get(key));
        String prevTotal = Double.toString(previousTotal);
        String newTotal = Double.toString(machineWallet.getTotal());

        String record = "Added " + added + "x " + denomination + " denominations to the machine. " + "Amount went from " + previous + " to " + newStock + ", total funds went from " + prevTotal + " to " + newTotal;
        machineHistory.add(record);

    }

    /**
     * <p>Creates a summary of a user's price update and adds it into the machineHistory ArrayList</p>
     *
     * @param itemIndex is the itemIndex of the item whose price has been updated
     * @param oldPrice is the old price of the item
     */
    public void updatePriceHistory(int itemIndex, double oldPrice){
        String item = itemSamples.get(itemIndex).getName();
        String oldPr = Double.toString(oldPrice);
        String newPr = Double.toString(itemSamples.get(itemIndex).getPrice());

        String record = "Updated the price of " + item + " from " + oldPr + " to " + newPr;
        machineHistory.add(record);

    }

    /**
     * <p>Creates a report of a user's machine fund withdrawal (total amount withdrawn) and adds it into the machineHistory ArrayList</p>
     */
    public void updateWithdrawHistory(){
        String record = "User withdrew " + machineWallet.getTotal() + " pesos from the machine." ;
        machineHistory.add(record);

    }

    /**
     * <p>Gets the list containing the denomination amounts for each type in the machine wallet</p>
     *
     * @return ArrayList containing the denomination amounts
     */
    public ArrayList<Integer> getDenominationAmounts(){

        ArrayList<Integer> denomAmounts = new ArrayList<>();

        for (Double i : machineWallet.getDenominations().descendingMap().keySet()){
            denomAmounts.add(machineWallet.getDenominations().get(i));
        }

        return denomAmounts;

    }

    /**
     * <p>Gets the list containing the machine history records</p>
     *
     * @return ArrayList containing the machine history records
     */
    public ArrayList<String> getMachineHistory(){
        return machineHistory;
    }

    /**
     * <p>Displays the change received by the user, distinguishes between coins and bills.</p>
     *
     * @param changeVal is the double value of the change to be given
     * @return a String output summarizing the received coins/bills
     */
    public String displayReceivedChangeMessage(double changeVal){

        String output = "";

        TreeMap<Double, Integer> change = machineWallet.convertToDenominations(changeVal);
        String currencyName, type;

        if (changeVal > 0) {
            for (Map.Entry<Double, Integer> entry : change.entrySet()) {
                double denomination = entry.getKey();
                int count = entry.getValue();
                if (count > 0) {
                    if (entry.getKey() < 1) {
                        denomination *= 100;
                        currencyName = " Centavo ";
                        type = "Coin";
                    } else if (entry.getKey() >= 1 && entry.getKey() < 20) {
                        currencyName = " Peso ";
                        type = "Coin";
                    } else {
                        currencyName = " Peso ";
                        type = "Bill";
                    }
                    output += count + "x " + denomination + currencyName + type + "-";
                }
            }
        }

        return output;
    }

    /**
     * Determines if the given item in the itemIndex is purchasable or not
     *
     * @param itemIndex is the index of the item to get
     * @return the isPurchasable boolean value of the item
     */
    public boolean isItemPurchasable(int itemIndex){
        return itemSamples.get(itemIndex).isPurchasable();
    }

}
