package com.hnq.toolkit.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author henengqiang
 * @date 2019/09/06
 */
class MathUtilsTest {

    @Test
    void pow() {
        long x = 2;
        int n = 4;
        long result = MathUtils.pow(x, n);
        Assertions.assertEquals(16, result);
        n = 10;
        result = MathUtils.pow(x, n);
        Assertions.assertEquals(1024, result);
    }

}