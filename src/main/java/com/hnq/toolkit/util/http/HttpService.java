package com.hnq.toolkit.util.http;

import com.hnq.toolkit.util.http.client.MoreHttp;
import com.hnq.toolkit.util.http.client.MoreHttpFactory;
import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;

import java.util.Map;

/**
 * Static utilities relating to the http client.
 *
 * 发送HTTP请求就用这一个服务就够了
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpService {

    private static final MoreHttp HTTP_CLIENT = MoreHttpFactory.createCustom();

    private HttpService() {
    }

    public static MoreHttp defaultHttpClient() {
        return HTTP_CLIENT;
    }

    // ------------------------------- 原始封装

    public static HttpResponse sendRequest(HttpUriRequest request) throws HttpException {
        return defaultHttpClient().sendRequest(request);
    }

    public static HttpResponse sendRequest(HttpUriRequest request, HttpContext context)
            throws HttpException {
        return defaultHttpClient().sendRequest(request, context);
    }

    public static <T> T send(HttpUriRequest request, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().send(request, responseHandler);
    }

    public static <T> T send(HttpUriRequest request, ResponseHandler<? extends T> responseHandler,
                             HttpContext context) throws HttpException {
        return defaultHttpClient().send(request, responseHandler, context);
    }

    public static <T> T send(HttpUriRequest request, Class<T> clazz) throws HttpException {
        return defaultHttpClient().send(request, clazz);
    }

    public static <T> T send(HttpUriRequest request, HttpContext context, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().send(request, context, clazz);
    }

    public static String send(HttpUriRequest request) throws HttpException {
        return defaultHttpClient().send(request);
    }

    public static String send(HttpUriRequest request, HttpContext context) throws HttpException {
        return defaultHttpClient().send(request, context);
    }

    // ------------------------------- 灵活，多配置

    public static HttpResponse sendRequest(MoreHttpRequest request) throws HttpException {
        return defaultHttpClient().sendRequest(request);
    }

    public static HttpResponse sendRequest(MoreHttpRequest request, HttpContext context)
            throws HttpException {
        return defaultHttpClient().sendRequest(request, context);
    }

    public static <T> T send(MoreHttpRequest request, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().send(request, responseHandler);
    }

    public static <T> T send(MoreHttpRequest request, ResponseHandler<? extends T> responseHandler,
                             HttpContext context) throws HttpException {
        return defaultHttpClient().send(request, responseHandler, context);
    }

    public static <T> T send(MoreHttpRequest request, Class<T> clazz) throws HttpException {
        return defaultHttpClient().send(request, clazz);
    }

    public static <T> T send(MoreHttpRequest request, HttpContext context, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().send(request, context, clazz);
    }

    public static String send(MoreHttpRequest request) throws HttpException {
        return defaultHttpClient().send(request);
    }

    public static String send(MoreHttpRequest request, HttpContext context) throws HttpException {
        return defaultHttpClient().send(request, context);
    }

    // ------------------------------- 基础，简单

    public static HttpResponse sendRequest(String url, HttpMethod method, Map<String, String> headers,
                                           Map<String, Object> params, Object body, String proxy) throws HttpException {
        return defaultHttpClient().sendRequest(url, method, headers, params, body, proxy);
    }

    public static HttpResponse sendRequest(String url, HttpMethod method, Map<String, String> headers,
                                           Map<String, Object> params, Object body) throws HttpException {
        return defaultHttpClient().sendRequest(url, method, headers, params, body);
    }

    public static HttpResponse sendRequest(String url, HttpMethod method, Map<String, Object> params,
                                           Object body, String proxy) throws HttpException {
        return defaultHttpClient().sendRequest(url, method, params, body, proxy);
    }

    public static HttpResponse sendRequest(String url, HttpMethod method, Map<String, Object> params,
                                           Object body) throws HttpException {
        return defaultHttpClient().sendRequest(url, method, params, body);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, String> headers,
                             Map<String, Object> params, Object body, String proxy,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().send(url, method, headers, params, body, proxy, responseHandler);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, String> headers,
                             Map<String, Object> params, Object body, String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().send(url, method, headers, params, body, proxy, clazz);
    }

    public static String send(String url, HttpMethod method, Map<String, String> headers,
                              Map<String, Object> params, Object body, String proxy) throws HttpException {
        return defaultHttpClient().send(url, method, headers, params, body, proxy);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, String> headers,
                             Map<String, Object> params, Object body, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().send(url, method, headers, params, body, responseHandler);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, String> headers,
                             Map<String, Object> params, Object body, Class<T> clazz) throws HttpException {
        return defaultHttpClient().send(url, method, headers, params, body, clazz);
    }

    public static String send(String url, HttpMethod method, Map<String, String> headers,
                              Map<String, Object> params, Object body) throws HttpException {
        return defaultHttpClient().send(url, method, headers, params, body);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, Object> params, Object body,
                             String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().send(url, method, params, body, proxy, responseHandler);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, Object> params, Object body,
                             String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().send(url, method, params, body, proxy, clazz);
    }

    public static String send(String url, HttpMethod method, Map<String, Object> params, Object body,
                              String proxy) throws HttpException {
        return defaultHttpClient().send(url, method, params, body, proxy);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, Object> params, Object body,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().send(url, method, params, body, responseHandler);
    }

    public static <T> T send(String url, HttpMethod method, Map<String, Object> params, Object body,
                             Class<T> clazz) throws HttpException {
        return defaultHttpClient().send(url, method, params, body, clazz);
    }

    public static String send(String url, HttpMethod method, Map<String, Object> params, Object body)
            throws HttpException {
        return defaultHttpClient().send(url, method, params, body);
    }

    public static HttpResponse sendMultipartRequest(String url, Map<String, String> headers,
                                                    Map<String, Object> params, String proxy) throws HttpException {
        return defaultHttpClient().sendMultipartRequest(url, headers, params, proxy);
    }

    public static HttpResponse sendMultipartRequest(String url, Map<String, String> headers,
                                                    Map<String, Object> params) throws HttpException {
        return defaultHttpClient().sendMultipartRequest(url, headers, params);
    }

    public static HttpResponse sendMultipartRequest(String url, Map<String, Object> params,
                                                    String proxy) throws HttpException {
        return defaultHttpClient().sendMultipartRequest(url, params, proxy);
    }

    public static HttpResponse sendMultipartRequest(String url, Map<String, Object> params)
            throws HttpException {
        return defaultHttpClient().sendMultipartRequest(url, params);
    }

    public static <T> T sendMultipartForm(String url, Map<String, String> headers,
                                          Map<String, Object> params, String proxy, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, headers, params, proxy, responseHandler);
    }

    public static <T> T sendMultipartForm(String url, Map<String, String> headers,
                                          Map<String, Object> params, String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, headers, params, proxy, clazz);
    }

    public static String sendMultipartForm(String url, Map<String, String> headers,
                                           Map<String, Object> params, String proxy) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, headers, params, proxy);
    }

    public static <T> T sendMultipartForm(String url, Map<String, String> headers,
                                          Map<String, Object> params, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, headers, params, responseHandler);
    }

    public static <T> T sendMultipartForm(String url, Map<String, String> headers,
                                          Map<String, Object> params, Class<T> clazz) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, headers, params, clazz);
    }

    public static String sendMultipartForm(String url, Map<String, String> headers,
                                           Map<String, Object> params) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, headers, params);
    }

    public static <T> T sendMultipartForm(String url, Map<String, Object> params, String proxy,
                                          ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, params, proxy, responseHandler);
    }

    public static <T> T sendMultipartForm(String url, Map<String, Object> params, String proxy,
                                          Class<T> clazz) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, params, proxy, clazz);
    }

    public static String sendMultipartForm(String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, params, proxy);
    }

    public static <T> T sendMultipartForm(String url, Map<String, Object> params,
                                          ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, params, responseHandler);
    }

    public static <T> T sendMultipartForm(String url, Map<String, Object> params, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, params, clazz);
    }

    public static String sendMultipartForm(String url, Map<String, Object> params)
            throws HttpException {
        return defaultHttpClient().sendMultipartForm(url, params);
    }

    // ------------------------------- Get Request

    public static HttpResponse requestWithGet(String url, Map<String, String> headers,
                                              Map<String, Object> params, Object body, String proxy) throws HttpException {
        return defaultHttpClient().requestWithGet(url, headers, params, body, proxy);
    }

    public static HttpResponse requestWithGet(String url, Map<String, String> headers,
                                              Map<String, Object> params, Object body) throws HttpException {
        return defaultHttpClient().requestWithGet(url, headers, params, body);
    }

    public static HttpResponse requestWithGet(String url, Map<String, String> headers,
                                              Map<String, Object> params) throws HttpException {
        return defaultHttpClient().requestWithGet(url, headers, params);
    }

    public static HttpResponse requestWithGet(String url, Map<String, Object> params, Object body,
                                              String proxy) throws HttpException {
        return defaultHttpClient().requestWithGet(url, params, body, proxy);
    }

    public static HttpResponse requestWithGet(String url, Map<String, Object> params, Object body)
            throws HttpException {
        return defaultHttpClient().requestWithGet(url, params, body);
    }

    public static HttpResponse requestWithGet(String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return defaultHttpClient().requestWithGet(url, params, proxy);
    }

    public static HttpResponse requestWithGet(String url, Map<String, Object> params)
            throws HttpException {
        return defaultHttpClient().requestWithGet(url, params);
    }

    public static HttpResponse requestWithGet(String url, String proxy) throws HttpException {
        return defaultHttpClient().requestWithGet(url, proxy);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            Object body, String proxy, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().get(url, headers, params, body, proxy, responseHandler);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            Object body, String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().get(url, headers, params, body, proxy, clazz);
    }

    public static String get(String url, Map<String, String> headers, Map<String, Object> params,
                             Object body, String proxy) throws HttpException {
        return defaultHttpClient().get(url, headers, params, body, proxy);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            Object body, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().get(url, headers, params, body, responseHandler);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            Object body, Class<T> clazz) throws HttpException {
        return defaultHttpClient().get(url, headers, params, body, clazz);
    }

    public static String get(String url, Map<String, String> headers, Map<String, Object> params,
                             Object body) throws HttpException {
        return defaultHttpClient().get(url, headers, params, body);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, headers, params, null, proxy, responseHandler);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            String proxy, Class<T> clazz) throws HttpException {
        return get(url, headers, params, null, proxy, clazz);
    }

    public static String get(String url, Map<String, String> headers, Map<String, Object> params,
                             String proxy) throws HttpException {
        return get(url, headers, params, proxy, String.class);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            ResponseHandler<? extends T> responseHandler) throws HttpException {
        return get(url, headers, params, StringUtils.EMPTY, responseHandler);
    }

    public static <T> T get(String url, Map<String, String> headers, Map<String, Object> params,
                            Class<T> clazz) throws HttpException {
        return get(url, headers, params, StringUtils.EMPTY, clazz);
    }

    public static String get(String url, Map<String, String> headers, Map<String, Object> params)
            throws HttpException {
        return defaultHttpClient().get(url, headers, params);
    }

    public static <T> T get(String url, Map<String, Object> params, Object body, String proxy,
                            ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().get(url, params, body, proxy, responseHandler);
    }

    public static <T> T get(String url, Map<String, Object> params, Object body, String proxy,
                            Class<T> clazz) throws HttpException {
        return defaultHttpClient().get(url, params, body, proxy, clazz);
    }

    public static String get(String url, Map<String, Object> params, Object body, String proxy)
            throws HttpException {
        return defaultHttpClient().get(url, params, body, proxy);
    }

    public static <T> T get(String url, Map<String, Object> params, Object body,
                            ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().get(url, params, body, responseHandler);
    }

    public static <T> T get(String url, Map<String, Object> params, Object body, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().get(url, params, body, clazz);
    }

    public static String get(String url, Map<String, Object> params, Object body)
            throws HttpException {
        return defaultHttpClient().get(url, params, body);
    }

    public static <T> T get(String url, Map<String, Object> params, String proxy,
                            ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().get(url, params, proxy, responseHandler);
    }

    public static <T> T get(String url, Map<String, Object> params, String proxy, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().get(url, params, proxy, clazz);
    }

    public static String get(String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return defaultHttpClient().get(url, params, proxy);
    }

    public static <T> T get(String url, Map<String, Object> params,
                            ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().get(url, params, responseHandler);
    }

    public static <T> T get(String url, Map<String, Object> params, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().get(url, params, clazz);
    }

    public static String get(String url, Map<String, Object> params) throws HttpException {
        return defaultHttpClient().get(url, params);
    }

    public static <T> T get(String url, String proxy, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().get(url, proxy, responseHandler);
    }

    public static <T> T get(String url, String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().get(url, proxy, clazz);
    }

    public static String get(String url, String proxy) throws HttpException {
        return defaultHttpClient().get(url, proxy);
    }

    public static String get(String url) throws HttpException {
        return defaultHttpClient().get(url);
    }

    // ------------------------------- Post Request

    public static HttpResponse requestWithPost(String url, Map<String, String> headers,
                                               Map<String, Object> params, Object body, String proxy) throws HttpException {
        return defaultHttpClient().requestWithPost(url, headers, params, body, proxy);
    }

    public static HttpResponse requestWithPost(String url, Map<String, String> headers,
                                               Map<String, Object> params, Object body) throws HttpException {
        return defaultHttpClient().requestWithPost(url, headers, params, body);
    }

    public static HttpResponse requestWithPost(String url, Map<String, Object> params, Object body,
                                               String proxy) throws HttpException {
        return defaultHttpClient().requestWithPost(url, params, body, proxy);
    }

    public static HttpResponse requestWithPost(String url, Map<String, Object> params, Object body)
            throws HttpException {
        return defaultHttpClient().requestWithPost(url, params, body);
    }

    public static HttpResponse requestWithPost(String url, Map<String, Object> params)
            throws HttpException {
        return defaultHttpClient().requestWithPost(url, params);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             Object body, String proxy, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().post(url, headers, params, body, proxy, responseHandler);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             Object body, String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, headers, params, body, proxy, clazz);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params,
                              Object body, String proxy) throws HttpException {
        return defaultHttpClient().post(url, headers, params, body, proxy);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             Object body, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, headers, params, body, responseHandler);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             Object body, Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, headers, params, body, clazz);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params,
                              Object body) throws HttpException {
        return defaultHttpClient().post(url, headers, params, body);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, headers, params, proxy, responseHandler);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             String proxy, Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, headers, params, proxy, clazz);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params,
                              String proxy) throws HttpException {
        return defaultHttpClient().post(url, headers, params, proxy);
    }

    public static <T> T post(String url, Map<String, Object> params, Object body, String proxy,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, params, body, proxy, responseHandler);
    }

    public static <T> T post(String url, Map<String, Object> params, Object body, String proxy,
                             Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, params, body, proxy, clazz);
    }

    public static String post(String url, Map<String, Object> params, Object body, String proxy)
            throws HttpException {
        return defaultHttpClient().post(url, params, body, proxy);
    }

    public static <T> T post(String url, Map<String, Object> params, Object body,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, params, body, responseHandler);
    }

    public static <T> T post(String url, Map<String, Object> params, Object body, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().post(url, params, body, clazz);
    }

    public static String post(String url, Map<String, Object> params, Object body)
            throws HttpException {
        return defaultHttpClient().post(url, params, body);
    }

    public static <T> T post(String url, Map<String, Object> params, String proxy,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, params, proxy, responseHandler);
    }

    public static <T> T post(String url, Map<String, Object> params, String proxy, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().post(url, params, proxy, clazz);
    }

    public static String post(String url, Map<String, Object> params, String proxy)
            throws HttpException {
        return defaultHttpClient().post(url, params, proxy);
    }

    public static String post(String url, Map<String, Object> params) throws HttpException {
        return defaultHttpClient().post(url, params);
    }

    public static String post(String url) throws HttpException {
        return defaultHttpClient().post(url);
    }

    // ------------------------------- POST METHOD, support 'multipart/form-data'

    public static HttpResponse requestWithPost(String url, Map<String, String> headers,
                                               Map<String, Object> params,
                                               String proxy, boolean multipart) throws HttpException {
        return defaultHttpClient().requestWithPost(url, headers, params, proxy, multipart);
    }

    public static HttpResponse requestWithPost(String url, Map<String, String> headers,
                                               Map<String, Object> params, boolean multipart) throws HttpException {
        return defaultHttpClient().requestWithPost(url, headers, params, multipart);
    }

    public static HttpResponse requestWithPost(String url, Map<String, Object> params, String proxy,
                                               boolean multipart) throws HttpException {
        return defaultHttpClient().requestWithPost(url, params, proxy, multipart);
    }

    public static HttpResponse requestWithPost(String url, Map<String, Object> params,
                                               boolean multipart)
            throws HttpException {
        return defaultHttpClient().requestWithPost(url, params, multipart);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             String proxy, boolean multipart, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        return defaultHttpClient().post(url, headers, params, proxy, multipart, responseHandler);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             String proxy, boolean multipart, Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, headers, params, proxy, multipart, clazz);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params,
                              String proxy, boolean multipart) throws HttpException {
        return defaultHttpClient().post(url, headers, params, proxy, multipart);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             boolean multipart, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, headers, params, multipart, responseHandler);
    }

    public static <T> T post(String url, Map<String, String> headers, Map<String, Object> params,
                             boolean multipart, Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, headers, params, multipart, clazz);
    }

    public static String post(String url, Map<String, String> headers, Map<String, Object> params,
                              boolean multipart) throws HttpException {
        return defaultHttpClient().post(url, headers, params, multipart);
    }

    public static <T> T post(String url, Map<String, Object> params, String proxy, boolean multipart,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, params, proxy, multipart, responseHandler);
    }

    public static <T> T post(String url, Map<String, Object> params, String proxy, boolean multipart,
                             Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, params, proxy, multipart, clazz);
    }

    public static String post(String url, Map<String, Object> params, String proxy, boolean multipart)
            throws HttpException {
        return defaultHttpClient().post(url, params, proxy, multipart);
    }

    public static <T> T post(String url, Map<String, Object> params, boolean multipart,
                             ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().post(url, params, multipart, responseHandler);
    }

    public static <T> T post(String url, Map<String, Object> params, boolean multipart,
                             Class<T> clazz) throws HttpException {
        return defaultHttpClient().post(url, params, multipart, clazz);
    }

    public static String post(String url, Map<String, Object> params, boolean multipart)
            throws HttpException {
        return defaultHttpClient().post(url, params, multipart);
    }

    // ------------------------------- POST METHOD, special

    public static <T> T postJSON(String url, Map<String, String> headers, String json, String proxy,
                                 ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().postJSON(url, headers, json, proxy, responseHandler);
    }

    public static <T> T postJSON(String url, Map<String, String> headers, String json, String proxy,
                                 Class<T> clazz) throws HttpException {
        return defaultHttpClient().postJSON(url, headers, json, proxy, clazz);
    }

    public static String postJSON(String url, Map<String, String> headers, String json, String proxy)
            throws HttpException {
        return defaultHttpClient().postJSON(url, headers, json, proxy);
    }

    public static String postJSON(String url, Map<String, String> headers, String json)
            throws HttpException {
        return defaultHttpClient().postJSON(url, headers, json);
    }

    public static <T> T postJSON(String url, String json, String proxy,
                                 ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().postJSON(url, json, proxy, responseHandler);
    }

    public static <T> T postJSON(String url, String json, String proxy, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().postJSON(url, json, proxy, clazz);
    }

    public static String postJSON(String url, String json, String proxy) throws HttpException {
        return defaultHttpClient().postJSON(url, json, proxy);
    }

    public static String postJSON(String url, String json) throws HttpException {
        return defaultHttpClient().postJSON(url, json);
    }

    public static <T> T postForm(String url, Map<String, String> headers, String params, String proxy,
                                 ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().postForm(url, headers, params, proxy, responseHandler);
    }

    public static <T> T postForm(String url, Map<String, String> headers, String params, String proxy,
                                 Class<T> clazz) throws HttpException {
        return defaultHttpClient().postForm(url, headers, params, proxy, clazz);
    }

    public static String postForm(String url, Map<String, String> headers, String params,
                                  String proxy) throws HttpException {
        return defaultHttpClient().postForm(url, headers, params, proxy);
    }

    public static String postForm(String url, Map<String, String> headers, String params)
            throws HttpException {
        return defaultHttpClient().postForm(url, headers, params);
    }

    public static <T> T postForm(String url, String params, String proxy,
                                 ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().postForm(url, params, proxy, responseHandler);
    }

    public static <T> T postForm(String url, String params, String proxy, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().postForm(url, params, proxy, clazz);
    }

    public static String postForm(String url, String params, String proxy) throws HttpException {
        return defaultHttpClient().postForm(url, params, proxy);
    }

    public static String postForm(String url, String params) throws HttpException {
        return defaultHttpClient().postForm(url, params);
    }

    public static <T> T postText(String url, Map<String, String> headers, String text, String proxy,
                                 ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().postText(url, headers, text, proxy, responseHandler);
    }

    public static <T> T postText(String url, Map<String, String> headers, String text, String proxy,
                                 Class<T> clazz) throws HttpException {
        return defaultHttpClient().postText(url, headers, text, proxy, clazz);
    }

    public static String postText(String url, Map<String, String> headers, String text, String proxy)
            throws HttpException {
        return defaultHttpClient().postText(url, headers, text, proxy);
    }

    public static String postText(String url, Map<String, String> headers, String text)
            throws HttpException {
        return defaultHttpClient().postText(url, headers, text);
    }

    public static <T> T postText(String url, String text, String proxy,
                                 ResponseHandler<? extends T> responseHandler) throws HttpException {
        return defaultHttpClient().postText(url, text, proxy, responseHandler);
    }

    public static <T> T postText(String url, String text, String proxy, Class<T> clazz)
            throws HttpException {
        return defaultHttpClient().postText(url, text, proxy, clazz);
    }

    public static String postText(String url, String text, String proxy) throws HttpException {
        return defaultHttpClient().postText(url, text, proxy);
    }

    public static String postText(String url, String text) throws HttpException {
        return defaultHttpClient().postText(url, text);
    }

}
