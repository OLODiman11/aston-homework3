package olodiman11.aston.homework3;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class MyListTests {

    private final static List<Integer> DEFAULT_ELEMENTS = List.of(1, 2, 3, 4);

    private MyList<Integer> myList;

    protected abstract MyList<Integer> createList();
    protected abstract MyList<Integer> createList(Collection<Integer> col);

    @BeforeEach
    public void setUp() {
        myList = createList(DEFAULT_ELEMENTS);
    }

    @Nested
    class ConstructorTests {
        @Test
        public void CreatesEmptyList(){
            MyList<Integer> list = createList();
            assertListEquals(list, List.of());
        }

        @Test
        public void InitializesFromCollection(){
            Collection<Integer> col = List.of(1, 2, 3, 4, 5, 6);
            MyList<Integer> list = createList(col);
            assertListEquals(list, col);
        }
    }

    @Nested
    class AddTests {
        @ParameterizedTest
        @NullSource
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIntegers")
        public void AddsElementToEndOfList(Integer el){
            myList.add(el);
            assertListEquals(myList, add(DEFAULT_ELEMENTS, el));
        }
    }

    @Nested
    class AddAtTests {
        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIntegersWithIndices")
        public void AddsElementAtInd(int ind, Integer el){
            myList.addAt(ind, el);
            assertListEquals(myList, addAt(DEFAULT_ELEMENTS, el, ind));
        }

        @Test
        public void DoesNotThrowIfIndexEqualsSize() {
            assertDoesNotThrow(() -> myList.addAt(DEFAULT_ELEMENTS.size(), 0));
        }

        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getInvalidIndicesExcludingSize")
        public void ThrowsIndexOutOfBoundsException(int ind) {
            assertThrows(IndexOutOfBoundsException.class, () -> myList.addAt(ind, 0));
        }
    }

    @Nested
    class GetTests {
        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIndices")
        public void GetsElementAtInd(int ind){
            Integer el = myList.get(ind);
            assertEquals(DEFAULT_ELEMENTS.get(ind), el);
        }

        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getInvalidIndices")
        public void ThrowsIndexOutOfBoundsException(int ind) {
            assertThrows(IndexOutOfBoundsException.class, () -> myList.get(ind));
        }
    }

    @Nested
    class RemoveTests {
        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIndices")
        public void RemovesElement(int ind){
            Integer el = DEFAULT_ELEMENTS.get(ind);
            myList.remove(el);
            assertListEquals(myList, remove(DEFAULT_ELEMENTS, el));
        }

        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIndices")
        public void ReturnsTrueIfElementWasRemoved(int ind) {
            Integer el = DEFAULT_ELEMENTS.get(ind);
            boolean res = myList.remove(el);
            assertTrue(res);
        }

        @Test
        public void ReturnsFalseIfElementIsNotPresent() {
            Integer el = Integer.MAX_VALUE;
            boolean res = myList.remove(el);
            assertFalse(res);
        }

        @Test
        public void WorksWidthEmptyList(){
            assertDoesNotThrow(() -> createList().remove(0));
        }

        @Test
        public void IgnoredIfElementNotPresent(){
            myList.remove(Integer.MAX_VALUE);
            assertListEquals(myList, DEFAULT_ELEMENTS);
        }
    }

    @Nested
    class RemoveAtTests {
        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIndices")
        public void RemovesElementAtInd(int ind){
            myList.removeAt(ind);
            assertListEquals(myList, removeAt(DEFAULT_ELEMENTS, ind));
        }

        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeIndices")
        public void ReturnsRemovedElement(int ind){
            Integer expected = DEFAULT_ELEMENTS.get(ind);
            Integer result = myList.removeAt(ind);
            assertEquals(expected, result);
        }

        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getInvalidIndices")
        public void ThrowsIndexOutOfBoundsException(int ind) {
            assertThrows(IndexOutOfBoundsException.class, () -> myList.removeAt(ind));
        }
    }

    @Nested
    class AddAllTests {
        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeCollections")
        public void AddsCollectionToEndOfList(Collection<Integer> col){
            myList.addAll(col);
            assertListEquals(myList, addAll(DEFAULT_ELEMENTS, col));
        }
    }

    @Nested
    class AddAllAtTests {
        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getSomeCollectionsWithIndices")
        public void AddsElementAtInd(int ind, Collection<Integer> col){
            myList.addAllAt(ind, col);
            assertListEquals(myList, addAllAt(DEFAULT_ELEMENTS, col, ind));
        }

        @Test
        public void DoesNotThrowIfIndexEqualsSize() {
            assertDoesNotThrow(() -> myList.addAllAt(DEFAULT_ELEMENTS.size(), List.of()));
        }

        @ParameterizedTest
        @MethodSource("olodiman11.aston.homework3.MyListTests#getInvalidIndicesExcludingSize")
        public void ThrowsIndexOutOfBoundsException(int ind) {
            assertThrows(IndexOutOfBoundsException.class, () -> myList.addAllAt(ind, DEFAULT_ELEMENTS));
        }
    }

    protected void assertListEquals(MyList<Integer> list, Collection<Integer> col) {
        assertEquals(col.size(), list.size());
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
        int i = 0;
        for(Integer el: col) {
            assertEquals(el, list.get(i++));
        }
    }

    private Collection<Integer> add(Collection<Integer> col, Integer i) {
        return Stream.concat(col.stream(), Stream.of(i)).toList();
    }

    private Collection<Integer> addAt(Collection<Integer> col, Integer i, int ind) {
        Stream<Integer> stream = Stream.concat(col.stream().limit(ind), Stream.of(i));
        return Stream.concat(stream, col.stream().skip(ind)).toList();
    }

    private Collection<Integer> remove(Collection<Integer> col, Integer i) {
        return col.stream().filter(x -> !x.equals(i)).toList();
    }

    private Collection<Integer> removeAt(Collection<Integer> col, int i) {
        return Stream.concat(col.stream().limit(i), col.stream().skip(i + 1)).toList();
    }

    private Collection<Integer> addAll(Collection<Integer> col1, Collection<Integer> col2) {
        if(col2 == null) return col1;
        return Stream.concat(col1.stream(), col2.stream()).toList();
    }

    private Collection<Integer> addAllAt(Collection<Integer> col1, Collection<Integer> col2, int ind) {
        if(col2 == null) return col1;
        Stream<Integer> stream = Stream.concat(col1.stream().limit(ind), col2.stream());
        return Stream.concat(stream, col1.stream().skip(ind)).toList();
    }

    private static Stream<Integer> getSomeIntegers() {
        return Stream.of(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
    }

    private static Stream<Arguments> getSomeIntegersWithIndices() {
        return Stream.of(
                Arguments.arguments(0, Integer.MIN_VALUE),
                Arguments.arguments(DEFAULT_ELEMENTS.size(), Integer.MAX_VALUE),
                Arguments.arguments(DEFAULT_ELEMENTS.size() / 2, 0),
                Arguments.arguments(0, null)
        );
    }

    private static IntStream getInvalidIndicesExcludingSize() {
        return IntStream.of(-1, DEFAULT_ELEMENTS.size() + 1);
    }

    private static IntStream getInvalidIndices() {
        return IntStream.concat(getInvalidIndicesExcludingSize(), IntStream.of(DEFAULT_ELEMENTS.size()));
    }

    private static IntStream getSomeIndices() {
        return IntStream.of(0, DEFAULT_ELEMENTS.size() / 2, DEFAULT_ELEMENTS.size() - 1);
    }

    private static Stream<Collection<Integer>> getSomeCollections() {
        return Stream.of(
                List.of(6, 7, 8, 9),
                List.of(),
                null
        );
    }

    private static Stream<Arguments> getSomeCollectionsWithIndices() {
        return Stream.of(
                Arguments.arguments(0, List.of(6, 7, 8, 9)),
                Arguments.arguments(DEFAULT_ELEMENTS.size(), List.of(6, 7, 8, 9)),
                Arguments.arguments(DEFAULT_ELEMENTS.size() / 2, List.of(6, 7, 8, 9)),
                Arguments.arguments(0, List.of()),
                Arguments.arguments(DEFAULT_ELEMENTS.size(), List.of()),
                Arguments.arguments(0, null),
                Arguments.arguments(DEFAULT_ELEMENTS.size(), null)
        );
    }

}
