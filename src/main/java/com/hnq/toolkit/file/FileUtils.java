package com.hnq.toolkit.file;

import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author henengqiang
 * @date 2018/10/15
 */
@Slf4j
public class FileUtils {
    
    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取的文件全路径形为：
     * /Users/hanif/studyProjects/test/words-game/target/classes/temp.json
     * 【注意这个方法获取到的资源文件路径是在target目录下】
     */
    public static String getResourceFilePath(Class<?> clazz, String fileName) {
        return Objects.requireNonNull(clazz.getResource("/" + fileName)).getPath();
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
     * 计算文件行数(多为内容以行为单位的文件)
     *
     * @param filePath      file path and itself
     * @throws IOException  throws {@link IOException}
     * @see <a href="https://blog.csdn.net/liuxiao723846/article/details/93604614">JAVA快速统计大文本文件行数</a>
     */
    public static Long lines(String filePath) throws IOException {
        Path path = Paths.get(new File(filePath).getPath());
        return Files.lines(path).count();
    }

    /**
     * 读取文件指定多少行(多为内容以行为单位的文件)
     *
     * @param filePath          文件路径
     * @param startIncluded     开始行数，包含
     * @param endExcluded       结束行数，不包含
     * @return                  返回读取的文件内容为List
     * @throws IOException      throws {@link IOException}
     */
    public static List<String> readLinesSpecified(String filePath, long startIncluded, long endExcluded) throws IOException {
        Preconditions.checkArgument(StringUtils.isNotBlank(filePath),
                "filePath must be not blank.");
        Preconditions.checkArgument(startIncluded > 0,
                "startIncluded must be greater than 0.");
        Preconditions.checkArgument(startIncluded < endExcluded,
                "startIncluded must be smaller than endExcluded.");
        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(new File(filePath).getPath()), StandardCharsets.UTF_8)) {
            return br.lines()
                    .skip(startIncluded - 1)
                    .limit(endExcluded - startIncluded)
                    .collect(Collectors.toList());
        }
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
