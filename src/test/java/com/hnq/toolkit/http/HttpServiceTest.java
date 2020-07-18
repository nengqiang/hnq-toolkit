package com.hnq.toolkit.http;

import com.google.common.collect.Maps;
import com.hnq.toolkit.http.exception.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
@Slf4j
class HttpServiceTest {

    @Test
    void getTest() {
        String url = "https://www.baidu.com";
        Map<String, Object> params = Maps.newHashMap();
        params.put("wd", "http");
        try {
            String response = HttpService.get(url, params);
            Assertions.assertNotNull(response);
        } catch (HttpException e) {
            e.printStackTrace();
        }
    }

}
