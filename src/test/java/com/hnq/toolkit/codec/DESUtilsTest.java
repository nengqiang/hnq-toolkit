package com.hnq.toolkit.codec;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author henengqiang
 * @date 2021/01/12
 */
class DESUtilsTest {

    private static final String DATA = "This is the data to be encrypt.";

    private static final String seed = "seed";

    private static final byte[] KEY = {-4, -3, -2, -1, 0, 1, 2, 3};

    private static final byte[] ENCRYPTED_DATA = {-29, 114, 1, 12, -96, -23, 61, 32, -79, -125, -1, -84, 52, 39, -37, -15, 98, -93, 88, 106, 3, 76, -44, -48, 93, -98, -6, 85, 4, -63, -95, 111};

    @Test
    void testInitKey56() {
        try {
            System.out.println(Arrays.toString(DESUtils.initKey56()));
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInitKey64() {
        try {
            System.out.println(Arrays.toString(DESUtils.initKey64()));
        } catch (NoSuchProviderException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInitKey() {
        try {
            System.out.println(Arrays.toString(DESUtils.initKey(seed)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testEncrypt() {
        try {
            assertArrayEquals(ENCRYPTED_DATA, DESUtils.encrypt(DATA.getBytes(), KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDecrypt() {
        try {
            assertArrayEquals(DATA.getBytes(), DESUtils.decrypt(ENCRYPTED_DATA, KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}