package com.hnq.toolkit.text;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author henengqiang
 * @date 2023/3/24
 */
class SqlUtilsTest {

    @Test
    void testBuildApplyInSql() {
        String sql = "SELECT ORG_ID FROM SYS_USER_INFO WHERE ";

        List<Integer> param = Lists.newArrayList(1, 3, 5, 7, 9);
        String inSql = SqlUtils.buildApplyInSql(param, "ID");
        System.out.println(sql + inSql);

        List<Integer> param2 = IntStream.range(0, 2023).boxed().collect(Collectors.toList());
        String inSql2 = SqlUtils.buildApplyInSql(param2, "ID");
        System.out.println(sql + inSql2);

        try {
            SqlUtils.buildApplyInSql(null, "ID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            SqlUtils.buildApplyInSql(param, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}