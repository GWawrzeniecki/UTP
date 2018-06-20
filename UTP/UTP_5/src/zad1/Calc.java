package zad1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class Calc {
    private HashMap<String, Runnable> helpMap;
    private BigDecimal a;
    private BigDecimal b;
    private BigDecimal result;

    public Calc() {

        helpMap = new HashMap<>();
        helpMap.put("+", () -> result = a.add(b));
        helpMap.put("-", () -> result = a.subtract(b));
        helpMap.put("*", () -> result = a.multiply(b));
        helpMap.put("/", () -> result = a.divide(b, 7, RoundingMode.FLOOR).stripTrailingZeros());


    }


    public String doCalc(String arg) {
        try {
            arg = arg.replaceAll("\\s+", " ");
            String[] tmpTab = arg.split("\\s");

            a = new BigDecimal(tmpTab[0]);
            b = new BigDecimal(tmpTab[2]);

            helpMap.get(tmpTab[1]).run();

            return result.toPlainString();
        } catch (Exception e) {
            return "Invalid command to calc";
        }


    }
}