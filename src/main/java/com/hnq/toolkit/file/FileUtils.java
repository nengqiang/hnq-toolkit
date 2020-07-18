package com.hnq.toolkit.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author henengqiang
 * @date 2018/10/15
 */
@Slf4j
public class FileUtils {
    
    private FileUtils() {}

    /**
     * 获取的文件全路径形为：
     * /Users/hanif/studyProjects/test/words-game/target/classes/temp.json
     * 【注意这个方法获取到的资源文件路径是在target目录下】
     */
    public static String getResourceFilePath(Class<?> clazz, String fileName) {
        return clazz.getResource("/" + fileName).getPath();
    }

    /**
     * like {@link FileUtils#getResourceFilePath(Class, String)}
     */
    public static String getResourceFilePath(String fileName) {
        return getResourceFileUrl(fileName).getPath();
    }

    /**
     * 根据 resources 目录下文件名称返回文件 URL
     *
     * @param fileName  文件名称，如果在resources目录的子目录下，需要包含子目录名称
     * @return          {@link URL}
     */
    public static URL getResourceFileUrl(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResource(fileName);
    }

    /**
     * 根据 resources 目录下文件名称返回文件输入流
     *
     * @param fileName  文件名称，如果在resources目录的子目录下，需要包含子目录名称
     * @return          {@link java.io.BufferedInputStream}
     * @see              org.apache.commons.io.FileUtils#readFileToString(File, Charset)
     */
    public static InputStream getResourceFileStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
    }

    /**
     * 根据 resources 目录下文件名称读取文件内容
     * 自己重写一个方法免得每次读取 resources 目录下的文件都要使用两个工具类里的方法
     *
     * @param fileName      文件名称，如果在resources目录的子目录下，需要包含子目录名称
     * @param encoding      文件编码
     * @return              文件内容
     * @throws IOException  throws {@link IOException}
     */
    public static String readResourceFileToString(String fileName, Charset encoding) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToString(
                new File(getResourceFilePath(fileName)), encoding);
    }

    public static byte[] readResourceFileToByteArray(String fileName) throws IOException {
        return org.apache.commons.io.FileUtils.readFileToByteArray(
                new File(getResourceFilePath(fileName)));
    }

    public static void listAllFileNames(String path, List<String> listFileNames) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null) {
            String[] completeNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                completeNames[i] = path + names[i];
            }
            listFileNames.addAll(Arrays.asList(completeNames));
        }
        for (File f : Objects.requireNonNull(files)) {
            if (f.isDirectory()) {
                listAllFileNames(f.getAbsolutePath() + File.separator, listFileNames);
            }
        }
    }

    /**
     * 把对象写入文件
     * 不知道怎么加编码 后面再更新
     */
    public static void writeObjectToFile(File file, Object obj) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            log.error("error:", e);
        }
    }

    /**
     * 读取文件内容为对象
     *
     * @param file 文件路径及其名称
     * @return     报异常时返回null
     * @see         org.apache.commons.io.FileUtils#readFileToByteArray(File)
     */
    public static Object readFileToObject(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            log.error("error:", e);
        }
        return null;
    }

    /**
     * 计算文件行数(多为内容以行为单位的文件)
     *
     * @param filePath      file path and itself
     * @throws IOException  throws {@link IOException}
     */
    public static Long countFileRows(String filePath) throws IOException {
        FileReader read = new FileReader(filePath);
        long rowNum;
        try (BufferedReader br = new BufferedReader(read)) {
            rowNum = 1L;
            while ((br.readLine()) != null) {
                rowNum++;
            }
        }
        return rowNum;
    }

    /**
     * 读取文件指定多少行(多为内容以行为单位的文件)
     *
     * @param filePath      文件路径
     * @param startLine     开始行数
     * @param endLine       结束行数
     * @return              返回读取的文件内容
     * @throws IOException  throws {@link IOException}
     * @see                 org.apache.commons.io.FileUtils#readLines(File, Charset)
     */
    public static String readFileOfLines(String filePath, int startLine, int endLine) throws IOException {
        FileReader read = new FileReader(filePath);
        StringBuilder sb;
        try (BufferedReader br = new BufferedReader(read)) {
            sb = new StringBuilder();
            long rowNum = 1L;
            String a;
            while ((a = br.readLine()) != null) {
                if (rowNum++ >= startLine) {
                    sb.append(a).append("\n");
                }
                if (rowNum > endLine) {
                    break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 把一个文件(目前支持CSV)拆分为多个文件(按行拆)
     *
     * @param filePath          源文件
     * @param targetFilePath    目标文件目录及其名称（不含后缀）
     * @param targetFileSuffix  目标文件后缀（不含"."）
     * @param targetFileRowNum  目标文件数据行数
     * @throws IOException      throws {@link IOException}
     */
    public static void breakFile(String filePath, String targetFilePath, String targetFileSuffix, int targetFileRowNum)
            throws IOException {
        FileReader read = new FileReader(filePath);
        FileWriter fw;
        try (BufferedReader br = new BufferedReader(read)) {
            String row;
            int rowNum = 0;
            int fileNo = 1;
            fw = new FileWriter(targetFilePath + fileNo + "." + targetFileSuffix);
            while ((row = br.readLine()) != null) {
                rowNum++;
                fw.append(row).append("\r\n");
                if (rowNum >= targetFileRowNum) {
                    fw.close();
                    rowNum = 0;
                    fileNo++;
                    fw = new FileWriter(targetFilePath + fileNo + "." + targetFileSuffix);
                }
            }
        }
        fw.close();
    }

    public static URL getResource(String resource, Class<?> clazz) {
        if (clazz == null) {
            clazz = FileUtils.class;
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

        classLoader = FileUtils.class.getClassLoader();
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

    public static String getContent(String resource, Class<?> clazz) {
        URL url = getResource(resource, clazz);
        return getStringRes(url);
    }

    public static String getContent(String resource) {
        return getContent(resource, null);
    }

    private static String getStringRes(URL url) {
        String result = null;
        if (url != null) {
            try (InputStream ins = url.openStream()) {
                result = IOUtils.toString(ins, Charset.defaultCharset());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
