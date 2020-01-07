package com.hnq.toolkit.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author henengqiang
 * @date 2019/05/20
 */
@Slf4j
public class StrUtils {

    private StrUtils() {}

    private static final Pattern PATTERN = Pattern.compile("\\{}");

    // --- 求一个字符串中，指定字符串出现的次数 ---//

    /**
     * 方法一：使用indexOf和subString方法，循环判断并截取
     */
    public static int calCharTimes1(String str, String dest) {
        int count = 0;
        while (str.contains(dest)) {
            str = str.substring(str.indexOf(dest) + dest.length());
            count++;
        }
        return count;
    }

    /**
     * 方法二：使用replace方法将字符串替换为空，然后求长度
     */
    public static int calCharTimes2(String str, String dest) {
        return (str.length() - str.replace(dest, "").length()) / dest.length();
    }

    /**
     * 类似{@link String#format(String, Object...)}，只不过是把%s换成了{}
     */
    public static String format(String format, Object... args) {
        if (StringUtils.isEmpty(format) || ArrayUtils.isEmpty(args)) {
            return format;
        }

        Matcher matcher = PATTERN.matcher(format);
        if (matcher.find()) {
            int size = args.length;
            int i = 0;
            StringBuffer result = new StringBuffer();
            do {
                matcher.appendReplacement(result, args[i] == null ? StringUtils.EMPTY : args[i].toString());
                i++;
            } while (i < size && matcher.find());
            matcher.appendTail(result);

            return result.toString();
        }

        return format;
    }

}
