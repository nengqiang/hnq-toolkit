package com.hnq.toolkit.util;

import com.alibaba.fastjson.JSONReader;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.hnq.toolkit.util.JsonUtils.splitJsonFile;

/**
 * @author henengqiang
 * @date 2019/04/03
 */
class JsonUtilsTest {


    String jsonString = "{\"array\":[1,2,3],\"arraylist\":[{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"}],\"object\":{\"a\":\"b\",\"c\":\"d\",\"e\":\"f\"},\"string\":\"HelloWorld\"}";

    private static String inputFilePath = "/Users/hanif/studyProjects/test/common/src/main/resources/temp.json";
    private static String outputFilePath = "/Users/hanif/studyProjects/test/common/src/main/resources/test.json";

    static void main(String[] args) throws IOException {
//        readBigJsonArrayListFile();
        splitJsonFile(inputFilePath, outputFilePath, 3);
    }

    private static void readBigJsonArrayListFile() throws IOException {
        String dataStr = org.apache.commons.io.FileUtils.readFileToString(new File(inputFilePath), "UTF-8");
        JSONReader reader = new JSONReader(new StringReader(dataStr));
        reader.startArray();
        int objNum = 0;
        int fileNum = 0;
        StringBuilder tempData = new StringBuilder("[");
        while (reader.hasNext()) {
            objNum++;
            reader.startObject();
            tempData.append("{");
            while (reader.hasNext()) {
                String arrayListItemKey = reader.readString();
                String arrayListItemValue = reader.readObject().toString();
                tempData.append("\"").append(arrayListItemKey).append("\"")
                        .append(":")
                        .append("\"").append(arrayListItemValue).append("\"")
                        .append(",");
            }
            reader.endObject();
            tempData.deleteCharAt(tempData.length() - 1).append("},");
            if (objNum % 5 == 0) {
                // 每5个对象保存一次
                fileNum++;
                tempData.deleteCharAt(tempData.length() - 1).append("]");
                System.out.println("写出第" + fileNum + "份json文件");
                String dealOutputFilePath = outputFilePath.split("\\.")[0] + fileNum + ".json";
                FileUtils.writeStringToFile(new File(dealOutputFilePath), tempData.toString(), StandardCharsets.UTF_8);
                tempData.delete(0, tempData.length()).append("[");
            }
        }
        reader.endArray();
    }

    private static void readBigJsonFileByCase(String filePath) {
        // 如果数据以json形式保存在文件中，用FileReader进行流读取！！
        // path为json数据文件路径！！
        // JSONReader reader = new JSONReader(new FileReader(path));

        // 为了直观，方便运行，就用StringReader做示例！
        JSONReader reader = new JSONReader(new StringReader(filePath));
        reader.startObject();
        System.out.print("start fastjson");
        while (reader.hasNext()) {
            String key = reader.readString();
            System.out.print("key " + key);
            switch (key) {
                case "array":
                    reader.startArray();
                    System.out.print("start " + key);
                    while (reader.hasNext()) {
                        String item = reader.readString();
                        System.out.print(item);
                    }
                    reader.endArray();
                    System.out.print("end " + key);
                    break;
                case "arraylist":
                    reader.startArray();
                    System.out.print("start " + key);
                    while (reader.hasNext()) {
                        reader.startObject();
                        System.out.print("start arraylist item");
                        while (reader.hasNext()) {
                            String arrayListItemKey = reader.readString();
                            String arrayListItemValue = reader.readObject().toString();
                            System.out.print("key " + arrayListItemKey);
                            System.out.print("value " + arrayListItemValue);
                        }
                        reader.endObject();
                        System.out.print("end arraylist item");
                    }
                    reader.endArray();
                    System.out.print("end " + key);
                    break;
                case "object":
                    reader.startObject();
                    System.out.print("start object item");
                    while (reader.hasNext()) {
                        String objectKey = reader.readString();
                        String objectValue = reader.readObject().toString();
                        System.out.print("key " + objectKey);
                        System.out.print("value " + objectValue);
                    }
                    reader.endObject();
                    System.out.print("end object item");
                    break;
                case "string":
                    System.out.print("start string");
                    String value = reader.readObject().toString();
                    System.out.print("value " + value);
                    System.out.print("end string");
                    break;
                default:
                    break;
            }
        }
        reader.endObject();
        System.out.print("start fastjson");
    }

    @Test
    void jsonToString() throws IOException {
        String file = "/Users/hanif/studyProjects/test/common/src/main/resources/temp.json";
        String data = FileUtils.readFileToString(new File(file), StandardCharsets.UTF_8);
        data = data.replaceAll("\\{", "")
                .replaceAll("\\s*", "")
                .replaceAll("}", "")
                .replaceAll("\"", "")
                .replaceAll(":", "=")
                .replaceAll(",\\s*\\n*", ";");
        System.out.println(data);
        file = "/Users/hanif/studyProjects/test/common/src/main/resources/temp.txt";
        String data2 = FileUtils.readFileToString(new File(file), StandardCharsets.UTF_8);
        String[] replaceData = data2.split(";");
        System.out.println(Arrays.toString(replaceData));
        data = data.replaceAll("FSSBBIl1UgzbN7N80S=[^;]*", replaceData[0])
                .replaceAll("FSSBBIl1UgzbN7N80T=[^;]*", replaceData[1])
                .replaceAll("\\s*\\n*", "");
        System.out.println("--------------------");
        System.out.println(data);
    }

}
