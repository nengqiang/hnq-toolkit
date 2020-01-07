package com.hnq.toolkit.util;

import com.google.common.collect.Maps;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.hnq.toolkit.util.SortUtils.*;

/**
 * @author henengqiang
 * @date 2019/06/13
 */
class SortUtilsTest {

    @Test
    void selectSortTest() {
        int[] arr = generateArray(10, 20);
        selectSort(arr, true);
        System.out.println(Arrays.toString(arr));
        System.out.println("Min=" + selectMaximumValue(arr, true));
    }

    @Test
    void bubbleSortTest() {
        int[] arr = genNotRepeatingArr(10, 20);
        bubbleSort(arr, false);
        System.out.println(Arrays.toString(arr));
        System.out.println("Max=" + selectMaximumValue(arr, false));
    }

    @Test
    void insertionSortTest() {
        int[] arr = generateArray(10, 20);
        insertionSort(arr, false);
        System.out.println(Arrays.toString(arr));
        System.out.println("Min=" + selectMaximumValue(arr, true));
    }

    @Test
    void mergingSortTest() {
        int[] arr = generateArray(10, 20);
        System.out.println("original: " + Arrays.toString(arr));
        mergingSort(arr, 0, arr.length / 2 - 1);
        System.out.println(Arrays.toString(arr));
        mergingSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));

        int[] arr2 = genNotRepeatingArr(10, 20);
        System.out.println("original: " + Arrays.toString(arr2));
        mergingSort(arr2, 0, arr2.length - 1);
        System.out.println(Arrays.toString(arr2));
    }

    @Test
    void quickSortTest() {
        int[] arr = generateArray(10, 20);
        quickSort(arr, true);
        System.out.println(Arrays.toString(arr));
        arr = genNotRepeatingArr(10, 20);
        quickSort(arr, false);
        System.out.println(Arrays.toString(arr));
    }

    @Test
    void sortMapByValueAscTest() {
        Map<Integer, String> map1 = Maps.newHashMap();
        map1.put(3, "3");
        map1.put(1, "1");
        map1.put(7, "7");
        map1.put(5, "5");
        map1 = sortByValueAsc(map1);
        Assertions.assertEquals("{1=1, 3=3, 5=5, 7=7}", map1.toString());

        Map<Integer, Integer> map2 = Maps.newHashMap();
        map2.put(3, 3);
        map2.put(1, 1);
        map2.put(7, 7);
        map2.put(5, 5);
        map2 = sortByValueAsc(map2);
        Assertions.assertEquals("{1=1, 3=3, 5=5, 7=7}", map2.toString());
    }

    @Test
    void sortMapByValueDescTest() {
        Map<Integer, String> map1 = Maps.newHashMap();
        map1.put(3, "3");
        map1.put(1, "1");
        map1.put(7, "7");
        map1.put(5, "5");
        map1 = sortByValueDesc(map1);
        Assertions.assertEquals("{7=7, 5=5, 3=3, 1=1}", map1.toString());

        Map<Integer, Integer> map2 = Maps.newHashMap();
        map2.put(3, 3);
        map2.put(1, 1);
        map2.put(7, 7);
        map2.put(5, 5);
        map2 = sortByValueDesc(map2);
        Assertions.assertEquals("{7=7, 5=5, 3=3, 1=1}", map2.toString());
    }

    @Test
    void sortMapByKeyTest() {
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("bob", "3");
        map1.put("alice", "1");
        map1.put("dandy", "7");
        map1.put("candy", "5");
        Map result = sortMapByStrKey(map1, true);
        Assertions.assertEquals("{alice=1, bob=3, candy=5, dandy=7}", result.toString());

        result = sortMapByKey(map1, false);
        Assertions.assertEquals("{dandy=7, candy=5, bob=3, alice=1}", result.toString());

        Map<Integer, Integer> map2 = Maps.newHashMap();
        map2.put(3, 3);
        map2.put(10, 10);
        map2.put(1, 1);
        map2.put(25, 25);
        result = sortMapByKey(map2, true);
        Assertions.assertEquals("{1=1, 3=3, 10=10, 25=25}", result.toString());
    }

    @Test
    void sortMapByValueTest() {
        Map<String, String> map1 = Maps.newHashMap();
        map1.put("1", "bob");
        map1.put("2", "alice");
        map1.put("3", "dandy");
        map1.put("4", "candy");
        Map result = sortMapByValue(map1, true);
        Assertions.assertEquals("{2=alice, 1=bob, 4=candy, 3=dandy}", result.toString());

        Map<String, Integer> map2 = Maps.newHashMap();
        map2.put("1", 1);
        map2.put("5", 5);
        map2.put("4", 4);
        map2.put("3", 3);
        result = sortMapByValue(map2, false);
        Assertions.assertEquals("{5=5, 4=4, 3=3, 1=1}", result.toString());

    }

    @Test
    void sortByValueTest() {
        List<Integer> list1 = Lists.newArrayList(1);
        List<Integer> list2 = Lists.newArrayList(1, 2);
        List<Integer> list3 = Lists.newArrayList(1, 2, 3);
        List<Integer> list4 = Lists.newArrayList(1, 2, 3, 4);

        Map<String, List<Integer>> groupMap = Maps.newHashMap();
        groupMap.put("test1", list1);
        groupMap.put("test4", list4);
        groupMap.put("test2", list2);
        groupMap.put("test3", list3);

//        Map<String, List<Integer>> res = groupMap.entrySet().stream()
//                .sorted(Map.Entry.<String, List<Integer>>comparingByValue().reversed())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, Maps::newLinkedHashMap));

        groupMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    void listSwapTest() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        List<Integer> res = Lists.newArrayList();
        for (Integer i : list) {
            if (i == 4) {
                res.add(i);
                list.remove(i);
                break;
            }
        }
        res.addAll(list);
        System.out.println(res);
    }

}