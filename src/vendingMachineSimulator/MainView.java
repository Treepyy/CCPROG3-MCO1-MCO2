package vendingMachineSimulator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GUI setup for the machine setup
 * @author Vance Gyan M. Robles
 */
public class MainView {

    private JFrame mainFrame, errorFrame, historyFrame;
    private JLabel welcomelbl, feedbackLbl, errorLbl, currentStockLbl, currentDenomAmtLbl;
    private JTextField priceTf;
    private JButton createBtn, testBtn, exitBtn, regularBtn, specialBtn, createHomeBtn, testHomeBtn, vendingBtn, maintBtn;
    private JButton restockBtn, setPriceBtn, collectBtn, replenishBtn, summaryBtn, invBtn, maintReturnBtn, optionReturnBtn, confirmBtn, changeBtn;
    private JButton overwriteYesBtn, overwriteNoBtn, okayBtn, confirmStockSelectBtn, confirmMoneySelectBtn, confirmRestockBtn;
    private JButton displayItemsBtn, displayMoneyBtn, addAmtBtn;
    private JPanel panel;
    private JComboBox itemNames, denominationTypes;
    private JTable itemTable, moneyTable;
    private DefaultTableModel itemTableModel, moneyTableModel;
    private JScrollPane scrollPane;

    /**
     * GUI setup for the main view
     */
    public MainView() {
        // Frames setup
        this.mainFrame = new JFrame("Vending Machine Factory");

        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.mainFrame.setSize(350, 400);

        this.errorFrame = new JFrame("");
        this.errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.errorFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.errorFrame.setSize(200, 200);
        this.errorLbl = new JLabel("Error!");

        this.welcomelbl = new JLabel("Welcome!");
        this.feedbackLbl = new JLabel();
        this.feedbackLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.feedbackLbl.setPreferredSize(new Dimension(300, 30));

        this.createBtn = new JButton("Create a Vending Machine");
        this.createBtn.setPreferredSize(new Dimension(220, 30));
        this.testBtn = new JButton("Test a Vending Machine");
        this.testBtn.setPreferredSize(new Dimension(220, 30));
        this.exitBtn = new JButton("Exit");
        this.exitBtn.setPreferredSize(new Dimension(220, 30));

        this.panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(this.welcomelbl);
        panel.setPreferredSize(new Dimension(220, 30));
        this.mainFrame.add(panel);

        this.mainFrame.add(createBtn);
        this.mainFrame.add(testBtn);
        this.mainFrame.add(exitBtn);

        this.mainFrame.setVisible(true);
        this.errorFrame.setVisible(false);

        // Buttons and text field setup
        this.regularBtn = new JButton("Regular Vending Machine");
        this.regularBtn.setPreferredSize(new Dimension(220, 30));
        this.specialBtn = new JButton("Special Vending Machine");
        this.specialBtn.setPreferredSize(new Dimension(220, 30));
        this.createHomeBtn = new JButton("Go Back");
        this.createHomeBtn.setPreferredSize(new Dimension(220, 30));

        this.vendingBtn = new JButton("Vending Features");
        this.vendingBtn.setPreferredSize(new Dimension(220, 30));
        this.maintBtn = new JButton("Maintenance Features");
        this.maintBtn.setPreferredSize(new Dimension(220, 30));
        this.testHomeBtn = new JButton("Go Back");
        this.testHomeBtn.setPreferredSize(new Dimension(220, 30));

        this.restockBtn = new JButton("Stock Item");
        this.restockBtn.setPreferredSize(new Dimension(220, 30));
        this.setPriceBtn = new JButton("Set Price");
        this.setPriceBtn.setPreferredSize(new Dimension(220, 30));
        this.collectBtn = new JButton("Collect Payment");
        this.collectBtn.setPreferredSize(new Dimension(220, 30));
        this.replenishBtn = new JButton("Replenish Money");
        this.replenishBtn.setPreferredSize(new Dimension(220, 30));
        this.summaryBtn = new JButton("Machine History");
        this.summaryBtn.setPreferredSize(new Dimension(220, 30));
        this.invBtn = new JButton("Display Inventory");
        this.invBtn.setPreferredSize(new Dimension(220, 30));
        this.maintReturnBtn = new JButton("Go Back");
        this.maintReturnBtn.setPreferredSize(new Dimension(220, 30));
        this.optionReturnBtn = new JButton("Go Back");
        this.optionReturnBtn.setPreferredSize(new Dimension(220, 30));

        this.confirmBtn = new JButton("Confirm");
        this.confirmBtn.setPreferredSize(new Dimension(160, 30));
        this.changeBtn = new JButton("Change Price!");
        this.changeBtn.setPreferredSize(new Dimension(200, 30));

        this.overwriteYesBtn = new JButton("Yes");
        this.overwriteYesBtn.setPreferredSize(new Dimension(100, 30));
        this.overwriteNoBtn = new JButton("No");
        this.overwriteNoBtn.setPreferredSize(new Dimension(100, 30));

        this.okayBtn = new JButton("Okay");
        this.okayBtn.setPreferredSize(new Dimension(100, 30));

        this.priceTf = new JTextField();
        this.priceTf.setPreferredSize(new Dimension(220, 30));

        String[] types = {"1 Centavo Coin", "5 Centavo Coin", "25 Centavo Coin", "1 Peso Coin", "5 Peso Coin", "10 Peso Coin",
                "20 Peso Bill", "50 Peso Bill", "100 Peso Bill", "200 Peso Bill", "500 Peso Bill", "1000 Peso Bill"};
        this.denominationTypes = new JComboBox(types);

        this.confirmStockSelectBtn = new JButton("Confirm");
        this.confirmStockSelectBtn.setPreferredSize(new Dimension(100, 30));
        this.confirmRestockBtn = new JButton("Restock!");
        this.confirmRestockBtn.setPreferredSize(new Dimension(100, 30));
        this.currentStockLbl = new JLabel("");

        this.confirmMoneySelectBtn = new JButton("Confirm");
        this.confirmMoneySelectBtn.setPreferredSize(new Dimension(100, 30));

        this.itemNames = new JComboBox<>();

        this.itemTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.itemTableModel.addColumn("Item Name");
        this.itemTableModel.addColumn("Quantity");
        this.itemTableModel.addColumn("Price");
        this.itemTableModel.addColumn("Calories");

        this.itemTable = new JTable(itemTableModel);

        this.moneyTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.moneyTableModel.addColumn("P1000");
        this.moneyTableModel.addColumn("P500");
        this.moneyTableModel.addColumn("P200");
        this.moneyTableModel.addColumn("P100");
        this.moneyTableModel.addColumn("P50");
        this.moneyTableModel.addColumn("P20");
        this.moneyTableModel.addColumn("P10");
        this.moneyTableModel.addColumn("P5");
        this.moneyTableModel.addColumn("P1");
        this.moneyTableModel.addColumn("25C");
        this.moneyTableModel.addColumn("5C");
        this.moneyTableModel.addColumn("1C");

        this.moneyTable = new JTable(moneyTableModel);

        this.scrollPane = new JScrollPane(itemTable);

        this.displayItemsBtn = new JButton("Display Items");
        this.displayItemsBtn.setPreferredSize(new Dimension(200, 30));
        this.displayMoneyBtn = new JButton("Display Money");
        this.displayMoneyBtn.setPreferredSize(new Dimension(200, 30));

        this.currentDenomAmtLbl = new JLabel("");
        this.addAmtBtn = new JButton("Add Amount");

        this.historyFrame = new JFrame();

    }

