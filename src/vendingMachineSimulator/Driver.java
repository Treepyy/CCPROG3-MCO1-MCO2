package vendingMachineSimulator;

/**
 * Driver class for executing the program.
 */
public class Driver {

    public static void main(String[] args) {

        MainView mainView = new MainView();
        VendingView vendingView = new VendingView();
        SpecialVendingView specialVendingView = new SpecialVendingView();
        MainModel mainModel = new MainModel();

        MainController mainController = new MainController(mainView, vendingView, mainModel, specialVendingView);

        /* TODO:
            finish special vending machine GUI for the other 2 special item types,
            implement purchasing of special item in model,
            set customizable items to set price instead of summation,
            convert SpecialVendingView to extended class of VendingView
            test script (model only)
            UML (model only) */
    }

}