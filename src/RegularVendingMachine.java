import java.util.ArrayList;
import java.util.TreeMap;
public class RegularVendingMachine {

    private Item[] itemSlots = new Item[8];
    private Wallet machineWallet = new Wallet();
    private TreeMap<Double, Integer> denominations = machineWallet.getDenominations();
    private ArrayList<String> purchaseHistory = new ArrayList<String>();
    public RegularVendingMachine(Item[] itemSlots){
        this.itemSlots = itemSlots;
    }
    public Item[] getItemSlots() {
        return itemSlots;
    }
    public void setItemSlots(Item[] itemSlots) {
        this.itemSlots = itemSlots;
    }
    public TreeMap<Double, Integer> getDenominations() {
        return denominations;
    }
    public Wallet getMachineWallet() {
        return machineWallet;
    }
    public void setDenominations(TreeMap<Double, Integer> denominations) {
        this.denominations = denominations;
    }
    public double getCurrentFunds(){

        double currentFunds = 0;

        for (double key : denominations.keySet()) {
            int count = denominations.get(key);
            double amount = key * count;
            currentFunds += amount;
        }

        return currentFunds;
    }

    public void withdrawCurrentFunds(){

        for (double key : denominations.keySet()) {
            denominations.put(key, 0);
        }

    }

    public void dispenseItem(int itemIndex){
        int newAmount = itemSlots[itemIndex].getAmount() - 1;
        System.out.println("Successfully dispensed!");
        System.out.println("Received: 1x " + itemSlots[itemIndex].getName());
        itemSlots[itemIndex].setAmount(newAmount);
    }

    public void addDenominations(double denominationValue, int amountAdded){
        int newAmount = denominations.get(denominationValue) + amountAdded;
        denominations.put(denominationValue, newAmount);
    }

    public void stockItems(int itemIndex, int stock){
        int newStock = itemSlots[itemIndex].getAmount() + stock;
        itemSlots[itemIndex].setAmount(newStock);
    }

    public double purchaseItem(double insertedCash, int itemIndex){
        // returns the change
        if (insertedCash >= itemSlots[itemIndex].getPrice()) {

            double change = insertedCash - itemSlots[itemIndex].getPrice();

            /* if (hasEnoughDenominations(change)){
                updatePurchaseHistory(insertedCash, itemIndex, change);
                return change;
            }

            else
                return insertedCash;*/
        }

        return insertedCash;
    }

    public boolean hasEnoughDenominations(TreeMap<Double, Integer> change){
        // TODO: returns true if there is enough denominations for change, false otherwise
        // should return false if there is at least 1 negative value for a treemap obtained after subtracting each denomination

        for (double key : machineWallet.getDenominations().descendingMap().keySet()){
            int denominationsRemaining = machineWallet.getDenominations().get(key) - change.get(key);
            if (denominationsRemaining < 0){
                return false;
            }
        }
        
        return true;
    }
    public void updatePurchaseHistory(double insertedCash, int itemIndex, double change){
        String itemName = itemSlots[itemIndex].getName();
        String givenMoney = Double.toString(insertedCash);
        String givenChange = Double.toString(change);
        String totalFunds = Double.toString(getCurrentFunds());
        
        String transaction = "User purchased " + itemName + "given " + givenMoney + "with change " + givenChange +". Total funds as of this purchase: " + totalFunds;
        purchaseHistory.add(transaction);
    }
    public void displayInventory(){
        int index = 0;
        System.out.println("Index (ID), Name, Price, Amount in Stock, Calories");
        for (Item i: itemSlots){
            System.out.println(index + " " + i.getName() + " " + i.getPrice() + " " + i.getAmount() + " " + i.getCalories());
            index++;
        }
        System.out.println();
        for (Double i : denominations.keySet()) {
            System.out.println("Denomination: " + i + ", Amount: " + denominations.get(i));
        }
        System.out.println("Current Total Funds in The Machine: " + getCurrentFunds());
    }
    public void displayProducts(){
        int index = 0;

        for (Item product: itemSlots){
            System.out.println("[" + index + "]" + " " + product.getName() + ", Price: P" + product.getPrice() + ", Amount in Stock: " + product.getAmount() + ", Calories: " + product.getCalories());
            index++;
        }

    }
    public void displayPurchaseHistory(){

    }
    
    public TreeMap<Double, Integer> convertToDenominations(double cashMoney){
        // TODO: returns a TreeMap of denomination:amount pairs given an amount of money

        TreeMap<Double, Integer> newDenominations = new TreeMap<Double, Integer>();;
        newDenominations.put(0.01, 0);
        newDenominations.put(0.05, 0);
        newDenominations.put(0.25, 0);
        newDenominations.put(1.00, 0);
        newDenominations.put(5.00, 0);
        newDenominations.put(10.00, 0);
        newDenominations.put(20.00, 0);
        newDenominations.put(50.00, 0);
        newDenominations.put(100.00, 0);
        newDenominations.put(200.00, 0);
        newDenominations.put(500.00, 0);
        newDenominations.put(1000.00, 0);

        return newDenominations;
    }






}
