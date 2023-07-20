package vendingMachineSimulator;

/**
 * Driver class for executing the program.
 */
public class Driver {

    public static void main(String[] args) {
        MainView mainView = new MainView();
        MainModel mainModel = new MainModel();
        MainController mainController = new MainController(mainView, mainModel);

        mainModel.displayMenu();
    }

}