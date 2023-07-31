package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class MainController {

    private MainView mainView;
    private VendingView vendingView;
    private MainModel mainModel;
    private String type;
    private boolean createdCashPanel = false;

    MainController(MainView mainView, VendingView vendingView, MainModel mainModel){

        this.mainView = mainView;
        this.mainModel = mainModel;
        this.vendingView = vendingView;

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
                    mainView.displayFeedback("Successfully created a Regular Vending Machine!", Color.GREEN);
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
                if (Objects.equals(type, "Regular"))
                    mainModel.createRegularVM();
                else
                    mainModel.createSpecialVM();

                mainView.closeErrorFrame();
                mainView.displayFeedback("Successfully created a " + type + " Vending Machine!", Color.GREEN);
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
                    mainView.displayFeedback("Successfully created a Special Vending Machine!", Color.GREEN);
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
                mainView.displayItemInventory();
            }
        });

        this.mainView.setDisplayItemsBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.clearTables();
                addItemTableInformation();
                mainView.displayItemInventory();
            }
        });

        this.mainView.setDisplayMoneyBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.clearTables();
                mainView.addMoneyInformation(mainModel.getMoneyInformation());
                mainView.displayMoneyInventory();
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

                // "Test Vending Features" option should be disabled while a vending machine window is open, re-enable it only once the current vending machine instance has been closed.
                System.out.println(mainModel.getCurrentVMType());

                if(Objects.equals(mainModel.getCurrentVMType(), "vendingMachineSimulator.RegularVendingMachine")){
                    vendingView.displayRegularGUI(mainModel.getItemNameList(), mainModel.getItemPriceList(), mainModel.getItemCalorieList(), mainModel.getItemAmountList());
                }
                else {

                }

            }
        });

        this.vendingView.setPurchaseButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = mainModel.purchaseItem(vendingView.getNumberInput()-1);
                System.out.println(message);
                if (Objects.equals(message, "INSUFFICIENT FUNDS") || Objects.equals(message, "CAN'T GET CHANGE")){
                    vendingView.displayErrorWithdraw(message);
                }
                else if (Objects.equals(message, "SUCCESS")){
                    vendingView.displaySuccess(message);
                }
                else {
                    vendingView.displayError(message);
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
                if (createdCashPanel)
                    vendingView.displayCashGUI();
                else{
                    vendingView.createCashGUI();
                    createdCashPanel = true;
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


    }



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

    public void updateVendingViewInformation(){
        vendingView.updateItemInformation(mainModel.getItemNameList(), mainModel.getItemPriceList(), mainModel.getItemCalorieList(), mainModel.getItemAmountList());
    }
}
