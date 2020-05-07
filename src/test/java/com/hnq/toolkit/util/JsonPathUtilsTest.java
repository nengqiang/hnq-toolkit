package com.hnq.toolkit.util;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import org.apache.commons.io.FileUtils;

/**
 * @author henengqiang
 * @date 2019/07/23
 */
class JsonPathUtilsTest {

    private File file;

    private void init() {
        file = new File(com.hnq.toolkit.util.FileUtils.getResourceFilePath(this.getClass(), "json_source.json"));
    }

    @Test
    void read() {
        try {
            init();
            String data = FileUtils.readFileToString(file, Charset.defaultCharset());
            Object read = JsonPathUtils.read(data, "$.totalCount");
            Assertions.assertEquals(1, read);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void readAsString() {
        try {
            init();
            String data = FileUtils.readFileToString(file, Charset.defaultCharset());
            String read = JsonPathUtils.readAsString(data, "$.offset");
            System.out.println(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void readAsList() {
        try {
            init();
            String data = FileUtils.readFileToString(file, Charset.defaultCharset());
            List<String> read = JsonPathUtils.readAsList(data, "$.postList");
            read.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    void jsonTest() {
        List<TestObj> list = Lists.newArrayList();
        TestObj obj1 = new TestObj();
        obj1.setId(1L);
        obj1.setType(new Byte("1"));
        list.add(obj1);
        TestObj obj2 = new TestObj();
        obj2.setId(2L);
        obj2.setType(new Byte("2"));
        list.add(obj2);
        System.out.println(JSON.toJSONString(list));
    }
    
    @Data
    private static class TestObj {
        private Long id;
        private Byte type;
    }
}