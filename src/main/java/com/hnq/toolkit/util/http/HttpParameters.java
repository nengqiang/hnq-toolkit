package com.hnq.toolkit.util.http;

import com.google.common.collect.Lists;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.Consts;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*;

/**
 * Static utilities relating to {@link HttpRequest}'s parameters.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpParameters {

    private static final NameValuePair[] EMPTY = new NameValuePair[0];

    private HttpParameters() {
    }

    /**
     * 转换成NameValuePair参数列表.
     */
    public static NameValuePair[] transformToArray(final Map<String, Object> params) {
        if (MapUtils.isEmpty(params)) {
            return EMPTY;
        }

        List<NameValuePair> list = transformToList(params);

        return list.toArray(new NameValuePair[0]);
    }

    /**
     * 转换成NameValuePair参数列表.
     */
    public static List<NameValuePair> transformToList(final Map<String, Object> params) {
        if (MapUtils.isEmpty(params)) {
            return Collections.emptyList();
        }

        Parameters parameters = new Parameters();

        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        for (Map.Entry<String, Object> e : entrySet) {
            appendTo(parameters, e.getKey(), e.getValue());
        }

        return parameters.nameValuePairs;
    }

    /**
     * create instance of {@link BasicNameValuePair}.
     */
    public static List<NameValuePair> build(String name, Object value) {
        Parameters parameters = new Parameters();
        appendTo(parameters, name, value);
        return parameters.nameValuePairs;
    }

    private static void appendTo(Parameters parameters, String name, Object value) {
        if (value == null) {
            parameters.add(name, StringUtils.EMPTY);
        } else {
            Class clazz = value.getClass();
            if (clazz.isPrimitive() || value instanceof String || value instanceof Number) {
                parameters.add(name, String.valueOf(value));
            } else if (value instanceof Date) {
                parameters.add(name, (Date) value);
            } else if (value instanceof Iterable) {
                parameters.add(name, ((Iterable) value).iterator());
            } else if (value instanceof Iterator) {
                parameters.add(name, (Iterator) value);
            } else if (clazz.isArray()) {
                Class<?> ccl = clazz.getComponentType();
                parameters.addArray(name, value, ccl);
            } else if (value instanceof Map || value instanceof Dictionary) {
                parameters.addUnknown(name, value);
            } else if (value instanceof Enumeration) {
                parameters.add(name, (Enumeration) value);
            } else {
                throw new IllegalArgumentException(
                        "Unsupported parameter type! >> name:" + name + " value:" + value + " type:" + clazz
                                .getSimpleName());
            }
        }
    }

    private static class Parameters {

        private final List<NameValuePair> nameValuePairs = Lists.newArrayList();

        public void add(String name, String value) {
            nameValuePairs.add(new BasicNameValuePair(name, value));
        }

        public void add(String name, Date value) {
            add(name, DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss"));
        }

        public void add(String name, Iterator value) {
            if (!value.hasNext()) {
                add(name, StringUtils.EMPTY);
                return;
            }

            Object first = value.next();
            if (first instanceof String || first instanceof Number) {
                add(name, String.valueOf(first));
                while (value.hasNext()) {
                    add(name, String.valueOf(value.next()));
                }
            } else if (first instanceof Date) {
                add(name, (Date) first);
                while (value.hasNext()) {
                    add(name, (Date) value.next());
                }
            } else {
                addUnknown(name, value);
            }
        }

        public void add(String name, Enumeration value) {
            if (!value.hasMoreElements()) {
                add(name, StringUtils.EMPTY);
                return;
            }

            Object first = value.nextElement();
            if (first instanceof String || first instanceof Number) {
                add(name, String.valueOf(first));
                while (value.hasMoreElements()) {
                    add(name, String.valueOf(value.nextElement()));
                }
            } else if (first instanceof Date) {
                add(name, (Date) first);
                while (value.hasMoreElements()) {
                    add(name, (Date) value.nextElement());
                }
            } else {
                addUnknown(name, value);
            }
        }

        public void add(String name, int[] value) {
            Arrays.stream(value).forEach(item -> add(name, String.valueOf(item)));
        }

        public void add(String name, long[] value) {
            Arrays.stream(value).forEach(item -> add(name, String.valueOf(item)));
        }

        public void add(String name, float[] value) {
            for (float obj : value) {
                add(name, String.valueOf(obj));
            }
        }

        public void add(String name, double[] value) {
            Arrays.stream(value).forEach(item -> add(name, String.valueOf(item)));
        }

        public void add(String name, short[] value) {
            for (short obj : value) {
                add(name, String.valueOf(obj));
            }
        }

        public void addArray(String name, Object value, Class<?> ccl) {
            if (ccl.isPrimitive()) {
                if (ccl == Byte.TYPE) {
                    add(name, new String((byte[]) value, Consts.UTF_8));
                } else if (ccl == Integer.TYPE) {
                    add(name, (int[]) value);
                } else if (ccl == Long.TYPE) {
                    add(name, (long[]) value);
                } else if (ccl == Float.TYPE) {
                    add(name, (float[]) value);
                } else if (ccl == Double.TYPE) {
                    add(name, (double[]) value);
                } else if (ccl == Short.TYPE) {
                    add(name, (short[]) value);
                } else if (ccl == Character.TYPE) {
                    add(name, new String((char[]) value));
                } else {
                    throw new IllegalArgumentException("Invalid parameter");
                }
            } else if (Byte.class.isAssignableFrom(ccl)) {
                byte[] bytes = ArrayUtils.toPrimitive((Byte[]) value);
                add(name, new String(bytes, Consts.UTF_8));
            } else if (Number.class.isAssignableFrom(ccl) || String.class.isAssignableFrom(ccl)) {
                Object[] array = (Object[]) value;
                for (Object obj : array) {
                    add(name, String.valueOf(obj));
                }
            } else {
                addUnknown(name, value);
            }
        }

        public void addUnknown(String name, Object value) {
            add(name, Jackson.toJSONString(value));
        }

    }

}
