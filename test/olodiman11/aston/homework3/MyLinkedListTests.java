package olodiman11.aston.homework3;

import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

public class MyLinkedListTests extends MyListTests {

    @Override
    protected MyList<Integer> createList() {
        return new MyLinkedList<>();
    }

    @Override
    protected MyList<Integer> createList(Collection<Integer> col) {
        return new MyLinkedList<>(col);
    }

    @Test
    public void BubbleSortTest() {
        MyLinkedList<Integer> list = new MyLinkedList<>(List.of(5, 4, 3, 2, 1));
        MyLinkedList.bubbleSort(list);
        assertListEquals(list, List.of(1, 2, 3, 4, 5));
    }
}
