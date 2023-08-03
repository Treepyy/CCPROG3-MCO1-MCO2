package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * GUI setup for the Special Vending Machine
 * @author Vance Gyan M. Robles
 */
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

    /**
     * Constructs a new SpecialVendingView object.
     */
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

    /**
     * Updates the information of items displayed in the vending machine.
     *
     * @param itemNames     ArrayList of item names.
     * @param itemPrices    ArrayList of item prices.
     * @param itemCalories  ArrayList of item calories.
     * @param itemAmounts   ArrayList of item stock amounts.
     * @param templatePrices ArrayList of customizable item template prices.
     */
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

    /**
     * Displays the Special Vending Machine GUI.
     *
     * @param itemNames     ArrayList of item names.
     * @param itemPrices    ArrayList of item prices.
     * @param itemCalories  ArrayList of item calories.
     * @param itemAmounts   ArrayList of item stock amounts.
     * @param templateNames ArrayList of customizable item template names.
     * @param templatePrices ArrayList of customizable item template prices.
     */
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

    /**
     * Creates the GUI for the cash insertion interface.
     */
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

    /**
     * Displays the cash insertion GUI.
     */
    public void displayCashGUI() {
        this.cashFrame.setVisible(true);
    }

    /**
     * Updates the label displaying the current amount of cash inserted.
     */
    private void updateAmountLabel() {
        this.amountLabel.setText("₱" + String.format("%.2f", currentAmount));
        keypadPanel.revalidate();
        keypadPanel.repaint();
        cashFrame.revalidate();
        cashFrame.repaint();
    }

    /**
     * Updates the item panel with the latest item information.
     */
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

    /**
     * Displays the customizable item menu given the template of the index, allows the user to customize their item.
     *
     * @param templateIndex is the index of the customizable item template to be used.
     *
     */
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

                ImageIcon brothIcon1 = new ImageIcon(getClass().getResource("resources/addons/shiobroth.png"));
                ImageIcon brothIcon2 = new ImageIcon(getClass().getResource("resources/addons/misobroth.png"));
                ImageIcon brothIcon3 = new ImageIcon(getClass().getResource("resources/addons/ukkokeibroth.png"));
                ImageIcon brothIcon4 = new ImageIcon(getClass().getResource("resources/addons/tonkotsubroth.png"));
                ImageIcon noodleIcon1 = new ImageIcon(getClass().getResource("resources/items/item5.png"));
                ImageIcon noodleIcon2 = new ImageIcon(getClass().getResource("resources/items/item6.png"));
                ImageIcon toppingIcon1 = new ImageIcon(getClass().getResource("resources/items/item8.png"));
                ImageIcon toppingIcon2 = new ImageIcon(getClass().getResource("resources/items/item9.png"));
                ImageIcon toppingIcon3 = new ImageIcon(getClass().getResource("resources/addons/saltedegg.png"));
                ImageIcon toppingIcon4 = new ImageIcon(getClass().getResource("resources/addons/chickenslices.png"));

                JButton shioBroth = new JButton(brothIcon1);
                shioBroth.setToolTipText("<html>" + "Shio Broth" + "<br>" + "Calories: " + itemCalories.get(9) + "<br>" + itemAmounts.get(9) + " In Stock" + "</html>");
                shioBroth.setPreferredSize(buttonSize);
                JButton misoBroth = new JButton(brothIcon2);
                misoBroth.setToolTipText("<html>" + "Miso Broth" + "<br>" + "Calories: " + itemCalories.get(10) + "<br>" + itemAmounts.get(10) + " In Stock" + "</html>");
                misoBroth.setPreferredSize(buttonSize);
                JButton ukkokeiBroth = new JButton(brothIcon3);
                ukkokeiBroth.setToolTipText("<html>" + "Ukkokei Broth" + "<br>" + "Calories: " + itemCalories.get(11) + "<br>" + itemAmounts.get(11) + " In Stock" + "</html>");
                ukkokeiBroth.setPreferredSize(buttonSize);
                JButton tonkotsuBroth = new JButton(brothIcon4);
                tonkotsuBroth.setToolTipText("<html>" + "Tonkotsu Broth" + "<br>" + "Calories: " + itemCalories.get(12) + "<br>" + itemAmounts.get(12) + " In Stock" + "</html>");
                tonkotsuBroth.setPreferredSize(buttonSize);
                JButton sobaNoodles = new JButton(noodleIcon1);
                sobaNoodles.setToolTipText("<html>" + "Soba Noodles" + "<br>" + "Calories: " + itemCalories.get(4) + "<br>" + itemAmounts.get(4) + " In Stock" + "</html>");
                sobaNoodles.setPreferredSize(buttonSize);
                JButton udonNoodles = new JButton(noodleIcon2);
                udonNoodles.setToolTipText("<html>" + "Udon Noodles" + "<br>" + "Calories: " + itemCalories.get(5) + "<br>" + itemAmounts.get(5) + " In Stock" + "</html>");
                udonNoodles.setPreferredSize(buttonSize);
                JButton fishCake = new JButton(toppingIcon1);
                fishCake.setToolTipText("<html>" + "Fish Cake" + "<br>" + "Calories: " + itemCalories.get(7) + "<br>" + itemAmounts.get(7) + " In Stock" + "</html>");
                fishCake.setPreferredSize(buttonSize);
                JButton chasuPork = new JButton(toppingIcon2);
                chasuPork.setToolTipText("<html>" + "Chasu Pork" + "<br>" + "Calories: " + itemCalories.get(8) + "<br>" + itemAmounts.get(8) + " In Stock" + "</html>");
                chasuPork.setPreferredSize(buttonSize);
                JButton saltedEgg = new JButton(toppingIcon3);
                saltedEgg.setToolTipText("<html>" + "Salted Egg" + "<br>" + "Calories: " + itemCalories.get(13) + "<br>" + itemAmounts.get(13) + " In Stock" + "</html>");
                saltedEgg.setPreferredSize(buttonSize);
                JButton chickenSlices = new JButton(toppingIcon4);
                chickenSlices.setToolTipText("<html>" + "Chicken Slices" + "<br>" + "Calories: " + itemCalories.get(15) + "<br>" + itemAmounts.get(15) + " In Stock" + "</html>");
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
                        selectedAddonIndexes.add(13); // Salted Egg index

                        addonCounter[0]++;

                        if (addonCounter[0] == 1) {
                            selectedRamenToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedRamenToppingsMessage[0] += "Salted Egg";

                        if (addonCounter[0] >= 1 && addonCounter[0] < addonLimit){
                            selectedRamenToppingsMessage[0] += ", ";
                        }

                        ramenCalorieCounter[0] += itemCalories.get(13);
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
                        selectedAddonIndexes.add(15); // Chicken Slices index

                        addonCounter[0]++;

                        if (addonCounter[0] == 1) {
                            selectedRamenToppingsMessage[0] += "Selected Toppings: ";
                        }

                        selectedRamenToppingsMessage[0] += "Chicken Slices";

                        if (addonCounter[0] >= 1 && addonCounter[0] < addonLimit){
                            selectedRamenToppingsMessage[0] += ", ";
                        }

                        ramenCalorieCounter[0] += itemCalories.get(15);
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
                regularRice.setToolTipText("<html>" + "Plain Rice" + "<br>" + "Calories: " + itemCalories.get(2) + "<br>" + itemAmounts.get(2) + " In Stock" + "</html>");
                JButton javaRice = new JButton(baseIcon2);
                javaRice.setToolTipText("<html>" + "Java Rice" + "<br>" + "Calories: " + itemCalories.get(3) + "<br>" + itemAmounts.get(3) + " In Stock" + "</html>");

                JButton bacon = new JButton(icon1);
                bacon.setToolTipText("<html>" + "Bacon" + "<br>" + "Calories: " + itemCalories.get(17) + "<br>" + itemAmounts.get(17) + " In Stock" + "</html>");
                JButton tapa = new JButton(icon2);
                tapa.setToolTipText("<html>" + "Tapa" + "<br>" + "Calories: " + itemCalories.get(18) + "<br>" + itemAmounts.get(18) + " In Stock" + "</html>");
                JButton tocino = new JButton(icon3);
                tocino.setToolTipText("<html>" + "Plain Rice" + "<br>" + "Calories: " + itemCalories.get(19) + "<br>" + itemAmounts.get(19) + " In Stock" + "</html>");

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

                ImageIcon addonIcon1 = new ImageIcon(getClass().getResource("resources/addons/nuts.png"));
                ImageIcon addonIcon2 = new ImageIcon(getClass().getResource("resources/addons/natadecoco.png"));
                ImageIcon addonIcon3 = new ImageIcon(getClass().getResource("resources/addons/macapuno.png"));
                ImageIcon addonIcon4 = new ImageIcon(getClass().getResource("resources/addons/lecheflan.png"));
                ImageIcon addonIcon5 = new ImageIcon(getClass().getResource("resources/addons/bananaslices.png"));
                ImageIcon addonIcon6 = new ImageIcon(getClass().getResource("resources/addons/langka.png"));

                JButton nuts = new JButton(addonIcon1);
                nuts.setToolTipText("<html>" + "Nuts" + "<br>" + "Calories: " + itemCalories.get(20) + "<br>" + itemAmounts.get(20) + " In Stock" + "</html>");
                JButton nataDeCoco = new JButton(addonIcon2);
                nataDeCoco.setToolTipText("<html>" + "Nata de Coco" + "<br>" + "Calories: " + itemCalories.get(21) + "<br>" + itemAmounts.get(21) + " In Stock" + "</html>");
                JButton macapuno = new JButton(addonIcon3);
                macapuno.setToolTipText("<html>" + "Macapuno" + "<br>" + "Calories: " + itemCalories.get(22) + "<br>" + itemAmounts.get(22) + " In Stock" + "</html>");
                JButton lecheFlan = new JButton(addonIcon4);
                lecheFlan.setToolTipText("<html>" + "Leche Flan" + "<br>" + "Calories: " + itemCalories.get(23) + "<br>" + itemAmounts.get(23) + " In Stock" + "</html>");
                JButton bananaSlices = new JButton(addonIcon5);
                bananaSlices.setToolTipText("<html>" + "Banana Slices" + "<br>" + "Calories: " + itemCalories.get(24) + "<br>" + itemAmounts.get(24) + " In Stock" + "</html>");
                JButton langka = new JButton(addonIcon6);
                langka.setToolTipText("<html>" + "Langka" + "<br>" + "Calories: " + itemCalories.get(25) + "<br>" + itemAmounts.get(25) + " In Stock" + "</html>");


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

    /**
     * Gets the ArrayList containing the user's selected addon item indexes.
     *
     * @return ArrayList of indexes of selected item addon(s).
     */
    public ArrayList<Integer> getSelectedAddonIndexes(){
        return selectedAddonIndexes;
    }

    /**
     * Gets the ArrayList containing the user's selected base item indexes.
     *
     * @return ArrayList of indexes of selected item base(s).
     */
    public ArrayList<Integer> getSelectedBaseIndexes(){
        return selectedBaseIndexes;
    }

    /**
     * Sets the ActionListener for the confirmation of customizing ramen.
     *
     * @param actionListener The ActionListener to be set.
     */
    public void setConfirmRamenButtonListener(ActionListener actionListener){
        this.confirmRamenButton.addActionListener(actionListener);
    }

    /**
     * Plays the preparation sequence for ramen.
     *
     * @param message is the user's change message (will be displayed if applicable).
     */
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

    /**
     * Sets the ActionListener for the confirmation of customizing silog meal.
     *
     * @param actionListener The ActionListener to be set.
     */
    public void setConfirmSilogButtonListener(ActionListener actionListener){
        this.confirmSilogButton.addActionListener(actionListener);
    }

    /**
     * Plays the preparation sequence for silog meal.
     *
     * @param message is the user's change message (will be displayed if applicable).
     */
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

    /**
     * Sets the ActionListener for the confirmation of customizing halo-halo.
     *
     * @param actionListener The ActionListener to be set.
     */
    public void setConfirmHaloHaloButtonListener(ActionListener actionListener){
        this.confirmHaloHaloButton.addActionListener(actionListener);
    }

    /**
     * Plays the preparation sequence for silog meal.
     *
     * @param message is the user's change message (will be displayed if applicable).
     */
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

    /**
     * ActionListener implementation for cash insert buttons. Updates the current amount,
     * updates the last pressed value, and calls the updateAmountLabel() method.
     */
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

    /**
     * Updates the last pressed value variable with the given value.
     *
     * @param value The value of the last pressed button.
     */
    public void updateLastPressedValue(double value) {
        this.lastPressedValue = value;
        System.out.println(lastPressedValue);
    }

    /**
     * Retrieves the value of the last pressed button.
     *
     * @return The value of the last pressed button.
     */
    public double getLastPressedValue() {
        return lastPressedValue;
    }

    /**
     * Sets the given ActionListener for all coin buttons.
     *
     * @param actionListener The ActionListener to be set for the coin buttons.
     */
    public void setCoinButtonsListeners(ActionListener actionListener) {
        for (JButton coinButton : this.coinButtons) {
            coinButton.addActionListener(actionListener);
        }
    }

    /**
     * Sets the given ActionListener for all bill buttons.
     *
     * @param actionListener The ActionListener to be set for the bill buttons.
     */
    public void setBillButtonsListeners(ActionListener actionListener) {
        for (JButton billButton : this.billButtons) {
            billButton.addActionListener(actionListener);
        }
    }

    /**
     * ActionListener for the Confirm button in the cash insertion GUI.
     */
    private class ConfirmButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            cashFrame.setVisible(false);
        }
    }

    /**
     * Withdraws the cash inserted by the user and resets the amount label.
     */
    public void withdrawInsertedCash() {
        cashFrame.setVisible(false);
        currentAmount = 0.0;
        updateAmountLabel();
        amountLabel.validate();
    }

    /**
     * Sets the ActionListener for the Cancel button in the cash insertion GUI.
     *
     * @param actionListener The ActionListener to be set for the Cancel button.
     */
    public void setCancelButtonListener(ActionListener actionListener) {
        this.cancelButton.addActionListener(actionListener);
    }

    /**
     * Converts the coin value from its image filename to its corresponding double value.
     *
     * @param coinValue The filename of the coin image.
     * @return The double value of the coin.
     */
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

    /**
     * Updates the input field displaying the current user input.
     */
    private void updateInputField() {
        // inputField.setText(currentInput);
        screenLabel.setForeground(Color.BLACK);
        if (currentInput.trim().length() < 3)
            screenLabel.setText(currentInput);
    }

    /**
     * ActionListener for number input buttons (0-9 and Clear).
     */
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

    /**
     * Updates the GUI with the item received by the user (for regular items).
     */
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

    /**
     * Updates the GUI with the item received by the user (for special items).
     * Includes additional information of the user's customized item within the corresponding image tooltip.
     *
     * @param selectedItemId The ID of the selected special item.
     */
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

    /**
     * Updates the GUI with the change denomination to be given to the user.
     *
     * @param message The message containing the change denomination details.
     */
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

    /**
     * Displays a popup window which shows the returned inserted money of the user.
     *
     * @param message The message containing the denominations of the returned money.
     */
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

    /**
     * Displays an error message in the GUI and temporarily locks the keypad.
     *
     * @param errorMessage The error message to be displayed.
     */
    public void displayError(String errorMessage) {
        screenLabel.setText(errorMessage);
        screenLabel.setForeground(Color.RED);
        screenLabel.validate();
        temporaryKeypadLock();
    }

    /**
     * Displays an error message in the GUI and temporarily locks the keypad.
     * Additionally, displays the withdrawal popup (of cash) to the user
     *
     * @param errorMessage The error message to be displayed.
     */
    public void displayErrorWithdraw(String errorMessage) {
        screenLabel.setText(errorMessage);
        screenLabel.setForeground(Color.RED);
        screenLabel.validate();
        withdrawInsertedCash();
        temporaryKeypadLock();
    }

    /**
     * Temporarily locks the keypad to prevent user input while displaying an error message.
     * After a short delay, the keypad will be unlocked.
     */
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

    /**
     * Disables the keypad (number input buttons).
     */
    private void disableKeypad() {
        setPanelEnabled(this.keypadPanel, false);
    }

    /**
     * Enables the keypad (number input buttons).
     */
    private void enableKeypad() {
        setPanelEnabled(this.keypadPanel, true);
    }

    /**
     * Recursively sets the enabled status for all components in a JPanel.
     * Function based on answer of Kesavamoorthi on StackOverflow: <a href="https://stackoverflow.com/questions/19324918/how-to-disable-all-components-in-a-jpanel">...</a>
     *
     * @param panel      The JPanel to modify.
     * @param isEnabled  The flag indicating whether to enable or disable the components.
     */
    void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    /**
     * Closes the popup window displaying the received item.
     */
    public void closeReceivedPanel() {
        receivedFrame.setVisible(false);
        receivedFrame.dispose();
    }

    /**
     * Sets the ActionListener for the Purchase button in the main GUI.
     *
     * @param actionListener The ActionListener to be set for the Purchase button.
     */
    public void setPurchaseButtonListener(ActionListener actionListener) {
        this.purchaseButton.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the GET button in the received item popup window.
     *
     * @param actionListener The ActionListener to be set for the GET button.
     */
    public void setGetButtonListener(ActionListener actionListener) {
        this.getButton.addActionListener(actionListener);
    }

    /**
     * Sets the ActionListener for the cash insert button in the main GUI.
     *
     * @param actionListener The ActionListener to be set for the cash insert button.
     */
    public void setCashInsertButtonListener(ActionListener actionListener) {
        this.cashInsertButton.addActionListener(actionListener);
    }

    /**
     * Retrieves the number input provided by the user.
     *
     * @return The integer value of the user's number input.
     */
    public int getNumberInput() {
        return Integer.parseInt(currentInput.trim());
    }

    /**
     * Closes the current window (main GUI).
     */
    public void disposeCurrentWindow() {
        try {
            this.frame.dispose();
        } catch (NullPointerException e) {
            System.out.println("Window currently not in view.");
        }
    }

}
