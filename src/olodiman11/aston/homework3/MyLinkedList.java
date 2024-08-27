package olodiman11.aston.homework3;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Doubly linked list.
 * @param <T> Elements type.
 */
public class MyLinkedList<T> implements MyList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Creates an empty list.
     */
    public MyLinkedList() {}

    /**
     * Creates a list and populates it with elements from a given collection.
     * @param col Initial collection.
     */
    public MyLinkedList(Collection<? extends T> col) {
        addAll(col);
    }

    /**
     * Sorts a given list using bubble sort algorithm.<br/>
     * Time complexity: {@code O(n^2)}<br/>
     * Space complexity: {@code O(1)}
     * @param list List to sort.
     * @param <R> List elements type.
     */
    public static <R extends Comparable<R>> void bubbleSort(MyLinkedList<R> list) {
        bubbleSort(list, R::compareTo);
    }

    /**
     * Sorts a given list using bubble sort algorithm.<br/>
     * Time complexity: {@code O(n^2)}<br/>
     * Space complexity: {@code O(1)}
     * @param list List to sort.
     * @param comparator Comparator for sorting.
     * @param <R> List elements type.
     */
    public static <R> void bubbleSort(MyLinkedList<R> list, Comparator<? super R> comparator) {
        if(list == null || list.head == null) return;
        if(comparator == null) return;
        boolean sorted;
        do {
            Node<R> current = list.head;
            sorted = true;
            while(current.next != null) {
                Node<R> next = current.next;
                if(comparator.compare(current.value, next.value) > 0) {
                    list.putInThatOrder(current.prev, next, current, next.next);
                    list.updateHeadAndTailIfNecessary(next, current);
                    sorted = false;
                    continue;
                }
                current = current.next;
            }
        } while(!sorted);
    }

    /**
     * Adds a new element to the end of the list.<br/>
     * Time complexity: {@code O(1)}
     * @param el Element to add.
     */
    @Override
    public void add(T el) {
        addAt(size, el);
    }

    /**
     * Inserts a new element at a specified index. Accepts index equal to size.<br/>
     * Time complexity: general: {@code O(n)}, start|end: {@code O(1)}
     * @param ind Index to insert at.
     * @param el Element to add.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind > size()}
     */
    @Override
    public void addAt(int ind, T el) throws IndexOutOfBoundsException {
        Node<T> nodeAtInd = ind == size ? null : getNodeAt(ind);
        Node<T> prev = nodeAtInd == null ? tail : nodeAtInd.prev;
        Node<T> newNode = new Node<>(el);
        putInThatOrder(prev, newNode, nodeAtInd);
        updateHeadAndTailIfNecessary(newNode, newNode);
        size++;
    }

    /**
     * Returns an element at a specified index.<br/>
     * Time complexity: general: {@code O(n)}, start|end: {@code O(1)}
     * @param ind Index of element.
     * @return Element at index.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind >= size()}
     */
    @Override
    public T get(int ind) throws IndexOutOfBoundsException {
        return getNodeAt(ind).value;
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
        Node<T> node = getNode(el);
        if(node == null) return false;
        removeNode(node);
        return true;
    }

    /**
     * Removes an element from a specified index.<br/>
     * Time complexity: general: {@code O(n)}, start|end: {@code O(1)}
     * @param ind Index of element.
     * @return Element that was removed.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind >= size()}
     */
    @Override
    public T removeAt(int ind) throws IndexOutOfBoundsException {
        Node<T> nodeAtInd = getNodeAt(ind);
        removeNode(nodeAtInd);
        return nodeAtInd.value;
    }

    /**
     * Adds elements from collection if size {@code m} to the end of the list.<br/>
     * Time complexity: {@code O(m)}
     * @param col Collection to be added.
     */
    @Override
    public void addAll(Collection<? extends T> col) {
        addAllAt(size, col);
    }

    /**
     * Adds elements from collection of size {@code m} to the end of the list. Accepts index equal to size.<br/>
     * Time complexity: general: {@code O(n + m)}, start|end: {@code O(1)}
     * @param ind Index to insert at.
     * @param col Collection to be added.
     * @throws IndexOutOfBoundsException If {@code ind < 0 || ind > size()}
     */
    @Override
    public void addAllAt(int ind, Collection<? extends T> col)
            throws IndexOutOfBoundsException {
        if(col == null || col.isEmpty()) return;

        Node<T> nodeAtInd = ind == size ? null : getNodeAt(ind);
        Node<T> prev = nodeAtInd == null ? tail : nodeAtInd.prev;
        Node<T> firstAdded = null;
        for(T el: col) {
            Node<T> newNode = new Node<>(el);
            if(firstAdded == null) firstAdded = newNode;
            putInThatOrder(prev, newNode);
            prev = newNode;
        }
        Node<T> lastAdded = prev;
        putInThatOrder(lastAdded, nodeAtInd);
        updateHeadAndTailIfNecessary(firstAdded, lastAdded);
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

    private void updateHeadAndTailIfNecessary(Node<T> newHead, Node<T> newTail) {
        if((head == null) || (head.prev != null)) head = newHead;
        if((tail == null) || (tail.next != null)) tail = newTail;
    }

    private void removeNode(Node<T> node) {
        putInThatOrder(node.prev, node.next);
        if(head == node) head = node.next;
        if(tail == node) tail = node.prev;
        size--;
    }

    private void putInThatOrder(Node<T> first, Node<T> second) {
        if(first != null) first.next = second;
        if(second != null) second.prev = first;
    }

    @SafeVarargs
    private void putInThatOrder(Node<T>... nodes) {
        for(int i = 0; i < nodes.length-1; i++)
            putInThatOrder(nodes[i], nodes[i + 1]);
    }

    private Node<T> getNode(T value) {
        Node<T> current = head;
        for(int i = 0; i < size; i++) {
            if(current.value.equals(value))
                return current;
            current = current.next;
        }
        return null;
    }

    private Node<T> getNodeAt(int ind) throws IndexOutOfBoundsException {
        if((ind < 0) || (ind >= size))
            throw new IndexOutOfBoundsException();

        Node<T> current;
        if(ind < size / 2) {
            current = head;
            for(int i = 0; i < ind; i++)
                current = current.next;
        } else {
            current = tail;
            for(int i = 0; i < size - ind - 1; i++)
                current = current.prev;
        }
        return current;
    }

    @Override
    public String toString() {
        String innerStr = Stream
                .iterate(head, Objects::nonNull, x -> x.next)
                .map(x -> x.value.toString())
                .collect(Collectors.joining(", "));
        return "[" + innerStr + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyLinkedList<?> that = (MyLinkedList<?>) o;
        Node<?> current = head;
        Node<?> thatCurrent = that.head;
        if(size != that.size) return false;
        while(current != null) {
            if(!current.value.equals(thatCurrent.value)) return false;
            current = current.next;
            thatCurrent = thatCurrent.next;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(head, tail, size);
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private final T value;

        private Node(T value){
            this.value = value;
        }
    }
}
