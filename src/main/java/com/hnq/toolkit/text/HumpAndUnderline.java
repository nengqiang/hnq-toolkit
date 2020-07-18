package com.hnq.toolkit.text;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author henengqiang
 * @date 2019/03/19
 */
public class HumpAndUnderline {

    private static final String UNDER_LINE = "_";

    /**
     * 驼峰转下划线
     */
    public static String getUnderlineName(String propertyName) {
        if (null == propertyName) {
            return "";
        }

        StringBuilder sbl = new StringBuilder(propertyName);
        sbl.setCharAt(0, Character.toLowerCase(sbl.charAt(0)));
        propertyName = sbl.toString();

        char[] chars = propertyName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (CharUtils.isAsciiAlphaUpper(c) && i == 0) {
                // 第一个大写字母
                sb.append(StringUtils.lowerCase(CharUtils.toString(c)));
            } else if (CharUtils.isAsciiAlphaUpper(c) && !CharUtils.isAsciiAlpha(chars[i - 1])) {
                // 大写字母前面没有字母
                sb.append(StringUtils.lowerCase(CharUtils.toString(c)));
            } else if (CharUtils.isAsciiAlphaUpper(c) && CharUtils.isAsciiAlpha(chars[i - 1])) {
                // 大写字母前面有字母
                sb.append(UNDER_LINE).append(StringUtils.lowerCase(CharUtils.toString(c)));
            } else {
                // 非大写字母
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * covert field name to column name
     * */
    public static String getHumpName(String fieldName) {
        if (null == fieldName) {
            return "";
        }
        fieldName = fieldName.toLowerCase();
        char[] chars = fieldName.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == '_') {
                int j = i + 1;
                if (j < chars.length) {
                    sb.append(StringUtils.upperCase(CharUtils.toString(chars[j])));
                    i++;
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
