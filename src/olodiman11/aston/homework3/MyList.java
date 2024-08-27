package olodiman11.aston.homework3;

import java.util.Collection;

public interface MyList<T> {
    void add(T el);
    void addAt(int ind, T el);
    T get(int ind);
    boolean remove(T el);
    T removeAt(int ind);
    void addAll(Collection<? extends T> col);
    void addAllAt(int ind, Collection<? extends T> col);
    int size();
}
