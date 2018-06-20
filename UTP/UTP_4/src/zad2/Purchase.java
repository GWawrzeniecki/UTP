package zad2;

public class Purchase  {
    //private String lastName;
    private String firstAndLastName;
    private String idClient;
    private String nameOfPurchase;
    private double amount;
    private double price;
    private double totalCost;

    public Purchase(String idClient, String firstAndLastName, String nameOfPurchase, double price, double amount) {
        this.amount = amount;
        this.firstAndLastName = firstAndLastName;
        this.idClient = idClient;
        this.price = price;
        this.nameOfPurchase = nameOfPurchase;
        totalCost = -1;


    }
    public Purchase(Object purch) {
    }

    public String getLastName() {
        String tmp[] = firstAndLastName.split(" ");

        return tmp[0];
    }

    public String getIdClient() {

        return idClient;
    }

    public String getPrice() {
        return String.valueOf(price);

    }

    public String getAmount() {
        return String.valueOf(amount);
    }

    public String toString() {

        if(totalCost != -1)
        return idClient + ";" + firstAndLastName + ";" + nameOfPurchase + ";" + price + ";" + amount + " (koszt: " + totalCost + ")";

        return idClient + ";" + firstAndLastName + ";" + nameOfPurchase + ";" + price + ";" + amount;
    }

    public void setTotalCost(double cost) {
        totalCost = cost;
    }



}
