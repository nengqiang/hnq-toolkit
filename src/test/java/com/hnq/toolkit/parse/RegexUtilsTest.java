package com.hnq.toolkit.parse;


import com.hnq.toolkit.file.FileUtils;
import com.hnq.toolkit.parse.RegexUtils;
import com.hnq.toolkit.parse.XpathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author henengqiang
 * @date 2019/10/14
 */
class RegexUtilsTest {

    private String content;

    @BeforeEach
    void getContent() {
        content = FileUtils.getContent("table.html");
    }

    @Test
    void findAllTest() {
        List<String> result = RegexUtils.findAll(content, "(\\{\\n*'id':\\s*'[\\w:-]+',[\\s\\S\\n]+?\\})", 1);
        System.err.println("【" + result.size() + "】result(s) selected.");
        result.forEach(System.out::println);
    }

    @Test
    void test() {
        String temp = XpathUtils.getValueByXpath("li:contains(联系方式):not(:contains(li))", content);
        System.out.println(temp);
        String phone = RegexUtils.group(temp, "(\\d{5,20})", 1);
        System.out.println(phone);

    }

    @Test
    void regexTest() {
        String regex = "\\D{4}年\\D{1,2}月\\D{1,3}日";
        String str = "二〇二〇年五月二十一日";
        System.out.println(str.matches(regex));
    }

}
