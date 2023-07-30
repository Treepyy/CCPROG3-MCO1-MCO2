package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class VendingView {
    private JFrame frame, receivedFrame, cashFrame;
    private JPanel itemPanel, dispensedItemPanel;
    private JTextField inputField, cashInputField;
    private JButton cashInsertButton;
    private JLabel screenLabel, cashLabel;
    private String currentInput = "", cashInput = "Current Cash: ₱0.0";
    private JLabel dispensedItemLabel;
    private JButton confirmButton;
    private JButton getButton;
    private ArrayList<String> itemNames;

    // Update this path to the directory containing the item images
    private static final String IMAGE_DIRECTORY_PATH = "resources/";

    public VendingView() {
        this.confirmButton = new JButton();
        this.getButton = new JButton();
        this.cashInsertButton = new JButton();
    }

    public void displayRegularGUI(ArrayList<String> itemNames) {

        this.itemNames = itemNames;

        // Create and set up the main frame
        frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        // Create item panel to display items
        itemPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        int imageWidth = 100;
        int imageHeight = 100;

        // Add items to the item panel (replace with actual image file names and item names)
        for (int i = 1; i <= 9; i++) {
            String itemImageFileName = IMAGE_DIRECTORY_PATH + "/items/item" + i + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
            Image image = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            JLabel itemLabel = new JLabel(icon);
            JLabel nameLabel = new JLabel(itemNames.get(i-1), SwingConstants.CENTER);
            JLabel numberLabel = new JLabel("[" + i + "]", SwingConstants.CENTER);
            JLabel amountInStock = new JLabel("Amount: " + 0, SwingConstants.CENTER);
            JPanel itemContainer = new JPanel(new BorderLayout());
            itemContainer.add(itemLabel, BorderLayout.CENTER);
            JPanel labelPanel = new JPanel(new GridLayout(3, 1));
            labelPanel.add(nameLabel);
            labelPanel.add(numberLabel);
            labelPanel.add(amountInStock);
            itemContainer.add(labelPanel, BorderLayout.SOUTH);
            itemPanel.add(itemContainer);
        }

        // Create panel for keypad and cash insertion button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEditable(false);
        // inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        for (int i = 1; i <= 9; i++) {
            JButton numberButton = new JButton(Integer.toString(i));
            numberButton.addActionListener(new NumberInputListener());
            keypadPanel.add(numberButton);
        }
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new NumberInputListener());
        JButton zeroButton = new JButton("0");
        zeroButton.addActionListener(new NumberInputListener());
        confirmButton.setText("Confirm");
        // confirmButton.addActionListener(new ConfirmButtonListener());
        keypadPanel.add(clearButton);
        keypadPanel.add(zeroButton);
        keypadPanel.add(confirmButton);

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

        // Create containers to display dispensed item image and cash denomination images
        JPanel dispensedItemContainer = new JPanel();
        dispensedItemLabel = new JLabel();
        dispensedItemContainer.add(dispensedItemLabel);

        JPanel cashDenominationContainer = new JPanel();
        JLabel cashDenominationLabel = new JLabel();
        cashDenominationContainer.add(cashDenominationLabel);

        // Add panels to the main frame
        frame.add(itemPanel, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.EAST);
        frame.add(cashInsertButton, BorderLayout.SOUTH);
        frame.add(dispensedItemContainer, BorderLayout.NORTH);
        frame.add(cashDenominationContainer, BorderLayout.WEST);

        // Display the GUI
        frame.setVisible(true);

        getButton.setText("GET");

        JPanel getButtonContainer = new JPanel();
        getButtonContainer.add(getButton);

        dispensedItemPanel = new JPanel();
        dispensedItemPanel.setLayout(new BoxLayout(dispensedItemPanel, BoxLayout.PAGE_AXIS));
        dispensedItemPanel.add(dispensedItemContainer);
        dispensedItemPanel.add(getButton);

        receivedFrame = new JFrame("Successfully Dispensed!");
        receivedFrame.setSize(400, 350);
        receivedFrame.setLayout(new BorderLayout());

        receivedFrame.add(dispensedItemPanel, BorderLayout.NORTH);

        cashFrame = new JFrame("Insert Cash");
        cashFrame.setSize(400, 300);
        cashFrame.setLayout(new BorderLayout());

        Icon oneCentIcon = new ImageIcon(getClass().getResource("resources/cash/1c.png"));
        JButton oneCentBtn = new JButton(oneCentIcon);
        cashDenominationContainer.add(oneCentBtn);

        Icon fiveCentIcon = new ImageIcon(getClass().getResource("resources/cash/5c.png"));
        JButton fiveCentBtn = new JButton(fiveCentIcon);
        cashDenominationContainer.add(fiveCentBtn);

        Icon twentyFiveCentIcon = new ImageIcon(getClass().getResource("resources/cash/25c.png"));
        JButton twentyFiveCentBtn = new JButton(twentyFiveCentIcon);
        cashDenominationContainer.add(twentyFiveCentBtn);

        Icon onePesoIcon = new ImageIcon(getClass().getResource("resources/cash/p1.png"));
        JButton onePesoBtn = new JButton(onePesoIcon);
        cashDenominationContainer.add(onePesoBtn);

        Icon fivePesoIcon = new ImageIcon(getClass().getResource("resources/cash/p5.png"));
        JButton fivePesoBtn = new JButton(fivePesoIcon);
        cashDenominationContainer.add(fivePesoBtn);

        cashFrame.add(cashDenominationContainer);

        // TODO : scale buttons down, fix layout, scale images down
        // for receiving change, user must click the corresponding denomination button n amount of times, where n = number of the denominations received




    }

    private void updateInputField() {
        // inputField.setText(currentInput);
        screenLabel.setText(currentInput);
    }

    private void updateCashInputField(){
        cashLabel.setText(cashInput);
    }

    private class NumberInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("Clear")) {
                currentInput = "";
            } else {
                currentInput += command;
            }
            updateInputField();
        }
    }

    private class CashInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            String command = e.getActionCommand();
            Double totalAdded = 0.0;
            Double currentAdded = Double.parseDouble(command);

            totalAdded += currentAdded;
            cashInput = "Current Cash: ₱" + totalAdded.toString();
            updateCashInputField();

        }
    }

    public void updateReceivedItem(){
        try{
            // Get the current input and convert it to an integer
            int selectedItemId = Integer.parseInt(currentInput);
            if (selectedItemId <= 9){
                // Display the corresponding item image in the dispensedItemContainer
                String itemImageFileName = IMAGE_DIRECTORY_PATH + "items/item" + selectedItemId + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                icon = new ImageIcon(image);

                // Update necessary fields
                dispensedItemLabel.setIcon(icon);
                receivedFrame.add(dispensedItemPanel);
                receivedFrame.setVisible(true);
                dispensedItemPanel.setVisible(true);
                currentInput = "";
                updateInputField();
            }

        }
        catch (NumberFormatException err){
            System.out.println("empty input");
        }
        catch (NullPointerException err){
            System.out.println("out of bounds");
        }
    }

    public void closeReceivedPanel(){
        dispensedItemLabel.setIcon(null);
        receivedFrame.setVisible(false);
        receivedFrame.dispose();
    }

    public void displayCashPanel(){
        cashFrame.setVisible(true);
    }

    public void setConfirmButtonListener(ActionListener actionListener){
        this.confirmButton.addActionListener(actionListener);
    }

    public void setGetButtonListener(ActionListener actionListener){
        this.getButton.addActionListener(actionListener);
    }

    public void setCashInsertButtonListener(ActionListener actionListener){
        this.cashInsertButton.addActionListener(actionListener);
    }

    public int getNumberInput(){
        return Integer.parseInt(currentInput);
    }
}

