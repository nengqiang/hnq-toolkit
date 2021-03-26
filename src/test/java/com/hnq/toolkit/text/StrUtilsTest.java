package com.hnq.toolkit.text;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;

import com.hnq.toolkit.parse.RegexUtils;

/**
 * @author henengqiang
 * @date 2019/10/18
 */
class StrUtilsTest {


    @Test
    void replaceTest() {
        String str = "\"{\\\"ageing\\\":\\\"0.00\\\",\\\"ageingu\\\":\\\"0.00\\\",\\\"avlimt\\\":\\\"32506.80\\\",\\\"cashcred\\\":\\\"15000.00\\\",\\\"creditLmt\\\":\\\"30000.00\\\",\\\"creditLmtD\\\":\\\"30000.00\\\",\\\"cstmbal\\\":\\\"0.00\\\",\\\"cstmbax\\\":\\\"0.00\\\",\\\"curbal\\\":\\\"493.20\\\",\\\"curbax\\\":\\\"0.00\\\",\\\"cycle\\\":\\\"20190724\\\",\\\"dueDate\\\":\\\"20190813\\\",\\\"minPay\\\":\\\"0.00\\\",\\\"minPayu\\\":\\\"0.00\\\",\\\"name\\\":\\\"郭颖 女士\\\",\\\"newSignLmt\\\":\\\"0.00\\\",\\\"newSignLmtu\\\":\\\"0.00\\\",\\\"noteCode\\\":\\\"\\\",\\\"noteCodeu\\\":\\\"\\\",\\\"openAmt\\\":\\\"226.99\\\",\\\"openAmtu\\\":\\\"0.00\\\",\\\"payed\\\":\\\"226.99\\\",\\\"payedu\\\":\\\"0.00\\\",\\\"stmtamt\\\":\\\"0.00\\\",\\\"stmtamtu\\\":\\\"0.00\\\"}\"";
        String res = StringEscapeUtils.unescapeJavaScript(str);
        System.out.println(res);
        System.out.println(RegexUtils.group(res, "\"(\\{.*})\"", 1));
        System.out.println(res.replaceAll("\"\\{", "\\{").replaceAll("}\"", "}"));
    }

    @Test
    void strDealTest() {

    }

    @Test
    void testFormat() {
        String template = "八大基本数据类型示例：\nString: {}, int: {}, short:{}, double: {}, long: {}, float: {}, char: {}, boolean: {}";
        String res = StrUtils.format(template, "String", Short.valueOf("1"), 1000, 1000D, 1000L, 1000F, 'c', true);
        System.out.println(res);
        // 八大基本数据类型示例：
        // String: String, int: 1, short:1000, double: 1000.0, long: 1000, float: 1000.0, char: c, boolean: true
    }

}
