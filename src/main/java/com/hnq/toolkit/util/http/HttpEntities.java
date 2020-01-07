package com.hnq.toolkit.util.http;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.*;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Static utilities relating to {@link HttpEntity}.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpEntities {

    private static final ContentType MULTIPART_FORM =
            ContentType.create("multipart/form-data", Consts.UTF_8);

    private HttpEntities() {
    }

    /**
     * read the {@link HttpEntity} as a string.
     *
     * @return string read from {@link HttpEntity}
     */
    public static String readToString(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return StringUtils.EMPTY;
        }

        return EntityUtils.toString(entity, Consts.UTF_8);
    }

    /**
     * read the {@link HttpEntity} as a byte arry.
     *
     * @return string read from {@link HttpEntity}
     */
    public static byte[] readToByteArray(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }

        return EntityUtils.toByteArray(entity);
    }

    public static HttpEntity createEntity(final Object requestBody) {
        return createEntity(requestBody, Consts.UTF_8);
    }

    public static HttpEntity createEntity(final Object requestBody, final Charset charset) {
        return createEntity(requestBody, null, charset);
    }

    /**
     * Create {@link HttpEntity}.
     *
     * @return {@link HttpEntity}
     * @see StringEntity
     * @see ByteArrayEntity
     * @see InputStreamEntity
     * @see FileEntity
     * @see EntityTemplate
     */
    @SuppressWarnings("unchecked")
    public static HttpEntity createEntity(final Object requestBody, final ContentType contentType,
                                          final Charset charset) {
        if (requestBody == null) {
            return null;
        } else if (requestBody instanceof HttpEntity) {
            HttpEntity entity = (HttpEntity) requestBody;
            if (contentType != null && entity.getContentType() == null
                    && entity instanceof AbstractHttpEntity) {
                ((AbstractHttpEntity) entity).setContentType(contentType.toString());
            }
            return entity;
        } else if (requestBody instanceof String) {
            if (contentType != null) {
                return new StringEntity((String) requestBody, contentType);
            }
            return new StringEntity((String) requestBody, defaultCharset(charset));
        } else if (requestBody instanceof byte[]) {
            return new ByteArrayEntity((byte[]) requestBody,
                    defaultContentType(contentType, ContentType.DEFAULT_BINARY));
        } else if (requestBody instanceof Map) {
            List<NameValuePair> parameters = HttpParameters
                    .transformToList((Map<String, Object>) requestBody);
            return new UrlEncodedFormEntity(parameters, defaultCharset(charset));
        } else if (requestBody instanceof InputStream) {
            return new InputStreamEntity((InputStream) requestBody,
                    defaultContentType(contentType, ContentType.DEFAULT_BINARY));
        } else if (requestBody instanceof File) {
            return new FileEntity((File) requestBody,
                    defaultContentType(contentType, ContentType.DEFAULT_BINARY));
        } else if (requestBody instanceof NameValuePair[]) {
            return new UrlEncodedFormEntity(Arrays.asList((NameValuePair[]) requestBody),
                    defaultCharset(charset));
        } else if (requestBody instanceof Serializable) {
            SerializableEntity entity = new SerializableEntity((Serializable) requestBody);
            entity.setContentType(defaultContentType(contentType, ContentType.DEFAULT_BINARY).toString());
            return entity;
        } else {
            EntityTemplate entity = new EntityTemplate(os -> {
                ObjectOutputStream out = new ObjectOutputStream(os);
                out.writeObject(requestBody);
                out.flush();
            });
            entity.setContentType(defaultContentType(contentType, ContentType.DEFAULT_BINARY).toString());
            return entity;
        }
    }

    public static Charset defaultCharset(final Charset charset) {
        return charset == null ? Consts.UTF_8 : charset;
    }

    private static ContentType defaultContentType(final ContentType contentType,
                                                  final ContentType def) {
        return contentType != null ? contentType : def;
    }

    /**
     * Create {@link HttpEntity}
     *
     * @return {@link HttpEntity}
     * @see org.apache.http.entity.mime.MultipartFormEntity
     * @see UrlEncodedFormEntity
     */
    public static HttpEntity createEntity(final Map<String, Object> params, final boolean multipart) {
        if (multipart) {
            return createMultipartEntity(params);
        }

        return new UrlEncodedFormEntity(HttpParameters.transformToList(params), Consts.UTF_8);
    }

    /**
     * create {@link org.apache.http.entity.mime.MultipartFormEntity}
     */
    public static HttpEntity createMultipartEntity(final Map<String, Object> params) {
        return createMultipartEntity(params, null);
    }

    public static HttpEntity createMultipartEntity(final Map<String, Object> params,
                                                   final Charset charset) {
        return createMultipartEntity(params, null, charset);
    }

    /**
     * create {@link org.apache.http.entity.mime.MultipartFormEntity}
     */
    public static HttpEntity createMultipartEntity(final Map<String, Object> params,
                                                   final ContentType contentType, final Charset charset) {
        Charset actualCharset = defaultCharset(charset);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create()
                .setCharset(actualCharset)
                .setContentType(defaultContentType(contentType, MULTIPART_FORM));

        if (MapUtils.isNotEmpty(params)) {
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> e : entrySet) {
                addPart(builder, e.getKey(), e.getValue(), actualCharset);
            }
        }

        return builder.build();
    }

    private static void addPart(final MultipartEntityBuilder builder, final String name,
                                final Object value, final Charset charset) {
        if (value instanceof ContentBody) {
            builder.addPart(name, (ContentBody) value);
        } else if (value instanceof File) {
            builder.addBinaryBody(name, (File) value);
        } else if (value instanceof InputStream) {
            builder.addBinaryBody(name, (InputStream) value);
        } else if (value == null) {
            builder.addTextBody(name, StringUtils.EMPTY);
        } else {
            Class<?> clazz = value.getClass();
            if (clazz.isArray()) {
                Class<?> ccl = clazz.getComponentType();
                if (ccl.isPrimitive() && ccl == Byte.TYPE) {
                    builder.addBinaryBody(name, (byte[]) value);
                    return;
                } else if (ccl == Byte.class) {
                    byte[] bytes = ArrayUtils.toPrimitive((Byte[]) value);
                    builder.addBinaryBody(name, bytes);
                    return;
                }
            }

            List<NameValuePair> pairs = HttpParameters.build(name, value);
            for (NameValuePair pair : pairs) {
                if (charset.equals(Consts.ISO_8859_1)) {
                    builder.addTextBody(pair.getName(), pair.getValue());
                } else {
                    builder.addTextBody(pair.getName(), pair.getValue(),
                            ContentType.TEXT_PLAIN.withCharset(charset));
                }
            }
        }
    }

}
