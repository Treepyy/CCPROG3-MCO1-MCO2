package vendingMachineSimulator;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

// TODO: Convert item amount to instantiation (not set number), implement SpecialVendingMachine and SpecialItem, ~~finish addDemon~~,

/**
 * Serves as the main hub to access the two main features of the Vending Machine simulator.
 * @author Vance Gyan M. Robles
 */
public class MainModel {

    /**
     * <p>Scanner for getting user inputs.</p>
     */
    private Scanner input = new Scanner(System.in);
    /**
     * <p>The current vending machine to be tested/used.</p>
     */
    private RegularVendingMachine currentVM;
    /**
     * <p>Holds the test items for RegularVendingMachine.</p>
     */
    private Item[] defaultItems = new Item[8];
    private ArrayList<Item> itemSamples = new ArrayList<Item>();
    /**
     * <p>Accesses the vending feature of the Vending Machine.</p>
     */
    private Customer currentCustomer;
    /**
     * <p>Accesses the maintenance feature of the Vending Machine.</p>
     */
    private Maintenance currentManager = new Maintenance();

    public MainModel(){
        itemSamples.add(new Item("Bread",207, 30.00));
        itemSamples.add(new Item("Ube Ice Cream",146, 45.50));
        itemSamples.add(new Item("Plain Rice", 130, 25.00));
        itemSamples.add(new Item("Java Rice", 136, 32.00));
        itemSamples.add(new Item("Soba Noodles", 99, 55.50));
        itemSamples.add(new Item("Udon Noodles",124, 50.00));
        itemSamples.add(new Item("Fried Tofu",77, 27.50));
        itemSamples.add(new Item("Fish Cake", 201, 32.00));
    }

    public void createRegularVM(){
        currentVM = new RegularVendingMachine(itemSamples);
    }

    public void createSpecialVM(){

    }

    public boolean hasCreatedVM(){
        if (currentVM != null)
            return true;

        return false;
    }
    public void testVending(){
        currentCustomer = new Customer();
        currentCustomer.purchaseItem(currentVM);
    }

    public void testMaintenance(){
        currentManager = new Maintenance();
        currentManager.performMaintenance(currentVM);
    }

    public ArrayList<String> getItemNameList(){

        ArrayList<String> itemNames = new ArrayList<String>();
        for (Item item : itemSamples){
            itemNames.add(item.getName());
        }

        return itemNames;
    }

    public ArrayList<String> getMachineHistory(){
        return currentVM.getMachineHistory();
    }

    public double getItemPrice(int index){
        return currentVM.getItemPrice(index);
    }

    public int getItemStock(int index){
        return currentVM.getItemAmount(index);
    }

    public void changeItemPrice(int index, double newPrice){
        currentManager.changeItemPrice(currentVM, index, newPrice);
    }

    public boolean addItemStock(int index){
        if ((currentVM.getItemAmount(index) + 1) > 10){
            return false;
        }
        else{
            int previousStock = currentVM.getItemAmount(index);
            currentVM.addItemStock(index, 1);
            currentVM.updateStockHistory(index, 1, previousStock);
            return true;
        }
    }

    public double withdrawAll(){
        double withdrawnFunds = currentManager.withdrawFunds(currentVM);
        return withdrawnFunds;
    }

    public ArrayList<String> getItemInformation(){
        ArrayList<String> itemInformation = new ArrayList<String>();
        for (int i = 0; i < itemSamples.size(); i++)
            itemInformation.add(currentVM.getItemName(i) + "," + currentVM.getItemAmount(i) + "," + currentVM.getItemPrice(i) + "," + currentVM.getItemCalories(i));

        return itemInformation;
    }

    public ArrayList<Integer> getMoneyInformation(){
        return currentVM.getDenominationAmounts();
    }

    public int getDenomAmount(int denomIndex){
        return currentManager.getDenomAmt(currentVM, denomIndex);
    }

    public void addDenom(int denomIndex, int amountToAdd){
        currentManager.addDenominations(currentVM, denomIndex, amountToAdd);
    }

