import java.util.Scanner;

public class Customer {

    private Scanner input = new Scanner(System.in);
    private Wallet userWallet = new Wallet();

    /**
     * <p>Displays the customer menu and allows the user to purchase an item from the vending machine.</p>
     * <p>Input of money to the machine is done through insertion of the different bill/coin denominations.</p>
     */
    public void purchaseItem(RegularVendingMachine current){

        int choice = -1;
        int productChoice;
        double keyChoice = 0;
        double currentAmount;

        // Continues loop until user inputs [0] for exiting.
        while (choice != 0) {

            currentAmount = userWallet.getTotal();
            displayDenominations();
            System.out.println("Current Total Money Inserted: " + currentAmount); // The total money inserted will be displayed
            System.out.print("Pick an Option: ");
            choice = input.nextInt();
            if (choice > 0 && choice < 13){
                keyChoice = convertToKey(choice);
                userWallet.insertDenomination(keyChoice, 1); // Once user picks a denomination, one of it will be inserted into the userWallet
            }

            else if (choice == 0){
                System.out.println("Transaction aborted!");
                userWallet.withdrawAll(); // If user chooses to exit, all money inserted will be returned.
            }

            else if (choice == 13){
                if (userWallet.getTotal() == 0){
                    System.out.println("Please insert money into the machine first!"); // Displays error when user tries to pick product without inserting money first
                }
                else{
                    productChoice = productSelector(current); // Calls helper function for getting user input to select product

                    if (productChoice == 9){ // Exits menu and returns money if user changes their mind and aborts
                        System.out.println("Transaction aborted!");
                        userWallet.withdrawAll();
                        choice = 0;
                    }
                    else if (productChoice >= 0 && productChoice < 8){
                        // The change is calculated by getting the difference of the total funds and price of the selected item.
                        double change = userWallet.getTotal() - current.getItemPrice(productChoice);
                        current.addFunds(userWallet); // The denominations inside userWallet is added into the machineWallet.
                        if (change >= 0 && current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                            // If the machine has enough denominations to provide the change, continues.
                            // The product is dispensed from the machine, the change (in denominations) are subtracted from the machine and is given to the user.
                            current.dispenseItem(productChoice);
                            current.subtractFunds(change);
                            current.displayReceivedChange(change);
                            // The userWallet is reset for the next cycle, and a record will be added to the vending machine's purchase history.
                            userWallet.resetWallet();
                            current.updatePurchaseHistory(currentAmount, productChoice, change);
                            System.out.println("Purchase Successful!");
                        }
                        else if (change >= 0 && !current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                            // If the machine does NOT have enough denominations to provide the change, displays an error and the denominations are returned
                            System.out.println("Error: Not enough funds in the machine for change!");
                            current.subtractFunds(userWallet);
                            userWallet.withdrawAll();
                            System.out.println("Inserted funds have been returned.");
                        }
                        else{
                            // Else, if the change value reaches a negative number, displays an insufficient funds message
                            System.out.println("Insufficient Funds!");
                            current.subtractFunds(userWallet);
                            userWallet.withdrawAll();
                            System.out.println("Transaction aborted!");
                        }
                    }
                    // Choosing productChoice == [8] will return user to the denomination input loop.
                }
            }
            else {
                System.out.println("Invalid input!");
            }
        }
    }
    /**
     * <p>Displays the available denominations to pick from. (for purchaseItem menu)</p>
     */
    private void displayDenominations(){
        System.out.println("Welcome! Please insert your coins/bills into the machine. Press [0] to cancel and exit the transaction.");
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
        System.out.println("[13] Continue to Selection");
        System.out.println();
    }
    /**
     * <p>Displays the available products in the vending machine and gets the user input.</p>
     *
     * @param current is the vending machine to select a product from
     * @return the integer corresponding to the user's selection
     */
    private int productSelector(RegularVendingMachine current){
        int selection = -1;
        while (selection > 9 || selection < 0){

            current.displayProducts();
            System.out.println("[8] Insert More Money");
            System.out.println("[9] Abort Transaction");
            System.out.print("Select a Product: ");
            selection = input.nextInt();

            if (selection > 9 || selection < 0){
                System.out.println("Invalid input!");
            }
        }
        return selection;
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
