import java.util.TreeMap;
import java.util.Objects;
import java.util.Scanner;
public class MainMenu {
    Scanner input = new Scanner(System.in);
    private RegularVendingMachine current;
    private Item[] defaultItems = new Item[8];

    public void mainMenu(){

       int choice = 0;

        while (choice != 3) {
            System.out.println("Welcome to the Vending Machine Factory Simulator!");
            System.out.println("[1] Create a Vending Machine");
            System.out.println("[2] Test a Vending Machine");
            System.out.println("[3] Exit");
            System.out.print("Pick an option: ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    createVendingMachineMenu();
                    break;

                case 2:
                    testVendingMachineMenu();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Input.");
            }
        }
    }

    public void createVendingMachineMenu(){

        defaultItems[0] = new Item("Coke", 10, 139, 50.00);
        defaultItems[1] = new Item("Sprite", 10, 146, 45.50);
        defaultItems[2] = new Item("Milk", 10, 103, 35.00);
        defaultItems[3] = new Item("Tropicana", 10, 110, 42.00);
        defaultItems[4] = new Item("Latte", 10, 80, 65.50);
        defaultItems[5] = new Item("Oreo", 10, 160, 40.00);
        defaultItems[6] = new Item("Banana Chips", 10, 147, 47.50);
        defaultItems[7] = new Item("Nova", 10, 55, 32.00);


        int choice = 0;
        int confirmation;

        while (choice != 3) {
            System.out.println("What would you like to create:");
            System.out.println("[1] Regular Vending Machine");
            System.out.println("[2] Special Vending Machine");
            System.out.println("[3] Exit");
            System.out.print("Pick an option: ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    if (current == null){
                        current = new RegularVendingMachine(defaultItems);
                        System.out.println("You successfully created a new regular vending machine.");
                    }
                    else{
                        System.out.println("Creating a new vending machine will overwrite the current one, continue? (Y/N)");
                        System.out.println("[1] Yes");
                        System.out.println("[2] No");
                        System.out.print("Pick an option: ");
                        confirmation = input.nextInt();
                        if (confirmation == 1){
                            current = new RegularVendingMachine(defaultItems);
                            System.out.println("You successfully created a new regular vending machine.");
                        }
                        else{
                            System.out.println("Operation aborted.");
                        }
                    }

                    break;

                case 2:
                    // current = new RegularVendingMachine(true);
                    System.out.println("Currently Unavailable...");
                    break;

                case 3:
                    System.out.println("Returning to previous menu...");
                    break;

                default:
                    System.out.println("Invalid Input.");
            }
        }


    }

    public void testVendingMachineMenu(){

        int choice = 0;
        if (current == null) {
            System.out.println("Error: Please create a Vending Machine first!");
        }
        else {
            while (choice != 3) {
                System.out.println("What would you like to test:");
                System.out.println("[1] Vending Features");
                System.out.println("[2] Maintenance Features");
                System.out.println("[3] Exit");
                System.out.print("Pick an option: ");
                choice = input.nextInt();

                switch (choice) {
                    case 1:
                        testVendingFeatures();
                        break;
                    case 2:
                        testMaintenanceFeatures();
                        break;
                    case 3:
                        System.out.println("Returning to previous menu...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            }
        }
    }

    // TODO: put vending and maint features into separate classes
    public void testVendingFeatures(){

        int choice = -1;
        int productChoice;
        double keyChoice = 0;
        double currentAmount;
        Wallet userWallet = new Wallet();

        while (choice != 0){

            currentAmount = userWallet.getTotal();
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
            System.out.println("Current Total Money Inserted: " + currentAmount);
            System.out.print("Pick an Option: ");
            choice = input.nextInt();

            switch (choice){
                case 0:
                    userWallet.withdrawAll();
                case 1:
                    keyChoice = 0.01;
                    break;
                case 2:
                    keyChoice = 0.05;
                    break;
                case 3:
                    keyChoice = 0.25;
                    break;
                case 4:
                    keyChoice = 1.00;
                    break;
                case 5:
                    keyChoice = 5.00;
                    break;
                case 6:
                    keyChoice = 10.00;
                    break;
                case 7:
                    keyChoice = 20.00;
                    break;
                case 8:
                    keyChoice = 50.00;
                    break;
                case 9:
                    keyChoice = 100.00;
                    break;
                case 10:
                    keyChoice = 200.00;
                    break;
                case 11:
                    keyChoice = 500.00;
                    break;
                case 12:
                    keyChoice = 1000.00;
                    break;
                case 13:
                    if (userWallet.getTotal() == 0){
                        System.out.println("Please insert money into the machine first!");
                    }
                    else{
                        productChoice = productSelector();

                        // exits menu and returns money if user changes their mind and aborts
                        if (productChoice == 9){
                            userWallet.withdrawAll();
                            choice = 0;
                        }
                        else if (productChoice >= 0 && productChoice < 8){
                            double change = userWallet.getTotal() - current.getItemSlots()[productChoice].getPrice();
                            current.getMachineWallet().addCash(currentAmount);
                            if (change >= 0){
                                TreeMap<Double, Integer> converted = userWallet.convertToDenominations(change);
                                if (current.hasEnoughDenominations(converted)) {
                                    System.out.println("Purchase Successful!");
                                    current.dispenseItem(productChoice);
                                    userWallet.resetWallet();
                                    current.getMachineWallet().subtractCash(change);
                                    current.updatePurchaseHistory(currentAmount, productChoice, change);
                                }
                                else{
                                    System.out.println("Error: Not enough funds in the machine for change!");
                                    System.out.println("Inserted funds have been returned.");
                                    current.getMachineWallet().subtractCash(currentAmount);
                                    userWallet.withdrawAll();
                                }

                            }
                            else{
                                System.out.println("Insufficient Funds!");
                                current.getMachineWallet().subtractCash(currentAmount);
                                userWallet.withdrawAll();
                            }
                        }

                    }
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }

            if (choice > 0 && choice < 13){
                userWallet.insertDenomination(keyChoice, 1);
            }

        }

    }

    private int productSelector(){
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

    public void testMaintenanceFeatures() {

        Item[] newItems;

        int choice = 0;
        int itemsToAdd;
        int itemsAdded;
        int newStock;
        double newPrice;
        double oldPrice;

        while (choice != 7) {
            int index = -1;

            System.out.println("Choose option:");
            System.out.println("[1] Restock/Stock Item");
            System.out.println("[2] Set Price");
            System.out.println("[3] Collect Payment");
            System.out.println("[4] Replenish Money");
            System.out.println("[5] Print Summary of Transactions");
            System.out.println("[6] Display Inventory");
            System.out.println("[7] Exit");
            System.out.print("Pick an option: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    newItems = current.getItemSlots();
                    while (index > 7 || index < 0){
                        System.out.print("Select the ID of the item to be restocked: ");
                        index = input.nextInt();
                        if (index > 7 || index < 0){
                            System.out.print("Error: Unknown ID, please try again.");
                        }
                    }
                    System.out.print("How many items to add? ");
                    itemsToAdd = input.nextInt();
                    if (newItems[index].getAmount() == 10){
                        System.out.println("Error: Item slot already full!");
                    }
                    else if (newItems[index].getAmount() + itemsToAdd > 10){
                        System.out.println("Error: Too many items to add!");
                    }
                    else{
                        itemsAdded = itemsToAdd;
                        itemsToAdd += newItems[index].getAmount();
                        newItems[index].setAmount(itemsToAdd);
                        current.setItemSlots(newItems);
                        System.out.println("Successfully added " + itemsAdded + " " + newItems[index].getName() + " into the machine.");
                    }

                    break;

                case 2:
                    newItems = current.getItemSlots();
                    while (index > 7 || index < 0){
                        System.out.print("Select the ID of the item to set a price: ");
                        index = input.nextInt();
                        if (index > 7 || index < 0){
                            System.out.print("Error: Unknown ID, please try again.");
                        }
                    }
                    oldPrice = newItems[index].getPrice();
                    System.out.print("What should be the new price? ");
                    newPrice = input.nextDouble();
                    newItems[index].setPrice(newPrice);
                    current.setItemSlots(newItems);
                    System.out.println("Successfully updated the price of " + newItems[index].getName() + " from " + oldPrice + " to " + newPrice);

                    break;
                case 3:
                    if (current.getCurrentFunds() == 0){
                        System.out.println("Error: No money in the machine!");
                    }
                    else{
                        System.out.println("Successfully withdrawn " + current.getCurrentFunds() + " from the machine.");
                        current.withdrawCurrentFunds();
                    }
                    break;
                case 4:
                    addDenominations();
                    break;
                case 5:
                    current.displayPurchaseHistory();
                    break;
                case 6:
                    current.displayInventory();
                    break;
                case 7:
                    System.out.println("Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public void addDenominations(){

        int choice = -1;
        double keyChoice = 0;
        int amountToAdd;
        int newAmount;
        TreeMap<Double, Integer> newDenominations = current.getDenominations();

        while (choice != 0){

            newDenominations = current.getDenominations();
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
            choice = input.nextInt();

            switch (choice){
                case 0:
                    break;
                case 1:
                    keyChoice = 0.01;
                    break;
                case 2:
                    keyChoice = 0.05;
                    break;
                case 3:
                    keyChoice = 0.25;
                    break;
                case 4:
                    keyChoice = 1.00;
                    break;
                case 5:
                    keyChoice = 5.00;
                    break;
                case 6:
                    keyChoice = 10.00;
                    break;
                case 7:
                    keyChoice = 20.00;
                    break;
                case 8:
                    keyChoice = 50.00;
                    break;
                case 9:
                    keyChoice = 100.00;
                    break;
                case 10:
                    keyChoice = 200.00;
                    break;
                case 11:
                    keyChoice = 500.00;
                    break;
                case 12:
                    keyChoice = 1000.00;
                    break;
                default:
                    System.out.println("Invalid input!");
                    break;
            }

            if (choice > 0 && choice < 13){
                System.out.print("Enter number of bills/coins to add: ");
                amountToAdd = input.nextInt();
                newAmount = newDenominations.get(keyChoice) + amountToAdd;
                newDenominations.put(keyChoice, newAmount);
                current.setDenominations(newDenominations);
                System.out.println("Successfully added " + amountToAdd + " " + keyChoice + " peso denominations into the machine.");
                // choice = -1;
            }

        }





    }

}