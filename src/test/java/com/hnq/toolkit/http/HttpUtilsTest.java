package com.hnq.toolkit.http;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.hnq.toolkit.file.FileUtils;

/**
 * @author henengqiang
 * @date 2019/06/26
 */
class HttpUtilsTest {

    private void writeResponseToTempHtml(String response) {
        File file = new File(FileUtils.getResourceFilePath(this.getClass(), "res.html"));
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, response, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}