package com.hnq.toolkit.util;

import com.hnq.toolkit.tool.SnowflakeKeyGenerator;
import org.junit.jupiter.api.Test;

/**
 * @author henengqiang
 * @date 2020/3/13
 */
class SnowflakeKeyGeneratorTest {

    private static SnowflakeKeyGenerator generator = new SnowflakeKeyGenerator();

    @Test
    void generateKey() {
        System.out.println(generator.generateKey());
        System.out.println(generator.generateKey());
        System.out.println(generator.generateKey());
    }
}