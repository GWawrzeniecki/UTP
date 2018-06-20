/**
 * @author Wawrzeniecki Grzegorz S15429
 */

package zad2;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class Main {
    public static void main(String[] args) {

        Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
        System.out.println(purch);

        purch.addPropertyChangeListener(e ->{
            Object oldVal =  e.getOldValue(), newVal = e.getNewValue();
            System.out.println("Change value of: " + e.getPropertyName() + " from: " + oldVal + " to: " + newVal);
        } );


        purch.addVetoableChangeListener(e -> { Double newVal = (Double) e.getNewValue();
            double val = newVal;
            // Sprawdzamy, czy zmiana  licznika jest dopuszczalna,
            // jeśli nie – sygnalizujemy wyjatek  PropertyVetoException
            if (val < 1000)//Price change to: 500.0 not allowed
                throw new PropertyVetoException(e.getPropertyName().substring(0,1).toUpperCase() + e.getPropertyName().substring(1) + " change to: " + e.getNewValue() + " not allowed", e);
        });
        try {
            purch.setData("w promocji");
            purch.setPrice(2000.00);
            System.out.println(purch);

            purch.setPrice(500.00);

        } catch (PropertyVetoException exc) {
            System.out.println(exc.getMessage());
        }
        System.out.println(purch);

    }

}
