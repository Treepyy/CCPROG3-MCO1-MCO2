package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendingView {
    private JFrame frame;
    private JPanel itemPanel, dispensedItemPanel;
    private JTextField inputField;
    private JButton cashInsertButton;
    private JLabel screenLabel;
    private String currentInput = "";
    private JLabel dispensedItemLabel;
    private JButton confirmButton;
    private JButton getButton;

    // Update this path to the directory containing the item images
    private static final String IMAGE_DIRECTORY_PATH = "/resources/";

    public VendingView() {

    }

    public void createAndShowGUI() {
        // Create and set up the main frame
        frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        // Create item panel to display items
        itemPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        int imageWidth = 100;
        int imageHeight = 100;

        // Add items to the item panel (replace with actual image file names and item names)
        for (int i = 1; i <= 12; i++) {
            String itemImageFileName = IMAGE_DIRECTORY_PATH + "items/item" + i + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
            Image image = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            JLabel itemLabel = new JLabel(icon);
            JLabel nameLabel = new JLabel("Item Name " + i, SwingConstants.CENTER); // Replace with actual item names
            JLabel numberLabel = new JLabel("Item " + i, SwingConstants.CENTER);
            JPanel itemContainer = new JPanel(new BorderLayout());
            itemContainer.add(itemLabel, BorderLayout.CENTER);
            JPanel labelPanel = new JPanel(new GridLayout(2, 1));
            labelPanel.add(nameLabel);
            labelPanel.add(numberLabel);
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
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ConfirmButtonListener());
        keypadPanel.add(clearButton);
        keypadPanel.add(zeroButton);
        keypadPanel.add(confirmButton);

        inputPanel.add(keypadPanel, BorderLayout.SOUTH);

        // Create cash insertion button
        cashInsertButton = new JButton("Insert Cash");

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

        getButton = new JButton("GET");
        getButton.addActionListener(new GetButtonListener());

        JPanel getButtonContainer = new JPanel();
        getButtonContainer.add(getButton);

        dispensedItemPanel = new JPanel();
        dispensedItemPanel.setLayout(new BoxLayout(dispensedItemPanel, BoxLayout.PAGE_AXIS));
        dispensedItemPanel.add(dispensedItemContainer);
        dispensedItemPanel.add(getButton);
        dispensedItemPanel.setVisible(false);
        frame.add(dispensedItemPanel, BorderLayout.NORTH);
    }

    private void createAndShowGUI1() {
        // Create and set up the main frame
        frame = new JFrame("Vending Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        // Create item panel to display items
        itemPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        int imageWidth = 100;
        int imageHeight = 100;

        // Add items to the item panel (replace with actual image file names and item names)
        for (int i = 1; i <= 12; i++) {
            String itemImageFileName = IMAGE_DIRECTORY_PATH + "items/item" + i + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
            Image image = icon.getImage().getScaledInstance(imageWidth, imageHeight, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            JLabel itemLabel = new JLabel(icon);
            JLabel nameLabel = new JLabel("Item Name " + i, SwingConstants.CENTER); // Replace with actual item names
            JLabel numberLabel = new JLabel("Item " + i, SwingConstants.CENTER);
            JPanel itemContainer = new JPanel(new BorderLayout());
            itemContainer.add(itemLabel, BorderLayout.CENTER);
            JPanel labelPanel = new JPanel(new GridLayout(2, 1));
            labelPanel.add(nameLabel);
            labelPanel.add(numberLabel);
            itemContainer.add(labelPanel, BorderLayout.SOUTH);
            itemPanel.add(itemContainer);
        }

        // Create panel for keypad and cash insertion button
        JPanel inputPanel = new JPanel(new BorderLayout());

        JPanel screenAndKeypadPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        inputField.setEditable(false);
        screenAndKeypadPanel.add(inputField, BorderLayout.NORTH);

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
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ConfirmButtonListener());
        keypadPanel.add(clearButton);
        keypadPanel.add(zeroButton);
        keypadPanel.add(confirmButton);

        screenAndKeypadPanel.add(keypadPanel, BorderLayout.CENTER);
        inputPanel.add(screenAndKeypadPanel, BorderLayout.CENTER);

        // Create the screen label to display the current input
        screenLabel = new JLabel("", SwingConstants.CENTER);
        screenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        screenLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        inputPanel.add(screenLabel, BorderLayout.NORTH);

        // Create cash insertion button
        cashInsertButton = new JButton("Insert Cash");
        inputPanel.add(cashInsertButton, BorderLayout.SOUTH);

        // Add item panel to the main frame (left side)
        frame.add(itemPanel, BorderLayout.WEST);

        // Create containers to display dispensed item image and cash denomination images
        dispensedItemPanel = new JPanel();
        dispensedItemPanel.setLayout(new BoxLayout(dispensedItemPanel, BoxLayout.PAGE_AXIS));
        dispensedItemPanel.setVisible(false);
        frame.add(dispensedItemPanel, BorderLayout.CENTER);

        // Create the screen label to display the current input (bottom side)
        inputField.setFont(new Font("Arial", Font.PLAIN, 24));
        inputField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Display the GUI
        frame.setVisible(true);
    }

    private void updateInputField() {
        // inputField.setText(currentInput);
        screenLabel.setText(currentInput);
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

    private class ConfirmButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                // Get the current input and convert it to an integer
                int selectedItemId = Integer.parseInt(currentInput);
                // Display the corresponding item image in the dispensedItemContainer
                String itemImageFileName = "/resources/items/item" + selectedItemId + ".png";
                ImageIcon icon = new ImageIcon(getClass().getResource(itemImageFileName));
                dispensedItemLabel.setIcon(icon);
                dispensedItemLabel.setText("Successfully dispensed!");

                dispensedItemPanel.setVisible(true);
                currentInput = "";
                updateInputField();
            }
            catch (NumberFormatException err){
                System.out.println("empty input");
            }
            catch (NullPointerException err){
                System.out.println("input out of range");
                currentInput = "";
                updateInputField();
            }
        }
    }

    private class GetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear the displayed item from the dispensedItemContainer
            dispensedItemLabel.setIcon(null);
            dispensedItemPanel.setVisible(false);
        }
    }
}

