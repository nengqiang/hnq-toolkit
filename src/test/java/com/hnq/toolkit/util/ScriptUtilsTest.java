package com.hnq.toolkit.util;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;

/**
 * @author henengqiang
 * @date 2019/10/21
 */
class ScriptUtilsTest {

    @Test
    void getTokenTest() throws Exception {
        Stopwatch watch = Stopwatch.createStarted();
        String tokenJs = ScriptUtils.getTokenJs();
        String res = ScriptUtils.invokeFunction(tokenJs, "getToken", "aaaaaa");
        System.out.println(res);
        System.out.println(watch.toString());
        watch.reset();

        tokenJs = ScriptUtils.getTokenJs();
        res = ScriptUtils.invokeFunction(tokenJs, "getToken", "bbb");
        System.out.println(res);
        System.out.println(watch.toString());
        watch.reset();
        tokenJs = ScriptUtils.getTokenJs();
        res = ScriptUtils.invokeFunction(tokenJs, "getToken", "ccc");
        System.out.println(res);
        System.out.println(watch.toString());
        watch.reset();

        tokenJs = ScriptUtils.getTokenJs2();
        res = ScriptUtils.invokeFunction(tokenJs, "getToken", "ddd");
        System.out.println(res);
        System.out.println(watch.toString());
        watch.reset();

        tokenJs = ScriptUtils.getTokenJs2();
        res = ScriptUtils.invokeFunction(tokenJs, "getToken", "ddd");
        System.out.println(res);
        System.out.println(watch.toString());
    }
}