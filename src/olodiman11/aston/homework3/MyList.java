package olodiman11.aston.homework3;

import java.util.Collection;
import java.util.Comparator;

public interface MyList<T> {
    void addAt(int ind, T el);
    T get(int ind);
    boolean remove(T el);
    T removeAt(int ind);
    void addAllAt(int ind, Collection<? extends T> col);
    int size();
    void sort(Comparator<? super T> comparator);

    /**
     * Adds a new element to the end of the list.<br/>
     * @param el Element to add.
     */
    default void add(T el) {
        addAt(size(), el);
    }

    /**
     * Adds elements from collection if size {@code m} to the end of the list.<br/>
     * @param col Collection to be added.
     */
    default void addAll(Collection<? extends T> col) {
        addAllAt(size(), col);
    }

    /**
     * Sorts a given list using bubble sort algorithm.<br/>
     * Time complexity: {@code O(n^2)}<br/>
     * Space complexity: {@code O(1)}
     * @param list List to sort.
     * @param <R> List elements type.
     */
    static <R extends Comparable<? super R>> void sort(MyList<R> list) {
        sort(list, R::compareTo);
    }

    /**
     * Sorts a given list using bubble sort algorithm.<br/>
     * Time complexity: {@code O(n^2)}<br/>
     * Space complexity: {@code O(1)}
     * @param list List to sort.
     * @param comparator Comparator for sorting.
     * @param <R> List elements type.
     */
    static <R> void sort(MyList<R> list,  Comparator<? super R> comparator) {
        list.sort(comparator);
    }
}
