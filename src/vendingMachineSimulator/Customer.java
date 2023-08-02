package vendingMachineSimulator;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Represents a customer of the vending machine.
 * The customer can purchase items from both RegularVendingMachine and SpecialVendingMachine.
 * The customer has a wallet that stores denominations to use for purchases.
 * @author Vance Gyan M. Robles
 */

public class Customer {
    /**
     * The Wallet of the user, which stores the current customer's denominations
     */
    private Wallet userWallet = new Wallet();

    /**
     * Purchases a regular item from the RegularVendingMachine at the specified item index.
     * @param current The RegularVendingMachine instance to purchase the item from.
     * @param itemIndex The index of the item to purchase.
     * @return A message indicating the status of the purchase.
     */
    public String purchaseItem(RegularVendingMachine current, int itemIndex){

        System.out.println("TOTAL IN WALLET: " + userWallet.getTotal());
        double currentAmount = userWallet.getTotal();
        if (current.getItemAmount(itemIndex) == 0){ // Checks if the item amount is greater than 0, if not, returns an error message

            if (!current.isItemPurchasable(itemIndex)){ // If item in index is not purchasable, display INVALID NUMBER instead.
                return "INVALID NUMBER";
            }

            return "OUT OF STOCK";
        }
        else{
            double change = userWallet.getTotal() - current.getItemPrice(itemIndex);
            current.addFunds(userWallet); // The denominations inside userWallet is added into the machineWallet.

            if (change >= 0 && current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                // If the machine has enough denominations to provide the change, continues.
                // The product is dispensed from the machine, the change (in denominations) are subtracted from the machine and is given to the user.
                current.dispenseItem(itemIndex);
                current.subtractFunds(change);
                // The userWallet is reset for the next cycle, and a record will be added to the vending machine's purchase history.
                userWallet.resetWallet();
                current.updatePurchaseHistory(currentAmount, itemIndex, change);

                return current.displayReceivedChangeMessage(change);
            }
            else if (change >= 0 && !current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                // If the machine does NOT have enough denominations to provide the change, displays an error and the denominations are returned
                current.subtractFunds(userWallet);
                userWallet.withdrawAll();
                return  "CAN'T GET CHANGE";
            }
            else{
                // Else, if the change value reaches a negative number, displays an insufficient funds message
                current.subtractFunds(userWallet);
                userWallet.withdrawAll();
                return "INSUFFICIENT FUNDS";
            }
        }
    }

    /**
     * Purchases a special item from the SpecialVendingMachine using the specified template index, base item indexes, and addon item indexes.
     * @param currentSP The SpecialVendingMachine instance to purchase the special item from.
     * @param templateIndex The index of the special item template to use.
     * @param baseIndexes The indexes of the base items to use in the special item.
     * @param addonIndexes The indexes of the addon items to use in the special item.
     * @return A message indicating the status of the purchase.
     */
    public String purchaseSpecialItem(SpecialVendingMachine currentSP, int templateIndex, ArrayList<Integer> baseIndexes, ArrayList<Integer> addonIndexes){

        System.out.println("TOTAL IN WALLET: " + userWallet.getTotal());
        // checks if there is enough stock for the selected items
        boolean hasEnoughStock = true;
        HashMap<Integer, Integer> requiredItems = new HashMap<>(); // Keep track of required items and their count

        for (int index : baseIndexes){
            int requiredAmount = requiredItems.getOrDefault(index, 0) + 1;
            requiredItems.put(index, requiredAmount);
        }

        for (int index : addonIndexes){
            int requiredAmount = requiredItems.getOrDefault(index, 0) + 1;
            requiredItems.put(index, requiredAmount);
        }

        for (int index : requiredItems.keySet()){
            int requiredAmount = requiredItems.get(index);
            int availableAmount = currentSP.getItemAmount(index);

            if (requiredAmount > availableAmount){
                hasEnoughStock = false;
                break; // stops checking if one item is out of stock
            }
        }

        for (HashMap.Entry<Integer, Integer> entry : requiredItems.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            System.out.println("Key: " + key + ", Value: " + value);
        }
        System.out.println("Has enough stock: " + hasEnoughStock);

        if (hasEnoughStock){

            double currentAmount = userWallet.getTotal();
            double change = userWallet.getTotal() - currentSP.getItemTemplatePrice(templateIndex);
            currentSP.addFunds(userWallet);
            if (change >= 0 && currentSP.hasEnoughDenominations(userWallet.convertToDenominations(change))){

                // If the machine has enough denominations to provide the change, continues.
                // The special item is CREATED, its ingredients are dispensed from the machine, and the change (in denominations) are subtracted from the machine and is given to the user.
                currentSP.createCustomItem(templateIndex, baseIndexes, addonIndexes);
                currentSP.subtractFunds(change);

                // The userWallet is reset for the next cycle, and a record will be added to the vending machine's purchase history.
                userWallet.resetWallet();
                currentSP.updateSpecialPurchaseHistory(currentAmount, templateIndex, change, baseIndexes, addonIndexes);

                return currentSP.displayReceivedChangeMessage(change);
            }
            else if (change >= 0 && !currentSP.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                // If the machine does NOT have enough denominations to provide the change, displays an error and the denominations are returned
                currentSP.subtractFunds(userWallet);
                userWallet.withdrawAll();
                return  "CAN'T GET CHANGE";
            }
            else{
                // Else, if the change value reaches a negative number, displays an insufficient funds message
                System.out.println("Change: " + change);
                currentSP.subtractFunds(userWallet);
                userWallet.withdrawAll();
                return "INSUFFICIENT FUNDS";
            }

        }

        else {
            return "OUT OF STOCK";
        }


    }

    /**
     * Retrieves the message for withdrawing cash (the machine returning the inserted cash to the user)
     * @return The corresponding message containing denominations and their amounts
     */
    public String getWithdrawMessage(){
       return userWallet.getWithdrawMessage();
    }

    /**
     * <p>Converts the user's choice input to a corresponding key value.</p>
     * <p><b>Precondition:</b>  The choice value is within the set range of (1,12)</p>
     *
     * @param choice the choice to convert
     * @return the key value corresponding to the choice
     */
    private double convertToKey(int choice){

        double keyChoice = switch (choice) {
            case 1 -> 0.01;
            case 2 -> 0.05;
            case 3 -> 0.25;
            case 4 -> 1.00;
            case 5 -> 5.00;
            case 6 -> 10.00;
            case 7 -> 20.00;
            case 8 -> 50.00;
            case 9 -> 100.00;
            case 10 -> 200.00;
            case 11 -> 500.00;
            case 12 -> 1000.00;
            default -> 0;
        };

        return keyChoice;
    }

    /**
     * Adds the specified denomination to the user's wallet.
     * @param key The denomination to add.
     */
    public void addCash(double key){
        userWallet.insertDenomination(key, 1);
        System.out.println("Current Total: " + userWallet.getTotal());
    }

    /**
     * Resets the user's wallet by emptying it of all denominations.
     */
    public void resetWallet(){
        userWallet.resetWallet();
        System.out.println("Current Total: " + userWallet.getTotal());
    }

    /**
     * Retrieves the total amount of money in the user's wallet.
     * @return The total amount of money in the wallet.
     */
    public double getUserWalletTotal(){
        return userWallet.getTotal();
    }

}
