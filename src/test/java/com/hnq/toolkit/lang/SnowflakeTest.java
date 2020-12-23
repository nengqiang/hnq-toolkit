package com.hnq.toolkit.lang;

import org.junit.jupiter.api.Test;

/**
 * @author henengqiang
 * @date 2020/3/13
 */
class SnowflakeTest {

    private static final Snowflake generator = new Snowflake();

    @Test
    void generateKey() {
        System.out.println(generator.generateKey());
        System.out.println(generator.generateKey());
        System.out.println(generator.generateKey());
    }
}