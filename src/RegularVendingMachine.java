import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
public class RegularVendingMachine {

    private Item[] itemSlots = new Item[8];
    private Wallet machineWallet = new Wallet();
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
    public Wallet getMachineWallet() {
        return machineWallet;
    }
    public double getCurrentFunds(){

        double currentFunds = 0;

        for (double key : machineWallet.getDenominations().keySet()) {
            int count = machineWallet.getDenominations().get(key);
            double amount = key * count;
            currentFunds += amount;
        }

        return currentFunds;
    }

    public void withdrawCurrentFunds(){
        machineWallet.withdrawAll();
    }

    public void insertMachineFunds(double key, int amount){
        machineWallet.insertDenomination(key, amount);
    }

    public void dispenseItem(int itemIndex){
        int newAmount = itemSlots[itemIndex].getAmount() - 1;
        System.out.println("Successfully dispensed!");
        System.out.println("Received: 1x " + itemSlots[itemIndex].getName());
        itemSlots[itemIndex].setAmount(newAmount);
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
        
        String transaction = "User purchased " + itemName + ", given P" + givenMoney + " with change P" + givenChange +", total funds as of this purchase: " + totalFunds;
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
        for (Double i : machineWallet.getDenominations().keySet()) {
            System.out.println("Denomination: " + i + ", Amount: " + machineWallet.getDenominations().get(i));
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
        for (String record : purchaseHistory){
            System.out.println(record);
        }
    }

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
                        denomination *= 10;
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
