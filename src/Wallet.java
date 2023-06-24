import java.util.TreeMap;
import java.util.Map;

public class Wallet {

    private TreeMap<Double, Integer> denominations = new TreeMap<Double, Integer>();

    public Wallet(){
        denominations.put(1000.00, 0);
        denominations.put(500.00, 0);
        denominations.put(200.00, 0);
        denominations.put(100.00, 0);
        denominations.put(50.00, 0);
        denominations.put(20.00, 0);
        denominations.put(10.00, 0);
        denominations.put(5.00, 0);
        denominations.put(1.00, 0);
        denominations.put(0.50, 0);
        denominations.put(0.25, 0);
        denominations.put(0.05, 0);
        denominations.put(0.01, 0);
    }

    public TreeMap<Double, Integer> getDenominations() {
        return denominations;
    }

    public double getTotal(){

        double total = 0;

        for (double key : denominations.keySet()) {
            int count = denominations.get(key);
            double amount = key * count;
            total += amount;
        }

        return total;
    }

    public void insertDenomination(double key, int amount){
        int newAmount = denominations.get(key) + amount;
        denominations.put(key, newAmount);
    }

    public void withdrawAll(){
        if (getTotal() != 0.0){
            displayReceivedDenominations(denominations);
            resetWallet();
        }
    }
    // converts a double value into a denomination treemap
    public TreeMap<Double, Integer> convertToDenominations(double cash){

        TreeMap<Double, Integer> converted = new TreeMap<Double, Integer>();
        converted.put(1000.00, 0);
        converted.put(500.00, 0);
        converted.put(200.00, 0);
        converted.put(100.00, 0);
        converted.put(50.00, 0);
        converted.put(20.00, 0);
        converted.put(10.00, 0);
        converted.put(5.00, 0);
        converted.put(1.00, 0);
        converted.put(0.50, 0);
        converted.put(0.25, 0);
        converted.put(0.05, 0);
        converted.put(0.01, 0);

        // loops through the entire treemap
        for (Map.Entry<Double, Integer> entry : converted.descendingMap().entrySet()) {
            double denomination = entry.getKey();
            int count = (int) (cash / denomination);
            converted.put(denomination, count);
            cash %= denomination;
        }

        return converted;

    }

    // this method will convert a given double value into denominations, then add it to the treemap
    public void addCash(Wallet addedWallet){

        for (double key : denominations.keySet()) {
            int newValue = denominations.get(key) + addedWallet.getDenominations().get(key);
            denominations.put(key, newValue);
        }

    }

    public void addCash(double cash){

        // loops through the entire treemap
        for (Map.Entry<Double, Integer> entry : denominations.descendingMap().entrySet()) {
            double denomination = entry.getKey();
            int count = (int) (cash / denomination);
            int newCount = entry.getValue() + count;
            denominations.put(denomination, newCount);
            cash %= denomination;
        }

    }

    public void subtractCash(Wallet subtractedWallet){

        for (double key : denominations.keySet()) {
            int newValue = denominations.get(key) - subtractedWallet.getDenominations().get(key);
            denominations.put(key, newValue);
        }

    }

    // this method will convert a given double value into denominations, then subtract it from the treemap
    public void subtractCash(double cash){

        // loops through the entire treemap
        for (Map.Entry<Double, Integer> entry : denominations.descendingMap().entrySet()) {
            double denomination = entry.getKey();
            int count = (int) (cash / denomination);
            int newCount = entry.getValue() - count;
            denominations.put(denomination, newCount);
            cash %= denomination;
        }

    }

    public void resetWallet(){
        for (double key : denominations.keySet()) {
            denominations.put(key, 0);
        }
    }

    private void displayReceivedDenominations(TreeMap<Double, Integer> denominations){

        String currencyName, type;
        System.out.println("You Received:");

        for (Map.Entry<Double, Integer> entry : denominations.entrySet()) {
            double denomination = entry.getKey();
            int count = entry.getValue();
            if (count > 0) {
                if (entry.getKey() < 1){
                    denomination *= 10;
                    currencyName = " Centavo ";
                    type = "Coin";
                }
                else if (entry.getKey() >= 1 && entry.getKey() < 20){
                    currencyName = " Peso ";
                    type = "Coin";
                }
                else{
                    currencyName = " Peso ";
                    type = "Bill";
                }
                System.out.println(count + "x " + denomination + currencyName + type);
            }
        }

    }


}