    /**
     * <p>Displays the main menu and allows the user to input integers for navigation.</p>
     */
    public void displayMenu() {

        int choice = -1;

        // Continues loop until user inputs [3] for exiting.
        while (choice != 3) {
            System.out.println();
            System.out.println("Welcome to the Vending Machine Factory Simulator!");
            System.out.println("[1] Create a Vending Machine");
            System.out.println("[2] Test a Vending Machine");
            System.out.println("[3] Exit");
            System.out.print("Pick an option: ");
            try{
                choice = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                choice = -1;
            }
            switch (choice) {
                case 1 -> createVendingMachineMenu();
                case 2 -> testVendingMachineMenu();
                case 3 -> System.out.println("Exiting...");
                default -> System.out.println("\nInvalid Input.");
            }
        }
    }

    /**
     * <p>Displays the menu for creating a VendingMachine instance.</p>
     */
    private void createVendingMachineMenu() {

        int choice = -1;
        int confirmation;

        // Continues loop until user inputs [3] for exiting.
        while (choice != 3) {
            System.out.println();
            System.out.println("What would you like to create:");
            System.out.println("[1] Regular Vending Machine");
            System.out.println("[2] Special Vending Machine");
            System.out.println("[3] Exit");
            System.out.print("Pick an option: ");
            try{
                choice = input.nextInt();
            }
            catch (InputMismatchException ex){
                input.reset();
                input.next();
                choice = -1;
            }
            switch (choice) {
                case 1 -> {
                    if (currentVM == null) { // If there is no current VendingMachine instance, proceeds to creation.
                        currentVM = new RegularVendingMachine(itemSamples);
                        System.out.println("\nYou successfully created a new regular vending machine.");
                    } else { // If there is already an existing VendingMachine instance, displays a confirmation message first.
                        System.out.println("Creating a new vending machine will overwrite the current one, continue? (Y/N)");
                        System.out.println("[1] Yes");
                        System.out.println("[2] No");
                        System.out.print("Pick an option: ");
                        try{
                            confirmation = input.nextInt();
                        }
                        catch (InputMismatchException ex){
                            input.reset();
                            input.next();
                            confirmation = 2;
                        }
                        if (confirmation == 1) { // If user confirms, creates a new instance which overwrites the first one.
                            currentVM = new RegularVendingMachine(itemSamples);
                            System.out.println("\nYou successfully created a new regular vending machine.");
                        } else { // Else, the operation is aborted and the user is returned to the previous choice.
                            System.out.println("\nOperation aborted.");
                        }
                    }
                }
                case 2 -> System.out.println("\nCurrently Unavailable...");
                case 3 -> System.out.println("\nReturning to previous menu...");
                default -> System.out.println("\nInvalid Input.");

            }
        }


    }

    /**
     * <p>Displays the menu for testing the functions of the created VendingMachine</p>
     */
    private void testVendingMachineMenu() {

        int choice = 0;
        if (currentVM == null) { // If there is no current VendingMachine instance, displays an error message.
            System.out.println("\nError: Please create a Vending Machine first!");
        } else {
            while (choice != 3) { // Else, continues to the testing menu.
                System.out.println();
                System.out.println("What would you like to test?");
                System.out.println("[1] Vending Features");
                System.out.println("[2] Maintenance Features");
                System.out.println("[3] Exit");
                System.out.print("Pick an option: ");
                try{
                    choice = input.nextInt();
                }
                catch (InputMismatchException ex){
                    input.reset();
                    input.next();
                    choice = -1;
                }

                switch (choice) {
                    case 1 -> { // If the user chooses to test the vending features, the currentCustomer is instantiated.
                        currentCustomer = new Customer();
                        currentCustomer.purchaseItem(currentVM);
                    }
                    case 2 -> { // If the user chooses to test the maintenance features, the currentManager is instantiated.
                        currentManager = new Maintenance();
                        currentManager.performMaintenance(currentVM);
                    }
                    case 3 -> System.out.println("\nReturning to previous menu...");
                    default -> System.out.println("\nInvalid choice. Please try again.");
                }
            }
        }
    }
}