    /**
     * Sets the items in the dropdown list for item selection.
     * @param itemNameList The list of item names to be set in the dropdown.
     */
    public void setItemDropdownItems(ArrayList<String> itemNameList){
        this.itemNames = new JComboBox(itemNameList.toArray());
    }

    /**
     * Gets the selected index from the item dropdown list.
     * @return The selected index from the item dropdown list.
     */
    public int getItemDropDownSelection(){
        return itemNames.getSelectedIndex();
    }

    /**
     * Gets the selected index from the denomination types dropdown list.
     * @return The selected index from the denomination types dropdown list.
     */
    public int getDenomTypeSelection(){
        return denominationTypes.getSelectedIndex();
    }
    /**
     * Gets the selected denomination (text) from the denomination types dropdown list.
     * @return The selected denomination (text) from the denomination types dropdown list.
     */
    public String getDenomSelectionText(){
        return (String) denominationTypes.getSelectedItem();
    }

    /**
     * Sets the listener for the create button.
     * @param actionListener The ActionListener to be set for the "Create" button.
     */
    public void setCreateBtnListener(ActionListener actionListener) {
        this.createBtn.addActionListener(actionListener);
    }

    /**
     * Sets the listener for the test button.
     * @param actionListener The ActionListener to be set for the "Test" button.
     */
    public void setTestBtnListener(ActionListener actionListener) {
        this.testBtn.addActionListener(actionListener);
    }

