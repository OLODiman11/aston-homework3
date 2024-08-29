# Aston Домашнее задание 3
Создание реализаций списка.

Создан общий интерфейс `MyList<T>`, который обладает следующими методами:
- `void add(T el)` - добавление элемента в конец списка
- `void addAt(int ind, T el)` - добавление элемента по индексу
- `T get(int ind)` - получение элемента по индексу
- `boolean remove(T el)` - удаление элемента
- `T removeAt(int ind)` - удаление элемента по индексу
- `void addAll(Collection<? extends T> col)` - добавление элементов коллекции в конец списка
- `void addAllAt(int ind, Collection<? extends T> col)` - добавление элементов коллекции по индексу
- `int size()` - получение количества элементов в списке
- `void sort(Comparator<? super T> comparator)` - сортирует список

Интерфейс реализуется двумя классами:
- `MyArrayList<T>` - список на основе массивов
- `MyLinkedList<T>` - двунаправленный список

Также в `MyList<T>` созданы статические методы, сортирующий список по алгоритму пузырьковой сортировки (bubble sort):
- `<R extends Comparable<R>> void sort(MyList<R> list)`
- `<R> void sort(MyList<R> list, Comparator<? super R> comparator)`

К публичным методам классов написана javadoc документация.\
Методы классов протестированы с использованием JUnit5.

### Полученный опыт:
- Написание реализаций списка на основе массивов (array list)
- Написание реализаций двунаправленного списка (doubly linked list)
- Написание пузырькового алгоритма сортировки для обеих реализаций
- Использование JUnit5:
  - Написание параметрических тестов `@ParameterizedTest` с источником из метода `@MethodSource`.
  - Группировка тестов в классах `@Nested`.
  - Написание общих тестов для интерфейса и распространение их на все реализации.
- Написание документации javadoc
- Работа с универсальными типами (generic type), вариантность
- Использование Stream API
