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

    }

}