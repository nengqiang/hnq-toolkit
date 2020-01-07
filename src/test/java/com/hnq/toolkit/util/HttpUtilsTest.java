package com.hnq.toolkit.util;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static com.hnq.toolkit.util.HttpUtils.*;

/**
 * @author henengqiang
 * @date 2019/06/26
 */
class HttpUtilsTest {

    @Test
    void getProxy() {
        System.out.println(getProxyStr());
    }

    @Test
    void noParamGetTest() {
        String url = "http://www.baidu.com";
        String response = doGet(url);
        writeResponseToTempHtml(response);
    }

    @Test
    void paramGetTest() {
        String url = "http://www.baidu.com/s";
        Map<String, String> params = Maps.newHashMap();
        params.put("itype", "web");
        params.put("ie", "utf-8");
        params.put("wd", "HttpClient");
        String response = doGet(url, params);
        writeResponseToTempHtml(response);
    }

    @Test
    void noParamPostTest() {
        String url = "https://www.oschina.net";
        String response = doPost(url);
        writeResponseToTempHtml(response);
    }

    @Test
    void paramPostTest() {
        String url = "https://www.oschina.net";
        Map<String, String> params = Maps.newHashMap();
        params.put("scope", "project");
        params.put("q", "java");
        String response = doPost(url, params);
        writeResponseToTempHtml(response);
    }

    private void writeResponseToTempHtml(String response) {
        File file = new File(FileUtils.getResourceFilePath(this.getClass(), "temp.html"));
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, response, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}