package com.hnq.toolkit.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.hnq.toolkit.collection.SortUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author henengqiang
 * @date 2020/3/26
 */
class ArrUtilsTest {

    @Test
    void testDistinctByKey1() {
        List<Person> persons = getPeople();

        List<Person> res = persons.stream().filter(ArrUtils.distinct(Person::getName)).collect(Collectors.toList());
        List<Person> res2 = persons.stream().filter(ArrUtils.distinct(Person::getAge)).collect(Collectors.toList());
        assertEquals(res, res2);
    }

    @Test
    void testDistinctByKey2() {
        List<Person> persons = getPeople();

        List<Person> res = ArrUtils.distinct(persons, Person::getName);
        List<Person> res2 = ArrUtils.distinct(persons, Person::getAge);
        assertEquals(res, res2);
    }

    private List<Person> getPeople() {
        List<Person> persons = new ArrayList<>(8);
        persons.add(new Person("alice", 20, ""));
        persons.add(new Person("alice", 20, ""));
        persons.add(new Person("bob", 30, ""));
        persons.add(new Person("candy", 40, ""));
        persons.add(new Person("candy", 40, ""));
        return persons;
    }

    @Data
    @AllArgsConstructor
    private static class Person {
        private String name;
        private Integer age;
        private String address;
    }

    @Test
    void selectSortTest() {
        int[] arr = ArrUtils.generate(10, 20);
        selectSort(arr, true);
        System.out.println(Arrays.toString(arr));
        System.out.println("Min=" + selectMaximum(arr, true));
    }

    @Test
    void bubbleSortTest() {
        int[] arr = ArrUtils.generateWithoutRepeat(10, 20);
        bubbleSort(arr, false);
        System.out.println(Arrays.toString(arr));
        System.out.println("Max=" + selectMaximum(arr, false));
    }

    @Test
    void insertionSortTest() {
        int[] arr = ArrUtils.generate(10, 20);
        insertionSort(arr, false);
        System.out.println(Arrays.toString(arr));
        System.out.println("Min=" + selectMaximum(arr, true));
    }

    @Test
    void mergingSortTest() {
        int[] arr = ArrUtils.generate(10, 20);
        System.out.println("original: " + Arrays.toString(arr));
        mergingSort(arr, 0, arr.length / 2 - 1);
        System.out.println(Arrays.toString(arr));
        mergingSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

        int[] arr2 = ArrUtils.generateWithoutRepeat(10, 20);
        System.out.println("original: " + Arrays.toString(arr2));
        mergingSort(arr2, 0, arr2.length - 1);
        System.out.println(Arrays.toString(arr2));
    }

    @Test
    void quickSortTest() {
        int[] arr = ArrUtils.generate(10, 20);
        quickSort(arr, true);
        System.out.println(Arrays.toString(arr));
        arr = ArrUtils.generateWithoutRepeat(10, 20);
        quickSort(arr, false);
        System.out.println(Arrays.toString(arr));
    }

}