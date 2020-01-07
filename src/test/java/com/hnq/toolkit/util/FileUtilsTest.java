package com.hnq.toolkit.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.hnq.toolkit.util.FileUtils.countFileRows;

/**
 * @author henengqiang
 * @date 2019/06/25
 */
class FileUtilsTest {

    @Test
    void countFileRowsTest() {
        try {
            String filePath = ("/Users/hanif/studyProjects/test/common/src/main/java/com/hnq/test/util/FileUtils.java");
            Long rows = countFileRows(filePath);
            System.out.println(rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readFileOfLinesTest() {
        try {
            String filePath = "/Users/hanif/studyProjects/test/crawler/src/main/resources/toutiao/userIds.csv";
            String data = FileUtils.readFileOfLines(filePath, 2, 5);
            System.out.println(data);
            System.out.println(data.replaceAll("\n", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}