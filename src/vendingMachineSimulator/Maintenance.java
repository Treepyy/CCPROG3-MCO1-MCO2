package vendingMachineSimulator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Responsible for managing and maintaining the usability of Vending Machines.
 * @author Vance Gyan M. Robles
 */
public class Maintenance {

    /**
     * <p>Scanner for getting user inputs.</p>
     */
    private Scanner input = new Scanner(System.in);

    /**
     * <p>Displays the maintenance menu and allows the user to select which action to perform on the machine.</p>
     *
     * @param currentVM is the vending machine to be used
     */
    public void performMaintenance(RegularVendingMachine currentVM) {

        int choice = -1;

        // Continues loop until user inputs [7] for exiting.
        while (choice != 7) {
            System.out.println();
            displayOptions();
            try{
                choice = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                choice = -1;
            }

            switch (choice) {
                case 1 -> addItems(currentVM);
                case 2 -> changeItemPrice(currentVM);
                case 3 -> withdrawFunds(currentVM);
                case 4 -> addDenominations(currentVM);
                case 5 -> currentVM.displayMachineHistory();
                case 6 -> currentVM.displayInventory();
                case 7 -> System.out.println("\nReturning to previous menu...");
                default -> System.out.println("\nInvalid input!");
            }
        }
    }

    /**
     * <p>Allows the user to add to the amount of items in the vending machine</p>
     *
     * @param currentVM is the vending machine to be used
     */
    private void addItems(RegularVendingMachine currentVM){
        currentVM.displayProducts();
        int index = -1; // Gets a user's integer input corresponding to the ID/index of the item to add
        while (index > 7 || index < 0){ // Continues loop until user inputs a valid index
            System.out.println();
            System.out.print("Select the ID of the item to be restocked: ");
            try{
                index = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                index = -1;
            }
            if (index > 7 || index < 0){
                System.out.println("\nError: Invalid ID, please try again.");
            }
        }

        if (currentVM.getItemAmount(index) == 10){
            System.out.println("\nError: Item slot already full!"); // If the amount of the item in the slot is already at max, displays an error message.
        }
        else{
            System.out.print("How many items to add? "); // Once a valid index has been picked, ask for input amount for items to add
            int itemsToAdd = 0;
            try{
                itemsToAdd = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                itemsToAdd = 0;
            }
            if (currentVM.getItemAmount(index) + itemsToAdd > 10) {
                System.out.println("\nError: Too many items to add!"); // If the amount of items to add makes it so that the item amount exceeds the maximum, displays an error message.
            }
            else if (itemsToAdd <= 0){
                System.out.println("\nError: Invalid input!");
            }
            else{ // Else, if everything is valid, the additional items will be added into the machine and a history record will be made
                int previousStock = currentVM.getItemAmount(index);
                currentVM.addItemStock(index, itemsToAdd);
                System.out.println();
                System.out.println("Successfully added " + itemsToAdd + " " + currentVM.getItemName(index) + " into the machine.");
                System.out.println();
                currentVM.updateStockHistory(index, itemsToAdd, previousStock);
            }
        }

    }
    public void addItems(RegularVendingMachine currentVM, int index, int itemsToAdd){
        int previousStock = currentVM.getItemAmount(index);
        currentVM.addItemStock(index, itemsToAdd);
        currentVM.updateStockHistory(index, itemsToAdd, previousStock);
    }
    /**
     * <p>Allows the user to add to change the price of an item in the vending machine.</p>
     *
     * @param currentVM is the vending machine to be used
     */
    public void changeItemPrice(RegularVendingMachine currentVM){

        int index = -1;

        while (index > 7 || index < 0){ // Continues loop until user inputs a valid index
            System.out.println();
            currentVM.displayProducts();
            System.out.print("Select the ID of the item to set a price: ");
            try{
                index = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                index = -1;
            }
            if (index > 7 || index < 0){
                System.out.println("\nError: Invalid ID, please try again.");
            }
        }
        double oldPrice = currentVM.getItemPrice(index);
        System.out.print("What should be the new price? "); // Gets user input for the new price and sets it as the new price once confirmed.
        double newPrice = 0.0;
        try{
            newPrice = input.nextDouble();
        }
        catch (InputMismatchException ex){
            input.reset();
            input.next();
            newPrice = 0.0;
        }
        if (oldPrice == newPrice) {
            System.out.println("\nError: Price inputted is already the current price!");
        }
        else if (newPrice <= 0.0){
            System.out.println("\nInvalid input!");
        }
        else{
            currentVM.setItemPrice(index, newPrice);
            System.out.println();
            System.out.println("Successfully updated the price of " + currentVM.getItemName(index) + " from " + oldPrice + " to " + newPrice);
            currentVM.updatePriceHistory(index, oldPrice);
        }

    }
    public void changeItemPrice(RegularVendingMachine currentVM, int index, double newPrice){
        double oldPrice = currentVM.getItemPrice(index);
        currentVM.setItemPrice(index, newPrice);
        currentVM.updatePriceHistory(index, oldPrice);
    }


