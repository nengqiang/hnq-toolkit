package com.hnq.toolkit.util.csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 *
 * @author chengwei
 * @date 2018/10/15 下午3:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CSVField {

    /**
     * CSV文件列名
     */
    String name() default "";

    String patten() default "";

}

