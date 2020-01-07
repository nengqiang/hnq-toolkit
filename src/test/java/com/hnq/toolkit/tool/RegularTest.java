package com.hnq.toolkit.tool;

import com.google.common.collect.Lists;
import com.hnq.toolkit.util.RegexUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.hnq.toolkit.tool.Regular.*;

/**
 * @author henengqiang
 * @date 2019/06/24
 */
class RegularTest {

    @Test
    void nameOrPwTest() {
        String user1 = "a12345";
        String pw1 = "H_45678904567";
        String user2 = "123456aaa";
        String pw2 = "a12345678901234567890";
        String user3 = "a123";
        String pw3 = "234534H";
        Assertions.assertTrue(user1.matches(RE_NAME_OR_PW));
        Assertions.assertTrue(pw1.matches(RE_NAME_OR_PW));
        Assertions.assertFalse(user2.matches(RE_NAME_OR_PW));
        Assertions.assertFalse(pw2.matches(RE_NAME_OR_PW));
        Assertions.assertFalse(user3.matches(RE_NAME_OR_PW));
        Assertions.assertFalse(pw3.matches(RE_NAME_OR_PW));
    }

    @Test
    void telNumberTest() {
        String no1 = "135-1234567";
        String no2 = "1357-12345678";
        String no3 = "12-1234567";
        String no4 = "1234-123";
        Assertions.assertTrue(no1.matches(RE_TEL_NUMBER));
        Assertions.assertTrue(no2.matches(RE_TEL_NUMBER));
        Assertions.assertFalse(no3.matches(RE_TEL_NUMBER));
        Assertions.assertFalse(no4.matches(RE_TEL_NUMBER));
    }

    @Test
    void phoneNumberTest() {
        String no1 = "13728468338";
        String no2 = "23732563622";
        String no3 = "15578786666";
        Assertions.assertTrue(no1.matches(RE_PHONE_NUMBER));
        Assertions.assertFalse(no2.matches(RE_PHONE_NUMBER));
        Assertions.assertTrue(no3.matches(RE_PHONE_NUMBER));
    }

    @Test
    void idCardTest() {
        String card1 = "51302119990909242X";
        String card2 = "320311770706001";
        String card3 = "6228489089908898";
        String card4 = "77777777777777x";
        Assertions.assertTrue(card1.matches(RE_ID_CARD_NO));
        Assertions.assertTrue(card2.matches(RE_ID_CARD_NO));
        Assertions.assertFalse(card3.matches(RE_ID_CARD_NO));
        Assertions.assertFalse(card4.matches(RE_ID_CARD_NO));
    }

    @Test
    void emailTest() {
        String email1 = "henq@henq.com";
        String email2 = "henq @henq.com";
        String email3 = "henq@henq.com.cn";
        String email4 = "henq-henq@henq.com";
        String email5 = "henq_henq@henq.com";
        String email6 = "henq.henq@henq.com";
        String email7 = "henq=henq@henq.com";
        String email8 = "henq&henq@henq.com";
        Assertions.assertTrue(email1.matches(RE_EMAIL));

        Assertions.assertFalse(email2.matches(RE_EMAIL));

        Assertions.assertTrue(email3.matches(RE_EMAIL));
        Assertions.assertTrue(email4.matches(RE_EMAIL));
        Assertions.assertTrue(email5.matches(RE_EMAIL));
        Assertions.assertTrue(email6.matches(RE_EMAIL));

        Assertions.assertFalse(email7.matches(RE_EMAIL));
        Assertions.assertFalse(email8.matches(RE_EMAIL));
    }

    @Test
    void numberOrCharTest() {
        String str1 = "124or123";
        String str2 = "123fds234$";
        Assertions.assertTrue(str1.matches(RE_NUM_OR_CHAR));
        Assertions.assertFalse(str2.matches(RE_NUM_OR_CHAR));
    }

    @Test
    void integerOrDecimalTest() {
        String str1 = "123";
        String str2 = "123.12";
        String str3 = "123.";
        String str4 = ".123";
        Assertions.assertTrue(str1.matches(RE_INTEGER_OR_DECIMAL));
        Assertions.assertTrue(str2.matches(RE_INTEGER_OR_DECIMAL));
        Assertions.assertFalse(str3.matches(RE_INTEGER_OR_DECIMAL));
        Assertions.assertFalse(str4.matches(RE_INTEGER_OR_DECIMAL));
    }

    @Test
    void chineseCharTest() {
        String word1 = "中国";
        String word2 = "中";
        String word3 = "I bored in 中国";
        Assertions.assertTrue(word1.matches(RE_CHINESE_CHA));
        Assertions.assertTrue(word2.matches(RE_CHINESE_CHA));
        Assertions.assertFalse(word3.matches(RE_CHINESE_CHA));
    }

