package com.hnq.toolkit.util;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author henengqiang
 * @date 2018/10/15
 */
public class FileUtils {

    /**
     * 获取的文件全路径形为：
     * /Users/hanif/studyProjects/test/words-game/target/classes/temp.json
     * 【注意这个方法获取到的资源文件路径是在target目录下】
     */
    public static String getResourceFilePath(Class clazz, String fileName) {
        return clazz.getResource("/" + fileName).getPath();
    }

    public static void getAllFileName(String path, List<String> listFileName) {
        File file = new File(path);
        File[] files = file.listFiles();
        String[] names = file.list();
        if (names != null) {
            String[] completeNames = new String[names.length];
            for (int i = 0; i < names.length; i++) {
                completeNames[i] = path + names[i];
            }
            listFileName.addAll(Arrays.asList(completeNames));
        }
        for (File f : Objects.requireNonNull(files)) {
            if (f.isDirectory()) {
                getAllFileName(f.getAbsolutePath() + File.separator, listFileName);
            }
        }
    }

    /**
     * 图片转换为二进制
     *
     * @param fileName
     *            本地图片路径
     * @return
     *       图片二进制流
     * */
    public static String getImageBinary(String fileName) {
        File f = new File(fileName);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return Base64.encodeBase64String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换为图片
     *
     * @param base64String
     *            图片二进制流
     * // TODO: 2019-07-08 henengqiang 待改
     */
    public static void saveImage(String base64String) {
        try {
            byte[] bytes1 = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            // 可以是jpg,png,gif格式
            File w2 = new File("D://code//22.jpg");
            // 不管输出什么格式图片，此处不需改动
            ImageIO.write(bi1, "jpg", w2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 把对象写入文件
     * 不知道怎么加编码 后面再更新
     */
    public static void writeObjectToFile(File file, Object obj) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取文件内容为对象
     * @param file 文件路径及其名称
     * @return     报异常时返回null
     */
    public static Object readFileToObject(File file) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算文件行数
     *
     * @param filePath  file path and itself
     * @throws IOException
     */
    public static Long countFileRows(String filePath) throws IOException {
        FileReader read = new FileReader(filePath);
        BufferedReader br = new BufferedReader(read);
        long rowNum = 1L;
        while ((br.readLine()) != null) {
            rowNum++;
        }
        return rowNum;
    }

    /**
     * 读取文件指定多少行
     * @param filePath
     * @param startLine
     * @param endLine
     * @return
     * @throws IOException
     */
    public static String readFileOfLines(String filePath, int startLine, int endLine) throws IOException {
        FileReader read = new FileReader(filePath);
        BufferedReader br = new BufferedReader(read);
        StringBuilder sb = new StringBuilder();
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
        return sb.toString();
    }

    /**
     * 把一个文件拆分为多个文件(按行拆)
     *
     * @param filePath          源文件
     * @param targetFilePath    目标文件目录及其名称（不含后缀）
     * @param targetFileSuffix  目标文件后缀（不含"."）
     * @param targetFileRowNum  目标文件数据行数
     * @throws IOException
     */
    public static void breakFile(String filePath, String targetFilePath, String targetFileSuffix, int targetFileRowNum)
            throws IOException {
        FileReader read = new FileReader(filePath);
        BufferedReader br = new BufferedReader(read);
        String row;
        int rowNum = 0;
        int fileNo = 1;
        FileWriter fw = new FileWriter(targetFilePath + fileNo + "." + targetFileSuffix);
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
        fw.close();
    }

}
