package com.hnq.toolkit.codec;


import org.junit.jupiter.api.Test;

import com.hnq.toolkit.codec.UnicodeUtils;
import com.hnq.toolkit.file.FileUtils;
import com.hnq.toolkit.parse.RegexUtils;

/**
 * @author henengqiang
 * @date 2019/10/22
 */
class UnicodeUtilsTest {

    @Test
    void unicodeToStringTest() {
        String us = FileUtils.getContent("temp.txt");
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