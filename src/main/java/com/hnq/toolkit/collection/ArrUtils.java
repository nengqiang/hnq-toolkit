package com.hnq.toolkit.collection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author henengqiang
 * @date 2020/3/26
 */
public class ArrUtils {

    private ArrUtils() {}

    /**
     * 根据对象属性对list去重
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
     * 使用举例：persons.stream().filter(distinctByKey(Person::getName))
     *
     * @param keyExtractor  对象的字段
     * @param <T>           对象泛型
     * @return              see {@link Stream#filter(java.util.function.Predicate)}
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>(16);
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
