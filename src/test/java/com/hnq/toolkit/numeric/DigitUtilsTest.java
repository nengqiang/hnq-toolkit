package com.hnq.toolkit.numeric;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author henengqiang
 * @date 2021/07/08
 */
class DigitUtilsTest {

    @Test
    void intToZh() {
        System.out.println(DigitUtils.intToZh(0));
        System.out.println(DigitUtils.intToZh(-0));
        System.out.println(DigitUtils.intToZh(1));
        System.out.println(DigitUtils.intToZh(-1));
        System.out.println(DigitUtils.intToZh(11));
        System.out.println(DigitUtils.intToZh(111));
        System.out.println(DigitUtils.intToZh(1234567890));
        System.out.println(DigitUtils.intToZh(236578923));
        System.out.println(DigitUtils.intToZh(123));
        System.out.println(DigitUtils.intToZh(0002));
        System.out.println(DigitUtils.intToZh(Integer.MAX_VALUE));
    }

    @Test
    void bigDecimalToZh() {
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal(0)));
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal(-0)));
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal(1)));
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal(-1)));
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal("1.1")));
        System.out.println(DigitUtils.bigDecimalToZh(BigDecimal.valueOf(-2.3)));
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal("234.567890")));
        System.out.println(DigitUtils.bigDecimalToZh(new BigDecimal("0234.0567890")));
    }

    @Test
    void chinese2Arab() {
        System.out.println(DigitUtils.zhToArab("十一"));
        System.out.println(DigitUtils.zhToArab("二"));
        System.out.println(DigitUtils.zhToArab("三"));
        System.out.println(DigitUtils.zhToArab("四"));
        System.out.println(DigitUtils.zhToArab("五"));
        System.out.println(DigitUtils.zhToArab("六"));
        System.out.println(DigitUtils.zhToArab("七"));
        System.out.println(DigitUtils.zhToArab("八"));
        System.out.println(DigitUtils.zhToArab("九"));
        System.out.println(DigitUtils.zhToArab("十"));
        System.out.println(DigitUtils.zhToArab("十一"));
        System.out.println(DigitUtils.zhToArab("十二"));
        System.out.println(DigitUtils.zhToArab("十三"));
        System.out.println(DigitUtils.zhToArab("十四"));
        System.out.println(DigitUtils.zhToArab("二十五"));
    }
}