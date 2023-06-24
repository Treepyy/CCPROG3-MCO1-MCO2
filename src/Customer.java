import java.util.Scanner;
import java.util.TreeMap;

public class Customer {

    private Scanner input = new Scanner(System.in);
    private Wallet userWallet = new Wallet();
    public void purchaseItem(RegularVendingMachine current){

        int choice = -1;
        int productChoice;
        double keyChoice = 0;
        double currentAmount;

        while (choice != 0) {

            currentAmount = userWallet.getTotal();
            displayDenominations();
            System.out.println("Current Total Money Inserted: " + currentAmount);
            System.out.print("Pick an Option: ");
            choice = input.nextInt();
            if (choice > 0 && choice < 13){
                keyChoice = convertToKey(choice);
                userWallet.insertDenomination(keyChoice, 1);
            }

            else if (choice == 0){
                System.out.println("Transaction aborted!");
                userWallet.withdrawAll();
            }

            else if (choice == 13){
                if (userWallet.getTotal() == 0){
                    System.out.println("Please insert money into the machine first!");
                }
                else{
                    productChoice = productSelector(current);

                    // exits menu and returns money if user changes their mind and aborts
                    if (productChoice == 9){
                        System.out.println("Transaction aborted!");
                        userWallet.withdrawAll();
                        choice = 0;
                    }
                    else if (productChoice >= 0 && productChoice < 8){
                        double change = userWallet.getTotal() - current.getItemSlots()[productChoice].getPrice();
                        current.getMachineWallet().addCash(userWallet);
                        if (change >= 0 && current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                            System.out.println("Purchase Successful!");
                            current.dispenseItem(productChoice);
                            current.getMachineWallet().subtractCash(change);
                            current.displayReceivedChange(change);
                            userWallet.resetWallet();
                            current.updatePurchaseHistory(currentAmount, productChoice, change);
                        }
                        else if (change >= 0 && !current.hasEnoughDenominations(userWallet.convertToDenominations(change))){
                            System.out.println("Error: Not enough funds in the machine for change!");
                            System.out.println("Inserted funds have been returned.");
                            current.getMachineWallet().subtractCash(userWallet);
                            userWallet.withdrawAll();
                        }
                        else{
                            System.out.println("Insufficient Funds!");
                            System.out.println("Transaction aborted!");
                            current.getMachineWallet().subtractCash(userWallet);
                            userWallet.withdrawAll();
                        }
                    }
                }
            }
            else {
                System.out.println("Invalid input!");
            }
        }
    }
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

    private int productSelector(RegularVendingMachine current){
        int selection = -1;
        while (selection > 9 || selection < 0){

            System.out.println("Select a Product: ");
            current.displayProducts();
            System.out.println("[8] Insert More Money");
            System.out.println("[9] Abort Transaction");
            selection = input.nextInt();

            if (selection > 9 || selection < 0){
                System.out.println("Invalid input!");
            }
        }
        return selection;
    }

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
