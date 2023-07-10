package com.hnq.toolkit.text;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author henengqiang
 * @date 2022/7/5
 */
public final class SqlUtils {

    private SqlUtils() {}

    public static final int ORACLE_MAX_IN_SIZE = 1000;

    /**
     * ORACLE IN 不能超过1000，使用此方法来构建SQL语句直接Apply
     *
     * @param paramIn       入参列表
     * @param columnName    字段列名
     * @return              SQL片段
     */
    public static <T> String buildApplyInSql(List<T> paramIn, String columnName) {
        Validate.notEmpty(paramIn, "入参列表[paramIn]不能为空！");
        Validate.notBlank(columnName, "字段列名[columnName]不能为空！");
        paramIn = paramIn.stream().filter(Objects::nonNull).collect(Collectors.toList());
        // if (CollectionUtils.isEmpty(paramIn)) {
        //     return columnName + " IN ''";
        // }

        List<List<T>> param = Lists.partition(paramIn, ORACLE_MAX_IN_SIZE);
        StringJoiner sql = new StringJoiner(
                " OR " + columnName + " IN ",
                paramIn.size() > ORACLE_MAX_IN_SIZE ? "(" + columnName + " IN " : columnName + " IN ",
                paramIn.size() > ORACLE_MAX_IN_SIZE ? ")" : ""
        );
        for (List<T> objects : param) {
            StringJoiner split = new StringJoiner(",", "(", ")");
            objects.stream().filter(Objects::nonNull).distinct().map(p -> "'" + p + "'").forEach(split::add);
            sql.add(split.toString());
        }
        return sql.toString();
    }

}
