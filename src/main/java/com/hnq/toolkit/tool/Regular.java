package com.hnq.toolkit.tool;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author henengqiang
 * @date 2019/06/24
 */
public class Regular {

    /**
     * 验证用户名和密码：（"^[a-zA-Z]\w{5,15}$"）正确格式："[A-Z][a-z]_[0-9]"组成,并且第一个字必须为字母6~16位
     */
    public static final String RE_NAME_OR_PW = "^[a-zA-Z]\\w{5,15}$";

    /**
     * 验证电话号码：（"^(\d{3,4}-)\d{7,8}$"）正确格式：xxx/xxxx-xxxxxxx/xxxxxxxx；
     */
    public static final String RE_TEL_NUMBER = "^(\\d{3,4}-)\\d{7,8}$";

    /**
     * 验证手机号码："^1[3|4|5|7|8][0-9]{9}$"
     */
    public static final String RE_PHONE_NUMBER = "^1[3|4|5|7|8][0-9]{9}$";

    /**
     * xxxxxx yyyy MM dd 375 0     十八位
     * xxxxxx    yy MM dd   75 0     十五位
     *
     * 地区：[1-9]\d{5}
     * 年的前两位：(18|19|([23]\d))            1800-2399
     * 年的后两位：\d{2}
     * 月份：((0[1-9])|(10|11|12))
     * 天数：(([0-2][1-9])|10|20|30|31)          闰年不能禁止29+
     *
     * 三位顺序码：\d{3}
     * 两位顺序码：\d{2}
     * 校验码：[0-9Xx]
     *
     * 十八位：^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$
     * 十五位：^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}[0-9Xx]$
     */
    public static final String RE_ID_CARD_NO = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]|[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$";

    /**
     * 验证Email地址：("^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$")
     */
    public static final String RE_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * 只能输入由数字和英文字母组成的字符串：("^[A-Za-z0-9]+$")
     */
    public static final String RE_NUM_OR_CHAR = "^[A-Za-z0-9]+$";

    /**
     * 整数或者小数：^[0-9]+([.][0-9]+){0,1}$
     */
    public static final String RE_INTEGER_OR_DECIMAL = "^[0-9]+([.][0-9]+){0,1}$";

    /**
     * 只能输入汉字："^[\u4e00-\u9fa5]{0,}$"
     */
    public static final String RE_CHINESE_CHA = "^[\\u4e00-\\u9fa5]{0,}$";

    /**
     * 验证URL：^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$
     */
    public static final String RE_URL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$";

    // ------------------------------------- //

    /**
     * 匹配双字节字符(包括汉字在内)：[^\x00-\xff]
     * 评注：可以用来计算字符串的长度（一个双字节字符长度计2，ASCII字符计1）
     */
    public static final String MA_DOUBLE_BYTE = "[^\\x00-\\xff]";

    /**
     * 用来匹配大多数年月日信息
     * 例:
     * 1998年02月02日
     * 1998-02-02
     * 1998.02.02
     * 1998/02/02
     * 1998/2/2
     */
    public static final String MA_DATE = "(?:19|20)\\d{2}[年|\\-|\\.|\\/](?:0?[1-9]|1[0-2])[月|\\-|\\.|\\/](?:0?[1-9]|[1|2][0-9]|3[0|1])日?";

    /**
     * 匹配空白行的正则表达式：linux: ^\s*\r windows: ^\s*\r\n
     * 评注：可以用来删除空白行
     */
    public static final String MA_WHITE_LINES = "^\\s*\\n";

    /**
     * 匹配腾讯QQ号：[1-9][0-9]{4,}
     * 评注：腾讯QQ号从10 000 开始
     */
    public static final String MA_TENCENT_QQ = "[1-9][0-9]{4,}";

    /**
     * 匹配中国邮政编码：[1-9]\d{5}(?!\d)
     * 评注：中国邮政编码为6位数字
     */
    public static final String MA_ZIP_CODE = "[1-9]\\d{5}(?!\\d)";

    /**
     * 匹配ip地址：(2[0-5]{2}|[0-1]?\d{1,2})(\.(2[0-5]{2}|[0-1]?\d{1,2})){3}
     * 评注：提取ip地址时有用
     */
    public static final String MA_IP_ADDRESS = "(2[0-5]{2}|[0-1]?\\d{1,2})(\\.(2[0-5]{2}|[0-1]?\\d{1,2})){3}";

    /**
     * 匹配MAC地址：([A-Fa-f0-9]{2}\:){5}[A-Fa-f0-9]{2}
     */
    public static final String MA_MAC_ADDRESS = "([A-Fa-f0-9]{2}\\:){5}[A-Fa-f0-9]{2}";

    /**
     * Replaces the first subsequence of the input sequence that matches the
     * pattern with the given replacement string.
     */
    public static String replaceFirst(String content, String replaceStr, String regular) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(regular)) {
            return content;
        }
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.replaceFirst(replaceStr);
        }
        return content;
    }

    /**
     * Replaces every subsequence of the input sequence that matches the
     * pattern with the given replacement string.
     */
    public static String replaceAll(String content, String replaceStr, String regular) {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(regular)) {
            return content;
        }
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.replaceAll(replaceStr);
        }
        return content;
    }

}
