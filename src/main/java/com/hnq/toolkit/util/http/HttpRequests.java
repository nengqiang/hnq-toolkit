package com.hnq.toolkit.util.http;

import com.hnq.toolkit.util.http.builder.HttpConfigs;
import com.hnq.toolkit.util.http.config.Proxy;
import com.hnq.toolkit.util.http.cookie.HttpCookies;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.GzipCompressingEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpMethod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Static utilities relating to {@link HttpRequest}.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
@Slf4j
public class HttpRequests {

    private static class Headers {

        private static final String COOKIE_DELIMITER = ";";
        private Map<String, String> map;

        public Headers(Map<String, String> map) {
            this.map = map;
        }

        private Map<String, String> ensureMap() {
            if (this.map == null) {
                this.map = new HashMap<>(8);
            }
            return this.map;
        }

        public Headers setCookies(String cookies) {
            if (StringUtils.isNotEmpty(cookies)) {
                ensureMap().compute(HttpHeaders.COOKIE, (key, oldValue) -> {
                    String val = StringUtils.trim(oldValue);
                    if (StringUtils.isEmpty(oldValue)) {
                        return cookies;
                    }

                    if (val.endsWith(COOKIE_DELIMITER)) {
                        return val + " " + cookies;
                    } else {
                        return val + COOKIE_DELIMITER + " " + cookies;
                    }
                });
            }
            return this;
        }

        public Headers setReferer(String referer) {
            if (StringUtils.isNotEmpty(referer)) {
                ensureMap().put(HttpHeaders.REFERER, referer);
            }
            return this;
        }

        public Headers setUserAgent(String userAgent) {
            if (StringUtils.isNotEmpty(userAgent)) {
                ensureMap().put(HttpHeaders.USER_AGENT, userAgent);
            }
            return this;
        }

        public Map<String, String> get() {
            return this.map;
        }
    }

    private HttpRequests() {
    }


    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static List<String> getCookies(@Nonnull final HttpRequest request) {
        Objects.requireNonNull(request);
        return HttpHeaders.getHeaderValues(request, HttpHeaders.COOKIE);
    }

    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static List<HttpCookie> getHttpCookies(@Nonnull final HttpRequest request) {
        Objects.requireNonNull(request);
        Header[] headers = request.getHeaders(HttpHeaders.COOKIE);

        return HttpCookies.getHttpCookies(headers);
    }

    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static List<String> getSimpleCookies(@Nonnull final HttpRequest request) {
        Objects.requireNonNull(request);
        Header[] headers = request.getHeaders(HttpHeaders.COOKIE);

        return HttpCookies.getCookies(headers);
    }

    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static String getCookieString(@Nonnull final HttpRequest request) {
        Objects.requireNonNull(request);
        Header[] headers = request.getHeaders(HttpHeaders.COOKIE);

        return HttpCookies.getCookieString(headers);
    }

    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static Map<String, String> getCookieMap(@Nonnull final HttpRequest request) {
        Objects.requireNonNull(request);
        Header[] headers = request.getHeaders(HttpHeaders.COOKIE);

        return HttpCookies.getCookieMap(headers);
    }

    /**
     * 获取指定cookie的值.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @param key cookie键值对中想要取出的键
     * @return 请求头中Cookie项中给定key对应的值
     */
    public static String getCookieValue(@Nonnull final HttpRequest request, final String key) {
        return getCookieMap(request).get(key);
    }

    /**
     * 由自定义的{@link MoreHttpRequest}转换成{@link HttpUriRequest}
     */
    public static HttpUriRequest from(@Nonnull final MoreHttpRequest request) {
        Objects.requireNonNull(request);

        Charset charset = request.getCharset();
        ContentType contentType = request.getContentType();
        if (charset == null) {
            if (contentType != null) {
                charset = contentType.getCharset();
            }
        } else if (contentType != null && !charset.equals(contentType.getCharset())) {
            contentType = contentType.withCharset(charset);
        }

        NameValuePair[] parameters = null;
        HttpEntity entity = null;
        Object body = request.getEntity();
        if (request.isMultipartForm()) {
            if (!(body instanceof HttpEntity) || !body.getClass().getSimpleName()
                    .startsWith("Multipart")) {
                entity = HttpEntities.createMultipartEntity(request.getParameters(), contentType, charset);
            }
        } else {
            parameters = HttpParameters.transformToArray(request.getParameters());
            boolean useCustomEntity = contentType == null && body instanceof String && (
                    HttpMethod.POST.equals(request.getMethod()) || HttpMethod.PUT
                            .equals(request.getMethod()));
            if (useCustomEntity) {
                entity = new StringEntity((String) body, ContentType.APPLICATION_FORM_URLENCODED
                        .withCharset(HttpEntities.defaultCharset(charset)));
            } else {
                entity = HttpEntities.createEntity(body, contentType, charset);
            }

            if (entity instanceof AbstractHttpEntity) {
                ((AbstractHttpEntity) entity).setContentEncoding(request.getContentEncoding());
                ((AbstractHttpEntity) entity).setChunked(request.isChunked());
                if (request.isGzipCompress()) {
                    entity = new GzipCompressingEntity(entity);
                }
            }
        }

        RequestConfig config = buildRequestConfig(request);

        Headers headers = new Headers(request.getHeaders());
        headers.setCookies(request.getCookies());
        headers.setReferer(request.getReferer());
        headers.setUserAgent(request.getUserAgent());

        return create(request.getUrl(), request.getMethod(), headers.get(), parameters, entity,
                config, charset);
    }


