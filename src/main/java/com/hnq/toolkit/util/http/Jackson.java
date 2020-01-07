package com.hnq.toolkit.util.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.InputStream;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class Jackson {

    // TODO: 2019-10-10 henengqiang ---- 这里偷懒了，以后有机会再专门写一个Jackson toolkit ----

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> T parse(@Nonnull final InputStream inputStream, @Nonnull final Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, clazz);
        } catch (Exception e) {
            throw new JacksonException( "Error parsing json string to the instance of '" + clazz + "'", e);
        }
    }

    public static String toJSONString(@Nullable Object object) {
        if (object == null) {
            return StringUtils.EMPTY;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JacksonException(e.getMessage(), e);
        }
    }

    private static class JacksonException extends RuntimeException {
        private JacksonException(String message) {
            super(message);
        }

        private JacksonException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
