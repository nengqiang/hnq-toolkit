package com.hnq.toolkit.util.csv;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.collect.Maps;
import com.hnq.toolkit.util.exception.ServiceException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;


/**
 * CSV工具类
 * 使用时配合注解{@link CSVField}使用
 *
 * @author chengwei
 * @date 2018/10/15 下午3:13
 */
public final class CSVUtils {

    private static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 私有无参构造方法
     **/
    private CSVUtils() {
    }

    /**
     * 生成CSV文件
     *
     * @param filePath 文件保存路径，例如：D:/temp/test.csv
     * @param beans    实体对象集合
     */
    public static <T> void write(String filePath, List<T> beans) {
        try {
            createDir(filePath);
            write(new FileOutputStream(filePath), beans);
        } catch (Exception e) {
            throw new ServiceException(-1, "生成CSV文件失败", e);
        }
    }

    /**
     * 生成CSV文件
     *
     * @param os
     * @param beans 实体对象集合
     */
    public static <T> void write(OutputStream os, List<T> beans) {
        CsvWriter writer = null;
        try {
            // 生成文件
            writer = new CsvWriter(os, ',', Charset.forName("UTF-8"));
            // 获取内容
            List<String[]> contents = getStringArrayFromBean(beans);
            // 写入内容
            for (String[] each : contents) {
                writer.writeRecord(each);
            }
        } catch (Exception e) {
            throw new ServiceException(-1, "生成CSV文件失败", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 读取CSV文件内容
     *
     * @param filePath 文件存放的路径，如：D:/csv/xxx.csv
     * @param bean     类类型
     * @return List<T>
     */
    public static <T> List<T> read(String filePath, Class<T> bean) {
        try {
            return read(new FileInputStream(filePath), bean);
        } catch (Exception e) {
            throw new ServiceException(-1, "读取CSV文件失败", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> read(InputStream inputStream, Class<T> clazz) {
        CsvReader reader = null;
        try {
            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
            reader = new CsvReader(inputStream, ',', Charset.forName("UTF-8"));
            /// 跳过表头，如果需要表头的话，这句可以忽略
            //reader.readHeaders();
            // 逐行读入除表头的数据
            List<String[]> dataList = new ArrayList<>();
            while (reader.readRecord()) {
                dataList.add(reader.getValues());
            }
            if (CollectionUtils.isEmpty(dataList)) {
                return Collections.emptyList();
            }

            List<Map<String, String>> recordValueMapList = getMapListFromStringArray(dataList);
            if (clazz.isAssignableFrom(Map.class)) {
                return (List) recordValueMapList;
            } else {
                return getBeanFromStringArray(recordValueMapList, clazz);
            }
        } catch (Exception e) {
            throw new ServiceException(-1, "读取CSV文件失败", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
            IOUtils.closeQuietly(inputStream);
        }
    }


    /**
     * 导出CSV文件
     *
     * @param response 响应对象
     * @param beans    实体对象集合
     */
    public static <T> void export(HttpServletResponse response, List<T> beans) {
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        export(response, beans, fileName);
    }

    /**
     * 导出CSV文件
     *
     * @param response 响应对象
     * @param beans    实体对象集合
     * @param fileName 文件名称 ,如：测试名称
     */
    public static <T> void export(HttpServletResponse response, List<T> beans, String fileName) {
        try {
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "*");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".csv");
            write(response.getOutputStream(), beans);
        } catch (Exception e) {
            throw new ServiceException(-1, "导出CSV文件失败", e);
        }
    }

    /**
     * 导出CSV模板
     *
     * @param response 响应对象
     * @param clazz    实体对象集合
     */
    public static <T> void exportCSVTemplate(HttpServletResponse response, Class<T> clazz) {
        // 以当前时间作为文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        exportCSVTemplate(response, clazz, fileName);
    }

    /**
     * 导出CSV模板
     *
     * @param response 响应对象
     * @param clazz    实体对象集合
     * @param fileName 文件名称
     */
    public static <T> void exportCSVTemplate(HttpServletResponse response, Class<T> clazz, String fileName) {
        CsvWriter writer = null;
        ServletOutputStream output = null;
        try {
            // 生成文件
            response.setContentType("application/csv;charset=UTF-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "*");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".csv");
            output = response.getOutputStream();

            writer = new CsvWriter(output, ',', Charset.forName("UTF-8"));
            // 筛选出标有注解的字段
            Field[] declaredFields = clazz.getDeclaredFields();
            List<Field> annoFields = new ArrayList<Field>();
            for (Field field : declaredFields) {
                CSVField anno = field.getAnnotation(CSVField.class);
                if (anno != null) {
                    annoFields.add(field);
                }
            }
            // 获取注解的值，即内容标题
            String[] title = new String[annoFields.size()];
            for (int i = 0; i < annoFields.size(); i++) {
                title[i] = annoFields.get(i).getAnnotation(CSVField.class).name();
            }
            // 写入内容
            if (ArrayUtils.isNotEmpty(title)) {
                writer.writeRecord(title);
            }

        } catch (Exception e) {
            throw new ServiceException(-1, "导出CSV模板失败", e);
        } finally {
            IOUtils.closeQuietly(output);
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 泛型实体转换为数组
     *
     * @param beans 实体类集合
     * @return List<String>
     */
    private static <T> List<String[]> getStringArrayFromBean(List<T> beans) {
        Class<?> cls = beans.get(0).getClass();
        Field[] declaredFields = cls.getDeclaredFields();
        List<CSVFieldValue> annoFields = new ArrayList<>();
        // 筛选出标有注解的字段
        for (Field field : declaredFields) {
            CSVField anno = field.getAnnotation(CSVField.class);
            if (anno != null) {
                CSVFieldValue csvFieldValue = new CSVFieldValue(anno, field);
                annoFields.add(csvFieldValue);
            }
        }
        // 获取注解的值，即内容标题
        String[] title = new String[annoFields.size()];
        for (int i = 0; i < annoFields.size(); i++) {
            title[i] = annoFields.get(i).getCsvField().name();
        }
        List<String[]> result = new ArrayList<>();
        result.add(title);
        try {
            // 获取内容
            for (T t : beans) {
                String[] item = new String[annoFields.size()];
                int index = 0;
                for (CSVFieldValue csvFieldValue : annoFields) {
                    Class<?> valType = csvFieldValue.getField().getType();
                    String fieldName = csvFieldValue.getField().getName();
                    String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method method = ReflectionUtils.findMethod(t.getClass(), methodName);
                    if (method != null) {
                        Object value = ReflectionUtils.invokeMethod(method, t);
                        item[index] = setValues(value, valType, csvFieldValue);
                    }
                    index++;
                }
                result.add(item);
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(-1, "实体对象转数组失败", e);
        }
    }

    /**
     * 数组转为对象集合
     *
     * @param recordValueMapList 集合数据
     * @param bean               类类型
     * @return List<T>
     */
    private static <T> List<T> getBeanFromStringArray(List<Map<String, String>> recordValueMapList, Class<T> bean) {
        List<T> list = new ArrayList<>();
        Map<String, CSVFieldValue> fields = getFields(bean);
        try {
            for (Map<String, String> map : recordValueMapList) {
                T t = bean.newInstance();
                for (Entry<String, String> entry : map.entrySet()) {
                    if (fields.containsKey(entry.getKey())) {
                        CSVFieldValue csvFieldValue = fields.get(entry.getKey());
                        Field field = csvFieldValue.getField();
                        Class<?> valType = field.getType();
                        Object value = getType(entry.getValue(), valType, csvFieldValue);
                        String fieldName = field.getName();
                        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method method = ReflectionUtils.findMethod(bean, methodName, valType);
                        if (method != null) {
                            ReflectionUtils.invokeMethod(method, t, value);
                        }
                    }
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            throw new ServiceException(-1, "创建实体失败", e);
        }
    }

    /**
     * 数组标题与值的对应关系
     *
     * @param dataList 集合数据
     * @return
     */
    private static List<Map<String, String>> getMapListFromStringArray(List<String[]> dataList) {
        List<Map<String, String>> list = new ArrayList<>();
        String[] titles = dataList.get(0);
        dataList.remove(0);
        for (String[] values : dataList) {
            Map<String, String> titleMap = Maps.newHashMap();
            for (int i = 0; i < values.length; i++) {
                titleMap.put(titles[i], values[i]);
            }
            list.add(titleMap);
        }
        return list;
    }

    /**
     * 注解名称与字段属性的对应关系
     *
     * @param clazz 实体对象类类型
     * @param <T>   泛型类型
     * @return Map<String, Field>
     */
    private static <T> Map<String, CSVFieldValue> getFields(Class<T> clazz) {
        Map<String, CSVFieldValue> annoMap = Maps.newHashMap();
        Field[] fileds = clazz.getDeclaredFields();
        for (Field filed : fileds) {
            CSVField anno = filed.getAnnotation(CSVField.class);
            if (anno != null) {
                // 获取name属性值
                if (StringUtils.isNotBlank(anno.name())) {
                    CSVFieldValue csvFieldValue = new CSVFieldValue(anno, filed);
                    annoMap.put(anno.name(), csvFieldValue);
                }
            }
        }
        return annoMap;
    }

    /**
     * 转换值
     *
     * @param value   属性值
     * @param valType 属性类型
     * @return
     */
    private static String setValues(Object value, Class<?> valType, CSVFieldValue csvFieldValue) {
        if (value == null) {
            return "";
        } else if (valType == Date.class) {
            String format = csvFieldValue.getCsvField().patten();
            if (StringUtils.isEmpty(format)) {
                format = DATE_TIME_PATTERN;
            }
            return DateFormatUtils.format(((Date) value).getTime(), format);
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * 转换成实体属性对应的类型
     *
     * @param value   每一格的数值
     * @param valType 实体属性类型
     * @return Object 转换为对应类型以obj返回
     */
    private static <T> Object getType(String value, Class<T> valType, CSVFieldValue csvFieldValue) {
        try {
            if (valType == String.class) {
                return value;
            } else if (valType == Date.class) {
                String format = csvFieldValue.getCsvField().patten();
                if (StringUtils.isEmpty(format)) {
                    format = DATE_TIME_PATTERN;
                }
                return DateUtils.parseDate(value, format);
            } else if (valType == Double.class) {
                return Double.parseDouble(value);
            } else if (valType == BigDecimal.class) {
                return new BigDecimal(value);
            } else if (valType == Integer.class) {
                return Integer.parseInt(value);
            } else if (valType == Long.class) {
                return Long.parseLong(value);
            } else if (valType == Boolean.class) {
                return Boolean.parseBoolean(value);
            }
        } catch (Exception e) {
            throw new ServiceException(-1, "类型转换异常", e);
        }
        return value;
    }

    /**
     * 创建文件目录
     *
     * @param filePath 文件路径，例如：temp/test.csv
     */
    private static void createDir(String filePath) {
        try {
            File file = new File(filePath.substring(0, filePath.lastIndexOf('/')));
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    throw new RuntimeException();
                }
            }
        } catch (Exception e) {
            throw new ServiceException(-1, "创建文件目录失败", e);
        }
    }
}