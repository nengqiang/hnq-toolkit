package com.hnq.toolkit.collection;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * 批处理工具类
 *
 * @author henengqiang
 * @date 2024/6/13
 */
public final class CollectUtils {

    private CollectUtils() {
        throw new IllegalStateException("Utility class");
    }


    public interface Tool<R, S> {
        R get(S s);
    }

    /**
     * 集合分批处理
     * ----默认1000一批
     *
     * @param dataSet 待处理数据
     * @param tool    处理方式
     * @param <S>     待处理数据类型
     * @param <R>     处理结果类型
     * @return        处理结果
     */
    public static <S, R> List<R> doSthPart(Collection<S> dataSet, Tool<List<R>, List<S>> tool) {
        return doSthPart(dataSet, tool, 1000);
    }

    /**
     * 集合分批处理
     *
     * @param dataSet 待处理数据
     * @param tool    处理方式
     * @param size    每批次大小
     * @param <S>     待处理数据类型
     * @param <R>     处理结果类型
     * @return        处理结果
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <S, R> List<R> doSthPart(Collection<S> dataSet, Tool<List<R>, List<S>> tool, int size) {
        if (CollectionUtils.isEmpty(dataSet)) {
            return new ArrayList<>();
        }
        List<S> arrayList;
        if (dataSet instanceof ArrayList) {
            arrayList = (ArrayList) dataSet;
        } else {
            arrayList = new ArrayList<>(dataSet);
        }
        if (arrayList.size() <= size) {
            return tool.get(arrayList);
        }
        List<List<S>> listList = Lists.partition(arrayList, size);
        List<R> resultList = new ArrayList<>();
        for (List<S> subList : listList) {
            List<R> tempResultList = tool.get(subList);
            if (tempResultList == null || tempResultList.size() == 0) {
                continue;
            }
            resultList.addAll(tempResultList);
        }
        return resultList;
    }

    /**
     * List 转 Map
     * --可重复
     *
     * @param dataSet   待处理数据
     * @param tool      处理方式
     * @param <K>       待处理数据类型
     * @param <V>       处理结果类型
     * @return          处理结果
     */
    public static <K, V> Map<K, List<V>> convertToMapList(Collection<V> dataSet, Tool<K, V> tool) {
        Map<K, List<V>> map = new HashMap<>();
        if (CollectionUtils.isEmpty(dataSet)) {
            return map;
        }
        for (V obj : dataSet) {
            K key = tool.get(obj);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(obj);
        }
        return map;
    }

    /**
     * List 转map
     * --无重复
     *
     * @param dataSet   待处理数据
     * @param tool      处理方式
     * @param <K>       待处理数据类型
     * @param <V>       处理结果类型
     * @return          处理结果
     */
    public static <K, V> Map<K, V> convertToMap(Collection<V> dataSet, Tool<K, V> tool) {
        Map<K, V> map = new HashMap<>();
        if (CollectionUtils.isEmpty(dataSet)) {
            return map;
        }
        for (V obj : dataSet) {
            K key = tool.get(obj);
            map.put(key, obj);
        }
        return map;
    }

    /**
     * List 转类型 List
     *
     * @param dataSet   待处理数据
     * @param tool      处理方式
     * @param <T>       待处理数据类型
     * @param <S>       处理结果类型
     * @return          处理结果
     */
    public static <T, S> List<T> convertToList(Collection<S> dataSet, Tool<T, S> tool) {
        List<T> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(dataSet)) {
            return list;
        }
        for (S obj : dataSet) {
            T t = tool.get(obj);
            list.add(t);
        }
        return list;
    }

    /**
     * List 转类型 Set
     *
     * @param dataSet   待处理数据
     * @param tool      处理方式
     * @param <T>       待处理数据类型
     * @param <S>       处理结果类型
     * @return          处理结果
     */
    public static <T, S> Set<T> convertToSet(Collection<S> dataSet, Tool<T, S> tool) {
        Set<T> set = new HashSet<>();
        if (CollectionUtils.isEmpty(dataSet)) {
            return set;
        }
        for (S obj : dataSet) {
            T t = tool.get(obj);
            set.add(t);
        }
        return set;
    }


    /**
     * 集合toString
     *
     * @param dateSet   待处理数据
     * @param <E>       待处理数据类型
     * @return          处理结果
     */
    public static <E> String toString(Collection<E> dateSet) {
        return toString(dateSet, 30);
    }

    /**
     * 集合toString
     *
     * @param dateSet   待处理数据
     * @param <E>       待处理数据类型
     * @return          处理结果
     */
    public static <E> String toString(Collection<E> dateSet, int max) {
        if (CollectionUtils.isEmpty(dateSet)) {
            return "{empty Collection}";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (E obj : dateSet) {
            stringBuilder.append(obj.toString()).append("\n");
            if (++count >= max) {
                break;
            }
        }
        return stringBuilder.toString();
    }

}
