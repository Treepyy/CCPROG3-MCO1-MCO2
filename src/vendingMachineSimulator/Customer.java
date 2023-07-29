package vendingMachineSimulator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Responsible for buying items from a Vending Machine using its own denominations.
 * @author Vance Gyan M. Robles
 */
public class Customer {
    /**
     * <p>Scanner for getting user inputs.</p>
     */
    private Scanner input = new Scanner(System.in);
    private Wallet userWallet = new Wallet();

    /**
     * <p>Displays the customer menu and allows the user to purchase an item from the vending machine.</p>
     * <p>Input of money to the machine is done through insertion of the different bill/coin denominations.</p>
     *
     * @param current the current vending machine to purchase from
     */
    public void purchaseItem(RegularVendingMachine current){

        int choice = -1;
        int productChoice;
        double keyChoice = 0;
        double currentAmount;

        // Continues loop until user inputs [0] for exiting.
        while (choice != 0) {

            currentAmount = userWallet.getTotal();
            System.out.println();
            System.out.println("Current Total Money Inserted: " + currentAmount); // The total money inserted will be displayed
            System.out.print("Pick an Option: ");
            try{
                choice = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                choice = -1;
            }
            if (choice > 0 && choice < 13){
                keyChoice = convertToKey(choice);
                userWallet.insertDenomination(keyChoice, 1); // Once user picks a denomination, one of it will be inserted into the userWallet
            }

            else if (choice == 0){
                System.out.println("\nTransaction aborted!");
                userWallet.withdrawAll(); // If user chooses to exit, all money inserted will be returned.
                System.out.println();
            }

            else if (choice == 13){
                if (userWallet.getTotal() == 0){
                    System.out.println("\nPlease insert money into the machine first!"); // Displays error when user tries to pick product without inserting money first
                }
                else{
                    productChoice = 0;

                    if (productChoice == 9){ // Exits menu and returns money if user changes their mind and aborts
                        System.out.println("\nTransaction aborted!");
                        userWallet.withdrawAll();
                        System.out.println();
                        choice = 0;
                    }
                    else if (productChoice >= 0 && productChoice < 8){

                        if (current.getItemAmount(productChoice) == 0){ // Checks if the item amount is greater than 0, if not then displays an error message
                            System.out.println("\nError: Not enough items in the machine!");
                        }
                        else{
                            // The change is calculated by getting the difference of the total funds and price of the selected item.
                            double change = userWallet.getTotal() - current.getItemPrice(productChoice);
                            current.addFunds(userWallet); // The denominations inside userWallet is added into the machineWallet.
                            if (change >= 0 && current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                                // If the machine has enough denominations to provide the change, continues.
                                // The product is dispensed from the machine, the change (in denominations) are subtracted from the machine and is given to the user.
                                System.out.println();
                                current.dispenseItem(productChoice);
                                current.subtractFunds(change);
                                // The userWallet is reset for the next cycle, and a record will be added to the vending machine's purchase history.
                                userWallet.resetWallet();
                                current.updatePurchaseHistory(currentAmount, productChoice, change);
                                System.out.println("Purchase Successful!");
                            }
                            else if (change >= 0 && !current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                                // If the machine does NOT have enough denominations to provide the change, displays an error and the denominations are returned
                                System.out.println();
                                System.out.println("Error: Not enough funds in the machine for change!");
                                current.subtractFunds(userWallet);
                                userWallet.withdrawAll();
                                System.out.println("Inserted funds have been returned.");
                            }
                            else{
                                // Else, if the change value reaches a negative number, displays an insufficient funds message
                                System.out.println();
                                System.out.println("Insufficient Funds!");
                                current.subtractFunds(userWallet);
                                userWallet.withdrawAll();
                                System.out.println("Transaction aborted!");
                            }
                        }

                    }
                    // Choosing productChoice == [8] will return user to the denomination input loop.
                }
            }
            else {
                System.out.println("\nInvalid input!");
            }
        }
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
