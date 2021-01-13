package com.hnq.toolkit.codec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author henengqiang
 * @date 2021/01/11
 */
class MD5UtilsTest {

    private static final String DATA = "This is the data to be codec.";

    @Test
    void testEncodeMd5() {
        assertArrayEquals(
                new byte[]{93, 123, -18, -35, 5, 4, -89, 5, -51, -3, -13, -34, -82, -17, -17, -122},
                MD5Utils.encodeMd5(DATA));
    }

    @Test
    void testEncodeMd5Hex() {
        assertEquals("5d7beedd0504a705cdfdf3deaeefef86", MD5Utils.encodeMd5Hex(DATA));
    }
}