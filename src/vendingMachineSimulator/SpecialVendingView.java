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
    private JButton cashInsertButton, confirmCustomizedButton, confirmRamenButton, confirmSilogButton, confirmHaloHaloButton;
    private JLabel screenLabel, amountLabel;
    private String currentInput = "", cashInput = "Current Cash: ₱0.0";
    private JLabel dispensedItemLabel, receivedLabel, cashDenominationLabel;
    private JButton confirmButton, getButton, cancelButton, purchaseButton;
    private JButton[] coinButtons = new JButton[6], billButtons = new JButton[6];
    private ArrayList<String> itemNames, templateNames;
    private ArrayList<Double> itemPrices, templatePrices;
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
        confirmRamenButton = new JButton("");
        confirmSilogButton = new JButton("");
        confirmHaloHaloButton = new JButton("");


    }

    public void updateItemInformation(ArrayList<String> itemNames, ArrayList<Double> itemPrices, ArrayList<Integer> itemCalories, ArrayList<Integer> itemAmounts, ArrayList<Double> templatePrices) {
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemCalories = itemCalories;
        this.itemAmounts = itemAmounts;
        this.templatePrices = templatePrices;

        try {
            updateItemPanel();
            frame.validate();
        } catch (NullPointerException e) {
            System.out.println("Frame not in view...");
        }
    }

    public void displaySpecialGUI(ArrayList<String> itemNames, ArrayList<Double> itemPrices, ArrayList<Integer> itemCalories, ArrayList<Integer> itemAmounts, ArrayList<String> templateNames, ArrayList<Double> templatePrices) {

        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.itemCalories = itemCalories;
        this.itemAmounts = itemAmounts;
        this.templateNames = templateNames;
        this.templatePrices = templatePrices;

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
            String[] specialItemToolips = {
                    "<html>" + "Contents:" + "<br>" + "1x Broth" + "<br>" + "1x Noodles" + "<br>" + "1-3x Addons" + "<br>" + "1x Black Garlic Oil" + "</html>",
                    "<html>" + "Contents:" + "<br>" + "1x Rice" + "<br>" + "1x Fried Egg"  + "<br>" + "1-2x Ulam" + "</html>",
                    "<html>" + "Contents:" + "<br>" + "1x Ube Ice Cream" + "<br>" + "1x Coconut Milk"  + "<br>" + "1-5x Addons" + "</html>"
            };
            int currentSpecial = 0;

            // Add items to the item panel (regular items)
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
                else { // Adds customizable/special items to the item panel
                    JLabel itemLabel = new JLabel(icon);
                    itemLabel.setToolTipText(specialItemToolips[currentSpecial]);
                    JLabel nameLabel = new JLabel(templateNames.get(i - 10), SwingConstants.CENTER);
                    JLabel numberLabel = new JLabel("[" + i + "]", SwingConstants.CENTER);
                    JLabel stockLabel = new JLabel("Customizable", SwingConstants.CENTER);
                    JLabel priceLabel = new JLabel("₱" + String.format("%.2f", templatePrices.get(i - 10)), SwingConstants.CENTER);
                    stockLabel.setForeground(Color.MAGENTA);

                    JPanel itemContainer = new JPanel(new BorderLayout());
                    itemContainer.add(itemLabel, BorderLayout.CENTER);
                    JPanel labelPanel = new JPanel(new GridLayout(5, 1));
                    labelPanel.add(nameLabel);
                    labelPanel.add(numberLabel);
                    labelPanel.add(stockLabel);
                    labelPanel.add(priceLabel);
                    itemContainer.add(labelPanel, BorderLayout.SOUTH);
                    itemPanel.add(itemContainer);
                    currentSpecial++;
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
        confirmRamenButton.setText("Confirm!");
        confirmSilogButton.setText("Confirm!");
        confirmHaloHaloButton.setText("Confirm!");
        JButton cancelButton = new JButton("Cancel");

        confirmCustomizedButton.setPreferredSize(new Dimension(100, 50));
        confirmRamenButton.setPreferredSize(new Dimension(100, 50));
        confirmSilogButton.setPreferredSize(new Dimension(100, 50));
        confirmHaloHaloButton.setPreferredSize(new Dimension(100, 50));
        cancelButton.setPreferredSize(new Dimension(100, 50));

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customizeFrame.setVisible(false);
                currentInput = " ";
                updateInputField();
                temporaryKeypadLock();
                frame.setVisible(true);

            }
        });


        JPanel topPanel, basePanel, addonPanel, bottomPanel, totalCaloriesContainer;
        JLabel title, info, customizeNoticeMessage, totalCalories;

        Dimension buttonSize = new Dimension(80, 80);


        switch (templateIndex) {
            case 0: { // Ramen
                customizeFrame.setTitle("Customize Your Ramen");
                selectedAddonIndexes = new ArrayList<>();
                selectedBaseIndexes = new ArrayList<>();

                baseRequirement = 2;
                addonRequirement = 1;
                addonLimit = 3;

                customizeFrame.setSize(720, 800);
                customizeFrame.setResizable(false);

                mainPanel.setLayout(new GridLayout(9, 1, 5, 5));

                // Top Panel
                topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                title = new JLabel("Customize Ramen", SwingConstants.CENTER);
                info = new JLabel("Select Your Broth, Soup, and Addons:", SwingConstants.CENTER);
                customizeNoticeMessage = new JLabel();
                customizeNoticeMessage.setHorizontalAlignment(SwingConstants.CENTER);
                customizeNoticeMessage.setForeground(Color.RED);

                JLabel selectedBroth = new JLabel("");
                JPanel selectedBrothContainer = new JPanel();
                JLabel selectedNoodles = new JLabel("");
                JPanel selectedNoodlesContainer = new JPanel();
                String[] selectedRamenToppingsMessage = {""};
                JLabel selectedRamenToppings = new JLabel("");
                JPanel selectedRamenToppingsContainer = new JPanel();

                final int[] ramenCalorieCounter = {0};
                totalCalories = new JLabel("");
                totalCaloriesContainer = new JPanel();

                topPanel.add(title);
                topPanel.add(info);
                topPanel.add(customizeNoticeMessage);
                mainPanel.add(topPanel, BorderLayout.NORTH);

                // Middle panel(s)
                basePanel = new JPanel(new GridLayout(1, 4, 20, 5));
                JPanel noodlePanel = new JPanel(new GridLayout(1, 2, 20, 5));
                addonPanel = new JPanel(new GridLayout(1, 4, 10, 5));

                ImageIcon brothIcon1 = new ImageIcon(getClass().getResource("resources/items/item1.png"));
                ImageIcon brothIcon2 = new ImageIcon(getClass().getResource("resources/items/item1.png"));
                ImageIcon brothIcon3 = new ImageIcon(getClass().getResource("resources/items/item1.png"));
                ImageIcon brothIcon4 = new ImageIcon(getClass().getResource("resources/items/item1.png"));
                ImageIcon noodleIcon1 = new ImageIcon(getClass().getResource("resources/items/item5.png"));
                ImageIcon noodleIcon2 = new ImageIcon(getClass().getResource("resources/items/item6.png"));
                ImageIcon toppingIcon1 = new ImageIcon(getClass().getResource("resources/items/item8.png"));
                ImageIcon toppingIcon2 = new ImageIcon(getClass().getResource("resources/items/item9.png"));
                ImageIcon toppingIcon3 = new ImageIcon(getClass().getResource("resources/items/item1.png"));
                ImageIcon toppingIcon4 = new ImageIcon(getClass().getResource("resources/items/item1.png"));

                JButton shioBroth = new JButton(brothIcon1);
                shioBroth.setToolTipText("Shio Broth");
                shioBroth.setPreferredSize(buttonSize);
                JButton misoBroth = new JButton(brothIcon2);
                misoBroth.setToolTipText("Miso Broth");
                misoBroth.setPreferredSize(buttonSize);
                JButton ukkokeiBroth = new JButton(brothIcon3);
                ukkokeiBroth.setToolTipText("Ukkokei Broth");
                ukkokeiBroth.setPreferredSize(buttonSize);
                JButton tonkotsuBroth = new JButton(brothIcon4);
                tonkotsuBroth.setToolTipText("Tonkotsu Broth");
                tonkotsuBroth.setPreferredSize(buttonSize);
                JButton sobaNoodles = new JButton(noodleIcon1);
                sobaNoodles.setToolTipText("Soba Noodles");
                sobaNoodles.setPreferredSize(buttonSize);
                JButton udonNoodles = new JButton(noodleIcon2);
                udonNoodles.setToolTipText("Udon Noodles");
                udonNoodles.setPreferredSize(buttonSize);
                JButton fishCake = new JButton(toppingIcon1);
                fishCake.setToolTipText("Fish Cake");
                fishCake.setPreferredSize(buttonSize);
                JButton chasuPork = new JButton(toppingIcon2);
                chasuPork.setToolTipText("Chasu Pork");
                chasuPork.setPreferredSize(buttonSize);
                JButton saltedEgg = new JButton(toppingIcon3);
                saltedEgg.setToolTipText("Salted Egg");
                saltedEgg.setPreferredSize(buttonSize);
                JButton chickenSlices = new JButton(toppingIcon4);
                chickenSlices.setToolTipText("Chicken Slices");
                chickenSlices.setPreferredSize(buttonSize);

                // Counters for checking the base and addons
                final int[] baseCounter = {0};
                final int[] addonCounter = {0};

                // Action listeners for displaying the selection
                shioBroth.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(9); // Shio broth index

                        baseCounter[0]++;
                        ramenCalorieCounter[0] += itemCalories.get(9);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedBroth.setText("Selected Broth: Shio Broth");
                        selectedBrothContainer.add(selectedBroth);
                        customizeFrame.validate();

                        shioBroth.setEnabled(false);
                        misoBroth.setEnabled(false);
                        tonkotsuBroth.setEnabled(false);
                        ukkokeiBroth.setEnabled(false);
                    }
                });

                misoBroth.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(10); // Miso broth index

                        baseCounter[0]++;
                        ramenCalorieCounter[0] += itemCalories.get(10);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedBroth.setText("Selected Broth: Miso Broth");
                        selectedBrothContainer.add(selectedBroth);
                        customizeFrame.validate();

                        shioBroth.setEnabled(false);
                        misoBroth.setEnabled(false);
                        tonkotsuBroth.setEnabled(false);
                        ukkokeiBroth.setEnabled(false);
                    }
                });

                tonkotsuBroth.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(11); // Tonkotsu broth index

                        baseCounter[0]++;
                        ramenCalorieCounter[0] += itemCalories.get(11);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedBroth.setText("Selected Broth: Tonkotsu Broth");
                        selectedBrothContainer.add(selectedBroth);
                        customizeFrame.validate();

                        shioBroth.setEnabled(false);
                        misoBroth.setEnabled(false);
                        tonkotsuBroth.setEnabled(false);
                        ukkokeiBroth.setEnabled(false);
                    }
                });

                ukkokeiBroth.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(12); // Ukkokei broth index

                        baseCounter[0]++;
                        ramenCalorieCounter[0] += itemCalories.get(12);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedBroth.setText("Selected Broth: Ukkokei Broth");
                        selectedBrothContainer.add(selectedBroth);
                        customizeFrame.validate();

                        shioBroth.setEnabled(false);
                        misoBroth.setEnabled(false);
                        tonkotsuBroth.setEnabled(false);
                        ukkokeiBroth.setEnabled(false);
                    }
                });

                sobaNoodles.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(4); // Soba noodles index

                        baseCounter[0]++;
                        ramenCalorieCounter[0] += itemCalories.get(4);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedNoodles.setText("Selected Noodles: Soba Noodles");
                        selectedNoodlesContainer.add(selectedNoodles);
                        customizeFrame.validate();

                        sobaNoodles.setEnabled(false);
                        udonNoodles.setEnabled(false);
                    }
                });

                udonNoodles.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(5); // Udon noodles index

                        baseCounter[0]++;
                        ramenCalorieCounter[0] += itemCalories.get(5);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedNoodles.setText("Selected Noodles: Udon Noodles");
                        selectedNoodlesContainer.add(selectedNoodles);
                        customizeFrame.validate();

                        sobaNoodles.setEnabled(false);
                        udonNoodles.setEnabled(false);
                    }
                });

                fishCake.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAddonIndexes.add(7); // Fish cake index

                        addonCounter[0]++;

                        if (addonCounter[0] == 1) {
                            selectedRamenToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedRamenToppingsMessage[0] += "Fish Cake";

                        if (addonCounter[0] >= 1 && addonCounter[0] < addonLimit){
                            selectedRamenToppingsMessage[0] += ", ";
                        }

                        ramenCalorieCounter[0] += itemCalories.get(7);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedRamenToppings.setText(selectedRamenToppingsMessage[0]);
                        selectedRamenToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedRamenToppingsContainer.add(selectedRamenToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (addonCounter[0] + 1 > addonLimit) {
                            fishCake.setEnabled(false);
                            chasuPork.setEnabled(false);
                            saltedEgg.setEnabled(false);
                            chickenSlices.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                chasuPork.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAddonIndexes.add(8); // Chasu Pork index

                        addonCounter[0]++;

                        if (addonCounter[0] == 1) {
                            selectedRamenToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedRamenToppingsMessage[0] += "Chasu Pork";

                        if (addonCounter[0] >= 1 && addonCounter[0] < addonLimit){
                            selectedRamenToppingsMessage[0] += ", ";
                        }

                        ramenCalorieCounter[0] += itemCalories.get(8);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedRamenToppings.setText(selectedRamenToppingsMessage[0]);
                        selectedRamenToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedRamenToppingsContainer.add(selectedRamenToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (addonCounter[0] + 1 > addonLimit) {
                            fishCake.setEnabled(false);
                            chasuPork.setEnabled(false);
                            saltedEgg.setEnabled(false);
                            chickenSlices.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                saltedEgg.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAddonIndexes.add(10); // Salted Egg index

                        addonCounter[0]++;

                        if (addonCounter[0] == 1) {
                            selectedRamenToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedRamenToppingsMessage[0] += "Salted Egg";

                        if (addonCounter[0] >= 1 && addonCounter[0] < addonLimit){
                            selectedRamenToppingsMessage[0] += ", ";
                        }

                        ramenCalorieCounter[0] += itemCalories.get(10);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedRamenToppings.setText(selectedRamenToppingsMessage[0]);
                        selectedRamenToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedRamenToppingsContainer.add(selectedRamenToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (addonCounter[0] + 1 > addonLimit) {
                            fishCake.setEnabled(false);
                            chasuPork.setEnabled(false);
                            saltedEgg.setEnabled(false);
                            chickenSlices.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                chickenSlices.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedAddonIndexes.add(11); // Chicken Slices index

                        addonCounter[0]++;

                        if (addonCounter[0] == 1) {
                            selectedRamenToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedRamenToppingsMessage[0] += "Chicken Slices";

                        if (addonCounter[0] >= 1 && addonCounter[0] < addonLimit){
                            selectedRamenToppingsMessage[0] += ", ";
                        }

                        ramenCalorieCounter[0] += itemCalories.get(11);
                        totalCalories.setText("Total Calories: " + ramenCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedRamenToppings.setText(selectedRamenToppingsMessage[0]);
                        selectedRamenToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedRamenToppingsContainer.add(selectedRamenToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (addonCounter[0] + 1 > addonLimit) {
                            fishCake.setEnabled(false);
                            chasuPork.setEnabled(false);
                            saltedEgg.setEnabled(false);
                            chickenSlices.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                confirmRamenButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (baseCounter[0] < baseRequirement || addonCounter[0] < addonRequirement) {
                            customizeNoticeMessage.setText("Please select broth and noodle type, and at least 1 addon.");
                            frame.setVisible(false);
                        } else {

                            if (!selectedBaseIndexes.contains(26))
                                selectedBaseIndexes.add(26); // adds the index of black garlic oil to baseIndexes (if not already added)

                            customizeFrame.setVisible(false);
                            frame.setVisible(true);
                        }
                    }
                });


                basePanel.add(shioBroth);
                basePanel.add(misoBroth);
                basePanel.add(ukkokeiBroth);
                basePanel.add(tonkotsuBroth);

                noodlePanel.add(sobaNoodles);
                noodlePanel.add(udonNoodles);

                addonPanel.add(fishCake);
                addonPanel.add(chasuPork);
                addonPanel.add(saltedEgg);
                addonPanel.add(chickenSlices);

                mainPanel.add(basePanel);
                mainPanel.add(selectedBrothContainer);
                mainPanel.add(noodlePanel);
                mainPanel.add(selectedNoodlesContainer);
                mainPanel.add(addonPanel);
                mainPanel.add(selectedRamenToppingsContainer);

                mainPanel.add(totalCaloriesContainer);


                // Bottom panel
                bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                bottomPanel.add(confirmRamenButton);
                bottomPanel.add(cancelButton);
                mainPanel.add(bottomPanel, BorderLayout.SOUTH);

                customizeFrame.add(mainPanel);
                customizeFrame.setVisible(true);
                frame.setVisible(false);

            }


            break;

            case 1: { // Silog meal

                customizeFrame.setTitle("Customize Your Silog Meal");
                customizeFrame.setResizable(false);
                selectedAddonIndexes = new ArrayList<>();
                selectedBaseIndexes = new ArrayList<>();

                baseRequirement = 1;
                addonRequirement = 1;
                addonLimit = 2;

                customizeFrame.setSize(720, 400);

                mainPanel.setLayout(new GridLayout(7, 1, 5, 5));

                // Top Panel
                topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                title = new JLabel("Customize Silog Meal", SwingConstants.CENTER);
                info = new JLabel("(Includes Fried Egg). Select Your Ulam and Rice:", SwingConstants.CENTER);
                customizeNoticeMessage = new JLabel();
                customizeNoticeMessage.setHorizontalAlignment(SwingConstants.CENTER);
                customizeNoticeMessage.setForeground(Color.RED);

                JLabel selectedRice = new JLabel("");
                JPanel selectedRiceContainer = new JPanel();

                String[] selectedUlamMessage = {""};
                JLabel selectedUlam = new JLabel("");
                JPanel selectedUlamContainer = new JPanel();

                final int[] silogCalorieCounter = {0};
                totalCalories = new JLabel("");
                totalCaloriesContainer = new JPanel();

                topPanel.add(title);
                topPanel.add(info);
                topPanel.add(customizeNoticeMessage);
                mainPanel.add(topPanel, BorderLayout.NORTH);

                // Middle Panel(s)
                basePanel = new JPanel(new GridLayout(1, 2, 20, 5));
                addonPanel = new JPanel(new GridLayout(1, 3, 10, 5));

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

                regularRice.setPreferredSize(buttonSize);
                javaRice.setPreferredSize(buttonSize);

                bacon.setPreferredSize(buttonSize);
                tapa.setPreferredSize(buttonSize);
                tocino.setPreferredSize(buttonSize);

                // Counter for the number of times the selection button is pressed
                final int[] baseCounter = {0};
                final int[] addonCounter = {0};

                // Action listeners for displaying the selection
                regularRice.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedBaseIndexes.add(2); // Regular Rice index

                        baseCounter[0]++;
                        silogCalorieCounter[0] += itemCalories.get(2);
                        totalCalories.setText("Total Calories: " + silogCalorieCounter[0]);
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
                        silogCalorieCounter[0] += itemCalories.get(3);
                        totalCalories.setText("Total Calories: " + silogCalorieCounter[0]);
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

                        if (addonCounter[0] == 1) {
                            selectedUlamMessage[0] += "Selected Ulam: ";
                        }

                        selectedUlamMessage[0] += "Bacon";

                        if (addonCounter[0] == 1) {
                            selectedUlamMessage[0] += ", ";
                        }

                        silogCalorieCounter[0] += itemCalories.get(17);
                        totalCalories.setText("Total Calories: " + silogCalorieCounter[0]);
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

                        if (addonCounter[0] == 1) {
                            selectedUlamMessage[0] += "Selected Ulam: ";
                        }

                        selectedUlamMessage[0] += "Tapa";

                        if (addonCounter[0] == 1) {
                            selectedUlamMessage[0] += ", ";
                        }

                        silogCalorieCounter[0] += itemCalories.get(18);
                        totalCalories.setText("Total Calories: " + silogCalorieCounter[0]);
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
                        if (addonCounter[0] == 1) {
                            selectedUlamMessage[0] += "Selected Ulam: ";
                        }

                        selectedUlamMessage[0] += "Tocino";

                        if (addonCounter[0] == 1) {
                            selectedUlamMessage[0] += ", ";
                        }

                        silogCalorieCounter[0] += itemCalories.get(19);
                        totalCalories.setText("Total Calories: " + silogCalorieCounter[0]);
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

                confirmSilogButton.addActionListener(new ActionListener() { // Displays error message if not enough selections, hides and returns to previous menu otherwise.
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("SILOG!");
                        if (baseCounter[0] < baseRequirement || addonCounter[0] < addonRequirement) {
                            customizeNoticeMessage.setText("Please select rice type and at least 1 ulam.");
                            frame.setVisible(false);
                        } else {

                            if (!selectedBaseIndexes.contains(14))
                                selectedBaseIndexes.add(14); // adds the index of fried egg into the selected base indexes (if not already added)

                            customizeFrame.setVisible(false);
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
                bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                bottomPanel.add(confirmSilogButton);
                bottomPanel.add(cancelButton);
                mainPanel.add(bottomPanel, BorderLayout.SOUTH);

                customizeFrame.add(mainPanel);
                customizeFrame.setVisible(true);
                frame.setVisible(false);

                break;
            }

            case 2: { // Halo-halo

                customizeFrame.setTitle("Customize Your Halo-halo");
                selectedAddonIndexes = new ArrayList<>();
                selectedBaseIndexes = new ArrayList<>();

                baseRequirement = 1;
                addonRequirement = 1;
                addonLimit = 5;

                customizeFrame.setSize(650, 400);

                mainPanel.setLayout(new GridLayout(6, 1, 5, 5));

                // Top Panel
                topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
                title = new JLabel("Customize Halo-halo", SwingConstants.CENTER);
                info = new JLabel("(Includes Ube Ice Cream and Coconut Milk). Select Your Addons:", SwingConstants.CENTER);
                customizeNoticeMessage = new JLabel();
                customizeNoticeMessage.setHorizontalAlignment(SwingConstants.CENTER);
                customizeNoticeMessage.setForeground(Color.RED);

                String[] selectedToppingsMessage = {""};
                JLabel selectedToppings = new JLabel("");
                JPanel selectedToppingsContainer = new JPanel();

                final int[] haloCalorieCounter = {0};
                totalCalories = new JLabel("");
                totalCaloriesContainer = new JPanel();

                topPanel.add(title);
                topPanel.add(info);
                topPanel.add(customizeNoticeMessage);
                mainPanel.add(topPanel, BorderLayout.NORTH);

                // Middle Panel(s)
                addonPanel = new JPanel(new GridLayout(2, 3, 10, 10));

                ImageIcon addonIcon1 = new ImageIcon(getClass().getResource("resources/addons/bacon.png"));
                ImageIcon addonIcon2 = new ImageIcon(getClass().getResource("resources/addons/tapa.png"));
                ImageIcon addonIcon3 = new ImageIcon(getClass().getResource("resources/addons/tocino.png"));
                ImageIcon addonIcon4 = new ImageIcon(getClass().getResource("resources/addons/tocino.png"));
                ImageIcon addonIcon5 = new ImageIcon(getClass().getResource("resources/addons/tocino.png"));
                ImageIcon addonIcon6 = new ImageIcon(getClass().getResource("resources/addons/tocino.png"));

                JButton nuts = new JButton(addonIcon1);
                nuts.setToolTipText("Nuts");
                JButton nataDeCoco = new JButton(addonIcon2);
                nataDeCoco.setToolTipText("Nata de Coco");
                JButton macapuno = new JButton(addonIcon3);
                macapuno.setToolTipText("Macapuno");
                JButton lecheFlan = new JButton(addonIcon4);
                lecheFlan.setToolTipText("Leche Flan");
                JButton bananaSlices = new JButton(addonIcon5);
                bananaSlices.setToolTipText("Banana Slices");
                JButton langka = new JButton(addonIcon6);
                langka.setToolTipText("Langka");


                // Counter for the number of times the selection button is pressed
                final int[] haloAddonCounter = {0};

                // Action listeners for displaying the selection(s)
                nuts.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        haloAddonCounter[0]++;
                        selectedAddonIndexes.add(20); // Nuts index
                        if (haloAddonCounter[0] == 1) {
                            selectedToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedToppingsMessage[0] += "Nuts";

                        if (haloAddonCounter[0] >= 1 && haloAddonCounter[0] < 5) {
                            selectedToppingsMessage[0] += ", ";
                        }

                        haloCalorieCounter[0] += itemCalories.get(20);
                        totalCalories.setText("Total Calories: " + haloCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedToppings.setText(selectedToppingsMessage[0]);
                        selectedToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedToppingsContainer.add(selectedToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (haloAddonCounter[0] + 1 > addonLimit) {
                            nuts.setEnabled(false);
                            nataDeCoco.setEnabled(false);
                            macapuno.setEnabled(false);
                            lecheFlan.setEnabled(false);
                            bananaSlices.setEnabled(false);
                            langka.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });
                nataDeCoco.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        haloAddonCounter[0]++;
                        selectedAddonIndexes.add(21); // Nata de coco index
                        if (haloAddonCounter[0] == 1) {
                            selectedToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedToppingsMessage[0] += "Nata de Coco";

                        if (haloAddonCounter[0] >= 1 && haloAddonCounter[0] < 5) {
                            selectedToppingsMessage[0] += ", ";
                        }

                        haloCalorieCounter[0] += itemCalories.get(21);
                        totalCalories.setText("Total Calories: " + haloCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedToppings.setText(selectedToppingsMessage[0]);
                        selectedToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedToppingsContainer.add(selectedToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (haloAddonCounter[0] + 1 > addonLimit) {
                            nuts.setEnabled(false);
                            nataDeCoco.setEnabled(false);
                            macapuno.setEnabled(false);
                            lecheFlan.setEnabled(false);
                            bananaSlices.setEnabled(false);
                            langka.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });
                macapuno.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        haloAddonCounter[0]++;
                        selectedAddonIndexes.add(22); // Macapuno index
                        if (haloAddonCounter[0] == 1) {
                            selectedToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedToppingsMessage[0] += "Macapuno";

                        if (haloAddonCounter[0] >= 1 && haloAddonCounter[0] < 5) {
                            selectedToppingsMessage[0] += ", ";
                        }

                        haloCalorieCounter[0] += itemCalories.get(22);
                        totalCalories.setText("Total Calories: " + haloCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedToppings.setText(selectedToppingsMessage[0]);
                        selectedToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedToppingsContainer.add(selectedToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (haloAddonCounter[0] + 1 > addonLimit) {
                            nuts.setEnabled(false);
                            nataDeCoco.setEnabled(false);
                            macapuno.setEnabled(false);
                            lecheFlan.setEnabled(false);
                            bananaSlices.setEnabled(false);
                            langka.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });
                lecheFlan.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        haloAddonCounter[0]++;
                        selectedAddonIndexes.add(23); // Leche flan index
                        if (haloAddonCounter[0] == 1) {
                            selectedToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedToppingsMessage[0] += "Leche Flan";

                        if (haloAddonCounter[0] >= 1 && haloAddonCounter[0] < 5) {
                            selectedToppingsMessage[0] += ", ";
                        }

                        haloCalorieCounter[0] += itemCalories.get(23);
                        totalCalories.setText("Total Calories: " + haloCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedToppings.setText(selectedToppingsMessage[0]);
                        selectedToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedToppingsContainer.add(selectedToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (haloAddonCounter[0] + 1 > addonLimit) {
                            nuts.setEnabled(false);
                            nataDeCoco.setEnabled(false);
                            macapuno.setEnabled(false);
                            lecheFlan.setEnabled(false);
                            bananaSlices.setEnabled(false);
                            langka.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });
                bananaSlices.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        haloAddonCounter[0]++;
                        selectedAddonIndexes.add(24); // Banana slices index
                        if (haloAddonCounter[0] == 1) {
                            selectedToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedToppingsMessage[0] += "Banana Slices";

                        if (haloAddonCounter[0] >= 1 && haloAddonCounter[0] < 5) {
                            selectedToppingsMessage[0] += ", ";
                        }

                        haloCalorieCounter[0] += itemCalories.get(24);
                        totalCalories.setText("Total Calories: " + haloCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedToppings.setText(selectedToppingsMessage[0]);
                        selectedToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedToppingsContainer.add(selectedToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (haloAddonCounter[0] + 1 > addonLimit) {
                            nuts.setEnabled(false);
                            nataDeCoco.setEnabled(false);
                            macapuno.setEnabled(false);
                            lecheFlan.setEnabled(false);
                            bananaSlices.setEnabled(false);
                            langka.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });
                langka.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        haloAddonCounter[0]++;
                        selectedAddonIndexes.add(25); // Macapuno index
                        if (haloAddonCounter[0] == 1) {
                            selectedToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedToppingsMessage[0] += "Langka";

                        if (haloAddonCounter[0] >= 1 && haloAddonCounter[0] < 5) {
                            selectedToppingsMessage[0] += ", ";
                        }

                        haloCalorieCounter[0] += itemCalories.get(25);
                        totalCalories.setText("Total Calories: " + haloCalorieCounter[0]);
                        totalCaloriesContainer.add(totalCalories);

                        selectedToppings.setText(selectedToppingsMessage[0]);
                        selectedToppings.setHorizontalAlignment(SwingConstants.CENTER);
                        selectedToppingsContainer.add(selectedToppings);
                        customizeFrame.validate();

                        // logic for checking requirement and limit
                        if (haloAddonCounter[0] + 1 > addonLimit) {
                            nuts.setEnabled(false);
                            nataDeCoco.setEnabled(false);
                            macapuno.setEnabled(false);
                            lecheFlan.setEnabled(false);
                            bananaSlices.setEnabled(false);
                            langka.setEnabled(false);
                            customizeNoticeMessage.setText("Addon Limit Reached!");
                        }
                    }
                });

                confirmHaloHaloButton.addActionListener(new ActionListener() { // Displays error message if not enough selections, hides and returns to previous menu otherwise.
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("HALO-HALO!");
                        if (haloAddonCounter[0] < addonRequirement) {
                            customizeNoticeMessage.setText("Please select atleast one addon.");
                            frame.setVisible(false);
                        } else {

                            // adds the index of coconut milk and ube ice cream into the selected base indexes (if not already added)
                            if (!selectedBaseIndexes.contains(1)) {
                                selectedBaseIndexes.add(1);
                            }
                            if (!selectedBaseIndexes.contains(16)) {
                                selectedBaseIndexes.add(16);
                            }


                            customizeFrame.setVisible(false);
                            frame.setVisible(true);
                        }
                    }
                });

                addonPanel.add(nuts);
                addonPanel.add(nataDeCoco);
                addonPanel.add(macapuno);
                addonPanel.add(lecheFlan);
                addonPanel.add(bananaSlices);
                addonPanel.add(langka);

                mainPanel.add(addonPanel);
                mainPanel.add(selectedToppingsContainer);
                mainPanel.add(totalCaloriesContainer);

                // Bottom Panel
                bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
                bottomPanel.add(confirmHaloHaloButton);
                bottomPanel.add(cancelButton);
                mainPanel.add(bottomPanel, BorderLayout.SOUTH);

                customizeFrame.add(mainPanel);
                customizeFrame.setVisible(true);
                frame.setVisible(false);

                break;
            }

            default: {
                break;
            }

        }



    }

    public ArrayList<Integer> getSelectedAddonIndexes(){
        return selectedAddonIndexes;
    }

    public ArrayList<Integer> getSelectedBaseIndexes(){
        return selectedBaseIndexes;
    }

    public void setConfirmRamenButtonListener(ActionListener actionListener){
        this.confirmRamenButton.addActionListener(actionListener);
    }

    public void prepareRamen(String message){

        disableKeypad();
        Timer t = new Timer(1350, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (step) {
                    case 0:
                        screenLabel.setText("HEATING BROTH...");
                        screenLabel.setForeground(Color.ORANGE);
                        break;
                    case 1:
                        screenLabel.setText("ADDING NOODLES...");
                        break;
                    case 2:
                        screenLabel.setText("ADDING TOPPINGS...");
                        break;
                    case 3:
                        screenLabel.setText("DONE!");
                        screenLabel.setForeground(Color.GREEN);
                        break;
                    case 4:
                        screenLabel.setForeground(Color.BLACK);
                        enableKeypad();
                        purchaseButton.setEnabled(false);
                        currentInput = " ";
                        updateInputField();

                        // display the item being dispensed
                        updateReceivedSpecialItem(10);
                        if (!message.equals(""))
                            updateReceivedChange(message);

                    default:
                        break;
                }
                screenLabel.validate();
                step++;
            }
        });
        t.setInitialDelay(0);
        t.setDelay(1000);
        t.setRepeats(true);
        t.setCoalesce(true);
        t.setCoalesce(true);
        t.start();

    }

    public void setConfirmSilogButtonListener(ActionListener actionListener){
        this.confirmSilogButton.addActionListener(actionListener);
    }

    public void prepareSilog(String message){

        disableKeypad();
        Timer t = new Timer(1350, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (step) {
                    case 0:
                        screenLabel.setText("HEATING RICE...");
                        screenLabel.setForeground(Color.ORANGE);
                        break;
                    case 1:
                        screenLabel.setText("COOKING EGG...");
                        break;
                    case 2:
                        screenLabel.setText("ADDING ULAM...");
                        break;
                    case 3:
                        screenLabel.setText("DONE!");
                        screenLabel.setForeground(Color.GREEN);
                        break;
                    case 4:
                        screenLabel.setForeground(Color.BLACK);
                        enableKeypad();
                        purchaseButton.setEnabled(false);
                        currentInput = " ";
                        updateInputField();

                        // display the item being dispensed
                        updateReceivedSpecialItem(11);
                        if (!message.equals(""))
                            updateReceivedChange(message);

                    default:
                        break;
                }
                screenLabel.validate();
                step++;
            }
        });
        t.setInitialDelay(0);
        t.setDelay(1000);
        t.setRepeats(true);
        t.setCoalesce(true);
        t.setCoalesce(true);
        t.start();

    }

    public void setConfirmHaloHaloButtonListener(ActionListener actionListener){
        this.confirmHaloHaloButton.addActionListener(actionListener);
    }

    public void prepareHaloHalo(String message){

        disableKeypad();
        Timer t = new Timer(1350, new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                switch (step) {
                    case 0:
                        screenLabel.setText("PREPARING ICE...");
                        screenLabel.setForeground(Color.ORANGE);
                        break;
                    case 1:
                        screenLabel.setText("ADDING MILK...");
                        break;
                    case 2:
                        screenLabel.setText("ADDING TOPPINGS...");
                        break;
                    case 3:
                        screenLabel.setText("DONE!");
                        screenLabel.setForeground(Color.GREEN);
                        break;
                    case 4:
                        screenLabel.setForeground(Color.BLACK);
                        enableKeypad();
                        purchaseButton.setEnabled(false);
                        currentInput = " ";
                        updateInputField();

                        // display the item being dispensed
                        updateReceivedSpecialItem(12);
                        if (!message.equals(""))
                            updateReceivedChange(message);

                    default:
                        break;
                }
                screenLabel.validate();
                step++;
            }
        });
        t.setInitialDelay(0);
        t.setDelay(1000);
        t.setRepeats(true);
        t.setCoalesce(true);
        t.setCoalesce(true);
        t.start();

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
                dispensedItemLabel.setToolTipText(null);
                receivedLabel.setText("1x " + itemNames.get(selectedItemId - 1));
                receivedLabel.setHorizontalAlignment(SwingConstants.CENTER);

                receivedFrame.setVisible(true);
                currentInput = " ";
                temporaryKeypadLock();
                updateInputField();
            }

        } catch (NullPointerException err) {
            displayError("INVALID NUMBER");
        }
    }

    public void updateReceivedSpecialItem(int selectedItemId) {

        this.currentAmount = 0.0;
        updateAmountLabel();
        this.amountLabel.validate();

        try {

            // Display the corresponding item image in the dispensedItemContainer
            String itemImageFileName = IMAGE_DIRECTORY_PATH + "items/item" + selectedItemId + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);

            // Update necessary fields
            dispensedItemLabel.setIcon(icon);
            int counter = 0;
            String toolTipText = "";
            for (int i : selectedBaseIndexes){
                if (counter == 1){
                    toolTipText += " and ";
                }
                toolTipText += itemNames.get(i);
                counter++;
            }
            counter = 0;
            toolTipText += " with ";
            for (int i : selectedAddonIndexes){
                if (counter == 1){
                    toolTipText += " and ";
                }
                toolTipText += itemNames.get(i);
                counter++;
            }
            dispensedItemLabel.setToolTipText(toolTipText);
            System.out.println(toolTipText);
            receivedLabel.setText("1x " + templateNames.get(selectedItemId - 10));
            receivedLabel.setHorizontalAlignment(SwingConstants.CENTER);

            receivedFrame.setVisible(true);
            frame.setVisible(true);
            temporaryKeypadLock();
            currentInput = " ";
            updateInputField();
        }

        catch (NullPointerException err) {
            displayError("INVALID NUMBER");
        }
    }

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

    public void disposeCurrentWindow() {
        try {
            this.frame.dispose();
        } catch (NullPointerException e) {
            System.out.println("Window currently not in view.");
        }
    }

}
