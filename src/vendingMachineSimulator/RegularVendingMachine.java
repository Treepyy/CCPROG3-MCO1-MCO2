package vendingMachineSimulator;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * A regular vending machine, can hold up to 8 item slots with a max capacity of 10 each. Supports denomination management through a Wallet and machine history recording.
 * @author Vance Gyan M. Robles
 */
public class RegularVendingMachine {
    /**
     * <p>Holds the items available for purchase.</p>
     */
    private Item[] itemSlots = new Item[8];
    /**
     * <p>Wallet which holds the funds of the machine.</p>
     */
    private Wallet machineWallet = new Wallet();
    /**
     * <p>ArrayList for keeping the history of machine transactions.</p>
     */
    private ArrayList<String> machineHistory = new ArrayList<String>();

    /**
     * <p>Constructs a new RegularVendingMachine object with the given parameters.</p>
     *
     * @param itemSlots the Item array which contains the products of the vending machine
     */
    public RegularVendingMachine(Item[] itemSlots){
        this.itemSlots = itemSlots;
    }

    /**
     * <p>Returns the price of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the price of the item
     */
    public double getItemPrice(int itemIndex){ return itemSlots[itemIndex].getPrice(); }

    /**
     * <p>Returns the name of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the name of the item
     */
    public String getItemName(int itemIndex) { return itemSlots[itemIndex].getName(); }

    /**
     * <p>Returns the amount value of the item in an item slot given its index.</p>
     *
     * @param itemIndex the index of the item to find
     * @return the amount of the item
     */
    public int getItemAmount(int itemIndex) { return itemSlots[itemIndex].getAmount(); }

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
    public void setItemPrice(int itemIndex, double newPrice){ itemSlots[itemIndex].setPrice(newPrice); }

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
     * <p>Dispenses an item from the machine given its index (decrements its amount value by 1 and displays a received message)</p>
     *
     * @param itemIndex the index of the item to be dispensed
     */
    public void dispenseItem(int itemIndex){
        int newAmount = itemSlots[itemIndex].getAmount() - 1;
        System.out.println("Successfully dispensed!");
        System.out.println("Received: 1x " + itemSlots[itemIndex].getName());
        itemSlots[itemIndex].setAmount(newAmount);
    }

    /**
     * <p>Adds a given amount of items into the item slot (increments its amount value by a given value)</p>
     *
     * @param itemIndex the index of the item to be restocked
     * @param addedAmount the amount of items to be added
     */
    public void addItemStock(int itemIndex, int addedAmount){
        int newAmount = itemSlots[itemIndex].getAmount() + addedAmount;

        itemSlots[itemIndex].setAmount(newAmount);
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
        String itemName = itemSlots[itemIndex].getName();
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
        String itemName = itemSlots[itemIndex].getName();
        String added = Integer.toString(addedStock);
        String previous = Integer.toString(previousStock);
        String newStock = Integer.toString(itemSlots[itemIndex].getAmount());

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
        String item = itemSlots[itemIndex].getName();
        String oldPr = Double.toString(oldPrice);
        String newPr = Double.toString(itemSlots[itemIndex].getPrice());

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
     * <p>Displays the inventory of the machine (products and denominations currently in the wallet)</p>
     */
    public void displayInventory(){
        int index = 0;
        System.out.println("\n[Product Inventory]");
        for (Item i: itemSlots){
            System.out.println("ID: " + index + ", Name: " + i.getName() + ", Price: " + i.getPrice() + ", Amount: " + i.getAmount() + ", Calories: " + i.getCalories());
            index++;
        }
        System.out.println("\n[Cash Inventory]");
        for (Double i : machineWallet.getDenominations().keySet()) {
            System.out.println("Denomination: " + i + ", Amount: " + machineWallet.getDenominations().get(i));
        }
        System.out.println("Current Total Funds in The Machine: " + getCurrentFunds());
    }

    /**
     * <p>Displays the available products in the machine along with their associated information.</p>
     */
    public void displayProducts(){
        int index = 0;

        for (Item product: itemSlots){
            System.out.println("[" + index + "]" + " " + product.getName() + ", Price: P" + product.getPrice() + ", Amount in Stock: " + product.getAmount() + ", Calories: " + product.getCalories());
            index++;
        }

    }

    /**
     * <p>Displays all history records of the machine.</p>
     */
    public void displayMachineHistory(){
        if (machineHistory.size() != 0)
            System.out.println("\n[History of Machine Transactions]");
        for (String record : machineHistory){
            System.out.println(record);
        }
    }

    /**
     * <p>Displays the change received by the user, distinguishes between coins and bills.</p>
     *
     * @param changeVal is the double value of the change to be given
     */
    public void displayReceivedChange(double changeVal){

        TreeMap<Double, Integer> change = machineWallet.convertToDenominations(changeVal);
        String currencyName, type;
        if (changeVal > 0) {
            System.out.println("Your Change:");
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
                    System.out.println(count + "x " + denomination + currencyName + type);
                }
            }
        }
    }

}
