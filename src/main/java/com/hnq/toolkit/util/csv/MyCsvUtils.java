package com.hnq.toolkit.util.csv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/03/28
 */
public class MyCsvUtils {

    /**
     * csv -> jsonArrayStr
     * @param filePath  file path and itself
     * @return          jsonArrayStr
     * @throws IOException
     */
    public static String getJsonFromCsvFile(String filePath) throws IOException {
        File input = new File(filePath);
        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

        ObjectMapper mapper = new ObjectMapper();

        // Write JSON formatted data to output.json file
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll);
    }

    /**
     * 把csv文件里的信息写出到json文件
     * @param inputPath     file path and itself
     * @param outPutPath    file path and itself
     * @throws IOException
     */
    public static void writeJsonFromCsvFile(String inputPath, String outPutPath) throws IOException {
        File input = new File(inputPath);
        File output = new File(outPutPath);

        CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        CsvMapper csvMapper = new CsvMapper();

        // Read data from CSV file
        List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

        ObjectMapper mapper = new ObjectMapper();

        // Write JSON formatted data to output.json file
        mapper.writerWithDefaultPrettyPrinter().writeValue(output, readAll);
    }

    /**
     * 把一份csv文件拆分为多分csv文件
     * (不能适用于数据有多行的情况)
     *
     * @param filePath          源文件
     * @param targetFilePath    目标文件目录及其名称（不含后缀）
     * @param targetFileRowNum  目标文件数据行数
     * @throws IOException
     */
    public static void breakFileWithHead(String filePath, String targetFilePath, int targetFileRowNum)
            throws IOException {
        FileReader read = new FileReader(filePath);
        BufferedReader br = new BufferedReader(read);
        String row;
        String firstRow;
        int rowNum = 0;
        int fileNo = 1;
        FileWriter fw = new FileWriter(targetFilePath + fileNo + ".csv");
        // 单独读出第一行，保存下来
        row = br.readLine();
        firstRow = row;
        StringBuilder sb = new StringBuilder();
        while ((row = br.readLine()) != null) {
            rowNum++;
            sb.append(row).append("\r\n");
            if (rowNum >= targetFileRowNum - 1) {
                sb.insert(0, firstRow + "\r\n");
                fw.append(sb);
                fw.close();
                sb.delete(0, sb.length());
                rowNum = 0;
                fileNo++;
                fw = new FileWriter(targetFilePath + fileNo + ".csv");
            }
        }
        // 最后一个文件
        if (StringUtils.isNotEmpty(sb)) {
            sb.insert(0, firstRow + "\r\n");
            fw.append(sb);
        } else {
            File file = new File(targetFilePath + fileNo + ".csv");
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
        fw.close();
    }

}
