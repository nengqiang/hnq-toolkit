package com.hnq.toolkit.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author henengqiang
 * @date 2019/10/22
 */
public class UnicodeUtils {

    private UnicodeUtils() {}

    /**
     * unicode 转字符串
     * @param unicode 全为 Unicode 的字符串
     */
    public static String unicodeToString(String unicode) {
        if (StringUtils.isEmpty(unicode)) {
            return StringUtils.EMPTY;
        }
        StringBuilder string = new StringBuilder();
        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {
            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);
            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    /**
     * 含有unicode 的字符串转一般字符串
     * @param unicodeStr 混有 Unicode 的字符串
     */
    public static String unicodeStrToString(String unicodeStr) {
        if (StringUtils.isEmpty(unicodeStr)) {
            return StringUtils.EMPTY;
        }
        int length = unicodeStr.length();
        int count = 0;
        // 正则匹配条件，可匹配“\\u”1到4位，一般是4位可直接使用 String regex = "\\\\u[a-f0-9A-F]{4}";
        String regex = "\\\\u[a-f0-9A-F]{1,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unicodeStr);
        StringBuilder sb = new StringBuilder();

        while(matcher.find()) {
            // 原本的Unicode字符
            String oldChar = matcher.group();
            // 转换为普通字符
            String newChar = unicodeToString(oldChar);
            // 在遇见重复出现的unicode代码的时候会造成从源字符串获取非unicode编码字符的时候截取索引越界等
            int index = matcher.start();

            // 添加前面不是unicode的字符
            sb.append(unicodeStr, count, index);
            // 添加转换后的字符
            sb.append(newChar);
            // 统计下标移动的位置
            count = index+oldChar.length();
        }
        // 添加末尾不是Unicode的字符
        sb.append(unicodeStr, count, length);
        return sb.toString();
    }

    /**
     * 字符串转换unicode
     */
    public static String stringToUnicode(String string) {
        if (StringUtils.isEmpty(string)) {
            return StringUtils.EMPTY;
        }
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u").append(Integer.toHexString(c));
        }
        return unicode.toString();
    }

}
