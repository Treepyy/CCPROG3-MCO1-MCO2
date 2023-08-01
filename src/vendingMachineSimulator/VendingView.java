package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

public class VendingView {
    private JFrame frame, receivedFrame, cashFrame;
    private JPanel itemPanel, dispensedItemPanel, keypadPanel, coinPanel;
    private JTextField inputField, cashInputField;
    private JButton cashInsertButton;
    private JLabel screenLabel, amountLabel;
    private String currentInput = "", cashInput = "Current Cash: ₱0.0";
    private JLabel dispensedItemLabel, receivedLabel, cashDenominationLabel;
    private JButton confirmButton, getButton, cancelButton, purchaseButton;
    private JButton[] coinButtons = new JButton[6], billButtons = new JButton[6];
    private ArrayList<String> itemNames;
    private ArrayList<Double> itemPrices;
    private ArrayList<Integer> itemCalories, itemAmounts;
    private double currentAmount;
    private int imageWidth = 100;
    private int imageHeight = 100;
    private double lastPressedValue;

    // Path to the directory containing the item/cash images
    private static final String IMAGE_DIRECTORY_PATH = "resources/";

    public VendingView() {
        this.confirmButton = new JButton();
        this.getButton = new JButton();
        this.cashInsertButton = new JButton();
        this.cancelButton = new JButton();
        this.purchaseButton = new JButton();

        for (int i = 0; i < 6; i++){
            this.coinButtons[i] = new JButton();
            this.billButtons[i] = new JButton();
        }

        keypadPanel = new JPanel(new GridLayout(5, 3, 10, 10));
        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(Integer.toString(i));
            numberButton.addActionListener(new NumberInputListener());
            keypadPanel.add(numberButton);
        }
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new NumberInputListener());
        JButton zeroButton = new JButton("0");
        zeroButton.addActionListener(new NumberInputListener());
        purchaseButton.setText("Purchase");

        amountLabel = new JLabel();
        keypadPanel.add(clearButton);
        keypadPanel.add(zeroButton);
        keypadPanel.add(purchaseButton);
        purchaseButton.setEnabled(false);
        keypadPanel.add(new JLabel("Inserted: "));
        keypadPanel.add(amountLabel);
        keypadPanel.add(new JLabel(""));

        dispensedItemPanel = new JPanel();
        receivedFrame = new JFrame();


    }

    public void updateItemInformation(ArrayList<String> itemNames, ArrayList<Double> itemPrices, ArrayList<Integer> itemCalories, ArrayList<Integer> itemAmounts){
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemCalories = itemCalories;
        this.itemAmounts = itemAmounts;

        try {
            updateItemPanel();
            frame.validate();
        }
        catch (NullPointerException e){
            System.out.println("Frame not in view...");
        }
    }

    public void displayRegularGUI(ArrayList<String> itemNames, ArrayList<Double> itemPrices, ArrayList<Integer> itemCalories, ArrayList<Integer> itemAmounts) {

        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemCalories = itemCalories;
        this.itemAmounts = itemAmounts;

        // Create and set up the main frame
        frame = new JFrame("Vending Machine");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        // Create item panel to display items
        itemPanel = new JPanel(new GridLayout(3, 4, 10, 10));

        // Add items to the item panel using the updateItemPanel function
        updateItemPanel();

        // Create panel for keypad and cash insertion button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEditable(false);

        inputPanel.add(keypadPanel, BorderLayout.SOUTH);

        // Create cash insertion button
        cashInsertButton.setText("Insert Cash");

        // Create the screen label to display the current input
        screenLabel = new JLabel(" ", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        screenLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputPanel.add(screenLabel, BorderLayout.NORTH);

        // Add panels to the main frame
        frame.add(itemPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.EAST);
        frame.add(cashInsertButton, BorderLayout.SOUTH);

        // Display the GUI
        frame.setVisible(true);

        // Create containers to display dispensed item image and cash denomination images
        JPanel dispensedItemContainer = new JPanel();
        dispensedItemLabel = new JLabel();
        dispensedItemContainer.add(dispensedItemLabel);

        // Setup for received item popup
        getButton.setText("GET");
        JPanel getButtonContainer = new JPanel();
        getButtonContainer.add(getButton);

        // for items
        dispensedItemPanel.setLayout(new BoxLayout(dispensedItemPanel, BoxLayout.PAGE_AXIS));
        dispensedItemPanel.add(new JLabel("You Received:", SwingConstants.CENTER));
        dispensedItemPanel.add(dispensedItemContainer);
        receivedLabel = new JLabel();
        dispensedItemPanel.add(receivedLabel);

        // adds the GET button
        dispensedItemPanel.add(getButtonContainer);

        // Create the received frame (popup window for dispensed item)
        receivedFrame.setTitle("Successfully Dispensed!");
        receivedFrame.setSize(400, 350);
        receivedFrame.setLayout(new BorderLayout());

        receivedFrame.add(dispensedItemPanel, BorderLayout.NORTH);

        // Create the main cashFrame
        cashFrame = new JFrame("Cash Input");
        cashFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cashFrame.setSize(550, 290);
        cashFrame.setLayout(new BorderLayout());
        amountLabel.setText("₱0.00");
    }

    public void createCashGUI() {

        // Create the panel for coin buttons
        coinPanel = new JPanel(new GridLayout(2, 3, 10, 10));

        String[] coinToolTips = {"1 Centavo", "5 Centavos", "25 Centavos", "1 Peso", "5 Pesos", "10 Pesos"};
        int coinBtnIndex = 0;

        String[] coinValues = {"1cent", "5cent", "25cent", "1peso", "5pesos", "10pesos"};

        for (String coinValue : coinValues) {
            String coinImagePath = "resources/cash/" + coinValue + ".png";
            ImageIcon coinIcon = new ImageIcon(getClass().getResource(coinImagePath));
            Image image = coinIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            coinIcon = new ImageIcon(image);
            this.coinButtons[coinBtnIndex].setIcon(coinIcon);
            this.coinButtons[coinBtnIndex].setToolTipText(coinToolTips[coinBtnIndex]);
            this.coinButtons[coinBtnIndex].addActionListener(new CashButtonListener(convertCoinValue(coinValue)));
            coinPanel.add(this.coinButtons[coinBtnIndex]);
            coinBtnIndex++;
        }

        // Create the panel for bill buttons
        JPanel billPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        int[] billValues = {20, 50, 100, 200, 500, 1000};
        int billBtnIndex = 0;

        for (int billValue : billValues) {
            String billImagePath = "resources/cash/" + billValue + "pesos.png";
            ImageIcon billIcon = new ImageIcon(getClass().getResource(billImagePath));
            Image image = billIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            billIcon = new ImageIcon(image);
            this.billButtons[billBtnIndex].setIcon(billIcon);
            this.billButtons[billBtnIndex].setToolTipText(billValue + " Pesos");
            this.billButtons[billBtnIndex].addActionListener(new CashButtonListener(billValue));
            billPanel.add(this.billButtons[billBtnIndex]);
            billBtnIndex++;
        }

        // Create the main panel for coin and bill panels
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        mainPanel.add(coinPanel);
        mainPanel.add(billPanel);

        // Create the panel for amount and buttons
        JPanel amountPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        this.cancelButton.setText("Withdraw");
        confirmButton.addActionListener(new ConfirmButtonListener());
        amountPanel.add(confirmButton);
        amountPanel.add(cancelButton);

        // Add panels to the main cashFrame
        this.cashFrame.add(mainPanel, BorderLayout.CENTER);
        this.cashFrame.add(amountPanel, BorderLayout.SOUTH);

        this.cashFrame.setResizable(false);
        this.cashFrame.setVisible(true);
    }

    public void displayCashGUI(){
        this.cashFrame.setVisible(true);
    }

    private void updateAmountLabel() {
        this.amountLabel.setText("₱" + String.format("%.2f", currentAmount));
        keypadPanel.revalidate();
        keypadPanel.repaint();
        cashFrame.revalidate();
        cashFrame.repaint();
    }

    private void updateItemPanel(){

        try{

            // Clears previous label
            itemPanel.removeAll();

            // Add items to the item panel (replace with actual image file names and item names)
            for (int i = 1; i <= 9; i++) {
                String itemImageFileName = IMAGE_DIRECTORY_PATH + "/items/item" + i + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
                Image image = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);

                JLabel itemLabel = new JLabel(icon);
                JLabel nameLabel = new JLabel(itemNames.get(i - 1), SwingConstants.CENTER);
                JLabel numberLabel = new JLabel("[" + i + "]", SwingConstants.CENTER);
                JLabel caloriesLabel = new JLabel("Calories: " + itemCalories.get(i - 1), SwingConstants.CENTER);
                JLabel stockLabel;

                if (itemAmounts.get(i - 1) > 0) {
                    stockLabel = new JLabel(itemAmounts.get(i - 1) + " In Stock!", SwingConstants.CENTER);
                    stockLabel.setForeground(Color.GREEN);
                } else {
                    stockLabel = new JLabel("Out of Stock!", SwingConstants.CENTER);
                    stockLabel.setForeground(Color.RED);
                }

                JLabel priceLabel = new JLabel("₱" + String.format("%.2f", itemPrices.get(i - 1)), SwingConstants.CENTER);

                JPanel itemContainer = new JPanel(new BorderLayout());
                itemContainer.add(itemLabel, BorderLayout.CENTER);
                JPanel labelPanel = new JPanel(new GridLayout(5, 1));
                labelPanel.add(nameLabel);
                labelPanel.add(numberLabel);
                labelPanel.add(caloriesLabel);
                labelPanel.add(stockLabel);
                labelPanel.add(priceLabel);
                itemContainer.add(labelPanel, BorderLayout.SOUTH);

                itemPanel.add(itemContainer);
            }
        }

        catch (NullPointerException e){
            System.out.println("Vending machine currently not in view");
        }
    }

    public void updateChangeDenomLabel(){

    }

    private class CashButtonListener implements ActionListener {
        private double value;

        public CashButtonListener(double value) {
            this.value = value;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            currentAmount += value;
            updateAmountLabel();
            updateLastPressedValue(value);
        }
    }

    public void updateLastPressedValue(double value){
        this.lastPressedValue = value;
        System.out.println(lastPressedValue);
    }

    public double getLastPressedValue(){
        return lastPressedValue;
    }

    public void setCoinButtonsListeners(ActionListener actionListener){
        for (JButton coinButton : this.coinButtons){
            coinButton.addActionListener(actionListener);
        }
    }

    public void setBillButtonsListeners(ActionListener actionListener){
        for (JButton billButton : this.billButtons){
            billButton.addActionListener(actionListener);
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cashFrame.setVisible(false);
        }
    }

    public void withdrawInsertedCash(){
        cashFrame.setVisible(false);
        currentAmount = 0.0;
        updateAmountLabel();
        amountLabel.validate();
    }

    public void setCancelButtonListener(ActionListener actionListener){
        this.cancelButton.addActionListener(actionListener);
    }

    private double convertCoinValue(String coinValue){

        switch (coinValue){
            case "1cent" -> {
                return 0.01;
            }
            case "5cent" -> {
                return 0.05;
            }
            case "25cent" -> {
                return 0.25;
            }
            case "1peso" -> {
                return 1.00;
            }
            case "5pesos" -> {
                return 5.00;
            }
            case "10pesos" -> {
                return 10.00;
            }

        }

        return 0;
    }

    private void updateInputField() {
        // inputField.setText(currentInput);
        screenLabel.setForeground(Color.BLACK);
        if (currentInput.trim().length() < 3)
            screenLabel.setText(currentInput);
    }

    private class NumberInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Clear")) {
                purchaseButton.setEnabled(false);
                currentInput = " ";
            } else {
                purchaseButton.setEnabled(true);
                currentInput += command;
            }
            updateInputField();
        }
    }

    public void updateReceivedItem(){

        this.currentAmount = 0.0;
        updateAmountLabel();
        this.amountLabel.validate();

        try{
            // Get the current input and convert it to an integer
            int selectedItemId = Integer.parseInt(currentInput.trim());
            if (selectedItemId <= 9){
                // Display the corresponding item image in the dispensedItemContainer
                String itemImageFileName = IMAGE_DIRECTORY_PATH + "items/item" + selectedItemId + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);

                // Update necessary fields
                dispensedItemLabel.setIcon(icon);
                receivedLabel.setText("1x " + itemNames.get(selectedItemId-1));
                receivedLabel.setHorizontalAlignment(SwingConstants.CENTER);

                receivedFrame.setVisible(true);
                currentInput = " ";
                updateInputField();
            }

        }
        catch (NullPointerException err){
            displayError("INVALID NUMBER");
        }
    }

    // TODO: convert money to img icons with tooltips
    public void updateReceivedChange(String message){

        dispensedItemPanel.remove(getButton);
        String[] messageParts = message.split("-");

        JPanel cashDenominationContainer = new JPanel(new GridLayout(messageParts.length + 1, 1, 5, 5));
        cashDenominationContainer.add(new JLabel("Your Change: ", SwingConstants.CENTER));

        for (String parts : messageParts){
            JLabel cashDenominationLabel = new JLabel(parts, SwingConstants.CENTER);
            cashDenominationContainer.add(cashDenominationLabel);
        }

        // adds the change
        dispensedItemPanel.add(cashDenominationContainer);
        dispensedItemPanel.add(getButton);

    }

    public void returnInsertedMoney(String message){

        String[] messageParts = message.split("-");

        JFrame insertedMoneyFrame = new JFrame();
        insertedMoneyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel returnPanel = new JPanel(new GridLayout(3, 1, 5, 5));

        // Create the received frame (popup window for dispensed item)
        insertedMoneyFrame.setTitle("Money Returned!");
        insertedMoneyFrame.setSize(400, 350);
        insertedMoneyFrame.setLayout(new BorderLayout());

        returnPanel.add(new JLabel("Your Change: ", SwingConstants.CENTER));
        JPanel cashDenominationContainer = new JPanel(new GridLayout(messageParts.length + 1, 1, 5, 5));

        for (String parts : messageParts){
            JLabel cashDenominationLabel = new JLabel(parts, SwingConstants.CENTER);
            cashDenominationContainer.add(cashDenominationLabel);
        }

        JPanel getButtonContainer = new JPanel();
        JButton getReturnedButton = new JButton("GET");
        getReturnedButton.setAlignmentX(SwingConstants.CENTER);
        getReturnedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertedMoneyFrame.setVisible(false);
            }
        });
        getButtonContainer.add(getReturnedButton);

        // adds the change and GET button
        returnPanel.add(cashDenominationContainer);
        returnPanel.add(getButtonContainer);

        insertedMoneyFrame.add(returnPanel);
        insertedMoneyFrame.setVisible(true);
        insertedMoneyFrame.validate();

    }

    public void displayError(String errorMessage){
        screenLabel.setText(errorMessage);
        screenLabel.setForeground(Color.RED);
        screenLabel.validate();
        temporaryKeypadLock();
    }

    public void displayErrorWithdraw(String errorMessage){
        screenLabel.setText(errorMessage);
        screenLabel.setForeground(Color.RED);
        screenLabel.validate();
        withdrawInsertedCash();
        temporaryKeypadLock();
    }

    public void displaySuccess(String message){
        screenLabel.setText(message);
        screenLabel.setForeground(Color.GREEN);
        screenLabel.validate();
        updateReceivedItem();
        temporaryKeypadLock();
    }

    private void temporaryKeypadLock(){  // temporarily disables keypad while errors are present
        disableKeypad();
        Timer t = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableKeypad();
                purchaseButton.setEnabled(false);
                currentInput = " ";
                updateInputField();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void disableKeypad(){
        setPanelEnabled(this.keypadPanel, false);
    }

    private void enableKeypad(){
        setPanelEnabled(this.keypadPanel, true);
    }

    void setPanelEnabled(JPanel panel, Boolean isEnabled) { // Function based on answer of Kesavamoorthi on StackOverflow: https://stackoverflow.com/questions/19324918/how-to-disable-all-components-in-a-jpanel
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    public void closeReceivedPanel(){
        receivedFrame.setVisible(false);
        receivedFrame.dispose();
    }

    public void setPurchaseButtonListener(ActionListener actionListener){
        this.purchaseButton.addActionListener(actionListener);
    }

    public void setGetButtonListener(ActionListener actionListener){
        this.getButton.addActionListener(actionListener);
    }

    public void setCashInsertButtonListener(ActionListener actionListener){
        this.cashInsertButton.addActionListener(actionListener);
    }

    public int getNumberInput(){
        return Integer.parseInt(currentInput.trim());
    }
}

