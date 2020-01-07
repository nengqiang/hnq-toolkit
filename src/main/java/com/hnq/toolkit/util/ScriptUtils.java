package com.hnq.toolkit.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 加锁是用于多线程的
 *
 * @author henengqiang
 * @date 2019/10/21
 */
@Slf4j
public class ScriptUtils {

    private static String tokenJs;

    private static String tokenJs2;

    private ScriptUtils() {}

    public static String getTokenJs() throws Exception {
        if (tokenJs == null) {
            synchronized (ScriptUtils.class) {
                if (tokenJs == null) {
                    try {
                        // getContent性能很垃圾 酌情使用
                        String javaScript = ResourceUtils.getContent("js/token.js");
                        if (StringUtils.isEmpty(javaScript)) {
                            throw new IllegalArgumentException("File \"js/token.js\" not found!");
                        }
                        tokenJs = javaScript.replaceAll("\\s*\n+\\s*", "");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return tokenJs;
    }

    public static String getTokenJs2() throws IOException {
        if (tokenJs2 == null) {
            synchronized (ScriptUtils.class) {
                if (tokenJs2 == null) {
                    try (InputStream inputStream = ScriptUtils.class.getClassLoader().getResourceAsStream("js/token.js")) {
                        if (inputStream == null) {
                            throw new IllegalArgumentException("File \"js/token.js\" not found!");
                        }
                        String javaScript = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                        tokenJs2 = javaScript.replaceAll("\\s*\n+\\s*", "");
                    }
                }
            }
        }
        return tokenJs2;
    }

    public static String invokeFunction(String scriptJs, String functionName, String ...args) {
        try {
            ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
            scriptEngine.eval(scriptJs);
            return ((Invocable)scriptEngine).invokeFunction(functionName, (Object) args).toString();
        } catch (Exception e) {
            log.error("执行token计算的javascript脚本出现错误, str={}", args, e);
        }
        return StringUtils.EMPTY;
    }

}
