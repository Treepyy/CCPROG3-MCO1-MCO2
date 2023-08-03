package vendingMachineSimulator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Responsible for managing and maintaining the usability of Vending Machines.
 * @author Vance Gyan M. Robles
 */
public class Maintenance {

    /**
     * <p>Allows the user to add to the amount of items in the vending machine</p>
     *
     * @param currentVM is the vending machine to be used
     * @param index is the index of the item to add
     * @param itemsToAdd is the number of items to add
     */
    public void addItems(RegularVendingMachine currentVM, int index, int itemsToAdd){
        int previousStock = currentVM.getItemAmount(index);
        currentVM.addItemStock(index, itemsToAdd);
        currentVM.updateStockHistory(index, itemsToAdd, previousStock);
    }
    /**
     * <p>Allows the user to change the price of an item in the vending machine.</p>
     *
     * @param currentVM is the vending machine to be used
     * @param index is the index of the item to be modified
     * @param newPrice is the new price of the item
     */
    public void changeItemPrice(RegularVendingMachine currentVM, int index, double newPrice){
        double oldPrice = currentVM.getItemPrice(index);
        currentVM.setItemPrice(index, newPrice);
        currentVM.updatePriceHistory(index, oldPrice);
    }


    /**
     * <p>Allows the user to withdraw all the funds in the vending machine (inside the machineWallet)</p>
     *
     * @param currentVM is the vending machine to be used
     * @return double value corresponding to the amount
     */
    public double withdrawFunds(RegularVendingMachine currentVM){
        if (currentVM.getCurrentFunds() == 0){
            return 0.0; // Returns 0 if no money in the machine
        }
        else{
            double withdrawnFunds = currentVM.getCurrentFunds();
            currentVM.updateWithdrawHistory();
            currentVM.withdrawCurrentFunds();
            return withdrawnFunds;
        }
    }

    /**
     * <p>Allows the user to add any amount of any valid denomination into the machine</p>
     *
     * @param currentVM is the vending machine to be used
     * @param denomIndex is the index of the denomination to add
     * @param amountToAdd is the amount to add
     */
    public void addDenominations(RegularVendingMachine currentVM, int denomIndex, int amountToAdd){
        double keyChoice = convertToKey(denomIndex);
        int oldAmount = currentVM.getDenominationAmount(convertToKey(denomIndex));
        double oldTotal = currentVM.getCurrentFunds();

        currentVM.insertMachineFunds(convertToKey(denomIndex), amountToAdd);
        currentVM.updateStockHistory(keyChoice, amountToAdd, oldAmount, oldTotal);
    }


    /**
     * <p>Converts the user's choice input to a corresponding key value.</p>
     * <p><b>Precondition:</b>  The choice value is within the set range of (1,12)</p>
     *
     * @param choiceIndex the choice to convert
     * @return the key value corresponding to the choice
     */
    private double convertToKey(int choiceIndex){

        double keyChoice = switch (choiceIndex) {
            case 0 -> 0.01;
            case 1 -> 0.05;
            case 2 -> 0.25;
            case 3 -> 1.00;
            case 4 -> 5.00;
            case 5 -> 10.00;
            case 6 -> 20.00;
            case 7 -> 50.00;
            case 8 -> 100.00;
            case 9 -> 200.00;
            case 10 -> 500.00;
            case 11 -> 1000.00;
            default -> 0;
        };

        return keyChoice;
    }

    /**
     * Gets the amount of a denomination currently in the vending machine.
     * @param currentVM is the vending machine to search
     * @param choiceIndex is the denomination type's index to get the amount for
     * @return the amount of specified denominations in the machine
     */
    public int getDenomAmt(RegularVendingMachine currentVM, int choiceIndex){
        return currentVM.getDenominationAmount(convertToKey(choiceIndex));
    }

}
