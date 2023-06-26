import java.util.Scanner;

public class MainMenu {
    private Scanner input = new Scanner(System.in);
    private RegularVendingMachine currentVM;
    private Item[] defaultItems = new Item[8];
    private Customer currentCustomer;
    private Maintenance currentManager;

    /**
     * <p>Displays the main menu and allows the user to input integers for navigation.</p>
     */
    public void displayMenu() {

        int choice = 0;

        // Continues loop until user inputs [3] for exiting.
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

    /**
     * <p>Displays the menu for creating a VendingMachine instance.</p>
     */
    private void createVendingMachineMenu() {

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

        // Continues loop until user inputs [3] for exiting.
        while (choice != 3) {
            System.out.println("What would you like to create:");
            System.out.println("[1] Regular Vending Machine");
            System.out.println("[2] Special Vending Machine");
            System.out.println("[3] Exit");
            System.out.print("Pick an option: ");
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    if (currentVM == null) { // If there is no current VendingMachine instance, proceeds to creation.
                        currentVM = new RegularVendingMachine(defaultItems);
                        System.out.println("You successfully created a new regular vending machine.");
                    } else { // If there is already an existing VendingMachine instance, displays a confirmation message first.
                        System.out.println("Creating a new vending machine will overwrite the current one, continue? (Y/N)");
                        System.out.println("[1] Yes");
                        System.out.println("[2] No");
                        System.out.print("Pick an option: ");
                        confirmation = input.nextInt();
                        if (confirmation == 1) { // If user confirms, creates a new instance which overwrites the first one.
                            currentVM = new RegularVendingMachine(defaultItems);
                            System.out.println("You successfully created a new regular vending machine.");
                        } else { // Else, the operation is aborted and the user is returned to the previous choice.
                            System.out.println("Operation aborted.");
                        }
                    }

                    break;

                case 2:
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

    /**
     * <p>Displays the menu for testing the functions of the created VendingMachine</p>
     */
    private void testVendingMachineMenu() {

        int choice = 0;
        if (currentVM == null) { // If there is no current VendingMachine instance, displays an error message.
            System.out.println("Error: Please create a Vending Machine first!");
        } else {
            while (choice != 3) { // Else, continues to the testing menu.
                System.out.println("What would you like to test:");
                System.out.println("[1] Vending Features");
                System.out.println("[2] Maintenance Features");
                System.out.println("[3] Exit");
                System.out.print("Pick an option: ");
                choice = input.nextInt();

                switch (choice) {
                    case 1: // If the user chooses to test the vending features, the currentCustomer is instantiated.
                        currentCustomer = new Customer();
                        currentCustomer.purchaseItem(currentVM);
                        break;
                    case 2: // If the user chooses to test the maintenance features, the currentManager is instantiated.
                        currentManager = new Maintenance();
                        currentManager.performMaintenance(currentVM);
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
}