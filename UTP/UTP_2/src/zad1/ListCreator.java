package zad1;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.*;

public class ListCreator<T, S> {

    private List<T> list;

    static <T, S> ListCreator<T, S> collectFrom(List<T> destinations) {
        ListCreator<T, S> ls = new ListCreator<>();
        ls.list = new ArrayList<>(destinations);
        return ls;
    }

    public ListCreator<T, S> when(Predicate<T> test) {

        for (Iterator<T> it = this.list.iterator(); it.hasNext(); ) {
            T t = it.next();
            if (!test.test(t))
                it.remove();
        }


        return this;
    }

    //WAZNE

    public <S> List<S> mapEvery(Function<T, S> f) {
        List<S> returnList = new ArrayList<>();
        for (T t : this.list) {
            S mod = f.apply(t);
            returnList.add(mod);
        }

        return returnList;
    }


}
