package olodiman11.aston.homework3;

import java.util.Collection;

public class MyLinkedListTests extends MyListTests {

    @Override
    protected MyList<Integer> createList() {
        return new MyLinkedList<>();
    }

    @Override
    protected MyList<Integer> createList(Collection<Integer> col) {
        return new MyLinkedList<>(col);
    }
}
