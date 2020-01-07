package com.hnq.toolkit.util;

import com.hnq.toolkit.util.xpath.XpathUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author henengqiang
 * @date 2019/09/19
 */
class XpathUtilsTest {

    private String content;

    @BeforeEach
    void getContent() {
        content = ResourceUtils.getContent("table.html");
    }

    @Test
    void getValueByXpathTest() {

        String select = "span:contains(发起投诉):not(:contains(span)) div";

        String result = XpathUtils.getValueByXpath(select, content);
        System.out.println(result);
    }

    @Test
    void getXpathTest() {

        String select = "div:contains(发起投诉):not(:contains(div))";

        List<String> result = XpathUtils.getXpath(select, content);
        System.err.println("【" + result.size() + "】 result selected:");
        result.forEach(System.out::println);
    }

    @Test
    void test() {

    }

}

