package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SpecialVendingView {
    private JFrame frame, receivedFrame, cashFrame;
    private JPanel itemPanel, dispensedItemPanel, keypadPanel, coinPanel;
    private JTextField inputField, cashInputField;
    private JButton cashInsertButton, confirmCustomizedButton;
    private JLabel screenLabel, amountLabel;
    private String currentInput = "", cashInput = "Current Cash: ₱0.0";
    private JLabel dispensedItemLabel, receivedLabel, cashDenominationLabel;
    private JButton confirmButton, getButton, cancelButton, purchaseButton;
    private JButton[] coinButtons = new JButton[6], billButtons = new JButton[6];
    private ArrayList<String> itemNames, templateNames;
    private ArrayList<Double> itemPrices;
    private ArrayList<Integer> itemCalories, itemAmounts, selectedAddonIndexes, selectedBaseIndexes;
    private double currentAmount;
    private int imageWidth = 100;
    private int imageHeight = 100;
    private double lastPressedValue;

    // Path to the directory containing the item/cash images
    private static final String IMAGE_DIRECTORY_PATH = "resources/";

    public SpecialVendingView() {
        this.confirmButton = new JButton();
        this.getButton = new JButton();
        this.cashInsertButton = new JButton();
        this.cancelButton = new JButton();
        this.purchaseButton = new JButton();

        for (int i = 0; i < 6; i++) {
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

        confirmCustomizedButton = new JButton("");


    }

    public void updateItemInformation(ArrayList<String> itemNames, ArrayList<Double> itemPrices, ArrayList<Integer> itemCalories, ArrayList<Integer> itemAmounts) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemCalories = itemCalories;
        this.itemAmounts = itemAmounts;

        try {
            updateItemPanel();
            frame.validate();
        } catch (NullPointerException e) {
            System.out.println("Frame not in view...");
        }
    }

    public void displaySpecialGUI(ArrayList<String> itemNames, ArrayList<Double> itemPrices, ArrayList<Integer> itemCalories, ArrayList<Integer> itemAmounts, ArrayList<String> templateNames) {

        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemCalories = itemCalories;
        this.itemAmounts = itemAmounts;
        this.templateNames = templateNames;

        // Create and set up the main frame
        frame = new JFrame("Special Vending Machine");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new BorderLayout());

        // Create item panel to display items
        itemPanel = new JPanel(new GridLayout(4, 4, 10, 10));

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

    public void displayCashGUI() {
        this.cashFrame.setVisible(true);
    }

    private void updateAmountLabel() {
        this.amountLabel.setText("₱" + String.format("%.2f", currentAmount));
        keypadPanel.revalidate();
        keypadPanel.repaint();
        cashFrame.revalidate();
        cashFrame.repaint();
    }

    private void updateItemPanel() {

        try {

            // Clears previous label
            itemPanel.removeAll();

            // Add items to the item panel (replace with actual image file names and item names)
            for (int i = 1; i <= 12; i++) {
                String itemImageFileName = IMAGE_DIRECTORY_PATH + "/items/item" + i + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
                Image image = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);

                if (i < 10){
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
                else {
                    JLabel itemLabel = new JLabel(icon);
                    JLabel nameLabel = new JLabel(templateNames.get(i - 10), SwingConstants.CENTER);
                    JLabel numberLabel = new JLabel("[" + i + "]", SwingConstants.CENTER);
                    JLabel stockLabel = new JLabel("Customizable", SwingConstants.CENTER);
                    stockLabel.setForeground(Color.MAGENTA);

                    JPanel itemContainer = new JPanel(new BorderLayout());
                    itemContainer.add(itemLabel, BorderLayout.CENTER);
                    JPanel labelPanel = new JPanel(new GridLayout(5, 1));
                    labelPanel.add(nameLabel);
                    labelPanel.add(numberLabel);
                    labelPanel.add(stockLabel);
                    itemContainer.add(labelPanel, BorderLayout.SOUTH);
                    itemPanel.add(itemContainer);
                }

            }


        } catch (NullPointerException e) {
            System.out.println("Vending machine currently not in view");
        }

    }


    public void displayCustomizeMenu(int templateIndex) {

        int addonRequirement;
        int addonLimit;
        int baseRequirement;

        // Create a new JFrame for the customize menu
        JFrame customizeFrame = new JFrame("");
        customizeFrame.setLayout(new BorderLayout());

        // Create the main panel for the customize menu
        JPanel mainPanel = new JPanel();

        confirmCustomizedButton.setText("Confirm!");
        JButton cancelButton = new JButton("Cancel");
        confirmCustomizedButton.setPreferredSize(new Dimension(100, 50));
        cancelButton.setPreferredSize(new Dimension(100, 50));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeFrame.setVisible(false);
                currentInput = " ";
                updateInputField();
                frame.setVisible(true);

            }
        });

        switch (templateIndex) {
            case 0: // Ramen
                break;

            case 1: // Silog meal

                customizeFrame.setTitle("Customize Your Silog Meal");
                selectedAddonIndexes = new ArrayList<>();
                selectedBaseIndexes = new ArrayList<>();

                baseRequirement = 1;
                addonRequirement = 1;
                addonLimit = 2;

                customizeFrame.setSize(720, 400);

                mainPanel.setLayout(new GridLayout(7,1,5,5));

                // Top Panel
                JPanel topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                JLabel title = new JLabel("Customize Silog Meal", SwingConstants.CENTER);
                JLabel info = new JLabel("(Includes Fried Egg). Select Your Ulam and Rice:", SwingConstants.CENTER);
                JLabel customizeNoticeMessage = new JLabel();
                customizeNoticeMessage.setHorizontalAlignment(SwingConstants.CENTER);
                customizeNoticeMessage.setForeground(Color.RED);

                JLabel selectedRice = new JLabel("");
                JPanel selectedRiceContainer = new JPanel();

                String[] selectedUlamMessage = {""};
                JLabel selectedUlam = new JLabel("");
                JPanel selectedUlamContainer = new JPanel();

                final int[] calorieCounter = {0};
                JLabel totalCalories = new JLabel("");
                JPanel totalCaloriesContainer = new JPanel();

                topPanel.add(title);
                topPanel.add(info);
                topPanel.add(customizeNoticeMessage);
                mainPanel.add(topPanel, BorderLayout.NORTH);

                // Middle Panel(s)
                JPanel basePanel = new JPanel(new GridLayout(1,2,20,5));
                JPanel addonPanel = new JPanel(new GridLayout(1, 3, 10, 5));

                ImageIcon baseIcon1 = new ImageIcon(getClass().getResource("resources/items/item3.png"));
                ImageIcon baseIcon2 = new ImageIcon(getClass().getResource("resources/items/item4.png"));
                ImageIcon icon1 = new ImageIcon(getClass().getResource("resources/addons/bacon.png"));
                ImageIcon icon2 = new ImageIcon(getClass().getResource("resources/addons/tapa.png"));
                ImageIcon icon3 = new ImageIcon(getClass().getResource("resources/addons/tocino.png"));

                JButton regularRice = new JButton(baseIcon1);
                regularRice.setToolTipText("Plain Rice");
                JButton javaRice = new JButton(baseIcon2);
                javaRice.setToolTipText("Java Rice");

                JButton bacon = new JButton(icon1);
                bacon.setToolTipText("Bacon");
                JButton tapa = new JButton(icon2);
                tapa.setToolTipText("Tapa");
                JButton tocino = new JButton(icon3);
                tocino.setToolTipText("Tocino");

                Dimension buttonSize = new Dimension(80, 80);
                regularRice.setPreferredSize(buttonSize);
                javaRice.setPreferredSize(buttonSize);

                bacon.setPreferredSize(buttonSize);
                tapa.setPreferredSize(buttonSize);
                tocino.setPreferredSize(buttonSize);

                // Counter for the number of times the selection button is pressed
                final int[] baseCounter = {0};
                final int[] addonCounter = {0};

                regularRice.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(2); // Regular Rice index

                        baseCounter[0]++;
                        calorieCounter[0] += itemCalories.get(2);
                        totalCalories.setText("Total Calories: " + calorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedRice.setText("Selected Rice: Plain Rice");
                        selectedRiceContainer.add(selectedRice);
                        customizeFrame.validate();

                        regularRice.setEnabled(false);
                        javaRice.setEnabled(false);
                    }
                });

                javaRice.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(3); // Java Rice index

                        baseCounter[0]++;
                        calorieCounter[0] += itemCalories.get(3);
                        totalCalories.setText("Total Calories: " + calorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedRice.setText("Selected Rice: Java Rice");
                        selectedRiceContainer.add(selectedRice);
                        customizeFrame.validate();

                        regularRice.setEnabled(false);
                        javaRice.setEnabled(false);
                    }
                });

                bacon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addonCounter[0]++;
                        selectedAddonIndexes.add(17); // Bacon index

                        if (addonCounter[0] == 1){
                            selectedUlamMessage[0] += "Selected Ulam: ";
                        }

                        selectedUlamMessage[0] += "Bacon";

                        if (addonCounter[0] == 1){
                            selectedUlamMessage[0] += ", ";
                        }

                        calorieCounter[0] += itemCalories.get(17);
                        totalCalories.setText("Total Calories: " + calorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedUlam.setText(selectedUlamMessage[0]);
                        selectedUlam.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedUlamContainer.add(selectedUlam);
                        customizeFrame.validate();

                        if (addonCounter[0] + 1 > addonLimit) {
                            bacon.setEnabled(false);
                            tapa.setEnabled(false);
                            tocino.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                tapa.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addonCounter[0]++;
                        selectedAddonIndexes.add(18); // Tapa index

                        if (addonCounter[0] == 1){
                            selectedUlamMessage[0] += "Selected Ulam: ";
                        }

                        selectedUlamMessage[0] += "Tapa";

                        if (addonCounter[0] == 1){
                            selectedUlamMessage[0] += ", ";
                        }

                        calorieCounter[0] += itemCalories.get(18);
                        totalCalories.setText("Total Calories: " + calorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedUlam.setText(selectedUlamMessage[0]);
                        selectedUlam.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedUlamContainer.add(selectedUlam);
                        customizeFrame.validate();

                        if (addonCounter[0] + 1 > addonLimit) {
                            bacon.setEnabled(false);
                            tapa.setEnabled(false);
                            tocino.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }

                });

                tocino.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        addonCounter[0]++;
                        selectedAddonIndexes.add(19); // Tocino index
                        if (addonCounter[0] == 1){
                            selectedUlamMessage[0] += "Selected Ulam: ";
                        }

                        selectedUlamMessage[0] += "Tocino";

                        if (addonCounter[0] == 1){
                            selectedUlamMessage[0] += ", ";
                        }

                        calorieCounter[0] += itemCalories.get(19);
                        totalCalories.setText("Total Calories: " + calorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedUlam.setText(selectedUlamMessage[0]);
                        selectedUlam.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedUlamContainer.add(selectedUlam);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (addonCounter[0] + 1 > addonLimit) {
                            bacon.setEnabled(false);
                            tapa.setEnabled(false);
                            tocino.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                confirmCustomizedButton.addActionListener(new ActionListener() { // Displays error message if not enough selections, hides and returns to previous menu otherwise.
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (baseCounter[0] < baseRequirement || addonCounter[0] < addonRequirement){
                            customizeNoticeMessage.setText("Please select rice type and at least 1 ulam.");
                        }
                        else {
                            customizeFrame.setVisible(false);
                            currentInput = " ";
                            updateInputField();
                            frame.setVisible(true);
                        }
                    }
                });

                basePanel.add(regularRice);
                basePanel.add(javaRice);
                addonPanel.add(bacon);
                addonPanel.add(tapa);
                addonPanel.add(tocino);

                mainPanel.add(basePanel);
                mainPanel.add(selectedRiceContainer);
                mainPanel.add(addonPanel);
                mainPanel.add(selectedUlamContainer);

                mainPanel.add(totalCaloriesContainer);

                // Bottom Panel
                JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                bottomPanel.add(confirmCustomizedButton);
                bottomPanel.add(cancelButton);
                mainPanel.add(bottomPanel, BorderLayout.SOUTH);

                customizeFrame.add(mainPanel);
                customizeFrame.setVisible(true);
                frame.setVisible(false);

                break;

            case 2: // Halo-halo
                break;

        }


    }

    public ArrayList<Integer> getSelectedAddonIndexes(){
        return selectedAddonIndexes;
    }

    public ArrayList<Integer> getSelectedBaseIndexes(){
        return selectedBaseIndexes;
    }

    public void setConfirmCustomizedButtonListener(ActionListener actionListener){
        // Should display preparation messages through the mainController (through the machine screen)
        this.confirmCustomizedButton.addActionListener(actionListener);
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

    public void updateLastPressedValue(double value) {
        this.lastPressedValue = value;
        System.out.println(lastPressedValue);
    }

    public double getLastPressedValue() {
        return lastPressedValue;
    }

    public void setCoinButtonsListeners(ActionListener actionListener) {
        for (JButton coinButton : this.coinButtons) {
            coinButton.addActionListener(actionListener);
        }
    }

    public void setBillButtonsListeners(ActionListener actionListener) {
        for (JButton billButton : this.billButtons) {
            billButton.addActionListener(actionListener);
        }
    }

    private class ConfirmButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cashFrame.setVisible(false);
        }
    }

    public void withdrawInsertedCash() {
        cashFrame.setVisible(false);
        currentAmount = 0.0;
        updateAmountLabel();
        amountLabel.validate();
    }

    public void setCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

    private double convertCoinValue(String coinValue) {

        switch (coinValue) {
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

    public void updateReceivedItem() {

        this.currentAmount = 0.0;
        updateAmountLabel();
        this.amountLabel.validate();

        try {
            // Get the current input and convert it to an integer
            int selectedItemId = Integer.parseInt(currentInput.trim());
            if (selectedItemId <= 9) {
                // Display the corresponding item image in the dispensedItemContainer
                String itemImageFileName = IMAGE_DIRECTORY_PATH + "items/item" + selectedItemId + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);

                // Update necessary fields
                dispensedItemLabel.setIcon(icon);
                receivedLabel.setText("1x " + itemNames.get(selectedItemId - 1));
                receivedLabel.setHorizontalAlignment(SwingConstants.CENTER);

                receivedFrame.setVisible(true);
                currentInput = " ";
                updateInputField();
            }

        } catch (NullPointerException err) {
            displayError("INVALID NUMBER");
        }
    }

    // TODO: convert money to img icons with tooltips
    public void updateReceivedChange(String message) {

        dispensedItemPanel.remove(getButton);
        String[] messageParts = message.split("-");

        JPanel cashDenominationContainer = new JPanel(new GridLayout(messageParts.length + 1, 1, 5, 5));
        cashDenominationContainer.add(new JLabel("Your Change: ", SwingConstants.CENTER));

        for (String parts : messageParts) {
            JLabel cashDenominationLabel = new JLabel(parts, SwingConstants.CENTER);
            cashDenominationContainer.add(cashDenominationLabel);
        }

        // adds the change
        dispensedItemPanel.add(cashDenominationContainer);
        dispensedItemPanel.add(getButton);

    }

    public void returnInsertedMoney(String message) {

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

        for (String parts : messageParts) {
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

    public void displayError(String errorMessage) {
        screenLabel.setText(errorMessage);
        screenLabel.setForeground(Color.RED);
        screenLabel.validate();
        temporaryKeypadLock();
    }

    public void displayErrorWithdraw(String errorMessage) {
        screenLabel.setText(errorMessage);
        screenLabel.setForeground(Color.RED);
        screenLabel.validate();
        withdrawInsertedCash();
        temporaryKeypadLock();
    }

    public void displaySuccess(String message) {
        screenLabel.setText(message);
        screenLabel.setForeground(Color.GREEN);
        screenLabel.validate();
        updateReceivedItem();
        temporaryKeypadLock();
    }

    private void temporaryKeypadLock() {  // temporarily disables keypad while errors are present
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

    private void disableKeypad() {
        setPanelEnabled(this.keypadPanel, false);
    }

    private void enableKeypad() {
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

    public void closeReceivedPanel() {
        receivedFrame.setVisible(false);
        receivedFrame.dispose();
    }

    public void setPurchaseButtonListener(ActionListener actionListener) {
        this.purchaseButton.addActionListener(actionListener);
    }

    public void setGetButtonListener(ActionListener actionListener) {
        this.getButton.addActionListener(actionListener);
    }

    public void setCashInsertButtonListener(ActionListener actionListener) {
        this.cashInsertButton.addActionListener(actionListener);
    }

    public int getNumberInput() {
        return Integer.parseInt(currentInput.trim());
    }

    public void disposeCurrentWindow(){
        this.frame.dispose();
    }

}
