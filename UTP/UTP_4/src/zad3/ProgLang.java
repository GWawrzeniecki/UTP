package zad3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Predicate;

public class ProgLang {
    private String path;
    private Map<String, List<String>> mainMap = new LinkedHashMap<>();
    private Map<String, List<String>> programmerListOfLang = new LinkedHashMap<>();

    public ProgLang(String path) {
        try {
            this.path = path;
            Scanner scan = new Scanner(new File(path));
            while (scan.hasNextLine()) {
                String tmpStr = scan.nextLine();
                String[] tab = tmpStr.split("\\t");
                String nameOfLang = tab[0];
                List<String> tmp = new ArrayList<>();
                for (int i = 1; i < tab.length; i++) {

                    if (!(tmp.contains(tab[i])))
                        tmp.add(tab[i]);
                }
                mainMap.put(nameOfLang, tmp);

            }


        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }

    }

    public Map<String, List<String>> getLangsMap() {
        return mainMap;
    }

    public Map<String, List<String>> getProgsMap() {
        Map<String, List<String>> returnMap = new LinkedHashMap<>();

        try {
            Scanner scan = new Scanner(new File(path));
            while (scan.hasNextLine()) {
                String nextLine = scan.nextLine();
                String[] tab = nextLine.split("\\t");
                for (int i = 1; i < tab.length; i++) {
                    List<String> tmp = new ArrayList<>();

                    if (returnMap.containsKey(tab[i])) {

                        if (!(returnMap.get(tab[i]).contains(tab[0])))
                            returnMap.get(tab[i]).add(tab[0]);

                    } else {
                        tmp.add(tab[0]);
                        returnMap.put(tab[i], tmp);

                    }
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
        }

        programmerListOfLang = returnMap;
        return returnMap;
    }

    private Comparator<Map.Entry<String, List<String>>> getCompDesc() {

        Comparator<Map.Entry<String, List<String>>> comp =
                ((Map.Entry<String, List<String>> o1, Map.Entry<String, List<String>> o2) -> {

                    if (!(o1.getValue().size() == o2.getValue().size())) {
                        if (o1.getValue().size() < o2.getValue().size()) {
                            return 1;
                        } else {
                            return -1;
                        }
                    } else {

                        if ((o1.getKey().substring(0, 1).compareTo(o2.getKey().substring(0, 1))) < 0) {
                            return -1;
                        } else if ((o1.getKey().substring(0, 1).compareTo(o2.getKey().substring(0, 1))) == 0) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }


                });

        return comp;
    }

    public Map<String, List<String>> getLangsMapSortedByNumOfProgs() {
        List<Map.Entry<String, List<String>>> sortedList = new ArrayList<>(mainMap.entrySet());

        sortedList.sort((a,b) -> {
            if (b.getValue().size() != a.getValue().size()){
                return b.getValue().size() - a.getValue().size();
            }else{
                return a.getKey().compareTo(b.getKey());
            }
        });

        //sortedList.sort(getCompDesc());

        Map<String, List<String>> returnMap = new LinkedHashMap<>();
        sortedList.forEach(e -> returnMap.put(e.getKey(), e.getValue()));


        return returnMap;

    }

    public Map<String, List<String>> getProgsMapSortedByNumOfLangs() {
        List<Map.Entry<String, List<String>>> sortedList = new ArrayList<>(programmerListOfLang.entrySet());


        sortedList.sort((a,b) -> {
            if (b.getValue().size() != a.getValue().size()){
                return b.getValue().size() - a.getValue().size();
            }else{
                return a.getKey().compareTo(b.getKey());
            }
        });

        //sortedList.sort(getCompDesc());

        Map<String, List<String>> returnMap = new LinkedHashMap<>();
        sortedList.forEach(e -> returnMap.put(e.getKey(), e.getValue()));


        return returnMap;


    }

    public Map<String, List<String>> getProgsMapForNumOfLangsGreaterThan(int i) {
        Map<String, List<String>> returnMap = new LinkedHashMap<>();

        programmerListOfLang.forEach((k, v) -> {
            if (programmerListOfLang.get(k).size() > i)
                returnMap.put(k, v);
        });


        return returnMap;
    }


    public <K, V> Map<K, V> sorted(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> sortedList = new ArrayList(map.entrySet());

        sortedList.sort(comparator);


        Map<K, V> returnMap = new LinkedHashMap<>();
        sortedList.forEach(e -> returnMap.put(e.getKey(), e.getValue()));



        /*
        return map.entrySet()
                .stream()
                .sorted(comparator)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
                */
        return returnMap;
    }


    public <K, V> Map<K, V> filtered(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        List<Map.Entry<K, V>> sortedList = new ArrayList(map.entrySet());
        List<Map.Entry<K, V>> sortedList2ret = new ArrayList(map.entrySet());
        sortedList.removeIf(predicate);
        sortedList2ret.removeAll(sortedList);


        Map<K, V> returnMap = new LinkedHashMap<>();
        sortedList2ret.forEach(e -> returnMap.put(e.getKey(), e.getValue()));

        /*


        return map.entrySet()
                .stream()
                .filter(x -> predicate.test(x.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1 ,LinkedHashMap::new));

                */

        return returnMap;
    }
}
