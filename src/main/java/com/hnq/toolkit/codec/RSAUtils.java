package com.hnq.toolkit.codec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * RSA是一种非对称加密算法。<br>
 * 使用RSA一般需要产生公钥和私钥，当采用公钥加密时，使用私钥解密；采用私钥加密时，使用公钥解密。
 *
 * @author henengqiang
 * @date 2021/01/12
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class RSAUtils {

    private RSAUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 非对称加密算法
     */
    public static final String KEY_ALGORITHM_RSA = "RSA";

    /**
     * 公钥
     */
    private static final String RSA_PUBLIC_KEY = "RSAPublicKey";

    /**
     * 私钥
     */
    private static final String RSA_PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 公钥类型
     */
    private static final int PUBLIC_TYPE = 1;

    /**
     * 私钥类型
     */
    private static final int PRIVATE_TYPE = 2;

    /**
     * RSA密钥长度，默认1024位
     * 密钥长度必须是64的倍数，范围在512~65536位之间
     */
    private static final int KEY_SIZE = 1024;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 公钥加密
     *
     * @param data          待加密数据
     * @param key           公钥
     * @return              byte[] 加密数据
     * @throws Exception    many exceptions
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, PUBLIC_TYPE);
    }

    /**
     * 公钥解密
     *
     * @param data          待解密数据
     * @param key           公钥
     * @return              byte[] 解密数据
     * @throws Exception    many exceptions
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        // 生成公钥
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data          待解密数据
     * @param key           私钥
     * @return              byte[] 解密数据
     * @throws Exception    many exceptions
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(key);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        // 生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        int blockSize = cipher.getBlockSize();
        if (blockSize > 0) {
            ByteArrayOutputStream out = new ByteArrayOutputStream(64);
            int j = 0;
            while (data.length - j * blockSize > 0) {
                out.write(cipher.doFinal(data, j * blockSize, blockSize));
                j++;
            }
            return out.toByteArray();
        }
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data          待加密数据
     * @param key           私钥
     * @return              byte[] 加密数据
     * @throws Exception    many exceptions
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        return encrypt(data, key, PRIVATE_TYPE);
    }

    /**
     * 取得公钥
     *
     * @param keyMap    密钥Map
     * @return          key 公钥
     */
    public static String getPublicKey(Map<String, Key> keyMap) {
        return Hex.toHexString(getPublicKeyByte(keyMap)).toUpperCase();
    }

    /**
     * 取得公钥
     *
     * @param keyMap    密钥Map
     * @return          byte[] 公钥
     */
    public static byte[] getPublicKeyByte(Map<String, Key> keyMap) {
        return keyMap.get(RSA_PUBLIC_KEY).getEncoded();
    }

    /**
     * 取得私钥
     *
     * @param keyMap        密钥Map
     * @return              key 私钥
     */
    public static String getPrivateKey(Map<String, Key> keyMap) {
        return Hex.toHexString(getPrivateKeyByte(keyMap)).toUpperCase();
    }

    /**
     * 取得私钥
     *
     * @param keyMap        密钥Map
     * @return              byte[] 私钥
     */
    public static byte[] getPrivateKeyByte(Map<String, Key> keyMap) {
        return keyMap.get(RSA_PRIVATE_KEY).getEncoded();
    }

    /**
     * 初始化密钥，以uuidStr作为seed
     *
     * @return              Map 密钥Map
     * @throws Exception    an exception
     */
    public static Map<String, Key> initKey() throws Exception {
        return initKey(UUID.randomUUID().toString());
    }


    /**
     * 初始化密钥
     * @param seed          种子
     * @return              Map 密钥Map
     * @throws Exception    an exception
     */
    public static Map<String,Key> initKey(String seed)throws Exception  {
        return initKey(seed.getBytes());
    }

    /**
     * 初始化密钥
     *
     * @param seed          byte[] 种子
     * @return Map          密钥Map
     * @throws Exception    an exception
     */
    public static Map<String, Key> initKey(byte[] seed)throws Exception{
        // 实例化密钥对生成器
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
        // 初始化密钥对生成器
        keyPairGen.initialize(KEY_SIZE,	new SecureRandom(seed));
        // 生成密钥对
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 封装密钥
        Map<String, Key> keyMap = new HashMap<>(3);
        keyMap.put(RSA_PUBLIC_KEY, publicKey);
        keyMap.put(RSA_PRIVATE_KEY, privateKey);
        return keyMap;
    }

    private static byte[] encrypt(byte[] data, byte[] key, int type) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        if (PUBLIC_TYPE == type) {
            // 获取公钥
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(publicKeySpec));
        }
        if (PRIVATE_TYPE == type) {
            // 取得私钥
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(key);
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePrivate(privateKeySpec));
        }
        // 对数据加密
        int blockSize = cipher.getBlockSize();
        if (blockSize > 0) {
            int outputSize = cipher.getOutputSize(data.length);
            int leaveSize = data.length % blockSize;
            int blocksSize = leaveSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blockSize];
            int i = 0, remainSize;
            while ((remainSize = data.length - i * blocksSize) > 0) {
                int inputLen = Math.min(remainSize, blockSize);
                cipher.doFinal(data, i * blockSize, inputLen, raw, i * outputSize);
                i++;
            }
            return raw;
        }
        return cipher.doFinal(data);
    }



}
