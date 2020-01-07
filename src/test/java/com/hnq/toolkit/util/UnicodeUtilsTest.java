package com.hnq.toolkit.util;


import org.junit.jupiter.api.Test;

/**
 * @author henengqiang
 * @date 2019/10/22
 */
class UnicodeUtilsTest {

    @Test
    void unicodeToStringTest() {
        String us = ResourceUtils.getContent("temp.txt");
        System.out.println(us);
        us = RegexUtils.group(us, "\\{jQuery\\w+\\((\\{.*})\\);}", 1);
        System.out.println(us);
        String str = UnicodeUtils.unicodeStrToString(us);
        System.out.println(str);
        str = str.replaceAll("</?[^>]+>", "");
        System.out.println(str);
    }

    @Test
    void stringToUnicodeTest() {
    }
}