package com.hnq.toolkit.util;

import com.alibaba.fastjson.JSONReader;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

/**
 * @author henengqiang
 * @date 2019/04/04
 */
public class JsonUtils {

    /**
     * 将一份jsonList文件拆分为多份jsonList文件
     * @param inFilePath        输入文件目录及其名称
     * @param outFilePath       输出文件目录及其名称
     * @param objNumOfOneFile   每份文件包含的对象数目
     * @throws IOException
     */
    public static void splitJsonFile(String inFilePath, String outFilePath, int objNumOfOneFile) throws IOException {
        String outFilePathPrefix = outFilePath.split("\\.")[0];
        String dataStr = FileUtils.readFileToString(new File(inFilePath),"UTF-8");
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
            if (objNum % objNumOfOneFile == 0) {
                // 每objNumOfOneFile个对象保存一次
                fileNum++;
                tempData.deleteCharAt(tempData.length() - 1).append("]");
                System.out.println("写出第" + fileNum + "份json文件");
                String dealOutputFilePath = outFilePathPrefix + fileNum + ".json";
                FileUtils.writeStringToFile(new File(dealOutputFilePath), tempData.toString(), Charset.forName("UTF-8"));
                tempData.delete(0, tempData.length()).append("[");
            }
        }
        // 保存最后一份文件
        if (StringUtils.isNotEmpty(tempData.toString())) {
            tempData.deleteCharAt(tempData.length() - 1).append("]");
            System.out.println("写出第" + (fileNum + 1) + "份json文件");
            String dealOutputFilePath = outFilePathPrefix + (fileNum + 1) + ".json";
            FileUtils.writeStringToFile(new File(dealOutputFilePath), tempData.toString(), Charset.forName("UTF-8"));
        }
        reader.endArray();
    }

}
