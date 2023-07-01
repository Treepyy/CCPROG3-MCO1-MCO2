package vendingMachineSimulator;

import java.util.TreeMap;
import java.util.Map;

/**
 * Serves as the container and manager of denominations. Uses Philippine Peso denominations from 1 centavo coin to 1000 peso bill.
 * @author Vance Gyan M. Robles
 */
public class Wallet {
    /**
     * <p>The treemap for holding the amount of all denomination types in the wallet. TreeMap is used to preserve order for calculating denominations needed for a given value.</p>
     */
    private TreeMap<Double, Integer> denominations = new TreeMap<Double, Integer>();

    /**
     * <p>Constructs a new Wallet and sets the default denominations.</p>
     * <p>The denominations are then arranged as a treemap to preserve the value hierarchy.</p>
     */
    public Wallet(){
        denominations.put(1000.00, 0);
        denominations.put(500.00, 0);
        denominations.put(200.00, 0);
        denominations.put(100.00, 0);
        denominations.put(50.00, 0);
        denominations.put(20.00, 0);
        denominations.put(10.00, 0);
        denominations.put(5.00, 0);
        denominations.put(1.00, 0);
        denominations.put(0.25, 0);
        denominations.put(0.05, 0);
        denominations.put(0.01, 0);
    }

    /**
     * <p>Returns the denominations of a wallet.</p>
     *
     * @return the denominations tree map
     */
    public TreeMap<Double, Integer> getDenominations() {
        return denominations;
    }

    /**
     * <p>Calculates the total by multiplying each denomination by its amount then adding them together.</p>
     *
     * @return the total amount of cash in the Wallet
     */
    public double getTotal(){

        double total = 0;

        for (double key : denominations.keySet()) {
            int count = denominations.get(key);
            double amount = key * count;
            total += amount;
        }

        return total;
    }

    /**
     * <p>Inserts a specific amount of a certain denomination into the Wallet.</p>
     * <p><b>Precondition: </b>amount is greater than 0, key is a valid denomination</p>
     *
     * @param key is the denomination (bill/coin) to be added
     * @param amount is the number of the denomination(s) to be added
     */
    public void insertDenomination(double key, int amount){
        int newAmount = denominations.get(key) + amount;
        denominations.put(key, newAmount);
    }

    /**
     * <p>Withdraws all cash in the Wallet.</p>
     * <p>Resets all denominations to 0 and displays what the user receives.</p>
     *
     */
    public void withdrawAll(){
        if (getTotal() != 0.0){
            displayReceivedDenominations(denominations);
            resetWallet();
        }
    }

    /**
     * <p>Converts a given double value into a denomination treemap.</p>
     *
     * @param cash is the double value to be converted
     * @return a treemap of the denominations which total to the given cash value
     */
    public TreeMap<Double, Integer> convertToDenominations(double cash){

        TreeMap<Double, Integer> converted = new TreeMap<Double, Integer>();
        converted.put(1000.00, 0);
        converted.put(500.00, 0);
        converted.put(200.00, 0);
        converted.put(100.00, 0);
        converted.put(50.00, 0);
        converted.put(20.00, 0);
        converted.put(10.00, 0);
        converted.put(5.00, 0);
        converted.put(1.00, 0);
        converted.put(0.25, 0);
        converted.put(0.05, 0);
        converted.put(0.01, 0);

        // loops through the entire treemap
        for (Map.Entry<Double, Integer> entry : converted.descendingMap().entrySet()) {
            double denomination = entry.getKey();
            int count = (int) (cash / denomination);
            converted.put(denomination, count);
            cash %= denomination;
        }

        return converted;

    }

    // this method will convert a given double value into denominations, then add it to the treemap
    /**
     * <p>Adds a given Wallet's denominations into this Wallet</p>
     *
     * @param addedWallet is the Wallet whose denominations are to be added
     */
    public void addCash(Wallet addedWallet){

        for (double key : denominations.keySet()) {
            int newValue = denominations.get(key) + addedWallet.getDenominations().get(key);
            denominations.put(key, newValue);
        }

    }

    public void addCash(double cash){

        // loops through the entire treemap
        for (Map.Entry<Double, Integer> entry : denominations.descendingMap().entrySet()) {
            double denomination = entry.getKey();
            int count = (int) (cash / denomination);
            int newCount = entry.getValue() + count;
            denominations.put(denomination, newCount);
            cash %= denomination;
        }

    }

    /**
     * <p>Subtracts a given Wallet's denominations into this Wallet</p>
     * <p><b>Precondition:</b> the resulting denominations when this operation is performed will not have negative values</p>
     *
     * @param subtractedWallet is the Wallet whose denominations are to be subtracted
     */
    public void subtractCash(Wallet subtractedWallet){

        for (double key : denominations.keySet()) {
            int newValue = denominations.get(key) - subtractedWallet.getDenominations().get(key);
            denominations.put(key, newValue);
        }

    }

    /**
     * <p>Subtracts a given Wallet's denominations into this Wallet, given a cash value.</p>
     * <p><b>Precondition:</b> the resulting denominations when this operation is performed will not have negative values</p>
     *
     * @param cash is the double amount to subtract from the Wallet
     */
    public void subtractCash(double cash){

        for (Map.Entry<Double, Integer> entry : denominations.descendingMap().entrySet()) { // Loops through the entire treemap in descending order
            double denomination = entry.getKey();
            int count = (int) (cash / denomination); // Calculate the count of the current denomination which can be subtracted from the cash amount
            int newCount = entry.getValue() - count;
            denominations.put(denomination, newCount); // Updates the count of the current denomination in the denominations map
            cash %= denomination; // Updates the cash amount by taking the modulo of the cash and the current denomination, reducing the cash by the maximum possible count of the current iteration's denomination
        }

    }

    /**
     * <p>Resets all denominations to 0</p>
     *
     */
    public void resetWallet(){
        denominations.replaceAll((k, v) -> 0);
    }

    /**
     * <p>Displays a given denominations treemap as items received.</p>
     * <p>Sorts each denomination into either bills or coins before displaying.</p>
     *
     * @param denominations is the treemap to be displayed as items received
     */
    private void displayReceivedDenominations(TreeMap<Double, Integer> denominations){

        String currencyName, type;
        System.out.println("You Received:");

        for (Map.Entry<Double, Integer> entry : denominations.entrySet()) {
            double denomination = entry.getKey();
            int count = entry.getValue();
            if (count > 0) {
                if (entry.getKey() < 1){
                    denomination *= 100;
                    currencyName = " Centavo ";
                    type = "Coin";
                }
                else if (entry.getKey() >= 1 && entry.getKey() < 20){
                    currencyName = " Peso ";
                    type = "Coin";
                }
                else{
                    currencyName = " Peso ";
                    type = "Bill";
                }
                System.out.println(count + "x " + denomination + currencyName + type);
            }
        }

    }


}
