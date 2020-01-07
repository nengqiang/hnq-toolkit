package com.hnq.toolkit.util;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author henengqiang
 * @date 2019/09/19
 */
public class ResourceUtils {

    private ResourceUtils() {}

    public static URL getResource(String resource, Class clazz) {
        if (clazz == null) {
            clazz = ResourceUtils.class;
        }

        URL url = clazz.getResource(resource);
        if (url != null) {
            return url;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(resource);
            if (url != null) {
                return url;
            }
        }

        classLoader = ResourceUtils.class.getClassLoader();
        if (classLoader != null) {
            url = classLoader.getResource(resource);
            if (url != null) {
                return url;
            }
        }

        return ClassLoader.getSystemResource(resource);
    }

    public static String getContent(URL url) {
        return getStringRes(url);
    }

    public static String getContent(String resource, Class clazz) {
        URL url = getResource(resource, clazz);
        return getStringRes(url);
    }

    public static String getContent(String resource) {
        return getContent(resource, null);
    }

    private static String getStringRes(URL url) {
        String result = null;
        if (url != null) {
            InputStream ins = null;

            try {
                ins = url.openStream();
                result = IOUtils.toString(ins, Charset.defaultCharset());
            } catch (Exception var7) {
                var7.printStackTrace();
            } finally {
                IOUtils.closeQuietly(ins);
            }

        }
        return result;
    }

}
