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
public interface IPostHttp extends ISimpleHttp {

    // ------------------------------- POST METHOD

    default HttpResponse requestWithPost(@Nonnull String url, Map<String, String> headers,
                                         Map<String, Object> params, Object body, String proxy) throws HttpException {
        return sendRequest(url, HttpMethod.POST, headers, params, body, proxy);
    }

    default HttpResponse requestWithPost(@Nonnull String url, Map<String, String> headers,
                                         Map<String, Object> params, Object body) throws HttpException {
        return sendRequest(url, HttpMethod.POST, headers, params, body);
    }

    default HttpResponse requestWithPost(@Nonnull String url, Map<String, Object> params, Object body,
                                         String proxy) throws HttpException {
        return requestWithPost(url, null, params, body, proxy);
    }

    default HttpResponse requestWithPost(@Nonnull String url, Map<String, Object> params, Object body)
            throws HttpException {
        return requestWithPost(url, null, params, body);
    }

    default HttpResponse requestWithPost(@Nonnull String url, Map<String, Object> params)
            throws HttpException {
        return requestWithPost(url, params, null);
    }

    default <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       Object body, String proxy, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return send(url, HttpMethod.POST, headers, params, body, proxy, responseHandler);
    }

    default <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       Object body, String proxy, Class<T> clazz) throws HttpException {
        return send(url, HttpMethod.POST, headers, params, body, proxy, clazz);
    }

    default String post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                        Object body, String proxy) throws HttpException {
        return post(url, headers, params, body, proxy, String.class);
    }

    default <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       Object body, @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, headers, params, body, StringUtils.EMPTY, responseHandler);
    }

    default <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       Object body, Class<T> clazz) throws HttpException {
        return post(url, headers, params, body, StringUtils.EMPTY, clazz);
    }

    default String post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                        Object body) throws HttpException {
        return post(url, headers, params, body, String.class);
    }

    default <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       String proxy, @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, headers, params, null, proxy, responseHandler);
    }

    default <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                       String proxy, Class<T> clazz) throws HttpException {
        return post(url, headers, params, null, proxy, clazz);
    }

    default String post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                        String proxy) throws HttpException {
        return post(url, headers, params, proxy, String.class);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, Object body, String proxy,
                       @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, null, params, body, proxy, responseHandler);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, Object body, String proxy,
                       Class<T> clazz) throws HttpException {
        return post(url, null, params, body, proxy, clazz);
    }

    default String post(@Nonnull String url, Map<String, Object> params, Object body, String proxy)
            throws HttpException {
        return post(url, params, body, proxy, String.class);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, Object body,
                       @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, params, body, StringUtils.EMPTY, responseHandler);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, Object body,
                       Class<T> clazz) throws HttpException {
        return post(url, params, body, StringUtils.EMPTY, clazz);
    }

    default String post(@Nonnull String url, Map<String, Object> params, Object body)
            throws HttpException {
        return post(url, params, body, String.class);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, String proxy,
                       @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, params, null, proxy, responseHandler);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, String proxy,
                       Class<T> clazz) throws HttpException {
        return post(url, params, null, proxy, clazz);
    }

    default <T> T post(@Nonnull String url, Map<String, Object> params, Class<T> clazz) throws HttpException {
        return post(url, params, null, StringUtils.EMPTY, clazz);
    }

    default String post(@Nonnull String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return post(url, params, proxy, String.class);
    }

    default String post(@Nonnull String url, Map<String, Object> params) throws HttpException {
        return post(url, params, StringUtils.EMPTY);
    }

    default String post(@Nonnull String url) throws HttpException {
        return post(url, null);
    }

    // ------------------------------- POST METHOD, support 'multipart/form-data'

    HttpResponse requestWithPost(@Nonnull String url, Map<String, String> headers,
                                 Map<String, Object> params,
                                 String proxy, boolean multipart) throws HttpException;

    default HttpResponse requestWithPost(String url, Map<String, String> headers,
                                         Map<String, Object> params, boolean multipart) throws HttpException {
        return requestWithPost(url, headers, params, StringUtils.EMPTY, multipart);
    }

    default HttpResponse requestWithPost(String url, Map<String, Object> params, String proxy,
                                         boolean multipart) throws HttpException {
        return requestWithPost(url, null, params, proxy, multipart);
    }

    default HttpResponse requestWithPost(String url, Map<String, Object> params, boolean multipart)
            throws HttpException {
        return requestWithPost(url, params, StringUtils.EMPTY, multipart);
    }

    <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
               String proxy, boolean multipart, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException;

    <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
               String proxy, boolean multipart, Class<T> clazz) throws HttpException;

    default String post(String url, Map<String, String> headers, Map<String, Object> params,
                        String proxy, boolean multipart) throws HttpException {
        return post(url, headers, params, proxy, multipart, String.class);
    }

    default <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                       boolean multipart, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, headers, params, StringUtils.EMPTY, multipart, responseHandler);
    }

    default <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                       boolean multipart, Class<T> clazz) throws HttpException {
        return post(url, headers, params, StringUtils.EMPTY, multipart, clazz);
    }

    default String post(String url, Map<String, String> headers, Map<String, Object> params,
                        boolean multipart) throws HttpException {
        return post(url, headers, params, multipart, String.class);
    }

    default <T> T post(String url, Map<String, Object> params, String proxy, boolean multipart,
                       ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, null, params, proxy, multipart, responseHandler);
    }

    default <T> T post(String url, Map<String, Object> params, String proxy, boolean multipart,
                       Class<T> clazz) throws HttpException {
        return post(url, null, params, proxy, multipart, clazz);
    }

    default String post(String url, Map<String, Object> params, String proxy, boolean multipart)
            throws HttpException {
        return post(url, params, proxy, multipart, String.class);
    }

    default <T> T post(String url, Map<String, Object> params, boolean multipart,
                       ResponseHandler<? extends T> responseHandler) throws HttpException {
        return post(url, params, StringUtils.EMPTY, multipart, responseHandler);
    }

    default <T> T post(String url, Map<String, Object> params, boolean multipart, Class<T> clazz)
            throws HttpException {
        return post(url, params, StringUtils.EMPTY, multipart, clazz);
    }

    default String post(String url, Map<String, Object> params, boolean multipart)
            throws HttpException {
        return post(url, params, multipart, String.class);
    }

}
