package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.MoreHttpRequest;
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
public interface ISimpleHttp extends IFlexibleHttp {

    // ------------------------------- 基础，简单

    default HttpResponse sendRequest(@Nonnull String url, @Nonnull HttpMethod method,
                                     Map<String, String> headers, Map<String, Object> params, Object body, String proxy)
            throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.from(url, method).setHeaders(headers)
                .setParameters(params).setEntity(body).setProxy(proxy);
        return sendRequest(request);
    }

    default HttpResponse sendRequest(@Nonnull String url, @Nonnull HttpMethod method,
                                     Map<String, String> headers, Map<String, Object> params, Object body) throws HttpException {
        return sendRequest(url, method, headers, params, body, StringUtils.EMPTY);
    }

    default HttpResponse sendRequest(@Nonnull String url, @Nonnull HttpMethod method,
                                     Map<String, Object> params, Object body, String proxy) throws HttpException {
        return sendRequest(url, method, null, params, body, proxy);
    }

    default HttpResponse sendRequest(@Nonnull String url, @Nonnull HttpMethod method,
                                     Map<String, Object> params, Object body) throws HttpException {
        return sendRequest(url, method, null, params, body, StringUtils.EMPTY);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, String> headers,
                       Map<String, Object> params, Object body, String proxy,
                       @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.from(url, method).setHeaders(headers)
                .setParameters(params).setEntity(body).setProxy(proxy);
        return send(request, responseHandler);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, String> headers,
                       Map<String, Object> params, Object body, String proxy, Class<T> clazz)
            throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.from(url, method).setHeaders(headers)
                .setParameters(params).setEntity(body).setProxy(proxy);
        return send(request, clazz);
    }

    default String send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, String> headers,
                        Map<String, Object> params, Object body, String proxy) throws HttpException {
        return send(url, method, headers, params, body, proxy, String.class);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, String> headers,
                       Map<String, Object> params, Object body,
                       @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return send(url, method, headers, params, body, StringUtils.EMPTY, responseHandler);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, String> headers,
                       Map<String, Object> params, Object body, Class<T> clazz) throws HttpException {
        return send(url, method, headers, params, body, StringUtils.EMPTY, clazz);
    }

    default String send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, String> headers,
                        Map<String, Object> params, Object body) throws HttpException {
        return send(url, method, headers, params, body, String.class);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, Object> params,
                       Object body, String proxy, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return send(url, method, null, params, body, proxy, responseHandler);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, Object> params,
                       Object body, String proxy, Class<T> clazz) throws HttpException {
        return send(url, method, null, params, body, proxy, clazz);
    }

    default String send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, Object> params,
                        Object body, String proxy) throws HttpException {
        return send(url, method, params, body, proxy, String.class);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, Object> params,
                       Object body, @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return send(url, method, null, params, body, responseHandler);
    }

    default <T> T send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, Object> params,
                       Object body, Class<T> clazz) throws HttpException {
        return send(url, method, null, params, body, clazz);
    }

    default String send(@Nonnull String url, @Nonnull HttpMethod method, Map<String, Object> params,
                        Object body) throws HttpException {
        return send(url, method, params, body, String.class);
    }

    HttpResponse sendMultipartRequest(@Nonnull String url, Map<String, String> headers,
                                      Map<String, Object> params, String proxy) throws HttpException;

    default HttpResponse sendMultipartRequest(@Nonnull String url, Map<String, String> headers,
                                              Map<String, Object> params) throws HttpException {
        return sendMultipartRequest(url, headers, params, StringUtils.EMPTY);
    }

    default HttpResponse sendMultipartRequest(@Nonnull String url, Map<String, Object> params,
                                              String proxy) throws HttpException {
        return sendMultipartRequest(url, null, params, proxy);
    }

    default HttpResponse sendMultipartRequest(@Nonnull String url, Map<String, Object> params)
            throws HttpException {
        return sendMultipartRequest(url, null, params);
    }

    <T> T sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                            Map<String, Object> params, String proxy,
                            @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException;

    <T> T sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                            Map<String, Object> params, String proxy, Class<T> clazz) throws HttpException;

    default String sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                                     Map<String, Object> params, String proxy) throws HttpException {
        return sendMultipartForm(url, headers, params, proxy, String.class);
    }

    default <T> T sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                                    Map<String, Object> params, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return sendMultipartForm(url, headers, params, StringUtils.EMPTY, responseHandler);
    }

    default <T> T sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                                    Map<String, Object> params, Class<T> clazz) throws HttpException {
        return sendMultipartForm(url, headers, params, StringUtils.EMPTY, clazz);
    }

    default String sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                                     Map<String, Object> params) throws HttpException {
        return sendMultipartForm(url, headers, params, String.class);
    }

    default <T> T sendMultipartForm(@Nonnull String url, Map<String, Object> params, String proxy,
                                    @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return sendMultipartForm(url, null, params, proxy, responseHandler);
    }

    default <T> T sendMultipartForm(@Nonnull String url, Map<String, Object> params, String proxy,
                                    Class<T> clazz) throws HttpException {
        return sendMultipartForm(url, null, params, proxy, clazz);
    }

    default String sendMultipartForm(@Nonnull String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return sendMultipartForm(url, params, proxy, String.class);
    }

    default <T> T sendMultipartForm(@Nonnull String url, Map<String, Object> params,
                                    @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        return sendMultipartForm(url, null, params, responseHandler);
    }

    default <T> T sendMultipartForm(@Nonnull String url, Map<String, Object> params,
                                    Class<T> clazz) throws HttpException {
        return sendMultipartForm(url, null, params, clazz);
    }

    default String sendMultipartForm(@Nonnull String url, Map<String, Object> params)
            throws HttpException {
        return sendMultipartForm(url, params, String.class);
    }

}
