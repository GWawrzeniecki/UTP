/**
 * @author Wawrzeniecki Grzegorz S15429
 */


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListCreator<T, S> { // Uwaga: klasa musi być sparametrtyzowana
    private List<T> list;

    public ListCreator() {

    }

    public static <T, S> ListCreator<T, S> collectFrom(List<T> list) {
        ListCreator<T, S> ls = new ListCreator<>();
        ls.list = new ArrayList(list);

        return ls;
    }

    public ListCreator<T, S> when(Selector<T> s) {

        Iterator<T> i = this.list.iterator();

        while (i.hasNext()) {
            T t = i.next();
            if (!s.select(t)) {
                i.remove();
            }
        }

        return this;
    }

    public <S> List<S> mapEvery(Mapper<T, S> s) {

        List<S> back = new ArrayList<>();
        Iterator<T> i = this.list.iterator();


        while (i.hasNext()) {
            T t = i.next();
            S mod = s.map(t);

            back.add(mod);
        }


        return back;
    }


}
