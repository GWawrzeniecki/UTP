/**
 * @author Wawrzeniecki Grzegorz S15429
 */

package zad1;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Anagrams {
    private String path;
    private List<String> listWords;
    private List<List<String>> resultLists;
    private List<String> tmp;

    public Anagrams(String path) throws FileNotFoundException {
        this.path = path;
        loadWords();
    }

    private void loadWords() throws FileNotFoundException {
        listWords = new ArrayList<>();
        resultLists = new ArrayList<>();
        tmp = new ArrayList<>();

        Scanner scan = new Scanner(new File(path));
        while (scan.hasNext()) {
            listWords.add(scan.next());
        }
        scan.close();
    }

    private boolean compare(String one, String two) {

        char[] tmp1 = one.toCharArray();
        char[] tmp2 = two.toCharArray();

        Arrays.sort(tmp1);
        Arrays.sort(tmp2);

            return Arrays.equals(tmp1,tmp2);

    }

    public List<List<String>> getSortedByAnQty() {


        for (int i = 0; i < listWords.size(); i++) {
            for (int j = 0; j < listWords.size(); j++) {
                if (compare(listWords.get(i), listWords.get(j))) {
                    tmp.add(listWords.get(j));
                }
            }
            List<String> tmp2 = new ArrayList<>(tmp);

            if (!(resultLists.contains(tmp2)))
                resultLists.add(tmp2);

            tmp.clear();
        }
        this.sort(resultLists);

        return resultLists;
    }

    private void sort(List<List<String>> listToSort) {
        for (int i = 0; i < listToSort.size(); i++) {
            for (int j = 0; j < listToSort.size(); j++) {

                if (listToSort.get(i).size() > listToSort.get(j).size()) {
                    List<String> tmp = new ArrayList<>(listToSort.get(i));
                    listToSort.set(i, listToSort.get(j));
                    listToSort.set(j, tmp);
                } else if (listToSort.get(i).size() == listToSort.get(j).size()) {
                    String tmp = listToSort.get(i).get(0);
                    String tmp2 = listToSort.get(j).get(0);

                    if ((tmp.substring(0, 1).compareTo(tmp2.substring(0, 1))) > 0) {
                        List<String> tmpx = new ArrayList<>(listToSort.get(i));
                        listToSort.set(i, listToSort.get(j));
                        listToSort.set(j, tmpx);

                    }
                }


            }

        }


    }


    public String getAnagramsFor(String next) {
        List<String> result = new ArrayList<>();


        for (int i = 0; i < listWords.size(); i++) {
            if (compare(next, listWords.get(i)) && (!(next.equals(listWords.get(i)))))
                result.add(listWords.get(i));

        }

        return next + ": " + result;
    }


}
