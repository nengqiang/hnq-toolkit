package com.hnq.toolkit.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.jupiter.api.Test;

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

}
