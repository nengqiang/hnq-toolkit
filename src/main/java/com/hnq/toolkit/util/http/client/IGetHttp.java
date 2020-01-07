package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.springframework.http.HttpMethod;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public interface IGetHttp extends ISimpleHttp {

    // ------------------------------- GET METHOD

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, String> headers,
                                        Map<String, Object> params, Object body, String proxy) throws HttpException {
        return sendRequest(url, HttpMethod.GET, headers, params, body, proxy);
    }

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, String> headers,
                                        Map<String, Object> params, Object body) throws HttpException {
        return sendRequest(url, HttpMethod.GET, headers, params, body);
    }

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, String> headers,
                                        Map<String, Object> params) throws HttpException {
        return requestWithGet(url, headers, params, null);
    }

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, Object> params, Object body,
                                        String proxy) throws HttpException {
        return requestWithGet(url, null, params, body, proxy);
    }

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, Object> params, Object body)
            throws HttpException {
        return requestWithGet(url, null, params, body);
    }

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return requestWithGet(url, params, null, proxy);
    }

    default HttpResponse requestWithGet(@Nonnull String url, Map<String, Object> params) throws HttpException {
        return requestWithGet(url, params, StringUtils.EMPTY);
    }

    default HttpResponse requestWithGet(@Nonnull String url, String proxy) throws HttpException {
        return requestWithGet(url, null, proxy);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      Object body, String proxy, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return send(url, HttpMethod.GET, headers, params, body, proxy, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      Object body, String proxy, Class<T> clazz) throws HttpException {
        return send(url, HttpMethod.GET, headers, params, body, proxy, clazz);
    }

    default String get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       Object body, String proxy) throws HttpException {
        return get(url, headers, params, body, proxy, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      Object body, @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, headers, params, body, StringUtils.EMPTY, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      Object body, Class<T> clazz) throws HttpException {
        return get(url, headers, params, body, StringUtils.EMPTY, clazz);
    }

    default String get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       Object body) throws HttpException {
        return get(url, headers, params, body, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      String proxy, @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, headers, params, null, proxy, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      String proxy, Class<T> clazz) throws HttpException {
        return get(url, headers, params, null, proxy, clazz);
    }

    default String get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       String proxy) throws HttpException {
        return get(url, headers, params, proxy, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, headers, params, StringUtils.EMPTY, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      Class<T> clazz) throws HttpException {
        return get(url, headers, params, StringUtils.EMPTY, clazz);
    }

    default String get(@Nonnull String url, Map<String, String> headers, Map<String, Object> params)
            throws HttpException {
        return get(url, headers, params, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, Object body, String proxy,
                      @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, null, params, body, proxy, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, Object body, String proxy,
                      Class<T> clazz) throws HttpException {
        return get(url, null, params, body, proxy, clazz);
    }

    default String get(@Nonnull String url, Map<String, Object> params, Object body, String proxy)
            throws HttpException {
        return get(url, params, body, proxy, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, Object body,
                      @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, null, params, body, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, Object body, Class<T> clazz)
            throws HttpException {
        return get(url, null, params, body, clazz);
    }

    default String get(@Nonnull String url, Map<String, Object> params, Object body) throws HttpException {
        return get(url, params, body, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, String proxy,
                      @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, params, null, proxy, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, String proxy, Class<T> clazz)
            throws HttpException {
        return get(url, params, null, proxy, clazz);
    }

    default String get(@Nonnull String url, Map<String, Object> params, String proxy) throws HttpException {
        return get(url, params, proxy, String.class);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params,
                      @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, params, StringUtils.EMPTY, responseHandler);
    }

    default <T> T get(@Nonnull String url, Map<String, Object> params, Class<T> clazz) throws HttpException {
        return get(url, params, StringUtils.EMPTY, clazz);
    }

    default String get(@Nonnull String url, Map<String, Object> params) throws HttpException {
        return get(url, params, String.class);
    }

    default <T> T get(@Nonnull String url, String proxy, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return get(url, null, proxy, responseHandler);
    }

    default <T> T get(@Nonnull String url, String proxy, Class<T> clazz) throws HttpException {
        return get(url, null, proxy, clazz);
    }

    default <T> T get(@Nonnull String url, Class<T> clazz) throws HttpException {
        return get(url, StringUtils.EMPTY, clazz);
    }

    default String get(@Nonnull String url, String proxy) throws HttpException {
        return get(url, proxy, String.class);
    }

    default String get(@Nonnull String url) throws HttpException {
        return get(url, StringUtils.EMPTY);
    }

}
