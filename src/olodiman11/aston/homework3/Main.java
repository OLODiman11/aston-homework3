package olodiman11.aston.homework3;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("MyLinkedList");
        MyList<Integer> llist1 = new MyArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        MyList<Integer> llist2 = new MyArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        MyList<Integer> llist3 = new MyArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 2, 9));
        System.out.println("Список 1: " + llist1);
        System.out.println("Список 2: " + llist2);
        System.out.println("Список 3: " + llist3);
        System.out.println("Списки 1 и 2 равны: " + llist1.equals(llist2));
        System.out.println("Списки 2 и 3 равны: " + llist2.equals(llist3));
        System.out.println();

        System.out.println("MyArrayList");
        MyList<Integer> alist1 = new MyLinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        MyList<Integer> alist2 = new MyLinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        MyList<Integer> alist3 = new MyLinkedList<>(List.of(1, 2, 3, 4, 5, 6, 7, 2, 9));
        System.out.println("Список 1: " + alist1);
        System.out.println("Список 2: " + alist2);
        System.out.println("Список 3: " + alist3);
        System.out.println("Списки 1 и 2 равны: " + alist1.equals(alist2));
        System.out.println("Списки 2 и 3 равны: " + alist2.equals(alist3));
    }
}