    private static RequestConfig buildRequestConfig(@Nonnull MoreHttpRequest request) {
        RequestConfig.Builder builder = RequestConfig.custom()
                .setConnectionRequestTimeout(request.getConnectionRequestTimeout())
                .setConnectTimeout(request.getConnectTimeout()).setSocketTimeout(request.getSocketTimeout())
                .setRedirectsEnabled(request.isRedirectsEnabled())
                .setRelativeRedirectsAllowed(request.isRelativeRedirectsAllowed())
                .setCircularRedirectsAllowed(request.isCircularRedirectsAllowed())
                .setMaxRedirects(request.getMaxRedirects()).setCookieSpec(
                        StringUtils.defaultIfEmpty(request.getCookieSpec(), HttpConfigs.CUSTOM_COOKIE_SPEC))
                .setExpectContinueEnabled(request.isExpectContinueEnabled())
                .setContentCompressionEnabled(request.isContentCompressionEnabled());

        Proxy proxy = request.getProxy();
        if (proxy != null) {
            builder.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }

        return builder.build();
    }

    /**
     * Create {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest create(@Nonnull final String url, @Nonnull final HttpMethod method,
                                        final Map<String, String> headers, final NameValuePair[] params, final HttpEntity entity,
                                        final RequestConfig config, final Charset charset) {
        RequestBuilder requestBuilder = RequestBuilder.create(method.name()).setUri(url);

        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach(requestBuilder::addHeader);
        }

        if (ArrayUtils.isNotEmpty(params)) {
            requestBuilder.addParameters(params);
        }

        if (entity != null) {
            requestBuilder.setEntity(entity);
        }

        if (config != null) {
            requestBuilder.setConfig(config);
        }

        return requestBuilder.setCharset(HttpEntities.defaultCharset(charset)).build();
    }

    /**
     * Create {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest create(@Nonnull final String url, @Nonnull final HttpMethod method,
                                        final Map<String, String> headers, final NameValuePair[] params, final HttpEntity entity,
                                        final String proxy) {
        if (log.isDebugEnabled()) {
            log.debug("Initial HttpUriRequest >>> url: {}, method: {}, parameters: {}, headers: {}, proxy: {}",
                    url, method, Jackson.toJSONString(params), Jackson.toJSONString(headers), proxy);
        }

        RequestConfig config = null;
        if (StringUtils.isNotEmpty(proxy)) {
            HttpHost host = null;

            String[] values = proxy.split(":");
            if (values.length == 1) {
                host = new HttpHost(values[0], 80);
            } else if (values.length == 2) {
                host = new HttpHost(values[0], Integer.parseInt(values[1]));
            }

            if (host != null) {
                config = RequestConfig.custom().setProxy(host).setConnectionRequestTimeout(5000)
                        .setConnectTimeout(5000).setSocketTimeout(5000).setCookieSpec(
                                HttpConfigs.CUSTOM_COOKIE_SPEC).build();
            }
        }

        return create(url, method, headers, params, entity, config, null);
    }

    /**
     * Create {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest create(@Nonnull final String url, @Nonnull final HttpMethod method,
                                        final NameValuePair[] params, final HttpEntity entity, final String proxy) {
        return create(url, method, null, params, entity, proxy);
    }

    /**
     * Create {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest create(@Nonnull final String url, @Nonnull final HttpMethod method,
                                        final Map<String, String> headers, final Map<String, Object> params, final Object body,
                                        final String proxy) {
        NameValuePair[] parameters = HttpParameters.transformToArray(params);
        HttpEntity entity = adaptEntity(body, method);

        return create(url, method, headers, parameters, entity, proxy);
    }

    /**
     * Create {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest create(@Nonnull final String url, @Nonnull final HttpMethod method,
                                        final Map<String, Object> params, final Object body, final String proxy) {
        NameValuePair[] parameters = HttpParameters.transformToArray(params);
        HttpEntity entity = adaptEntity(body, method);

        return create(url, method, parameters, entity, proxy);
    }

    private static HttpEntity adaptEntity(Object body, HttpMethod method) {
        if (body instanceof String && (HttpMethod.POST.equals(method) || HttpMethod.PUT
                .equals(method))) {
            return new StringEntity((String) body,
                    ContentType.APPLICATION_FORM_URLENCODED.withCharset(Consts.UTF_8));
        }

        return HttpEntities.createEntity(body);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final Map<String, String> headers,
                                     final NameValuePair[] params, final HttpEntity entity, final String proxy) {
        return create(url, HttpMethod.GET, headers, params, entity, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final Map<String, String> headers,
                                     final NameValuePair[] params, final String proxy) {
        return get(url, headers, params, null, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final NameValuePair[] params,
                                     final HttpEntity entity, final String proxy) {
        return get(url, null, params, entity, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final NameValuePair[] params,
                                     final String proxy) {
        return get(url, params, null, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final Map<String, String> headers,
                                     final Map<String, Object> params, final Object body, final String proxy) {
        return create(url, HttpMethod.GET, headers, params, body, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final Map<String, String> headers,
                                     final Map<String, Object> params, final String proxy) {
        return get(url, headers, params, null, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final Map<String, Object> params,
                                     final Object body, final String proxy) {
        return get(url, null, params, body, proxy);
    }

    /**
     * Create GET {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest get(@Nonnull final String url, final Map<String, Object> params,
                                     final String proxy) {
        return get(url, params, null, proxy);
    }

    public static HttpUriRequest get(@Nonnull final String url, @Nullable final String referer,
                                     @Nullable final String userAgent) {
        RequestBuilder requestBuilder = RequestBuilder.get(url);

        if (StringUtils.isNotEmpty(userAgent)) {
            requestBuilder.setHeader(HttpHeaders.USER_AGENT, userAgent);
        }

        if (StringUtils.isNotEmpty(referer)) {
            requestBuilder.setHeader(HttpHeaders.REFERER, referer);
        }

        return requestBuilder.build();
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, String> headers,
                                      final NameValuePair[] params, final HttpEntity entity, final String proxy) {
        return create(url, HttpMethod.POST, headers, params, entity, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, String> headers,
                                      final NameValuePair[] params, final String proxy) {
        return post(url, headers, params, null, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final NameValuePair[] params,
                                      final HttpEntity entity, final String proxy) {
        return post(url, null, params, entity, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final NameValuePair[] params,
                                      final String proxy) {
        return post(url, params, null, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, String> headers,
                                      final Map<String, Object> params, final Object body, final String proxy) {
        return create(url, HttpMethod.POST, headers, params, body, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, String> headers,
                                      final Map<String, Object> params, final String proxy) {
        return post(url, headers, params, null, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, Object> params,
                                      final Object body, final String proxy) {
        return post(url, null, params, body, proxy);
    }

    /**
     * Create POST {@link HttpRequest}.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, Object> params,
                                      final String proxy) {
        return post(url, params, null, proxy);
    }

    /**
     * Create {@link HttpRequest} which content-type is 'multipart/form-data'.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest createMultipart(@Nonnull final String url,
                                                 final Map<String, String> headers, final Map<String, Object> params, final String proxy) {
        HttpEntity entity = HttpEntities.createMultipartEntity(params);

        return post(url, headers, new NameValuePair[0], entity, proxy);
    }

    /**
     * Create {@link HttpRequest} which content-type is 'multipart/form-data'.
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest createMultipart(@Nonnull final String url,
                                                 final Map<String, Object> params, final String proxy) {
        return createMultipart(url, null, params, proxy);
    }

    /**
     * Create POST {@link HttpRequest}. if {@code multipart} is true, the request's content-type will
     * be set as "multipart/form-data".
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, String> headers,
                                      final Map<String, Object> params, final String proxy, final boolean multipart) {
        if (multipart) {
            return createMultipart(url, headers, params, proxy);
        }

        return post(url, headers, params, proxy);
    }

    /**
     * Create POST {@link HttpRequest}. if {@code multipart} is true, the request's content-type will
     * be set as "multipart/form-data".
     *
     * @return {@link HttpUriRequest}
     */
    public static HttpUriRequest post(@Nonnull final String url, final Map<String, Object> params,
                                      final String proxy, final boolean multipart) {
        return post(url, null, params, proxy, multipart);
    }

}
