package olodiman11.aston.homework3;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Array list.
 * @param <T> Elements type.
 */
public class MyArrayList<T> implements MyList<T> {
    private final static int DEFAULT_CAPACITY = 10;
    private final static double EXPANSION_COEFFICIENT = 1.5;

    private int capacity;
    private int size;
    private T[] array;

    /**
     * Creates an empty list with capacity of {@value DEFAULT_CAPACITY}.
     */
    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list with specified capacity.
     * @param capacity Initial capacity.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        this.capacity = capacity;
        array = (T[]) new Object[capacity];
    }

    /**
     * Creates a list and populates it with elements from a given collection.<br/>
     * Capacity is set to the collection size.
     * @param col Initial collection.
     */
    public MyArrayList(Collection<? extends T> col) {
        this(col.size(), col);
    }

    /**
     * Creates a list with specified capacity and populates it with elements from a given collection.<br/>
     * @param capacity Initial capacity.
     * @param col Initial collection.
     */
    public MyArrayList(int capacity, Collection<? extends T> col) {
        this(capacity);
        addAll(col);
    }

    /**
     * Sorts a given list using bubble sort algorithm.<br/>
     * Time complexity: {@code O(n^2)}<br/>
     * Space complexity: {@code O(1)}
     * @param comparator Comparator for sorting.
     */
    @Override
    public void sort(Comparator<? super T> comparator) {
        if(size == 0) return;
        if(comparator == null) return;
        boolean sorted;
        do {
            sorted = true;
            for(int i = 0; i < size() - 1; i++) {
                if(comparator.compare(get(i), get(i + 1)) > 0) {
                    T temp = array[i + 1];
                    array[i + 1] = array[i];
                    array[i] = temp;
                    sorted = false;
                }
            }
        } while(!sorted);
    }

    /**
     * Inserts a new element at a specified index. Accepts index equal to size.<br/>
     * Time complexity: {@code O(n)}
     * @param ind Index to insert at.
     * @param el Element to add.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind > size()}
     */
    @Override
    public void addAt(int ind, T el) throws IndexOutOfBoundsException {
        if(ind != size) throwIfOutOfBounds(ind);
        expandIfNecessary(1);
        moveRightHandSide(ind, ind + 1);
        array[ind] = el;
        size++;
    }

    /**
     * Returns an element at a specified index.<br/>
     * Time complexity: {@code O(1)}
     * @param ind Index of element.
     * @return Element at index.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind >= size()}
     */
    @Override
    public T get(int ind) throws IndexOutOfBoundsException {
        throwIfOutOfBounds(ind);
        return array[ind];
    }

    /**
     * Removes an element from the list.<br/>
     * If element is not present then remove is ignored.<br/>
     * Time complexity: {@code O(n)}
     * @param el Element to be removed.
     * @return {@code true} if element was removed, otherwise {@code false}.
     */
    @Override
    public boolean remove(T el) {
        int index = indexOf(el);
        if(index < 0) return false;
        removeAt(index);
        return true;
    }

    /**
     * Removes an element from a specified index.<br/>
     * Time complexity: {@code O(n)}
     * @param ind Index of element.
     * @return Element that was removed.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind >= size()}
     */
    @Override
    public T removeAt(int ind) throws IndexOutOfBoundsException {
        throwIfOutOfBounds(ind);
        T elAtInd = array[ind];
        moveRightHandSide(ind + 1, ind);
        size--;
        return elAtInd;
    }

    /**
     * Adds elements from collection of size {@code m} to the end of the list. Accepts index equal to size.<br/>
     * Time complexity: {@code O(n + m)}
     * @param ind Index to insert at.
     * @param col Collection to be added.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind > size()}
     */
    @Override
    public void addAllAt(int ind, Collection<? extends T> col)
            throws IndexOutOfBoundsException {
        if(col == null) return;
        if(ind != size) throwIfOutOfBounds(ind);
        expandIfNecessary(col.size());
        moveRightHandSide(ind, ind + col.size());
        int i = 0;
        for(T el: col) array[ind + i++] = el;
        size += col.size();
    }

    /**
     * Returns number of elements in the list.
     * @return Number of elements in the list.
     */
    @Override
    public int size() {
        return size;
    }

    @SuppressWarnings("unchecked")
    private void expandIfNecessary(int requiredSpace){
        long newSize = (long) size + (long) requiredSpace;
        if(newSize > Integer.MAX_VALUE)
            throw new RuntimeException("Required size is greater than Integer.MAX_VALUE.");
        if(newSize <= capacity) return;
        capacity = getNewCapacity((int) newSize);

        T[] newArray = (T[]) new Object[capacity];
        if (size() >= 0) System.arraycopy(array, 0, newArray, 0, size());
        array = newArray;
    }

    private int getNewCapacity(int newSize) {
        long newCapacity = capacity;
        while(newCapacity < newSize){
            newCapacity = (long) (EXPANSION_COEFFICIENT * newCapacity + 1);
            if(newCapacity <= capacity)
                throw new RuntimeException("Capacity has not increased.");
            if(newCapacity > Integer.MAX_VALUE){
                newCapacity = Integer.MAX_VALUE;
                break;
            }
        }
        return (int) newCapacity;
    }

    private void throwIfOutOfBounds(int ind) throws IndexOutOfBoundsException {
        if((ind < 0) || (ind >= size))
            throw new IndexOutOfBoundsException();
    }

    private int indexOf(T el) {
        for(int i = 0; i < size; i++) {
            if(array[i].equals(el))
                return i;
        }
        return -1;
    }

    // Сдвигает в 'to' всё что справа от 'from' включительно. Работает в обе стороны (from > to | from < to).
    private void moveRightHandSide(int from, int to) {
        if(from == to) return;
        if(from == size) return;

        int shift = to - from;
        int shiftDir = Integer.signum(shift);
        int length = size - from;
        int start = shiftDir < 0 ? from : size - 1;
        for(int i = 0; Math.abs(i) < length; i -= shiftDir) {
            int index = start + i;
            array[index + shift] = array[index];
        }
    }

    @Override
    public String toString() {
        String innerStr = Stream.of(array)
                .limit(size)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        return "[" + innerStr + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList<?> that = (MyArrayList<?>) o;
        return capacity == that.capacity && size == that.size && Objects.deepEquals(array, that.array);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, size, Arrays.hashCode(array));
    }
}
