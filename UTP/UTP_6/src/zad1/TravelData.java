package zad1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TravelData {
    private File file;
    private List<String> listTrips;
    private List<String> helpList;
    private List<String> databaseList;
    private Locale loc;
    private Map map_Locales;
    private SimpleDateFormat simpleDate;


    public TravelData(File file) {
        this.file = file;
        map_Locales = new HashMap();
        simpleDate = new SimpleDateFormat();
        readFiles();
    }

    private void loadLocales(String lang, String country) {
        Locale.setDefault(new Locale(lang, country));
        Locale[] loc = Locale.getAvailableLocales();
        for (int i = 0; i < loc.length; i++) {
            String countryCode = loc[i].getCountry();  // kod kraju
            if (countryCode.equals("")) continue;
            String kraj = loc[i].getDisplayCountry();
            map_Locales.put(kraj, loc[i]);
        }

    }

    private void loadLocales(String lang) {
        Locale.setDefault(new Locale(lang));
        Locale[] loc = Locale.getAvailableLocales();
        for (int i = 0; i < loc.length; i++) {
            String countryCode = loc[i].getCountry();  // kod kraju
            if (countryCode.equals("")) continue;
            String kraj = loc[i].getDisplayCountry();
            map_Locales.put(kraj, loc[i]);
        }

    }

    private void readFiles() {
        helpList = new ArrayList<>();
        try {
            Files.walk(file.toPath())
                    .filter(Files::isRegularFile)
                    .forEach(e -> {
                        try {
                            Scanner scan = new Scanner(e);
                            while (scan.hasNextLine()) {
                            	//System.out.println(scan.nextLine());
                               helpList.add(scan.nextLine());

                            }
                        } catch (IOException e1) {
                            System.out.println("Brak plliku");
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public List<String> getOffersDescriptionsList(String locale, String dateFormat) {
        listTrips = new ArrayList<>();
        databaseList = new ArrayList();
        simpleDate = new SimpleDateFormat(dateFormat);

        String[] locale_IN = locale.split("_");
        loc = new Locale(locale_IN[0], locale_IN[1]);


        for (int i = 0; i < helpList.size(); i++) {
            String[] tripInfo = helpList.get(i).split("\\t");
      
            String[] localesFromFile = tripInfo[0].split("_");
            if (localesFromFile.length > 1) {
                loadLocales(localesFromFile[0], localesFromFile[1]);
            } else {
                loadLocales(localesFromFile[0]);
            }
         
            Locale savedLoc = (Locale) map_Locales.get(tripInfo[1]);
            Date date = null;
            Date dateend = null;
            try {
                date = simpleDate.parse(tripInfo[2]);
                dateend = simpleDate.parse(tripInfo[3]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Locale defLoc = new Locale(locale_IN[0]);
            ResourceBundle msgs = ResourceBundle.getBundle("Places", defLoc);

            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
            DecimalFormat decimalFormat2 = (DecimalFormat) NumberFormat.getInstance(new Locale(locale_IN[0]));


            try {
                listTrips.add(savedLoc.getDisplayCountry(loc) + " " + simpleDate.format(date) + " " +
                        simpleDate.format(dateend) + " " + msgs.getString(tripInfo[4]) + " " + decimalFormat2.format(decimalFormat.parse(tripInfo[5])) +
                        " " + tripInfo[6]);
                databaseList.add(savedLoc.getDisplayCountry(loc) + "sep" + simpleDate.format(date) + "sep" +
                        simpleDate.format(dateend) + "sep" + msgs.getString(tripInfo[4]) + "sep" + decimalFormat2.format(decimalFormat.parse(tripInfo[5])) +
                        "sep" + tripInfo[6]);
                
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }

        return listTrips;

    }
    
    protected List<String> getListOfTrips(){
    	return databaseList;
    }
    protected SimpleDateFormat getDateFormat(){
    	return simpleDate;
    }
}