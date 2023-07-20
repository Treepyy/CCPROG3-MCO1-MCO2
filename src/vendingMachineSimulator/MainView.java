package vendingMachineSimulator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainView {

    private JFrame mainFrame, errorFrame;
    private JLabel welcomelbl, feedbackLbl, errorLbl;
    private JTextField priceTf;
    private JButton createBtn, testBtn, exitBtn, regularBtn, specialBtn, createHomeBtn, testHomeBtn, vendingBtn, maintBtn;
    private JButton restockBtn, setPriceBtn, collectBtn, replenishBtn, summaryBtn, invBtn, maintReturnBtn, optionReturnBtn, confirmBtn, changeBtn;
    private JButton overwriteYesBtn, overwriteNoBtn, okayBtn;
    private JTextArea employeeListTextArea;
    private JPanel panel, leftPanel;
    private JComboBox itemNames;

    public MainView() {
        // Frames setup
        this.mainFrame = new JFrame("Vending Machine Factory");

        this.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.mainFrame.setSize(250, 400);

        this.errorFrame = new JFrame("");
        this.errorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.errorFrame.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.errorFrame.setSize(200, 200);
        this.errorLbl = new JLabel("Error!");

        this.welcomelbl = new JLabel("Welcome!");
        this.feedbackLbl = new JLabel();
        this.feedbackLbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.feedbackLbl.setPreferredSize(new Dimension(220, 30));

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
        this.summaryBtn = new JButton("Display Transactions");
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

        this.itemNames = new JComboBox<>();


    }

    public void setDropDownItems(ArrayList<String> itemNameList){
        this.itemNames = new JComboBox(itemNameList.toArray());
    }

    public int getDropDownSelection(){
        return itemNames.getSelectedIndex();
    }

    public void setCreateBtnListener(ActionListener actionListener) {
        this.createBtn.addActionListener(actionListener);
    }

    public void setTestBtnListener(ActionListener actionListener) {
        this.testBtn.addActionListener(actionListener);
    }

    public void setExitBtnListener(ActionListener actionListener) {
        this.exitBtn.addActionListener(actionListener);
    }

    public void setRegularBtnListener(ActionListener actionListener) {
        this.regularBtn.addActionListener(actionListener);
    }

    public void setSpecialBtnListener(ActionListener actionListener) {
        this.specialBtn.addActionListener(actionListener);
    }

    public void setHomeBtnListener(ActionListener actionListener) {
        this.createHomeBtn.addActionListener(actionListener);
        this.testHomeBtn.addActionListener(actionListener);
    }

    public void setMaintBtnListener(ActionListener actionListener){
        this.maintBtn.addActionListener(actionListener);
    }

    public void setMaintReturnBtnListener(ActionListener actionListener){
        this.maintReturnBtn.addActionListener(actionListener);
    }

    public void setOptionReturnBtnListener(ActionListener actionListener){
        this.optionReturnBtn.addActionListener(actionListener);
    }

    public void setSetPriceBtnListener(ActionListener actionListener){
        this.setPriceBtn.addActionListener(actionListener);
    }

    public void setConfirmBtnListener(ActionListener actionListener){
        this.confirmBtn.addActionListener(actionListener);
    }

    public void setChangeBtnListener(ActionListener actionListener){
        this.changeBtn.addActionListener(actionListener);
    }

    public void setOverwriteYesBtnListener(ActionListener actionListener){
        this.overwriteYesBtn.addActionListener(actionListener);
    }

    public void setOverwriteNoBtnListener(ActionListener actionListener){
        this.overwriteNoBtn.addActionListener(actionListener);
    }
    public void setOkayBtnListener(ActionListener actionListener){
        this.okayBtn.addActionListener(actionListener);
    }
    public void displayFeedback(String feedback, Color textColor){
        this.feedbackLbl.setForeground(textColor);
        this.feedbackLbl.setText(feedback);
    }

    public void setEmployeeListLblText(String text) {
        this.employeeListTextArea.setText(text);
    }

    public String getPriceTfText() {
        return this.priceTf.getText();
    }

    public void clearFeedback() {
        this.feedbackLbl.setText("");
    }

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

    public void displayError(String errorMessage){
        this.errorFrame.setVisible(true);
        this.errorFrame.setTitle("Error!");
        this.errorFrame.remove(errorLbl);
        this.errorLbl.setText(errorMessage);
        this.errorFrame.add(errorLbl);
        this.errorFrame.add(okayBtn);
        this.errorFrame.validate();
    }
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

    public void closeErrorFrame(){
        this.errorFrame.setVisible(false);
        this.errorFrame.getContentPane().removeAll();
        this.errorFrame.dispose();
    }

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

    public void displayRestock(){

    }

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
    public void displaySetPrice(double oldItemPrice){
        this.confirmBtn.setEnabled(false);
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

    public void displayReplenish(){

    }

    public void displayInventory(){

    }

}
