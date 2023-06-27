import java.util.Scanner;

public class Maintenance {
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

            displayOptions();
            choice = input.nextInt();

            switch (choice) {
                case 1 -> addItems(currentVM);
                case 2 -> changeItemPrice(currentVM);
                case 3 -> withdrawFunds(currentVM);
                case 4 -> addDenominations(currentVM);
                case 5 -> currentVM.displayMachineHistory();
                case 6 -> currentVM.displayInventory();
                case 7 -> System.out.println("Returning to previous menu...");
                default -> System.out.println("Invalid choice. Please try again.");
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
            System.out.print("Select the ID of the item to be restocked: ");
            index = input.nextInt();
            if (index > 7 || index < 0){
                System.out.print("Error: Unknown ID, please try again.");
            }
        }

        if (currentVM.getItemAmount(index) == 10){
            System.out.println("Error: Item slot already full!"); // If the amount of the item in the slot is already at max, displays an error message.
        }
        else{
            System.out.print("How many items to add? "); // Once a valid index has been picked, ask for input amount for items to add
            int itemsToAdd = input.nextInt();
            if (currentVM.getItemAmount(index) + itemsToAdd > 10) {
                System.out.println("Error: Too many items to add!"); // If the amount of items to add makes it so that the item amount exceeds the maximum, displays an error message.
            }
            else{ // Else, if everything is valid, the additional items will be added into the machine and a history record will be made
                int previousStock = currentVM.getItemAmount(index);
                currentVM.addItemStock(index, itemsToAdd);
                System.out.println("Successfully added " + itemsToAdd + " " + currentVM.getItemName(index) + " into the machine.");
                currentVM.updateStockHistory(index, itemsToAdd, previousStock);
            }
        }

    }
    /**
     * <p>Allows the user to add to change the price of an item in the vending machine.</p>
     *
     * @param currentVM is the vending machine to be used
     */
    private void changeItemPrice(RegularVendingMachine currentVM){

        int index = -1;

        while (index > 7 || index < 0){ // Continues loop until user inputs a valid index
            System.out.print("Select the ID of the item to set a price: ");
            index = input.nextInt();
            if (index > 7 || index < 0){
                System.out.print("Error: Unknown ID, please try again.");
            }
        }
        double oldPrice = currentVM.getItemPrice(index);
        System.out.print("What should be the new price? "); // Gets user input for the new price and sets it as the new price once confirmed.
        double newPrice = input.nextDouble();
        if (oldPrice == newPrice) {
            System.out.println("Price inputted is already the current price!");
        }
        else{
            currentVM.setItemPrice(index, newPrice);
            System.out.println("Successfully updated the price of " + currentVM.getItemName(index) + " from " + oldPrice + " to " + newPrice);
            currentVM.updatePriceHistory(index, oldPrice);
        }

    }
    /**
     * <p>Allows the user to withdraw all the funds in the vending machine (inside the machineWallet)</p>
     *
     * @param currentVM is the vending machine to be used
     */
    private void withdrawFunds(RegularVendingMachine currentVM){
        if (currentVM.getCurrentFunds() == 0){
            System.out.println("Error: No money in the machine!"); // Displays error message when there are no funds in the machine.
        }
        else{
            System.out.println("Successfully withdrawn " + currentVM.getCurrentFunds() + " from the machine.");
            currentVM.withdrawCurrentFunds();
        }
    }

    /**
     * <p>Allows the user to add any amount of any valid denomination into the machine</p>
     *
     * @param currentVM is the vending machine to be used
     */
    private void addDenominations(RegularVendingMachine currentVM){

        int choice = -1, amountToAdd;
        double keyChoice;
        while (choice != 0){ // Continues loop until user inputs [0] to exit

            displayDenominations();
            choice = input.nextInt();

            if (choice > 0 && choice < 13){ // If choice is valid, prompts for the amount to add, adds to the machine, and a history record will be made.
                keyChoice = convertToKey(choice);
                System.out.print("Enter number of bills/coins to add: ");
                amountToAdd = input.nextInt();
                int oldAmount = currentVM.getDenominationAmount(keyChoice);
                double oldTotal = currentVM.getCurrentFunds();
                currentVM.insertMachineFunds(keyChoice, amountToAdd);
                System.out.println("Successfully added " + amountToAdd + " " + keyChoice + " peso denominations into the machine.");
                currentVM.updateStockHistory(keyChoice, amountToAdd, oldAmount, oldTotal);

            }
            else if (choice == 0){
                System.out.println();
            }
            else{
                System.out.println("Invalid input!");
            }
        }
    }

    /**
     * <p>Displays all the available maintenance options.</p>
     */
    private void displayOptions(){
        System.out.println("Choose option:");
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

}