    /**
     * <p>Allows the user to withdraw all the funds in the vending machine (inside the machineWallet)</p>
     *
     * @param currentVM is the vending machine to be used
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
     */
    private void addDenominations(RegularVendingMachine currentVM){

        int choice = -1, amountToAdd = 0;
        double keyChoice;
        while (choice != 0){ // Continues loop until user inputs [0] to exit
            System.out.println();
            displayDenominations();
            try{
                choice = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                choice = -1;
            }

            if (choice > 0 && choice < 13){ // If choice is valid, prompts for the amount to add, adds to the machine, and a history record will be made.
                keyChoice = convertToKey(choice);
                System.out.print("Enter number of bills/coins to add: ");
                try{
                    amountToAdd = input.nextInt();
                }
                catch (InputMismatchException ex){
                    input.reset();
                    input.next();
                    amountToAdd = 0;
                }

                if (amountToAdd <= 0){
                    System.out.println("\nInvalid input!");
                }
                else{
                    int oldAmount = currentVM.getDenominationAmount(keyChoice);
                    double oldTotal = currentVM.getCurrentFunds();
                    currentVM.insertMachineFunds(keyChoice, amountToAdd);
                    System.out.println();
                    System.out.println("Successfully added " + amountToAdd + " " + keyChoice + " peso denominations into the machine.");
                    currentVM.updateStockHistory(keyChoice, amountToAdd, oldAmount, oldTotal);
                }


            }
            else if (choice == 0){
                System.out.println("\nReturning to previous menu...");
            }
            else{
                System.out.println("\nInvalid input!");
            }
        }
    }

    public void addDenominations(RegularVendingMachine currentVM, int denomIndex, int amountToAdd){
        double keyChoice = convertToKey(denomIndex);
        int oldAmount = currentVM.getDenominationAmount(convertToKey(denomIndex));
        double oldTotal = currentVM.getCurrentFunds();

        currentVM.insertMachineFunds(convertToKey(denomIndex), amountToAdd);
        currentVM.updateStockHistory(keyChoice, amountToAdd, oldAmount, oldTotal);
    }

    /**
     * <p>Displays all the available maintenance options.</p>
     */
    private void displayOptions(){
        System.out.println("[Maintenance Features]");
        System.out.println("[1] Restock/Stock Item");
        System.out.println("[2] Set Price");
        System.out.println("[3] Collect Payment");
        System.out.println("[4] Replenish Money");
        System.out.println("[5] Print Summary of Transactions");
        System.out.println("[6] Display Inventory");
        System.out.println("[7] Exit");
        System.out.print("Pick an option: ");
    }

    /**
     * <p>Displays the available denominations to pick from. (for addDenominations menu)</p>
     */
    private void displayDenominations(){
        System.out.println("What to add?");
        System.out.println("[0] Exit");
        System.out.println("[1] 1 Centavo");
        System.out.println("[2] 5 Centavos");
        System.out.println("[3] 25 Centavos");
        System.out.println("[4] 1 Peso");
        System.out.println("[5] 5 Pesos");
        System.out.println("[6] 10 Pesos");
        System.out.println("[7] 20 Pesos");
        System.out.println("[8] 50 Pesos");
        System.out.println("[9] 100 Pesos");
        System.out.println("[10] 200 Pesos");
        System.out.println("[11] 500 Pesos");
        System.out.println("[12] 1000 Pesos");
        System.out.print("Pick an option: ");
    }

    /**
     * <p>Converts the user's choice input to a corresponding key value.</p>
     * <p><b>Precondition:</b>  The choice value is within the set range of (1,12)</p>
     *
     * @param choiceIndex the choice to convert
     * @return the key value corresponding to the choice
     */
    /*private double convertToKey(int choice){

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
    }*/

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

    public int getDenomAmt(RegularVendingMachine currentVM, int choiceIndex){
        return currentVM.getDenominationAmount(convertToKey(choiceIndex));
    }

}
