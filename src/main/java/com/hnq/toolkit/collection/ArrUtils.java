package com.hnq.toolkit.collection;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author henengqiang
 * @date 2020/3/26
 */
public class ArrUtils {

    private ArrUtils() {}

    /**
     * 根据对象属性对list去重
     * 使用举例：persons.stream().filter(distinctByKey(Person::getName))
     *
     * @param list          要去重的list
     * @param keyExtractor  去重依据的对象的字段
     * @param <T>           对象泛型
     * @return              去重后的list
     */
    public static <T> List<T> distinctByKey(List<T> list, Function<? super T, Object> keyExtractor) {
        return list.stream().filter(distinctByKey(keyExtractor)).collect(Collectors.toList());
    }

    /**
     * 根据对象属性去重
     *
     * @param keyExtractor  对象的字段
     * @param <T>           对象泛型
     * @return              see {@link Stream#filter(java.util.function.Predicate)}
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>(16);
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    // ------------------------------ ------------------------------ //

    private static final Random R = new Random();

    /**
     * 生成随机数组（可能重复）
     *
     * @param size  数组大小
     * @param bound 数组值边界
     * @return      数组
     */
    public static int[] generateArray(int size, int bound) {
        return IntStream.range(0, size).map(i -> R.nextInt(bound)).toArray();
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
            nums[i] = R.nextInt(bound);
            while (j < i) {
                for (j = 0; j < i; j++) {
                    if (nums[i] == nums[j]) {
                        nums[i] = R.nextInt(bound);
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
        return IntStream.range(0, size).map(i -> min + R.nextInt(Math.abs(max - min))).toArray();
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
            nums[i] = min + R.nextInt(diff);
            while (j < i) {
                for (j = 0; j < i; j++) {
                    if (nums[i] == nums[j]) {
                        nums[i] = min + R.nextInt(diff);
                        break;
                    }
                }
            }
        }
        return nums;
    }

}
