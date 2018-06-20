/**
 * @author Wawrzeniecki Grzegorz S15429
 */

package zad2;


import java.beans.*;

public class Purchase {

    private String prod; // prosta
    private String data; //zwiazana
    private double price; // zwiazana i ograniczona

    // Pomocniczy obiekt do prowadzenia listy słuchaczy zmian właściwości oraz
    // propagowania zmian  wśród zarejestrowanych złuchaczy
    private PropertyChangeSupport chg = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetos = new VetoableChangeSupport(this);


    public Purchase(String prod, String data, double price) {
        this.prod = prod;
        this.data = data;
        this.price = price;
    }

    //setter wlasciwosci data
    public synchronized void setData(String newData) {
        String oldData = data;
        data = newData;
        // wywołanie metody firePropertChange z klasy PropertyChangeSupport
        // powoduje wygenerowanie zdarzenia PropertyChangeEvent i rozpropagowanie
        // go wśród wszystkich przyłączonych słuchaczy, ale tylko wtedy, gdy nowa
        // wartość właściwości różni się od starej wartości

        chg.firePropertyChange("data", oldData, newData);


    }

    // Metody przyłączania i odłączania słuchaczy zmian właściwości
    public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
        chg.addPropertyChangeListener(l);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener l) {
        chg.removePropertyChangeListener(l);
    }

    public synchronized void addVetoableChangeListener(VetoableChangeListener l) {
        vetos.addVetoableChangeListener(l);
    }

    public synchronized void removeVetoableChangeListener(VetoableChangeListener l) {
        vetos.removeVetoableChangeListener(l);
    }

    public void setPrice(double price) throws PropertyVetoException {
        double oldValue = this.price;
        vetos.fireVetoableChange("price", oldValue, price);

        this.price = price;
        chg.firePropertyChange("price", oldValue, price);

    }

    @Override
    public String toString() {
        return "Purchase " + "[" + "prod" + "=" + prod + ", " + "data=" + data + ", " + "price=" + price + "]";
    }
}
