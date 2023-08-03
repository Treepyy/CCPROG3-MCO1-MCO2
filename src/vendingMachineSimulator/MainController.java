package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The controller model for the GUI, allows the view(s) to interact with the model
 * @author Vance Gyan M. Robles
 */
public class MainController {

    private MainView mainView;
    private VendingView vendingView;
    private SpecialVendingView specialVendingView;
    private MainModel mainModel;
    private String currentType, type;
    private boolean createdRegularCashPanel = false, createdSpecialCashPanel = false;

    MainController(MainView mainView, VendingView vendingView, MainModel mainModel, SpecialVendingView specialVendingView){

        this.mainView = mainView;
        this.mainModel = mainModel;
        this.vendingView = vendingView;
        this.specialVendingView = specialVendingView;

        // ----------- Action listeners for the Maintenance Features for a Vending Machine. -----------
        this.mainView.setExitBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.mainView.setCreateBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayCreate();
            }
        });

        this.mainView.setTestBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainModel.hasCreatedVM())
                    mainView.displayTest();
                else
                    mainView.displayError("Create a Vending Machine first!");
            }
        });

        this.mainView.setHomeBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayMain();
                mainView.clearFeedback();
            }
        });

        this.mainView.setRegularBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainModel.hasCreatedVM()){
                    mainModel.createRegularVM();
                    mainView.displayFeedback("Created New Regular Vending Machine!", Color.GREEN);
                    currentType = "Regular";
                }
                else{
                    mainView.displayOverwriteConfirmation();
                    type = "Regular";
                }
            }
        });

        this.mainView.setOverwriteYesBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(type, "Regular")){
                    mainModel.createRegularVM();
                    currentType = "Regular";
                }

                else{
                    mainModel.createSpecialVM();
                    currentType = "Special";
                }
                mainView.closeErrorFrame();
                mainView.displayFeedback("Created New " + type + " Vending Machine!", Color.GREEN);
            }
        });

        this.mainView.setOverwriteNoBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.closeErrorFrame();
                mainView.displayFeedback("Operation aborted.", Color.RED);
            }
        });

        this.mainView.setSpecialBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainModel.hasCreatedVM()){
                    mainModel.createSpecialVM();
                    mainView.displayFeedback("Created New Special Vending Machine!", Color.GREEN);
                    currentType = "Special";
                }
                else{
                    mainView.displayOverwriteConfirmation();
                    type = "Special";
                }
            }
        });

        this.mainView.setOkayBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.closeErrorFrame();
            }
        });

        this.mainView.setMaintBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayMaint();
            }
        });

        this.mainView.setMaintReturnBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayTest();
            }
        });

        this.mainView.setOptionReturnBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayMaint();
                mainView.clearFeedback();
            }
        });

        this.mainView.setSetPriceBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.setItemDropdownItems(mainModel.getItemNameList());
                mainView.displayProductSelection();
            }
        });

        this.mainView.setConfirmBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displaySetPrice(mainModel.getItemPrice(mainView.getItemDropDownSelection()));
            }
        });

        this.mainView.setChangeBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(mainView.getPriceTfText(), "")){
                    mainView.displayError("Input cannot be empty!");
                }
                else if(Double.parseDouble(mainView.getPriceTfText()) == mainModel.getItemPrice(mainView.getItemDropDownSelection())){
                    mainView.displayError("Input price is same as old price!");
                }
                else if(Double.parseDouble(mainView.getPriceTfText()) <= 0){
                    mainView.displayError("Price cannot be zero or negative!");
                }
                else{
                    mainModel.changeItemPrice(mainView.getItemDropDownSelection(), Double.parseDouble(mainView.getPriceTfText()));
                    mainView.displayFeedback("Price Changed Successfully!", Color.GREEN);
                    mainView.displayProductSelection();
                    updateVendingViewInformation();
                }
            }
        });

        this.mainView.setRestockBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.setItemDropdownItems(mainModel.getItemNameList());
                mainView.displayRestockSelection();
            }
        });

        this.mainView.setStockDropDownListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currIndex = mainView.getItemDropDownSelection();
                mainView.updateCurrentStock(mainModel.getItemStock(currIndex));
            }
        });

        this.mainView.setConfirmRestockBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainModel.getItemStock(mainView.getItemDropDownSelection()) == 10){
                    mainView.displayFeedback("Item slot full!", Color.RED);
                    mainView.updateCurrentStock(mainModel.getItemStock(mainView.getItemDropDownSelection()));

                }
                else{
                    mainModel.addItemStock(mainView.getItemDropDownSelection());
                    System.out.println(mainModel.getItemStock(mainView.getItemDropDownSelection()));

                    mainView.displayFeedback("Stock Added Successfully!", Color.GREEN);
                    mainView.displayRestockSelection();
                    mainView.updateCurrentStock(mainModel.getItemStock(mainView.getItemDropDownSelection()));
                    updateVendingViewInformation();
                }
            }
        });

        this.mainView.setSummaryBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String historySummary = "";
                for (String entry : mainModel.getMachineHistory()){
                    historySummary += entry + "\n";
                }
                System.out.println(historySummary);
                mainView.displayHistory(historySummary);
            }
        });

        this.mainView.setCollectBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double amountWithdrawn = mainModel.withdrawAll();
                if (amountWithdrawn > 0)
                    mainView.displayFeedback("Successfully withdrawn " + amountWithdrawn + " pesos from the machine.", Color.GREEN);
                else
                    mainView.displayFeedback("No money in the machine!", Color.RED);
            }
        });

        this.mainView.setReplenishBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayReplenish();
            }
        });

        this.mainView.setInvBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.clearTables();
                addItemTableInformation();
                mainView.addMoneyInformation(mainModel.getMoneyInformation());
                mainView.displayItemInventory();
            }
        });

        this.mainView.setDisplayItemsBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.clearTables();
                addItemTableInformation();
                mainView.addMoneyInformation(mainModel.getMoneyInformation());
                mainView.displayItemInventory();
            }
        });

        this.mainView.setConfirmMoneySelectBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.updateCurrentDenomAmount(mainModel.getDenomAmount(mainView.getDenomTypeSelection()));
                mainView.displayConfirmReplenish();
            }
        });

        this.mainView.setAddAmtBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainModel.addDenom(mainView.getDenomTypeSelection(), Integer.parseInt(mainView.getPriceTfText()));
                mainView.displayReplenish();
                mainView.displayFeedback("Added x" + mainView.getPriceTfText() + " " + mainView.getDenomSelectionText() + "(s)" + " into the machine.", Color.GREEN );

            }
        });


        // ----------- Action listeners for the Vending Features for a Vending Machine. -----------
        this.mainView.setVendingBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(mainModel.getCurrentVMType());
                try {
                    if(Objects.equals(currentType, "Regular")){
                        vendingView.disposeCurrentWindow();
                    }
                    else {
                        specialVendingView.disposeCurrentWindow();
                    }
                }
                catch (NullPointerException err){

                }

                if(Objects.equals(currentType, "Regular")){
                    vendingView.displayRegularGUI(mainModel.getItemNameList(), mainModel.getItemPriceList(), mainModel.getItemCalorieList(), mainModel.getItemAmountList());
                }
                else {
                    specialVendingView.displaySpecialGUI(mainModel.getItemNameList(), mainModel.getItemPriceList(), mainModel.getItemCalorieList(), mainModel.getItemAmountList(), mainModel.getTemplateNames(), mainModel.getTemplatePrices());
                }

            }
        });

        // Action Listeners for "Regular Vending Machine"
        this.vendingView.setPurchaseButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawMessage = mainModel.getWithdrawMessage();
                String message = "";
                try{
                    message = mainModel.purchaseItem(vendingView.getNumberInput()-1);
                }
                catch (ArrayIndexOutOfBoundsException err){
                    vendingView.displayError("INVALID NUMBER");
                }

                if (Objects.equals(message, "INSUFFICIENT FUNDS") || Objects.equals(message, "CAN'T GET CHANGE")){
                    vendingView.displayErrorWithdraw(message);
                    if (!withdrawMessage.equals(""))
                        vendingView.returnInsertedMoney(withdrawMessage);
                }
                else if (Objects.equals(message, "INVALID NUMBER") || Objects.equals(message, "OUT OF STOCK")){
                    vendingView.displayError(message);
                }
                else {
                    vendingView.updateReceivedItem();
                    if (!message.equals(""))
                        vendingView.updateReceivedChange(message);
                }

                updateVendingViewInformation();


            }
        });

        this.vendingView.setGetButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vendingView.closeReceivedPanel();
            }
        });

        this.vendingView.setCashInsertButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (createdRegularCashPanel)
                    vendingView.displayCashGUI();
                else{
                    vendingView.createCashGUI();
                    createdRegularCashPanel = true;
                }

            }
        });

        this.vendingView.setCoinButtonsListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainModel.addToUserWallet(vendingView.getLastPressedValue());
            }
        });

        this.vendingView.setBillButtonsListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               mainModel.addToUserWallet(vendingView.getLastPressedValue());
            }
        });

        this.vendingView.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vendingView.withdrawInsertedCash();
                mainModel.resetUserWallet();
            }
        });

        // Action Listeners for "Special Vending Machine"
        this.specialVendingView.setPurchaseButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawMessage = mainModel.getWithdrawMessage();
                String message = "";

                if (specialVendingView.getNumberInput() < 10 && specialVendingView.getNumberInput() > 0){

                    message = mainModel.purchaseItem(specialVendingView.getNumberInput()-1);


                    if (Objects.equals(message, "INSUFFICIENT FUNDS") || Objects.equals(message, "CAN'T GET CHANGE")){
                        specialVendingView.displayErrorWithdraw(message);
                        if (!withdrawMessage.equals(""))
                            specialVendingView.returnInsertedMoney(withdrawMessage);
                    }
                    else if (Objects.equals(message, "INVALID NUMBER") || Objects.equals(message, "OUT OF STOCK")){
                        specialVendingView.displayError(message);
                    }
                    else {
                        specialVendingView.updateReceivedItem();
                        if (!message.equals(""))
                            specialVendingView.updateReceivedChange(message);
                    }

                    updateVendingViewInformation();
                }

                else if (specialVendingView.getNumberInput() >= 10 && specialVendingView.getNumberInput() < 13){
                    if (mainModel.canPurchaseSpecialItem(specialVendingView.getNumberInput() - 10))
                        specialVendingView.displayCustomizeMenu(specialVendingView.getNumberInput() - 10);
                    else
                        specialVendingView.displayError("INSUFFICIENT FUNDS");

                }
                else {
                    specialVendingView.displayError("INVALID NUMBER");
                }




            }
        });

        this.specialVendingView.setConfirmRamenButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawMessage = mainModel.getWithdrawMessage();
                String message = "";

                if (specialVendingView.getSelectedBaseIndexes().size() == 0 || specialVendingView.getSelectedAddonIndexes().size() == 0){
                    System.out.println("Ramen");
                }
                else {

                    for (int i : specialVendingView.getSelectedBaseIndexes()){
                        System.out.println(i);
                    }
                    for (int i : specialVendingView.getSelectedAddonIndexes()){
                        System.out.println(i);
                    }

                    System.out.println("Selected: " + (specialVendingView.getNumberInput() - 10));
                    message = mainModel.purchaseSpecialItem(0, specialVendingView.getSelectedBaseIndexes(), specialVendingView.getSelectedAddonIndexes());
                    System.out.println(message);

                    if (Objects.equals(message, "INSUFFICIENT FUNDS") || Objects.equals(message, "CAN'T GET CHANGE")){
                        specialVendingView.displayErrorWithdraw(message);
                        if (!withdrawMessage.equals(""))
                            specialVendingView.returnInsertedMoney(withdrawMessage);
                    }
                    else if (Objects.equals(message, "INVALID NUMBER") || Objects.equals(message, "OUT OF STOCK")){
                        specialVendingView.displayError(message);
                    }
                    else {
                        specialVendingView.prepareRamen(message);
                    }

                    updateVendingViewInformation();

                }
            }
        });

        this.specialVendingView.setConfirmSilogButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawMessage = mainModel.getWithdrawMessage();
                String message = "";

                if (specialVendingView.getSelectedBaseIndexes().size() == 0 || specialVendingView.getSelectedAddonIndexes().size() == 0){
                    System.out.println("Silog");
                }
                else {

                    for (int i : specialVendingView.getSelectedBaseIndexes()){
                        System.out.println(i);
                    }
                    for (int i : specialVendingView.getSelectedAddonIndexes()){
                        System.out.println(i);
                    }

                    System.out.println("Selected: " + (specialVendingView.getNumberInput() - 10));
                    message = mainModel.purchaseSpecialItem(1, specialVendingView.getSelectedBaseIndexes(), specialVendingView.getSelectedAddonIndexes());
                    System.out.println(message);

                    if (Objects.equals(message, "INSUFFICIENT FUNDS") || Objects.equals(message, "CAN'T GET CHANGE")){
                        specialVendingView.displayErrorWithdraw(message);
                        if (!withdrawMessage.equals(""))
                            specialVendingView.returnInsertedMoney(withdrawMessage);
                    }
                    else if (Objects.equals(message, "INVALID NUMBER") || Objects.equals(message, "OUT OF STOCK")){
                        specialVendingView.displayError(message);
                    }
                    else {
                        System.out.println(message);
                        specialVendingView.prepareSilog(message);
                    }

                    updateVendingViewInformation();


                }
            }
        });

        this.specialVendingView.setConfirmHaloHaloButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String withdrawMessage = mainModel.getWithdrawMessage();
                String message = "";

                if (specialVendingView.getSelectedBaseIndexes().size() == 0 || specialVendingView.getSelectedAddonIndexes().size() == 0){
                    System.out.println("Halo-halo");
                }
                else {

                    for (int i : specialVendingView.getSelectedBaseIndexes()){
                        System.out.println(i);
                    }
                    for (int i : specialVendingView.getSelectedAddonIndexes()){
                        System.out.println(i);
                    }

                    System.out.println("Selected: " + (specialVendingView.getNumberInput() - 10));
                    message = mainModel.purchaseSpecialItem(2, specialVendingView.getSelectedBaseIndexes(), specialVendingView.getSelectedAddonIndexes());
                    System.out.println(message);

                    if (Objects.equals(message, "INSUFFICIENT FUNDS") || Objects.equals(message, "CAN'T GET CHANGE")){
                        specialVendingView.displayErrorWithdraw(message);
                        if (!withdrawMessage.equals(""))
                            specialVendingView.returnInsertedMoney(withdrawMessage);
                    }
                    else if (Objects.equals(message, "INVALID NUMBER") || Objects.equals(message, "OUT OF STOCK")){
                        specialVendingView.displayError(message);
                    }
                    else {
                        System.out.println(message);
                        specialVendingView.prepareHaloHalo(message);
                    }

                    updateVendingViewInformation();


                }
            }
        });


        this.specialVendingView.setGetButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialVendingView.closeReceivedPanel();
            }
        });

        this.specialVendingView.setCashInsertButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (createdSpecialCashPanel)
                    specialVendingView.displayCashGUI();
                else{
                    specialVendingView.createCashGUI();
                    createdSpecialCashPanel = true;
                }

            }
        });

        this.specialVendingView.setCoinButtonsListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainModel.addToUserWallet(specialVendingView.getLastPressedValue());
            }
        });

        this.specialVendingView.setBillButtonsListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainModel.addToUserWallet(specialVendingView.getLastPressedValue());
            }
        });

        this.specialVendingView.setCancelButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                specialVendingView.withdrawInsertedCash();
                mainModel.resetUserWallet();
            }
        });

    }


    /**
     * Adds the table information to be displayed in the main view
     */
    public void addItemTableInformation(){
        ArrayList<String> itemInfo = mainModel.getItemInformation();
        ArrayList<String> itemNames = new ArrayList<>();
        ArrayList<String> itemAmounts = new ArrayList<>();
        ArrayList<String> itemPrices = new ArrayList<>();
        ArrayList<String> itemCalories = new ArrayList<>();

        for (String info : itemInfo) {
            String[] parts = info.split(",");
            itemNames.add(parts[0]);
            itemAmounts.add(parts[1]);
            itemPrices.add(parts[2]);
            itemCalories.add(parts[3]);
        }

        for (int i = 0; i < itemInfo.size(); i++){
            mainView.addItemInformation(itemNames.get(i), itemAmounts.get(i), itemPrices.get(i), itemCalories.get(i));
        }
    }

    /**
     * Refreshes certain components of vendingView to account for stock changes
     */
    public void updateVendingViewInformation(){
        if (Objects.equals(currentType, "Regular"))
            vendingView.updateItemInformation(mainModel.getItemNameList(), mainModel.getItemPriceList(), mainModel.getItemCalorieList(), mainModel.getItemAmountList());
        else
            specialVendingView.updateItemInformation(mainModel.getItemNameList(), mainModel.getItemPriceList(), mainModel.getItemCalorieList(), mainModel.getItemAmountList(), mainModel.getTemplatePrices());
    }
}
