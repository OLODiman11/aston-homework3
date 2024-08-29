package olodiman11.aston.homework3;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.List;

public class MyArrayListTests extends MyListTests {

    @Override
    protected MyList<Integer> createList() {
        return new MyArrayList<>();
    }

    @Override
    protected MyList<Integer> createList(Collection<Integer> col) {
        return new MyArrayList<>(col);
    }

    @Nested
    class CapacityTests {
        private MyArrayList<Integer> list;

        @BeforeEach
        public void setUp() {
            list = new MyArrayList<>(0);
        }

        @Test
        public void ExpandsOnAdd() {
            assertDoesNotThrow(() -> list.add(0));
        }

        @Test
        public void ExpandsOnAddAt() {
            assertDoesNotThrow(() -> list.addAt(0, 0));
        }

        @Test
        public void ExpandsOnAddAll() {
            Collection<Integer> col = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            assertDoesNotThrow(() -> list.addAll(col));
        }

        @Test
        public void ExpandsOnAddAllAt() {
            Collection<Integer> col = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            assertDoesNotThrow(() -> list.addAllAt(0, col));
        }
    }
}