    @Test
    void urlTest() {
        String url1 = "https://blog.csdn.net/acmman/article/details/43804149";
        String url2 = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&ch=&tn=02003390_43_hao_pg&bar=&wd=AssertionError&oq=15%25E4%25BD%258D%25E8%25BA%25AB%25E4%25BB%25BD%25E8%25AF%2581&rsv_pq=81cd196100029259&rsv_t=07e3MtpOdyKFVBZlR3wD%2B7Tq1%2FFExy1snlAoWLp069aVDqUn689Tu%2Fde0xqYkwJdAmBb%2FNJwUy2M&rqlang=cn&rsv_enter=1&inputT=1083";
        String url3 = "https://baike.baidu.com/item/%E6%AD%A3%E5%88%99%E8%A1%A8%E8%BE%BE%E5%BC%8F/1700215?fr=aladdin#7";
        String url4 = "https://baike.baidu.com/item/隐隐约约/1700215?fr=aladdin#7";
        Assertions.assertTrue(url1.matches(RE_URL));
        Assertions.assertTrue(url2.matches(RE_URL));
        Assertions.assertTrue(url3.matches(RE_URL));
        Assertions.assertFalse(url4.matches(RE_URL));
    }

    @Test
    void doubleByteTest() {
        String content = "I bored in 中国";
        String regular = "(" + MA_DOUBLE_BYTE + ")";
        String result = RegexUtils.group(content, regular, 1);
        Assertions.assertEquals("中", result);
    }

    @Test
    void datePatternTest() {
        String content = "201909092019年01月01日2019-09-092019/09/09";
        String regular = "(" + MA_DATE + ")";
        String result = RegexUtils.group(content, regular, 1);
        Assertions.assertEquals("2019年01月01日", result);
    }

    @Test
    void whiteLineTest() {
        String str = "          \n      \n      test        \n";
        String regular = "(" + MA_WHITE_LINES + ")";
        String result = replaceAll(str, "", regular);
        Assertions.assertEquals("      test        \n", result);
    }

    @Test
    void tencentQQTest() {
        String qq = "1451786521";
        Assertions.assertTrue(qq.matches(MA_TENCENT_QQ));
        qq = "1001";
        Assertions.assertFalse(qq.matches(MA_TENCENT_QQ));
    }

    @Test
    void ipAddressTest() {
        String address = "192.168.0.1";
        Assertions.assertTrue(address.matches(MA_IP_ADDRESS));
        address = "255.255.255.0";
        Assertions.assertTrue(address.matches(MA_IP_ADDRESS));
        address = "400.400.245.0";
        Assertions.assertFalse(address.matches(MA_IP_ADDRESS));
    }

    @Test
    void getMatcherGroupTest() {
        String content = "依赖获取（Dependency Locate）是指在系统中提供一个获取点，客户类仍然依赖服务类的接口。当客户类需要服务类时，从获取点主动取得制定的服务类，具体的服务类类型由获取点的配置决定。test 123546467iuuhtijewfl;/fg,.d,gp;'qwktpero;rmgsl;''q=159-=1680=34]5lt'gk;;lw45jky5;o;gjksdfo;";
        String regular = "([\\w\\s]+)";
        String result = RegexUtils.group(content, regular, 1);
        Assertions.assertEquals("Dependency Locate", result);
        result = RegexUtils.group("", regular, 1);
        Assertions.assertEquals("", result);
        try {
             RegexUtils.group(content, regular, 2);
        } catch (Exception e) {
            Assertions.assertEquals("No group 2", e.getMessage());
        }
    }

    @Test
    void replaceFirstTest() {
        String content = "aaa123aaa";
        String regular = "[a-z]+";
        String result = replaceFirst(content, "", regular);
        Assertions.assertEquals("123aaa", result);
    }

    @Test
    void forTest() {
        List<String> list1 = Lists.newArrayList();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        list1.add("5");

        List<String> list2 = Lists.newArrayList();
        list2.add("4");
        list2.add("5");

        for (String s : list2) {
            // 关键！！每次循环重新取iterator
            Iterator<String> it = list1.iterator();
            while (it.hasNext()) {
                if (Objects.equals(s, it.next())) {
                    System.out.println(s);
                    it.remove();
                }
            }
        }
        System.out.println(list1);
    }

    @Test
    void splitTest() {
        String str = "201909:201908:201907";
        System.out.println(Arrays.toString(str.split(":")));
    }

}