package zad2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CustomersPurchaseSortFind {
    List<String> listCustomers;
    List<Purchase> listPurchase;
    private String path;

    public void readFile(String path) {
        listCustomers = new ArrayList<>();
        this.path = path;
        Scanner scan = null;
        try {
            scan = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
            return;
        }
        while (scan.hasNext()) {
            listCustomers.add(scan.nextLine());
        }
        scan.close();
        listPurchase = new ArrayList<>();

        for (String t : listCustomers) {
            String tmp[] = t.split(";");
            listPurchase.add(new Purchase(tmp[0], tmp[1], tmp[2], Double.valueOf(tmp[3]), Double.valueOf(tmp[4])));
        }


    }


    public void showSortedBy(String kind) {


        if (kind.equals("Nazwiska")) {
            System.out.println("Nazwiska");


            Collections.sort(listPurchase, (Purchase o1, Purchase o2) -> {
                if (!(o1.getLastName().equals(o2.getLastName()))) {
                    if ((o1.getLastName().substring(0, 1).compareTo(o2.getLastName().substring(0, 1))) < 0) {
                        return -1;
                    } else if ((o1.getLastName().substring(0, 1).compareTo(o2.getLastName().substring(0, 1))) == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    if ((o1.getIdClient().substring(5, 6).compareTo(o2.getIdClient().substring(5, 6))) < 0) {
                        return -1;
                    } else if ((o1.getIdClient().substring(5, 6).compareTo(o2.getIdClient().substring(5, 6))) == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }


            });

            listPurchase.forEach(System.out::println);
            System.out.println();

        } else if (kind.equals("Koszty")) {
            System.out.println("Koszty");


            Collections.sort(listPurchase, (Purchase o1, Purchase o2) -> {


                double costO1 = Double.valueOf(o1.getPrice()) * Double.valueOf(o1.getAmount());
                o1.setTotalCost(Double.parseDouble(String.valueOf(costO1)));
                double costO2 = Double.valueOf(o2.getPrice()) * Double.valueOf(o2.getAmount());
                o2.setTotalCost(Double.parseDouble(String.valueOf(costO2)));


                if (!(costO1 == costO2)) {
                    if (costO1 < costO2) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else {

                    if ((o1.getIdClient().substring(5, 6).compareTo(o2.getIdClient().substring(5, 6))) < 0) {
                        return -1;
                    } else if ((o1.getIdClient().substring(5, 6).compareTo(o2.getIdClient().substring(5, 6))) == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }


            });


            listPurchase.forEach(System.out::println);
            System.out.println();
        }


    }

    public void showPurchaseFor(String id) {
        System.out.println("Klient " + id);
        readFile(path);

        for (int i = 0; i < listPurchase.size(); i++) {
            if (listPurchase.get(i).getIdClient().equals(id)) {
                System.out.println(listPurchase.get(i));
            }
        }
        System.out.println();

    }


}
