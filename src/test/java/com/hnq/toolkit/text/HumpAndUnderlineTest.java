package com.hnq.toolkit.text;

import org.junit.jupiter.api.Test;

import com.hnq.toolkit.text.HumpAndUnderline;

/**
 * @author henengqiang
 * @date 2019/06/10
 */
class HumpAndUnderlineTest {

    private static final String TEST_STR =
            "userName varchar(12) comment '用户名', " +
            "phoneNumber int(11) comment '电话号码' ";

    @Test
    void humpAndUnderlineTest() {
        String result = HumpAndUnderline.getUnderlineName(TEST_STR);
        System.out.println(result);

        result = HumpAndUnderline.getHumpName(result);
        System.out.println(result);
    }

}