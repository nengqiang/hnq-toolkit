package com.hnq.toolkit.util;


import com.hnq.toolkit.util.xpath.XpathUtils;
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
        content = ResourceUtils.getContent("table.html");
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

}
