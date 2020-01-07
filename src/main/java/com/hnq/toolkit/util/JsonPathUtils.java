package com.hnq.toolkit.util;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author henengqiang
 * @date 2019/07/23
 */
public class JsonPathUtils {

    private JsonPathUtils() {}

    public static Object read(String json, String jsonPath) {
        if (StringUtils.isEmpty(json) || StringUtils.isEmpty(jsonPath)) {
            return null;
        }

        return JsonPath.read(json, jsonPath);
    }

    private static String parse(Object obj) {
        return JsonPath.parse(obj).jsonString();
    }

    private static String parseAsString(Object jsonObj) {
        String result;

        if (jsonObj == null) {
            result = StringUtils.EMPTY;
        } else if (jsonObj instanceof String) {
            result = (String)jsonObj;
        } else if (jsonObj instanceof Number || jsonObj instanceof Boolean) {
            result = jsonObj.toString();
        } else if (jsonObj instanceof JSONArray) {
            JSONArray array = (JSONArray) jsonObj;
            if (array.size() == 1) {
                result = array.get(0).toString();
            } else {
                result = array.toJSONString();
            }
        } else {
            result = parse(jsonObj);
        }

        return result;
    }

    private static List<String> parseAsList(Object jsonObj) {
        if (jsonObj == null) {
            return Collections.emptyList();
        }

        List<String> result;
        if (jsonObj instanceof String) {
            result = Collections.singletonList(jsonObj.toString());
        } else if (jsonObj instanceof JSONArray) {
            JSONArray array = (JSONArray) jsonObj;
            result = array.stream().map(item -> {
                if (item instanceof String) {
                    return (String) item;
                }
                return parse(item);
            }).collect(Collectors.toList());
        } else {
            result = Collections.singletonList(parse(jsonObj));
        }

        return result;
    }

    /**
     * 对JSON进行jsonpath解析，返回结果json字符串
     * <p>
     * 例如：
     * { "store": {
     * "book": [
     * { "category": "reference",
     * "author": "Nigel Rees",
     * "title": "Sayings of the Century",
     * "price": 8.95
     * },
     * { "category": "fiction",
     * "author": "Evelyn Waugh",
     * "title": "Sword of Honour",
     * "price": 12.99,
     * "isbn": "0-553-21311-3"
     * }
     * ],
     * "bicycle": {
     * "color": "red",
     * "price": 19.95
     * }
     * }
     * }
     * jsonpath:$.store.book.category
     * result:"[\"reference\", \"fiction\"]"
     * </p>
     */
    public static String readAsString(String json, String jsonPath) {
        return parseAsString(read(json, jsonPath));
    }

    /**
     * * 对JSON进行jsonpath解析，返回结果json字符串列表
     * <p>
     * 例如：
     * { "store": {
     * "book": [
     * { "category": "reference",
     * "author": "Nigel Rees",
     * "title": "Sayings of the Century",
     * "price": 8.95
     * },
     * { "category": "fiction",
     * "author": "Evelyn Waugh",
     * "title": "Sword of Honour",
     * "price": 12.99,
     * "isbn": "0-553-21311-3"
     * }
     * ],
     * "bicycle": {
     * "color": "red",
     * "price": 19.95
     * }
     * }
     * }
     * jsonpath:$.store.book.category
     * result: List["reference", "fiction"]
     * </p>
     */
    public static List<String> readAsList(String json, String jsonPath) {
        return parseAsList(read(json, jsonPath));
    }

}
