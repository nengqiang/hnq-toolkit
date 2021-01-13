package com.hnq.toolkit.codec;

import com.google.common.collect.Maps;
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