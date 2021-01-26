package com.hnq.toolkit.codec;

import com.google.common.collect.Maps;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import java.security.Key;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author henengqiang
 * @date 2021/01/13
 */
class RSAUtilsTest {

    private static final String DATA = "This is the data to be encrypt.";

    private static final String SEED = "seed";

    private static Map<String, Key> keyMap = Maps.newHashMap();

    @Test
    void test() {
        try {
            System.out.println(Arrays.toString(RSAUtils.encryptByPublicKey(DATA.getBytes(), Hex.decodeHex("30819f300d06092a864886f70d010101050003818d00308189028181008789f7f57a65043f76112ec95268a3584477398d984c2629e9c24af6c7699c985e0ceacb02f3acc11f6d0630e594c459a6146f0209969d84a4162f0c0e64f6da19837228f45f94fca435b20423fa20382c65548e973723921381a63798f86d74aecc68010d437c2000d71914d84427fcfe9b8bdc1988ab64942944ae93d4129f0203010001"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetPairKey() {
        try {
            keyMap = RSAUtils.initKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("publicKey:" + RSAUtils.getPublicKey(keyMap));
        System.out.println("privateKey:" + RSAUtils.getPrivateKey(keyMap));
    }

    @Test
    void testEncryptByPublicKeyDecryptByPrivateKey() {
        testInitKey1();
        try {
            byte[] encryptData = RSAUtils.encryptByPublicKey(DATA.getBytes(), RSAUtils.getPublicKeyByte(keyMap));
            byte[] decryptData = RSAUtils.decryptByPrivateKey(encryptData, RSAUtils.getPrivateKeyByte(keyMap));
            assertEquals(DATA, new String(decryptData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEncryptByPrivateKeyDecryptByPublicKey() {
        testInitKey2();
        try {
            byte[] encryptData = RSAUtils.encryptByPrivateKey(DATA.getBytes(), RSAUtils.getPrivateKeyByte(keyMap));
            byte[] decryptData = RSAUtils.decryptByPublicKey(encryptData, RSAUtils.getPublicKeyByte(keyMap));
            assertEquals(DATA, new String(decryptData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetPublicKey() {
        testInitKey1();
        System.out.println(RSAUtils.getPublicKey(keyMap));
    }

    @Test
    void testGetPublicKeyByte() {
        testInitKey2();
        System.out.println(Arrays.toString(RSAUtils.getPublicKeyByte(keyMap)));
    }

    @Test
    void testGetPrivateKey() {
        testInitKey3();
        System.out.println(RSAUtils.getPrivateKey(keyMap));
    }

    @Test
    void testGetPrivateKeyByte() {
        testInitKey1();
        System.out.println(Arrays.toString(RSAUtils.getPrivateKeyByte(keyMap)));
    }

    @Test
    void testInitKey1() {
        try {
            keyMap = RSAUtils.initKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInitKey2() {
        try {
            keyMap = RSAUtils.initKey(SEED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInitKey3() {
        try {
            keyMap = RSAUtils.initKey(SEED.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}