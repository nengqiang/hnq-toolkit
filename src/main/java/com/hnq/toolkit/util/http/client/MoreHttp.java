package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.MoreHttpRequest;
import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;

import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public interface MoreHttp extends IGetHttp, IPostHttp {

    // ------------------------------- POST METHOD, special

    // ------------------------------- POST with "application/json"

    default HttpResponse sendJSON(String url, Map<String, String> headers, String json, String proxy)
            throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(json)
                .setContentType(ContentType.APPLICATION_JSON).setProxy(proxy);

        return sendRequest(request);
    }

    default HttpResponse sendJSON(String url, Map<String, String> headers, String json)
            throws HttpException {
        return sendJSON(url, headers, json, StringUtils.EMPTY);
    }

    default HttpResponse sendJSON(String url, String json, String proxy) throws HttpException {
        return sendJSON(url, null, json, proxy);
    }

    default HttpResponse sendJSON(String url, String json) throws HttpException {
        return sendJSON(url, json, StringUtils.EMPTY);
    }

    default <T> T postJSON(String url, Map<String, String> headers, String json, String proxy,
                           ResponseHandler<? extends T> responseHandler) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(json)
                .setContentType(ContentType.APPLICATION_JSON).setProxy(proxy);

        return send(request, responseHandler);
    }

    default <T> T postJSON(String url, Map<String, String> headers, String json, String proxy,
                           Class<T> clazz) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(json)
                .setContentType(ContentType.APPLICATION_JSON).setProxy(proxy);

        return send(request, clazz);
    }

    default <T> T postJSON(String url, String json, String proxy,
                           ResponseHandler<? extends T> responseHandler) throws HttpException {
        return postJSON(url, null, json, proxy, responseHandler);
    }

    default <T> T postJSON(String url, String json, String proxy, Class<T> clazz)
            throws HttpException {
        return postJSON(url, null, json, proxy, clazz);
    }

    default String postJSON(String url, Map<String, String> headers, String json, String proxy)
            throws HttpException {
        return postJSON(url, headers, json, proxy, String.class);
    }

    default String postJSON(String url, Map<String, String> headers, String json)
            throws HttpException {
        return postJSON(url, headers, json, StringUtils.EMPTY);
    }

    default String postJSON(String url, String json, String proxy) throws HttpException {
        return postJSON(url, json, proxy, String.class);
    }

    default String postJSON(String url, String json) throws HttpException {
        return postJSON(url, json, StringUtils.EMPTY);
    }

    // ------------------------------- POST with "application/x-www-form-urlencoded"

    default HttpResponse sendForm(String url, Map<String, String> headers, String params, String proxy) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(params)
                .setContentType(ContentType.APPLICATION_FORM_URLENCODED, Consts.UTF_8)
                .setProxy(proxy);

        return sendRequest(request);
    }

    default HttpResponse sendForm(String url, Map<String, String> headers, String params) throws HttpException {
        return sendForm(url, headers, params, StringUtils.EMPTY);
    }

    default HttpResponse sendForm(String url, String params, String proxy) throws HttpException {
        return sendForm(url, null, params, proxy);
    }

    default HttpResponse sendForm(String url, String params) throws HttpException {
        return sendForm(url, params, StringUtils.EMPTY);
    }

    default <T> T postForm(String url, Map<String, String> headers, String params, String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(params)
                .setContentType(ContentType.APPLICATION_FORM_URLENCODED, Consts.UTF_8)
                .setProxy(proxy);

        return send(request, responseHandler);
    }

    default <T> T postForm(String url, Map<String, String> headers, String params, String proxy, Class<T> clazz) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(params)
                .setContentType(ContentType.APPLICATION_FORM_URLENCODED, Consts.UTF_8)
                .setProxy(proxy);

        return send(request, clazz);
    }

    default <T> T postForm(String url, String params, String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return postForm(url, null, params, proxy, responseHandler);
    }

    default <T> T postForm(String url, String params, String proxy, Class<T> clazz) throws HttpException {
        return postForm(url, null, params, proxy, clazz);
    }

    default String postForm(String url, Map<String, String> headers, String params, String proxy) throws HttpException {
        return postForm(url, headers, params, proxy, String.class);
    }

    default String postForm(String url, Map<String, String> headers, String params) throws HttpException {
        return postForm(url, headers, params, StringUtils.EMPTY);
    }

    default String postForm(String url, String params, String proxy) throws HttpException {
        return postForm(url, params, proxy, String.class);
    }

    default String postForm(String url, String params) throws HttpException {
        return postForm(url, params, StringUtils.EMPTY);
    }

    // ------------------------------- POST with "text/plain"

    default HttpResponse sendText(String url, Map<String, String> headers, String text, String proxy) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(text)
                .setContentType(ContentType.TEXT_PLAIN, Consts.UTF_8).setProxy(proxy);

        return sendRequest(request);
    }

    default HttpResponse sendText(String url, Map<String, String> headers, String text) throws HttpException {
        return sendText(url, headers, text, StringUtils.EMPTY);
    }

    default HttpResponse sendText(String url, String text, String proxy) throws HttpException {
        return sendText(url, null, text, proxy);
    }

    default HttpResponse sendText(String url, String text) throws HttpException {
        return sendText(url, text, StringUtils.EMPTY);
    }

    default <T> T postText(String url, Map<String, String> headers, String text, String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(text)
                .setContentType(ContentType.TEXT_PLAIN, Consts.UTF_8).setProxy(proxy);

        return send(request, responseHandler);
    }

    default <T> T postText(String url, Map<String, String> headers, String text, String proxy, Class<T> clazz) throws HttpException {
        MoreHttpRequest request = MoreHttpRequest.post(url).setHeaders(headers).setEntity(text)
                .setContentType(ContentType.TEXT_PLAIN, Consts.UTF_8).setProxy(proxy);

        return send(request, clazz);
    }

    default <T> T postText(String url, String text, String proxy, ResponseHandler<? extends T> responseHandler) throws HttpException {
        return postText(url, null, text, proxy, responseHandler);
    }

    default <T> T postText(String url, String text, String proxy, Class<T> clazz) throws HttpException {
        return postText(url, null, text, proxy, clazz);
    }

    default String postText(String url, Map<String, String> headers, String text, String proxy) throws HttpException {
        return postText(url, headers, text, proxy, String.class);
    }

    default String postText(String url, Map<String, String> headers, String text) throws HttpException {
        return postText(url, headers, text, StringUtils.EMPTY);
    }

    default String postText(String url, String text, String proxy) throws HttpException {
        return postText(url, text, proxy, String.class);
    }

    default String postText(String url, String text) throws HttpException {
        return postText(url, text, StringUtils.EMPTY);
    }

}
