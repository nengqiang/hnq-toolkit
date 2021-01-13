package com.hnq.toolkit.codec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author henengqiang
 * @date 2021/01/11
 */
class Base64UtilsTest {

    private static final String DATA = "This is the data to be encode.";

    @Test
    void testEncode() {
        assertEquals("VGhpcyBpcyB0aGUgZGF0YSB0byBiZSBlbmNvZGUu", Base64Utils.encode(DATA));
    }

    @Test
    void testEncodeSafe() {
        assertEquals("VGhpcyBpcyB0aGUgZGF0YSB0byBiZSBlbmNvZGUu", Base64Utils.encodeSafe(DATA));
    }

    @Test
    void testDecode() {
        assertEquals(DATA, Base64Utils.decode("VGhpcyBpcyB0aGUgZGF0YSB0byBiZSBlbmNvZGUu"));
    }
}