package com.hnq.toolkit.util;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.hnq.toolkit.util.FileUtils.countFileRows;

/**
 * @author henengqiang
 * @date 2019/06/25
 */
class FileUtilsTest {

    private static final String S = File.separator;

    private static final String FILE_PATH = ("src" + S + "main" + S + "java" + S + "com" + S + "hnq" + S + "toolkit" + S + "util" + S + "FileUtils.java");

    @Test
    void countFileRowsTest() {
        try {
            Long rows = countFileRows(FILE_PATH);
            System.out.println(rows);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readFileOfLinesTest() {
        try {
            String data = FileUtils.readFileOfLines(FILE_PATH, 2, 100);
            System.out.println(data);
            System.out.println(data.replaceAll("\n", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}