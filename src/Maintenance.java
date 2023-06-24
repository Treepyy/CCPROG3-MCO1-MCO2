import java.util.Scanner;

public class Maintenance {
    private Scanner input = new Scanner(System.in);
    public void performMaintenance(RegularVendingMachine currentVM) {

        int choice = 0;

        while (choice != 7) {

            displayOptions();
            choice = input.nextInt();

            switch (choice) {
                case 1 -> addItems(currentVM);
                case 2 -> changeItemPrice(currentVM);
                case 3 -> withdrawFunds(currentVM);
                case 4 -> addDenominations(currentVM);
                case 5 -> currentVM.displayPurchaseHistory();
                case 6 -> currentVM.displayInventory();
                case 7 -> System.out.println("Returning to previous menu...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addItems(RegularVendingMachine currentVM){

        int index = input.nextInt();
        Item[] newItems = currentVM.getItemSlots();
        while (index > 7 || index < 0){
            System.out.print("Select the ID of the item to be restocked: ");
            index = input.nextInt();
            if (index > 7 || index < 0){
                System.out.print("Error: Unknown ID, please try again.");
            }
        }
        System.out.print("How many items to add? ");
        int itemsToAdd = input.nextInt();
        if (newItems[index].getAmount() == 10){
            System.out.println("Error: Item slot already full!");
        }
        else if (newItems[index].getAmount() + itemsToAdd > 10){
            System.out.println("Error: Too many items to add!");
        }
        else{
            int itemsAdded = itemsToAdd;
            itemsToAdd += newItems[index].getAmount();
            newItems[index].setAmount(itemsToAdd);
            currentVM.setItemSlots(newItems);
            System.out.println("Successfully added " + itemsAdded + " " + newItems[index].getName() + " into the machine.");
        }
    }

    private void changeItemPrice(RegularVendingMachine currentVM){

        Item[] newItems = currentVM.getItemSlots();
        int index = -1;

        while (index > 7 || index < 0){
            System.out.print("Select the ID of the item to set a price: ");
            index = input.nextInt();
            if (index > 7 || index < 0){
                System.out.print("Error: Unknown ID, please try again.");
            }
        }
        double oldPrice = newItems[index].getPrice();
        System.out.print("What should be the new price? ");
        double newPrice = input.nextDouble();
        newItems[index].setPrice(newPrice);
        currentVM.setItemSlots(newItems);
        System.out.println("Successfully updated the price of " + newItems[index].getName() + " from " + oldPrice + " to " + newPrice);
    }
    private void withdrawFunds(RegularVendingMachine currentVM){
        if (currentVM.getCurrentFunds() == 0){
            System.out.println("Error: No money in the machine!");
        }
        else{
            currentVM.withdrawCurrentFunds();
            System.out.println("Successfully withdrawn " + currentVM.getCurrentFunds() + " from the machine.");
        }
    }
    private void addDenominations(RegularVendingMachine currentVM){

        int choice = -1, amountToAdd;
        double keyChoice;
        while (choice != 0){

            displayDenominations();
            choice = input.nextInt();

            if (choice > 0 && choice < 13){
                keyChoice = convertToKey(choice);
                System.out.print("Enter number of bills/coins to add: ");
                amountToAdd = input.nextInt();
                currentVM.insertMachineFunds(keyChoice, amountToAdd);
                System.out.println("Successfully added " + amountToAdd + " " + keyChoice + " peso denominations into the machine.");
            }
            else if (choice == 0){
                System.out.println();
            }
            else{
                System.out.println("Invalid input!");
            }
        }
    }

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
