package com.hnq.toolkit.codec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.*;

/**
 * @author henengqiang
 * @date 2021/01/12
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class DESUtils {

    private DESUtils() {}

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "DES";

    public static final int BIT_56 = 56;

    public static final int BIT_64 = 64;

    /**
     * 加密、解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5PADDING";

    /**
     * 生成56bit密钥
     *
     * @return                          byte[] 二进制密钥
     * @throws NoSuchAlgorithmException an exception
     */
    public static byte[] initKey56() throws NoSuchAlgorithmException, NoSuchProviderException {
        return initKey(BIT_56);
    }

    /**
     * 生成64bit密钥<br>
     * Bouncy Castle 支持
     *
     * @return                          byte[] 二进制密钥
     * @throws NoSuchProviderException  an exception
     * @throws NoSuchAlgorithmException an exception
     */
    public static byte[] initKey64() throws NoSuchProviderException, NoSuchAlgorithmException {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
        return initKey(BIT_64);
    }

    /**
     * 生成密钥
     *
     * @param seed                          钥种
     * @return                              byte[] 密钥
     * @throws NoSuchAlgorithmException     an exception
     */
    public static byte[] initKey(String seed) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = new SecureRandom(new Base64().decode(seed));
        kg.init(secureRandom);
        return kg.generateKey().getEncoded();
    }

    /**
     * DES 加密
     *
     * @param data          待加密数据
     * @param key           加密密钥
     * @return              byte[] 加密数据
     * @throws Exception    many exceptions
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        return process(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * DES 解密
     *
     * @param data          待解密数据
     * @param key           解密密钥
     * @return              byte[] 解密数据
     * @throws Exception    many exceptions
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        return process(data, key, Cipher.DECRYPT_MODE);
    }

    private static byte[] process(byte[] data, byte[] key, int mode) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 初始化，设置加解密模式
        cipher.init(mode, k);
        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 转换密钥
     * @param key           二进制密钥
     * @return              密钥
     * @throws Exception    InvalidKeyException，NoSuchAlgorithmException，InvalidKeySpecException
     */
    private static Key toKey(byte[] key) throws Exception {
        // 实例化DES密钥材料
        DESKeySpec dks = new DESKeySpec(key);
        // 实例化秘密密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 生成秘密密钥
        return keyFactory.generateSecret(dks);
    }

    private static byte[] initKey(int bit) throws NoSuchProviderException, NoSuchAlgorithmException {
        // 实例化密钥生成器
        KeyGenerator kg = BIT_64 == bit
                ? KeyGenerator.getInstance(KEY_ALGORITHM, "BC")
                : KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器
        kg.init(bit, new SecureRandom());
        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();
        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }
}
