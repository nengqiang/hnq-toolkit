package com.hnq.toolkit.codec;

import org.apache.commons.codec.binary.Base64;

/**
 * @author henengqiang
 * @date 2021/01/11
 */
public class Base64Utils {

    private Base64Utils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * base64编码，默认UTF-8
     *
     * @param data  待编码数据
     * @return      编码结果
     */
    public static String encode(String data) {
        byte[] b = Base64.encodeBase64(data.getBytes());
        return new String(b);
    }

    /**
     * base64安全编码，默认UTF-8，遵循 RFC 2045 实现
     *
     * @param data  待编码数据
     * @return      编码结果
     */
    public static String encodeSafe(String data) {
        byte[] b = Base64.encodeBase64(data.getBytes(), true);
        return new String(b);
    }

    /**
     * base64解码，默认UTF-8
     *
     * @param data  待解码数据
     * @return      解码结果
     */
    public static String decode(String data) {
        byte[] b = Base64.decodeBase64(data.getBytes());
        return new String(b);
    }

}
