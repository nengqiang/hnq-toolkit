package com.hnq.toolkit.codec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author henengqiang
 * @date 2021/01/11
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class MD5Utils {

    private MD5Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * md5加密
     *
     * @param data  待加密数据
     * @return      byte[] 消息摘要
     */
    public static byte[] encodeMd5(String data) {
        return DigestUtils.md5(data);
    }

    /**
     * md5加密
     *
     * @param data  待加密数据
     * @return      消息摘要  returns the value as a 32 character hex string
     */
    public static String encodeMd5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

}
