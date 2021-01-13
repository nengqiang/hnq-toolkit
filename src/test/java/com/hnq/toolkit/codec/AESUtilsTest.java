package com.hnq.toolkit.codec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author henengqiang
 * @date 2021/01/11
 */
class AESUtilsTest {

    private static final String DATA = "This is the data to be encrypt.";

    private static final String ENCRYPTED_DATA = "0AEBD800DD94A6EFBCD8465BA6B9F449C3BFCF6B5747CCCB8FC5FB71ACC9107F";

    private static final String KEY = "1234%^&*asdf()_+";

    @Test
    void testEncrypt() {
        try {
            assertEquals("0AEBD800DD94A6EFBCD8465BA6B9F449C3BFCF6B5747CCCB8FC5FB71ACC9107F",
                    AESUtils.encrypt(DATA, KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDecrypt() {
        try {
            assertEquals(DATA, AESUtils.decrypt(ENCRYPTED_DATA, KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}