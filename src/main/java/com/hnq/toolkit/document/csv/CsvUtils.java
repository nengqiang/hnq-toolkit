package com.hnq.toolkit.document.csv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author henengqiang
 * @date 2020/09/15
 */
public class CsvUtils {

    /**
     * csv -> jsonArrayStr
     *
     * @param filePath      file path and itself
     * @return              jsonArrayStr
     * @throws IOException  throws {@link IOException}
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
     *
     * @param inputPath     file path and itself
     * @param outPutPath    file path and itself
     * @throws IOException  throws {@link IOException}
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

}
