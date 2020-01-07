package com.hnq.toolkit.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author henengqiang
 * @date 2019/04/23
 */
public class BeanUtils {

    private static final Map<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    /**
     * 基于 CGLIB 的 bean properties 的拷`贝，性能要远优于{@code org.springframework.beans.BeanUtils.copyProperties}
     *
     * @param source    包含数据的原始对象
     * @param target    被塞入数据的目标对象
     */
    public static void copyProperties(Object source, Object target) {
        copyPropertiesBaseCopier(source, target);
    }

    /**
     * convert source class to target class obj
     */
    public static <F, T> T convert(F source, Class<T> targetClz) {
        try {
            T target = targetClz.newInstance();
            copyPropertiesBaseCopier(source, target);
            return target;
        } catch (IllegalAccessException | InstantiationException | ExceptionInInitializerError | SecurityException e) {
            throw new RuntimeException("failed to create instance of " + targetClz.getName() + " - " + e.getMessage());
        }
    }

    /**
     * convert source class to target class obj list
     */
    public static <F, T> List<T> convert(List<F> sourceList, Class<T> targetClz) {
        if (CollectionUtils.isNotEmpty(sourceList)) {
            List<T> ret = Lists.newArrayListWithExpectedSize(sourceList.size());
            for (F source : sourceList) {
                ret.add(convert(source, targetClz));
            }
            return ret;
        }
        return Lists.newArrayList();
    }

    /**
     * 比较两个实体属性值，返回一个boolean。true则表时两个对象中的属性值无差异
     * 注：不适用于比较包含List，Map等类的Class
     * @param oldObj 进行属性比较的对象1
     * @param newObj 进行属性比较的对象2
     * @return       属性差异比较结果boolean
     */
    public static boolean compareObject(Object oldObj, Object newObj) {
        Map<String, Map<String, Object>> resultMap = compareFields(oldObj, newObj);
        return resultMap.size() == 0;
    }

    /**
     * 比较两个实体属性值，返回一个map以有差异的属性名为key，value为一个Map分别存oldObject,newObject此属性名的值
     * @param oldObj 进行属性比较的对象1
     * @param newObj 进行属性比较的对象2
     * @return       属性差异比较结果map
     */
    public static Map<String, Map<String, Object>> compareFields(Object oldObj, Object newObj) {
        Map<String, Map<String, Object>> map = Maps.newHashMap();
        try {
            // 只有两个对象都是同一类型的才有可比性
            if (oldObj.getClass() == newObj.getClass()) {
                Class clazz = oldObj.getClass();
                // 获取Object的所有属性
                PropertyDescriptor[] pds = Introspector.getBeanInfo(clazz, Object.class).getPropertyDescriptors();

                for (PropertyDescriptor pd : pds) {
                    // 获取属性名
                    String name = pd.getName();
                    // 获取属性的get方法
                    Method readMethod = pd.getReadMethod();
                    // 在oldObject上调用get方法等同于获得oldObject的属性值
                    Object oldValue = readMethod.invoke(oldObj);
                    // 在newObject上调用get方法等同于获得newObject的属性值
                    Object newValue = readMethod.invoke(newObj);

                    if (oldValue instanceof List) {
                        continue;
                    }
                    if (newValue instanceof List) {
                        continue;
                    }

                    if (oldValue instanceof Timestamp) {
                        oldValue = new Date(((Timestamp) oldValue).getTime());
                    }
                    if (newValue instanceof Timestamp) {
                        newValue = new Date(((Timestamp) newValue).getTime());
                    }

                    if (oldValue == null && newValue == null) {
                        // 都为null
                        continue;
                    } else if (oldValue == null) {
                        // 但程序运行至此处，newValue一定不为null
                        Map<String, Object> valueMap = Maps.newHashMap();
                        valueMap.put("oldValue", null);
                        valueMap.put("newValue", newValue);
                        map.put(name, valueMap);
                        continue;
                    }

                    if (!oldValue.equals(newValue)) {
                        Map<String, Object> valueMap = Maps.newHashMap();
                        valueMap.put("oldValue", oldValue);
                        valueMap.put("newValue", newValue);
                        map.put(name, valueMap);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 基于 CGLIB 的 bean properties 的拷`贝，性能要远优于{@code org.springframework.beans.BeanUtils.copyProperties}
     *
     * @param source    包含数据的原始对象
     * @param target    被塞入数据的目标对象
     */
    private static void copyPropertiesBaseCopier(Object source, Object target) {
        if (Objects.isNull(source) || Objects.isNull(target)) {
            return;
        }
        String key = String.format("%s:%s", source.getClass().getName(), target.getClass().getName());
        if (!BEAN_COPIER_MAP.containsKey(key)) {
            BeanCopier beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
            BEAN_COPIER_MAP.putIfAbsent(key, beanCopier);
        }
        BeanCopier beanCopier = BEAN_COPIER_MAP.get(key);
        beanCopier.copy(source, target, null);
    }

}
