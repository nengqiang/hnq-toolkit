package com.hnq.toolkit.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.hnq.toolkit.util.FileUtils.countFileRows;
import static com.hnq.toolkit.util.FileUtils.getResourceFilePath;
import static com.hnq.toolkit.util.FileUtils.getResourceFileStream;
import static com.hnq.toolkit.util.FileUtils.listAllFileNames;
import static com.hnq.toolkit.util.FileUtils.readResourceFileToByteArray;
import static com.hnq.toolkit.util.FileUtils.readResourceFileToString;

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

    @Test
    void testGetResourceFilePath() {
        String actualPath1 = getResourceFilePath(FileUtils.class, "res.html");
        String actualPath2 = getResourceFilePath("res.html");
        Assertions.assertEquals(actualPath1, actualPath2);
    }

    @Test
    void testGetResourceFileStream() {
        InputStream actualIn1 = getResourceFileStream("res.html");
        InputStream actualIn2 = null;
        try {
            actualIn2 = new FileInputStream(new File(getResourceFilePath("res.html")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(actualIn1 instanceof BufferedInputStream);
        Assertions.assertNotNull(actualIn2);
    }

    @Test
    void testListAllFileNames() {
        List<String> fileNames = new ArrayList<>(16);
        listAllFileNames(getResourceFilePath("js"), fileNames);
        System.out.println(fileNames);
    }

    @Test
    void testReadResourceFileToString() {
        try {
            String content = readResourceFileToString("xml_source.xml", Charset.defaultCharset());
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadResourceFileToByteArray() {
        try {
            byte[] content = readResourceFileToByteArray("xml_source.xml");
            System.out.println(Arrays.toString(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}