package com.hnq.toolkit.util;

import com.google.common.collect.Maps;
import org.apache.commons.collections.MapUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Use {@link Arrays#sort(int[])} instead.
 *
 * @author henengqiang
 * @date 2019/06/11
 */
public class SortUtils {

    // Arrays 自带sort()方法，我为什么还要在这里写

    /**
     * 简单选择排序
     * 通过设置一个变量min，每一次比较仅存储较小元素的数组下标，当轮循环结束之后，
     * 那这个变量存储的就是当前最小元素的下标，此时再执行交换操作即可。
     * <p>
     * 在最好情况下也就是数组完全有序的时候，无需任何交换移动，在最差情况下，也就是数组倒序的时候，交换次数为n-1次。
     * 综合下来，时间复杂度为O(n2)
     *
     * @param arr   需要排序的数组
     * @param isAsc true: 升序，false: 降序
     */
    public static void selectSort(int[] arr, boolean isAsc) {
        for (int i = 0; i < arr.length - 1; i++) {
            // 存放数组下标
            int minOrMaxIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (isAsc) {
                    if (arr[minOrMaxIndex] > arr[j]) {
                        minOrMaxIndex = j;
                    }
                } else {
                    if (arr[minOrMaxIndex] < arr[j]) {
                        minOrMaxIndex = j;
                    }
                }
            }
            if (minOrMaxIndex != i) {
                swap(arr, minOrMaxIndex, i);
            }
        }
    }

    /**
     * 冒泡排序
     * 对相邻的元素进行两两比较，顺序相反则进行交换，这样，每一趟会将最小或最大的元素“浮”到顶端，最终达到完全有序
     * <p>
     * 若原数组本身就是有序的（这是最好情况），仅需n-1次比较就可完成；若是倒序，比较次数为 n-1+n-2+…+1=n(n-1)/2，
     * 交换次数和比较次数等值。所以，其时间复杂度依然为O(n2）。综合来看，冒泡排序性能还还是稍差于上面那种选择排序的。
     *
     * @param arr   需要排序的数组
     * @param isAsc true: 升序，false: 降序
     */
    public static void bubbleSort(int[] arr, boolean isAsc) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (isAsc) {
                    if (arr[i] > arr[j]) {
                        swap(arr, i, j);
                    }
                } else {
                    if (arr[i] < arr[j]) {
                        swap(arr, i, j);
                    }
                }
            }
        }
    }

    /**
     * 插入排序
     * 每一步将一个待排序的记录，插入到前面已经排好序的有序序列中去，直到插完所有元素为止
     * <p>
     * 简单插入排序在最好情况下，需要比较n-1次，无需交换元素，时间复杂度为O(n);在最坏情况下，时间复杂度依然为O(n2)。
     * 但是在数组元素随机排列的情况下，插入排序还是要优于上面两种排序（简单选择排序和冒泡排序）的。
     *
     * @param arr   需要排序的数组
     * @param isAsc true: 升序，false: 降序
     */
    public static void insertionSort(int[] arr, boolean isAsc) {
        for (int i = 0; i < arr.length; i++) {
            int j = i;
            if (isAsc) {
                while (j > 0 && arr[j - 1] > arr[j]) {
                    swap(arr, j - 1, j);
                    j--;
                }
            } else {
                while (j > 0 && arr[j - 1] < arr[j]) {
                    swap(arr, j - 1, j);
                    j--;
                }
            }
        }
    }

    /**
     * 归并排序
     * 归并排序利用的是分治的思想实现的，对于给定的一组数据，利用递归与分治技术将数据序列划分成为越来越小的子序列，之后对子序列排序，
     * 最后再用递归方法将排好序的子序列合并成为有序序列。合并两个子序列时，需要申请两个子序列加起来长度的内存，临时存储新的生成序列，
     * 再将新生成的序列赋值到原数组相应的位置。
     *
     * @param arr       等待排序的数组
     * @param left      排序起始位置
     * @param right     排序截止位置
     */
    public static void mergingSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            // 左边归并排序，使得左子序列有序
            mergingSort(arr, left, mid);
            // 右边归并排序，使得右子序列有序
            mergingSort(arr, mid + 1, right);
            // 合并两个子序列
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp [k++] = arr [j++];
            }
        }
        // 将左边剩余元素填充进temp中
        while (i <= mid) {
            temp[k++] = arr[i++];
        }
        // 将右序列剩余元素填充进temp中
        while (j <= right) {
            temp[k++] = arr[j++];
        }
        // 将temp中的元素全部拷贝到原数组中
        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    /**
     * 快速排序
     * 通过一趟排序将待排记录分割成独立的两部分，其中一部分记录的关键字均比另一部分记录的关键字小，
     * 则可分别对这两部分记录继续进行排序，已达到整个序列有序。
     *
     * @param arr   等待排序的数组
     * @param isAsc true: 升序，false: 降序
     */
    public static void quickSort(int[] arr, boolean isAsc) {
        if (isAsc) {
            quickSortAsc(arr, 0, arr.length - 1);
        } else {
            quickSortDesc(arr, 0, arr.length - 1);
        }
    }

    private static void quickSortAsc(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int i = start, j = end;
        // 基准数
        int baseVal = arr[start];
        while (i < j) {
            // 从右向左找比基准数小的数
            while (i < j && arr[j] >= baseVal) {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }
            // 从左向右找比基准数大的数
            while (i < j && arr[i] < baseVal) {
                i++;
            }
            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }
        // 把基准数放到i的位置
        arr[i] = baseVal;
        // 递归
        quickSortAsc(arr, start, i - 1);
        quickSortAsc(arr, i + 1, end);
    }

    private static void quickSortDesc(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }
        int i = start, j = end;
        // 基准数
        int baseVal = arr[start];
        while (i < j) {
            while (i < j && arr[j] < baseVal) {
                j--;
            }
            if (i < j) {
                arr[i] = arr[j];
                i++;
            }
            while (i < j && arr[i] >= baseVal) {
                i++;
            }
            if (i < j) {
                arr[j] = arr[i];
                j--;
            }
        }
        // 把基准数放到i的位置
        arr[i] = baseVal;
        // 递归
        quickSortDesc(arr, start, i - 1);
        quickSortDesc(arr, i + 1, end);
    }

    /**
     * 选出数组中的最值
     *
     * @param nums     选择对象
     * @param smallest true：选出最小值，false：选出最大值
     * @return 给定数组中的最值
     */
    public static int selectMaximumValue(int[] nums, boolean smallest) {
        int maxValue = nums[0];
        int minValue = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (maxValue < nums[i]) {
                maxValue = nums[i];
            }
            if (minValue > nums[i]) {
                minValue = nums[i];
            }
        }
        return smallest ? minValue : maxValue;
    }

    /**
     * 交换数组给定两下标的值
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // ------------------------------ ------------------------------ //

    private static Random r = new Random();

    /**
     * 生成随机数组（可能重复）
     *
     * @param size  数组大小
     * @param bound 数组值边界
     * @return      数组
     */
    public static int[] generateArray(int size, int bound) {
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = r.nextInt(bound);
        }
        return nums;
    }

    /**
     * 生成随机数组（不重复）
     *
     * @param size  数组大小
     * @param bound 数组值边界
     * @return      数组
     */
    public static int[] genNotRepeatingArr(int size, int bound) {
        int[] nums = new int[size];
        int j = 0;
        for (int i = 0; i < size; i++) {
            nums[i] = r.nextInt(bound);
            while (j < i) {
                for (j = 0; j < i; j++) {
                    if (nums[i] == nums[j]) {
                        nums[i] = r.nextInt(bound);
                        break;
                    }
                }
            }
        }
        return nums;
    }

    /**
     * 生成两个指定数范围内的随机数组（可能重复）
     * @param size  数组大小
     * @param min   数组元素值最小值
     * @param max   数组元素值最大值
     * @return      数组
     */
    public static int[] generateArrayBetween(int size, int min, int max) {
        int[] nums = new int[size];
        for (int i = 0; i < size; i++) {
            nums[i] = min + r.nextInt(Math.abs(max - min));
        }
        return nums;
    }

    /**
     * 生成两个指定数范围内的随机数组（不重复）
     * @param size  数组大小
     * @param min   数组元素值最小值
     * @param max   数组元素值最大值
     * @return      数组
     */
    public static int[] genNotRepeatingArrBetween(int size, int min, int max) {
        int[] nums = new int[size];
        int diff = Math.abs(max - min);
        int j = 0;
        for (int i = 0; i < size; i++) {
            nums[i] = min + r.nextInt(diff);
            while (j < i) {
                for (j = 0; j < i; j++) {
                    if (nums[i] == nums[j]) {
                        nums[i] = min + r.nextInt(diff);
                        break;
                    }
                }
            }
        }
        return nums;
    }

    // ------------------------------ ------------------------------ //

    /**
     * 对Map按key进行排序
     * 推荐使用 {@link SortUtils#sortMapByKey(Map, boolean)}
     * @param map   等待排序的map
     * @param isAsc true: 升序，false: 降序
     * @return      排序后的map
     */
    public static Map<String, String> sortMapByStrKey(Map<String, String> map, boolean isAsc) {
        if (MapUtils.isEmpty(map)) {
            // 这样调用方法的地方就不用担心空指针了
            return Maps.newHashMap();
        }
        // TreeMap默认按key升序排序
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator(isAsc));
        sortMap.putAll(map);
        return sortMap;
    }

    private static class MapKeyComparator implements Comparator<String> {
        private boolean isAsc;

        MapKeyComparator(boolean isAsc) {
            this.isAsc = isAsc;
        }

        @Override
        public int compare(String o1, String o2) {
            return isAsc ? o1.compareTo(o2) : o2.compareTo(o1);
        }
    }

    // ------------------------------ ------------------------------ //

    /**
     * 使Map按value排序（升序）
     * 推荐使用 {@link SortUtils#sortMapByValue(Map, boolean)}
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueAsc(Map<K, V> map) {
        if (MapUtils.isEmpty(map)) {
            // 这样调用方法的地方就不用担心空指针了
            return Maps.newHashMap();
        }
        // LinkedHashMap可以通过构造方法指定是按放入的顺序，还是get顺序 排序
        Map<K, V> result = Maps.newLinkedHashMap();
        Stream<Map.Entry<K, V>> st = map.entrySet().stream();
        st.sorted(Comparator.comparing(Map.Entry::getValue)).forEach(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

    /**
     * 使Map按value排序（降序）
     * 推荐使用 {@link SortUtils#sortMapByValue(Map, boolean)}
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDesc(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        // 同理 o1， o2换一下就是升序排序了
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));

        Map<K, V> result = new LinkedHashMap<>();
        list.forEach(kvEntry -> result.put(kvEntry.getKey(), kvEntry.getValue()));
        return result;
    }

    // ------------------------------ ------------------------------ //

    /**
     * 根据key对map进行排序
     *         Map<K, V> result = Maps.newLinkedHashMap();
     *         if (asc) {
     *             map.entrySet().stream().sorted(Map.Entry.comparingByKey())
     *                     .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
     *         } else {
     *             map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed())
     *                     .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
     *         }
     *         return result;
     *
     * @param map 等待排序的map
     * @param asc true: 升序，false: 降序
     * @param <K> key的类型
     * @param <V> value的类型
     * @return    排序后的map 是一个LinkedHashMap
     */
    public static <K extends Comparable<? super K>, V> Map<K, V> sortMapByKey(Map<K, V> map, boolean asc) {
        if (asc) {
            return map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, Maps::newLinkedHashMap));
        } else {
            return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByKey().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, Maps::newLinkedHashMap));
        }
    }

    /**
     * 根据value对map进行排序
     *         Map<K, V> result = Maps.newLinkedHashMap();
     *         if (asc) {
     *             map.entrySet().stream().sorted(Map.Entry.comparingByValue())
     *                     .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
     *         } else {
     *             map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed())
     *                     .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
     *         }
     *         return result;
     *
     * @param map 等待排序的map
     * @param asc true: 升序，false: 降序
     * @param <K> key的类型
     * @param <V> value的类型
     * @return    排序后的map 是一个LinkedHashMap
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map, boolean asc) {
        if (asc) {
            return map.entrySet().stream().sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, Maps::newLinkedHashMap));
        } else {
            return map.entrySet().stream().sorted(Map.Entry.<K, V>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, Maps::newLinkedHashMap));
        }
    }

}
