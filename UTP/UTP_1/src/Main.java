/**
 * @author Wawrzeniecki Grzegorz S15429
 */

import java.util.*;

public class Main {
    public Main() {
        List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);
        System.out.println(test1(src1));

        List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv" );
        System.out.println(test2(src2));


    }

    public List<Integer> test1(List<Integer> src) {
        Selector<Integer> sel = new Selector<>() {

            @Override
            public boolean select(Integer i) {

                if (!(i < 10))
                    return false;

                return true;
            }
        }; /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
        Mapper<Integer, Integer> map = new Mapper<Integer, Integer>() {
            @Override
            public Integer map(Integer integer) {
                return (integer + 10);
            }
        };    /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
        return ListCreator.<Integer, Integer>collectFrom(src).when(sel).mapEvery(map);  /*<-- zwrot wyniku
            uzyskanego przez wywołanie statycznej metody klasy ListCreator:
           */
    }

    public List<Integer> test2(List<String> src) {
        Selector<String> sel = new Selector<>() {
            @Override
            public boolean select(String s) {

                if (!(s.length() > 3))
                    return false;

                return true;
            }
        }; /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
        Mapper<String, Integer> map = new Mapper<>() {


            @Override
            public Integer map(String s) {
                return (s.length() + 10);
            }
        };   /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
        return ListCreator.collectFrom(src).when(sel).mapEvery(map); /*<-- zwrot wyniku
            uzyskanego przez wywołanie statycznej metody klasy ListCreator:
           */
    }

    public static void main(String[] args) {
        new Main();
    }
}