    /**
     * Sets the listener for the exit button.
     * @param actionListener The ActionListener to be set for the "Exit" button.
     */
    public void setExitBtnListener(ActionListener actionListener) {
        this.exitBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Regular" button.
     *
     * @param actionListener The ActionListener to be set for the "Regular" button.
     */
    public void setRegularBtnListener(ActionListener actionListener) {
        this.regularBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Special" button.
     *
     * @param actionListener The ActionListener to be set for the "Special" button.
     */
    public void setSpecialBtnListener(ActionListener actionListener) {
        this.specialBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Home" button.
     *
     * @param actionListener The ActionListener to be set for the "Home" button.
     */
    public void setHomeBtnListener(ActionListener actionListener) {
        this.createHomeBtn.addActionListener(actionListener);
        this.testHomeBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Maint" button.
     *
     * @param actionListener The ActionListener to be set for the "Maint" button.
     */
    public void setMaintBtnListener(ActionListener actionListener){
        this.maintBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Maint Return" button.
     *
     * @param actionListener The ActionListener to be set for the "Maint Return" button.
     */
    public void setMaintReturnBtnListener(ActionListener actionListener){
        this.maintReturnBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Option Return" button.
     *
     * @param actionListener The ActionListener to be set for the "Option Return" button.
     */
    public void setOptionReturnBtnListener(ActionListener actionListener){
        this.optionReturnBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Restock" button.
     *
     * @param actionListener The ActionListener to be set for the "Restock" button.
     */
    public void setRestockBtnListener(ActionListener actionListener){
        this.restockBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Confirm Restock" button.
     *
     * @param actionListener The ActionListener to be set for the "Confirm Restock" button.
     */
    public void setConfirmRestockBtnListener(ActionListener actionListener){
        this.confirmRestockBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Set Price" button.
     *
     * @param actionListener The ActionListener to be set for the "Set Price" button.
     */
    public void setSetPriceBtnListener(ActionListener actionListener){
        this.setPriceBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Confirm" button.
     *
     * @param actionListener The ActionListener to be set for the "Confirm" button.
     */
    public void setConfirmBtnListener(ActionListener actionListener){
        this.confirmBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Change" button.
     *
     * @param actionListener The ActionListener to be set for the "Change" button.
     */
    public void setChangeBtnListener(ActionListener actionListener){
        this.changeBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Yes" button in the overwrite popup.
     *
     * @param actionListener The ActionListener to be set for the "Yes" button in the overwrite popup
     */

    public void setOverwriteYesBtnListener(ActionListener actionListener){
        this.overwriteYesBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "No" button in the overwrite popup.
     *
     * @param actionListener The ActionListener to be set for the "No" button in the overwrite popup
     */
    public void setOverwriteNoBtnListener(ActionListener actionListener){
        this.overwriteNoBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Okay" button.
     *
     * @param actionListener The ActionListener to be set for the "Okay" button.
     */
    public void setOkayBtnListener(ActionListener actionListener){
        this.okayBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Collect" button.
     *
     * @param actionListener The ActionListener to be set for the "Collect" button.
     */
    public void setCollectBtnListener(ActionListener actionListener){
        this.collectBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Replenish" button.
     *
     * @param actionListener The ActionListener to be set for the "Replenish" button.
     */
    public void setReplenishBtnListener(ActionListener actionListener){
        this.replenishBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Confirm Money Select" button.
     *
     * @param actionListener The ActionListener to be set for the "Confirm Money Select" button.
     */
    public void setConfirmMoneySelectBtnListener(ActionListener actionListener){
        this.confirmMoneySelectBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Add Amount" button.
     *
     * @param actionListener The ActionListener to be set for the "Add Amount" button.
     */
    public void setAddAmtBtnListener(ActionListener actionListener){
        this.addAmtBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "History" button.
     *
     * @param actionListener The ActionListener to be set for the "History" button.
     */
    public void setSummaryBtnListener(ActionListener actionListener){
        this.summaryBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Display Inventory" button.
     *
     * @param actionListener The ActionListener to be set for the "Display Inventory" button.
     */
    public void setInvBtnListener(ActionListener actionListener){
        this.invBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the stock dropdown.
     *
     * @param actionListener The ActionListener to be set for the stock dropdown.
     */
    public void setStockDropDownListener(ActionListener actionListener){
        this.itemNames.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Display Items" button.
     *
     * @param actionListener The ActionListener to be set for the "Display Items" button.
     */
    public void setDisplayItemsBtnListener(ActionListener actionListener){
        this.displayItemsBtn.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the "Vending Features" button.
     *
     * @param actionListener The ActionListener to be set for the "Vending Features" button.
     */
    public void setVendingBtnListener(ActionListener actionListener){
        this.vendingBtn.addActionListener(actionListener);
    }

    /**
     * Displays feedback on the UI with the given text and text color.
     *
     * @param feedback The feedback message to be displayed.
     * @param textColor The color of the feedback text.
     */
    public void displayFeedback(String feedback, Color textColor){
        this.feedbackLbl.setForeground(textColor);
        this.feedbackLbl.setText(feedback);
    }

    /**
     * Retrieves the text from the price text field.
     *
     * @return The text from the price text field.
     */
    public String getPriceTfText() {
        return this.priceTf.getText();
    }


    /**
     * Clears the feedback label on the UI.
     */
    public void clearFeedback() {
        this.feedbackLbl.setText("");
    }

    /**
     * Displays the main frame view.
     */
    public void displayMain(){
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.panel.add(new JLabel("Welcome!"));
        this.mainFrame.add(panel);
        this.mainFrame.add(createBtn);
        this.mainFrame.add(testBtn);
        this.mainFrame.add(exitBtn);
        this.mainFrame.validate();
    }


    /**
     * Displays the test frame view.
     */
    public void displayTest(){
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.panel.add(new JLabel("What would you like to test?"));
        this.mainFrame.add(panel);
        this.mainFrame.add(vendingBtn);
        this.mainFrame.add(maintBtn);
        this.mainFrame.add(testHomeBtn);
        this.mainFrame.validate();
    }

    /**
     * Displays the create frame view.
     */
    public void displayCreate(){
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.panel.add(new JLabel("What would you like to create?"));
        this.mainFrame.add(panel);
        this.mainFrame.add(regularBtn);
        this.mainFrame.add(specialBtn);
        this.mainFrame.add(createHomeBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();
    }

    /**
     * Displays an error message in the error frame.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void displayError(String errorMessage){
        this.errorFrame.setVisible(true);
        this.errorFrame.setTitle("Error!");
        this.errorFrame.remove(errorLbl);
        this.errorLbl.setText(errorMessage);
        this.errorFrame.add(errorLbl);
        this.errorFrame.add(okayBtn);
        this.errorFrame.validate();
    }

    /**
     * Displays a confirmation message in the error frame for overwriting existing machine.
     */
    public void displayOverwriteConfirmation(){
        this.errorFrame.setVisible(true);
        this.errorFrame.setTitle("Confirmation");
        this.errorFrame.remove(errorLbl);
        this.errorLbl.setText("Overwrite existing machine?");
        this.errorFrame.add(errorLbl);
        this.errorFrame.add(overwriteYesBtn);
        this.errorFrame.add(overwriteNoBtn);
        this.errorFrame.validate();
    }

    /**
     * Closes the error frame and removes its contents.
     */
    public void closeErrorFrame(){
        this.errorFrame.setVisible(false);
        this.errorFrame.getContentPane().removeAll();
        this.errorFrame.dispose();
    }

    /**
     * Displays the maintenance frame view.
     */
    public void displayMaint(){
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.panel.add(new JLabel("Maintenance Features"));
        this.mainFrame.add(panel);
        this.mainFrame.add(restockBtn);
        this.mainFrame.add(setPriceBtn);
        this.mainFrame.add(collectBtn);
        this.mainFrame.add(replenishBtn);
        this.mainFrame.add(summaryBtn);
        this.mainFrame.add(invBtn);
        this.mainFrame.add(maintReturnBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();
    }


    /**
     * Displays the restock selection view.
     */
    public void displayRestockSelection(){
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.confirmRestockBtn.setEnabled(true);
        this.itemNames.setEnabled(true);
        this.panel.add(new JLabel("Restock Items"));
        this.mainFrame.add(panel);
        this.mainFrame.add(optionReturnBtn);
        this.mainFrame.add(new JLabel("Select a Product: "));
        this.mainFrame.add(itemNames);
        this.mainFrame.add(currentStockLbl);
        this.currentStockLbl.setVisible(false);
        this.mainFrame.add(confirmRestockBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();
    }

    /**
     * Updates the current stock label with the given current stock value.
     *
     * @param currentStock The current stock value to be displayed.
     */
    public void updateCurrentStock(int currentStock){
        this.currentStockLbl.setVisible(true);
        this.currentStockLbl.setText("Current Stock: " + currentStock);
    }

    /**
     * Displays the product selection view for setting a new price.
     */
    public void displayProductSelection(){
        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.confirmBtn.setEnabled(true);
        this.itemNames.setEnabled(true);
        this.panel.add(new JLabel("Set a New Price"));
        this.mainFrame.add(panel);
        this.mainFrame.add(optionReturnBtn);
        this.mainFrame.add(new JLabel("Select a Product: "));
        this.mainFrame.add(itemNames);
        this.mainFrame.add(confirmBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();
    }

    /**
     * Displays the set price view with the provided old item price.
     *
     * @param oldItemPrice The old price of the item.
     */
    public void displaySetPrice(double oldItemPrice){
        this.mainFrame.remove(confirmBtn);
        this.itemNames.setEnabled(false);
        this.feedbackLbl.setText("");
        this.mainFrame.add(new JLabel("Old Price: " + oldItemPrice));
        this.mainFrame.remove(priceTf);
        this.priceTf.setText("");
        this.mainFrame.add(priceTf);
        this.mainFrame.add(new JLabel("Input new Price: "));
        this.mainFrame.add(priceTf);
        this.mainFrame.add(changeBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();
    }

    /**
     * Displays the replenish view with coin/bill selection.
     */
    public void displayReplenish(){
        // display selection of coins/bills, to be converted to image format

        this.mainFrame.getContentPane().removeAll();
        this.mainFrame.getContentPane().repaint();
        this.panel.removeAll();
        this.denominationTypes.setEnabled(true);
        this.confirmBtn.setEnabled(true);
        this.itemNames.setEnabled(true);
        this.panel.add(new JLabel("Replenish Machine Funds"));
        this.mainFrame.add(panel);
        this.mainFrame.add(optionReturnBtn);
        this.mainFrame.add(new JLabel("What to Insert? "));
        this.mainFrame.add(denominationTypes);
        this.mainFrame.add(confirmMoneySelectBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();

    }

    /**
     * Updates the current denomination amount label with the given current amount.
     *
     * @param currentAmt The current amount to be displayed.
     */
    public void updateCurrentDenomAmount(int currentAmt){
        this.currentDenomAmtLbl.setText("Current Amount: " + currentAmt);
    }

    /**
     * Displays the confirmation view for replenishing.
     */
    public void displayConfirmReplenish(){
        this.mainFrame.remove(confirmMoneySelectBtn);
        this.denominationTypes.setEnabled(false);
        this.feedbackLbl.setText("");
        this.mainFrame.add(currentDenomAmtLbl);
        this.mainFrame.remove(priceTf);
        this.priceTf.setText("");
        this.mainFrame.add(new JLabel("Input number of bills/coins to add: "));
        this.mainFrame.add(priceTf);
        this.mainFrame.add(addAmtBtn);
        this.mainFrame.add(feedbackLbl);
        this.mainFrame.validate();
    }

    /**
     * Displays the machine history in a separate frame.
     *
     * @param historyContent The content of the machine history to be displayed.
     */
    public void displayHistory(String historyContent){

        this.historyFrame.setSize(800, 800);
        this.historyFrame.setTitle("Machine History");
        this.historyFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel historyPanel = new JPanel(new GridLayout(2,1,10,10));

        JTextPane historyTextArea = new JTextPane();
        historyTextArea.setEditable(false);
        historyTextArea.setText(historyContent);

        JPanel closeBtnContainer = new JPanel();
        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                historyFrame.dispose();
            }
        });

        closeBtnContainer.add(closeBtn);
        historyPanel.add(historyTextArea);
        historyPanel.add(closeBtnContainer);
        historyPanel.repaint();
        historyPanel.validate();

        this.historyFrame.add(historyPanel);
        this.historyFrame.validate();
        this.historyFrame.setVisible(true);



    }

    /**
     * Displays the item inventory in a separate frame with a toggle button for item/money inventory.
     */
    public void displayItemInventory(){
        // display inventory in table format, add button for toggling between item and money inventory

        JFrame inventory = new JFrame("Item Inventory");
        inventory.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        inventory.setSize(600,600);

        JPanel invPanel = new JPanel(new FlowLayout());
        invPanel.add(scrollPane);
        invPanel.add(itemTable, BorderLayout.CENTER);
        invPanel.add(moneyTable, BorderLayout.CENTER);

        JButton switchButton = new JButton("Switch Inventory");
        switchButton.setPreferredSize(new Dimension(150, 30));
        invPanel.add(switchButton, BorderLayout.CENTER);
        final String[] currentInv = {"Items"};
        switchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentInv[0] == "Items"){
                    scrollPane.setViewportView(moneyTable);
                    scrollPane.setVisible(true);
                    itemTable.setVisible(false);
                    moneyTable.setVisible(true);
                    currentInv[0] = "Money";
                }
                else {
                    scrollPane.setViewportView(itemTable);
                    scrollPane.setVisible(true);
                    itemTable.setVisible(true);
                    moneyTable.setVisible(false);
                    currentInv[0] = "Items";
                }
            }
        });

        this.scrollPane.setViewportView(itemTable);
        this.scrollPane.setVisible(true);
        this.itemTable.setVisible(true);
        this.moneyTable.setVisible(false);

        inventory.add(invPanel);
        inventory.validate();
        inventory.setVisible(true);

    }

    /**
     * Adds a new row to the item table with the provided information.
     *
     * @param itemName The name of the item to be added to the table.
     * @param quantity The quantity of the item to be added to the table.
     * @param price The price of the item to be added to the table.
     * @param calories The calorie information of the item to be added to the table.
     */
    public void addItemInformation(String itemName, String quantity, String price, String calories) {
        // Add a new row to the table with the given information
        this.itemTableModel.addRow(new Object[]{itemName, quantity, price, calories});
    }

    /**
     * Adds a new row to the money table with the provided money denominations.
     *
     * @param amountInformation The list of money denominations to be added to the table.
     */
    public void addMoneyInformation(ArrayList<Integer> amountInformation){
        this.moneyTableModel.addRow(amountInformation.toArray());
    }

    /**
     * Clears the item and money tables.
     */
    public void clearTables(){
        this.itemTableModel.setRowCount(0);
        this.moneyTableModel.setRowCount(0);
    }

}
