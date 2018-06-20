package zad1;


import java.util.*;
import java.util.function.BiConsumer;
import java.lang.Object;
import java.util.function.Function;


import static java.util.stream.Collectors.toList;


public class XList<T> extends ArrayList<T> {

    private Collection<T> coll;
    private List<T> list = new ArrayList<>();
    private List<T> listTMP;
    private List<List<T>> comb = new ArrayList<>();
    private StringBuilder result = new StringBuilder();

    public XList() {
        coll = new ArrayList<>();
    }

    public XList(T... arrays) {
        //System.out.println("Pierwszy");
        coll = Arrays.asList(arrays);


    }

    public XList(T[]... arrays) {
        //System.out.println("drugi");
        coll = new ArrayList<>();
        for (T[] arr : arrays)
            for (T t : arr) {
                coll.add(t);
            }
    }

    public XList(Collection<T> col) {
        //System.out.println("Trzeci");
        coll = new ArrayList<>();
        coll.addAll(col);
    }

    @Override
    public String toString() {

        return coll.toString();
    }

    public static <T> XList<T> of(T... arrays) {

        XList<T> tmp = new XList<>(arrays);

        return tmp;
    }

    public static <T> XList<T> of(T[]... arrays) {

        XList<T> tmp = new XList(arrays);
        return tmp;
    }

    public static <T> XList<T> of(Collection<T> col) {
        //System.out.println("hashset");
        XList<T> tmp = new XList<>(col);


        return tmp;
    }

    public static <T> XList<T> charsOf(T str) {

        Object tab[] = ((String) str).split("");
        XList<T> tmp = new XList<>();
        for (int i = 0; i < tab.length; i++) {
            tmp.coll.add((T) tab[i]);
        }

        return tmp;
    }

    public static <T> XList<T> tokensOf(T str) {

        Object tab[] = ((String) str).split("\\s");
        XList<T> tmp = new XList<>();
        for (int i = 0; i < tab.length; i++) {
            tmp.coll.add((T) tab[i]);
        }

        return tmp;
    }


    public static <T> XList<T> tokensOf(T... str) {

        String sep = ((String) str[1]);
        XList<T> tmp = new XList<>();
        for (T t : str) {
            Object tab[] = ((String) t).split(sep);
            for (Object obj : tab)
                tmp.coll.add((T) obj);
        }

        return tmp;
    }

    public XList<T> union(Collection<T> col) {
        XList<T> returnList = new XList<>();
        if (col instanceof XList) {
            returnList.coll.addAll(this.coll);
            returnList.coll.addAll(((XList) col).coll);
        } else {
            returnList.coll.addAll(this.coll);
            returnList.coll.addAll(col);

        }

        return returnList;
    }

    public XList<T> union(T... arrays) {

        XList<T> returnList = new XList<>();
        returnList.coll.addAll(this.coll);

        for (T t : arrays)
            returnList.coll.add(t);


        return returnList;

    }

    @Override
    public boolean add(Object obj) {
        coll.add((T) obj);
        return true;
    }

    public Collection<T> getColl() {
        return coll;
    }

    public List<List<T>> getComb() {
        return comb;
    }

    public XList<T> diff(Collection<T> col) {
        Collection<T> cpCol = new ArrayList<>();
        cpCol.addAll(this.coll);

        if (col instanceof XList) {
            cpCol.removeAll(((XList) col).getColl());
        } else {
            cpCol.removeAll(col);
        }

        XList<T> returnList = new XList<>();
        returnList.coll = cpCol;
        return returnList;
    }

    public XList<T> unique() {
        XList<T> returnList = new XList<>();
        returnList.coll = this.coll.stream().distinct().collect(toList());
        return returnList;
    }


    public XList<XList<T>> combine() {


        List<T> listIterate = Arrays.asList((T) this.coll);

        List<List<T>> list2 = new ArrayList<>();
        for (int i = 0; i < listIterate.size(); i++) {
            List<T> tmp = Arrays.asList(listIterate.get(i));
            list2.add(Arrays.asList(listIterate.get(i)));
        }


        return null;
    }


    public XList<T> getCartesianProduct(List<List<T>> lists) {

        List<List<T>> combinations = new ArrayList<>();

        for (List<T> list : lists) {
            List<List<T>> extraColumnCombinations = new ArrayList<>();
            for (T element : list) {
                if (combinations.isEmpty()) {
                    extraColumnCombinations.add(Arrays.asList(element));
                } else {
                    for (List<T> productList : combinations) {
                        List<T> newProductList = new ArrayList<>(productList);
                        newProductList.add(element);
                        extraColumnCombinations.add(newProductList);
                    }
                }
            }
            combinations = extraColumnCombinations;
        }


        XList<T> xlist = new XList<>();
        xlist.comb = combinations;

        return xlist;
    }

    public void forEachWithIndex(BiConsumer<T, Integer> bi) {
        listTMP = new ArrayList<>();
        listTMP.addAll(this.coll);

        for (int i = 0; i < listTMP.size(); i++) {
            bi.accept(listTMP.get(i), i);

        }

        this.coll = list;
    }

    @Override
    public Object set(int index, Object obj) {
        list.add(index, (T) obj);
        return obj;
    }

    @Override
    public boolean remove(Object obj) {
        this.coll.remove(obj);
        listTMP.remove(obj);
        return true;
    }

    public XList<String> collect(Function<XList<T>, String> fc) {

        List<String> resultList = new ArrayList<>();


        for (List<T> t : this.comb) {
            XList<T> tmp = new XList<>(t);
            resultList.add(fc.apply(tmp));
        }


        return new XList<>(resultList);
    }

    public String join(String sep) {


        List<T> list = new ArrayList<>();
        list.addAll(this.coll);

        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                result.append(list.get(i) + sep);

            } else {
                result.append(list.get(i));
            }
        }
        return result.toString();


    }


    public String join() {

        StringBuilder str = new StringBuilder();

        for (T t : this.coll) {
            str.append(t.toString());
        }

        return str.toString();
    }


}
