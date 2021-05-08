package com.hnq.toolkit.codec;

import com.google.common.base.Preconditions;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author henengqiang
 * @date 2021/01/11
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class AESUtils {

    private AESUtils() {
        throw new IllegalStateException("Utility class");
    }

    private static final String KEY_AES = "AES";

    public static final int STANDARD_KEY_LENGTH = 16;

    /**
     * AES 加密
     *
     * @param src           待加密数据
     * @param key           加密密钥
     * @return              加密数据
     * @throws Exception    many exceptions
     */
    public static String encrypt(String src, String key) throws Exception {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(key.length() == STANDARD_KEY_LENGTH,
                "key's length not equal to %s", STANDARD_KEY_LENGTH);
        byte[] raw = key.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(src.getBytes());
        return byteToHex(encrypted);
    }

    /**
     * AES 解密
     *
     * @param src           待解密数据
     * @param key           解密密钥
     * @return              解密后的数据
     * @throws Exception    many exceptions
     */
    public static String decrypt(String src, String key) throws Exception {
        Preconditions.checkNotNull(key);
        Preconditions.checkArgument(key.length() == STANDARD_KEY_LENGTH,
                "key's length not equal to %s", STANDARD_KEY_LENGTH);
        byte[] raw = key.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(raw, KEY_AES);
        Cipher cipher = Cipher.getInstance(KEY_AES);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encrypted = hexToByte(src);
        byte[] original = cipher.doFinal(encrypted);
        return new String(original);
    }

    private static byte[] hexToByte(String hexStr) {
        if (hexStr == null) {
            return null;
        }
        int len = hexStr.length();
        if (len % 2 == 1) {
            return null;
        }
        byte[] b = new byte[len / 2];
        for (int i = 0; i != len / 2 ; i++) {
            b[i] = (byte) Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), STANDARD_KEY_LENGTH);
        }
        return b;
    }

    private static String byteToHex(byte[] b) {
        StringBuilder str = new StringBuilder();
        String tmp;
        for (byte value : b) {
            tmp = Integer.toHexString(value & 0XFF);
            if (tmp.length() == 1) {
                str.append("0").append(tmp);
            } else {
                str.append(tmp);
            }
        }
        return str.toString().toUpperCase();
    }

}
