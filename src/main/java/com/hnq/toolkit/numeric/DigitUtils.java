package com.hnq.toolkit.numeric;

import com.google.common.collect.ImmutableMap;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author henengqiang
 * @date 2021/07/08
 */
public class DigitUtils {

    private DigitUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 中文数字
     */
    private static final String[] CN_NUM = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    /**
     * 中文数字单位
     */
    private static final String[] CN_UNIT = {"", "十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};

    /**
     * 特殊字符：负
     */
    private static final String CN_NEGATIVE = "负";

    /**
     * 特殊字符：点
     */
    private static final String CN_POINT = "点";


    /**
     * int 转 中文数字
     * 支持到int最大值
     *
     * @param intNum 要转换的整型数
     * @return       中文数字
     */
    public static String intToZh(int intNum) {
        if (intNum == 0) {
            return CN_NUM[0];
        }
        StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        if (intNum < 0) {
            isNegative = true;
            intNum *= -1;
        }
        int count = 0;
        while(intNum > 0) {
            sb.insert(0, CN_NUM[intNum % 10] + CN_UNIT[count]);
            intNum = intNum / 10;
            count++;
        }

        if (isNegative) {
            sb.insert(0, CN_NEGATIVE);
        }
        return sb.toString()
                .replaceAll("零[千百十]", "零")
                .replaceAll("零+万", "万")
                .replaceAll("零+亿", "亿")
                .replaceAll("亿万", "亿零")
                .replaceAll("零+", "零")
                .replaceAll("零$", "");
    }

    /**
     * bigDecimal 转 中文数字
     * 整数部分只支持到int的最大值
     *
     * @param bigDecimalNum 要转换的BigDecimal数
     * @return 中文数字
     */
    public static String bigDecimalToZh(BigDecimal bigDecimalNum) {
        if (bigDecimalNum == null) {
            return CN_NUM[0];
        }
        StringBuilder sb = new StringBuilder();
        //将小数点后面的零给去除
        String numStr = bigDecimalNum.abs().stripTrailingZeros().toPlainString();
        String[] split = numStr.split("\\.");
        String integerStr = intToZh(Integer.parseInt(split[0]));
        sb.append(integerStr);
        //如果传入的数有小数，则进行切割，将整数与小数部分分离
        if (split.length == 2) {
            //有小数部分
            sb.append(CN_POINT);
            String decimalStr = split[1];
            char[] chars = decimalStr.toCharArray();
            for (char aChar : chars) {
                int index = Integer.parseInt(String.valueOf(aChar));
                sb.append(CN_NUM[index]);
            }
        }
        //判断传入数字为正数还是负数
        int sigNum = bigDecimalNum.signum();
        if (sigNum == -1) {
            sb.insert(0, CN_NEGATIVE);
        }
        return sb.toString();
    }

    /**
     * 读音与汉字对应的表
     */
    private static final Map<Character, Integer> ARAB_2_CHINESE = ImmutableMap.<Character, Integer>builder()
            .put('零', 0)
            .put('一', 1)
            .put('二', 2)
            .put('三', 3)
            .put('四', 4)
            .put('五', 5)
            .put('六', 6)
            .put('七', 7)
            .put('八', 8)
            .put('九', 9)
            .put('十', 10)
            .build();

    /**
     * 单位对应数量级的表
     */
    private static final Map<Character, Integer> UNIT_MAP = ImmutableMap.<Character, Integer>builder()
            .put('十', 10)
            .put('百', 100)
            .put('千', 1000)
            .put('万', 10000)
            .put('亿', 10000 * 10000)
            .build();

    /**
     * 正则提取
     */
    private static final Pattern PATTERN = Pattern.compile("[零一二三四五六七八九十]?[十百千万亿]?");

    /**
     * 处理思路：
     * <p>
     * 可能的类型：
     * 一千三百万  130000000
     * 十一       11
     * 一万       10000
     * 一百二十五  125
     * <p>
     * 通过正则将字符串处理为 数字+单位的情况
     * 然后处理的时候通过数字*单位+数字*单位...得到最后结果
     *
     * @param chinese 汉字输入，比如一千三百二十八
     * @return 阿拉伯输入，比如 1328
     */
    public static Integer zhToArab(String chinese) {
        Objects.requireNonNull(chinese);
        //判断参数合法性
        if (!"".equals(PATTERN.matcher(chinese).replaceAll("").trim())) {
            throw new InvalidParameterException();
        }
        Integer result = 0;
        Matcher matcher = PATTERN.matcher(chinese);
        //正则提取所有数字
        while (matcher.find()) {
            String res = matcher.group(0);
            if (res.length() == 2) {
                result += ARAB_2_CHINESE.get(res.charAt(0)) * UNIT_MAP.get(res.charAt(1));
            } else if (res.length() == 1) {
                //处理三十、一百万的情况
                if (UNIT_MAP.containsKey(res.charAt(0))) {
                    if (result == 0) {
                        result = 1;
                    }
                    result *= UNIT_MAP.get(res.charAt(0));
                } else if (ARAB_2_CHINESE.containsKey(res.charAt(0))) {
                    result += ARAB_2_CHINESE.get(res.charAt(0));
                }
            }
        }
        return result;
    }

}
