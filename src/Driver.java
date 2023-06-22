public class Driver {

    /*
        TODO: TEST - Vending features & Denomination manager, class cleanup, UML diagram, demo video, documentation/comments, test script

        Questions:

        Should the input for money of the customer should be text input-based (typing the exact value) or through
        denominations too (i.e. continuously looping a switch until the user has entered their desired amount of money)
        or can it be either?

        For collecting payment, should all the money in the machine be withdrawn?

        Should the machine only dispense items one at a time or can the user choose to get multiple items at the same time?

        Should the machine start emp2ty (i.e. no items in the slots and no money inside)?
     */
    public static void main(String[] args) {

        MainMenu menu = new MainMenu();
        menu.mainMenu();
    }
}