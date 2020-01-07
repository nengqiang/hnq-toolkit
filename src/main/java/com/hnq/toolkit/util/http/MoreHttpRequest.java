package com.hnq.toolkit.util.http;

import com.hnq.toolkit.util.http.config.Proxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpMethod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class MoreHttpRequest implements Serializable {

    private final String url;
    private HttpMethod method;
    private Map<String, String> headers;
    private Map<String, Object> parameters;
    private Object entity;
    private boolean multipartForm;
    private Proxy proxy;
    private Charset charset;
    private ContentType contentType;
    private String contentEncoding;
    private boolean chunked;
    private boolean gzipCompress;
    private int connectionRequestTimeout;
    private int connectTimeout;
    private int socketTimeout;
    private boolean redirectsEnabled;
    private boolean relativeRedirectsAllowed;
    private boolean circularRedirectsAllowed;
    private int maxRedirects;
    private String cookieSpec;
    private boolean expectContinueEnabled;
    private boolean contentCompressionEnabled;
    private String cookies;
    private String referer;
    private String userAgent;

    private MoreHttpRequest(@Nonnull String url, HttpMethod method) {
        this.url = Objects.requireNonNull(url);
        this.method = method == null ? HttpMethod.GET : method;
        this.charset = Consts.UTF_8;
        this.connectionRequestTimeout = 5000;
        this.connectTimeout = 5000;
        this.socketTimeout = 5000;
        this.redirectsEnabled = true;
        this.maxRedirects = 50;
        this.relativeRedirectsAllowed = true;
        this.contentCompressionEnabled = true;
    }

    private MoreHttpRequest(@Nonnull String url, boolean multipartForm) {
        this(url, multipartForm ? HttpMethod.POST : null);
        this.multipartForm = multipartForm;
    }

    public static MoreHttpRequest from(@Nonnull String url, @Nullable HttpMethod method) {
        return new MoreHttpRequest(url, method);
    }

    public static MoreHttpRequest from(@Nonnull String url) {
        return from(url, null);
    }

    public static MoreHttpRequest get(@Nonnull String url) {
        return from(url, HttpMethod.GET);
    }

    public static MoreHttpRequest post(@Nonnull String url) {
        return from(url, HttpMethod.POST);
    }

    public static MoreHttpRequest multipartForm(@Nonnull String url) {
        return new MoreHttpRequest(url, true);
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public MoreHttpRequest setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public MoreHttpRequest setHeaders(Map<String, String> headers) {
        if (headers != null) {
            this.headers = new HashMap<>(headers);
        }
        return this;
    }

    public MoreHttpRequest addHeader(String key, String value) {
        ensureHeaders().put(key, StringUtils.defaultString(value));
        return this;
    }

    private Map<String, String> ensureHeaders() {
        if (headers == null) {
            headers = new HashMap<>(8);
        }
        return headers;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public MoreHttpRequest setParameters(Map<String, Object> parameters) {
        if (parameters != null) {
            this.parameters = new HashMap<>(parameters);
        }
        return this;
    }

    public MoreHttpRequest addParameter(String key, Object value) {
        ensureParams().put(key, value);
        return this;
    }

    public MoreHttpRequest addParameter(String key, String value) {
        ensureParams().put(key, StringUtils.defaultString(value));
        return this;
    }

    private Map<String, Object> ensureParams() {
        if (parameters == null) {
            parameters = new HashMap<>(8);
        }
        return parameters;
    }

    public Object getEntity() {
        return entity;
    }

    public MoreHttpRequest setEntity(Object entity) {
        this.entity = entity;
        return this;
    }

    public boolean isMultipartForm() {
        return multipartForm;
    }

    public MoreHttpRequest setMultipartForm(boolean multipartForm) {
        this.multipartForm = multipartForm;
        return this;
    }

    public Proxy getProxy() {
        return proxy;
    }

    public MoreHttpRequest setProxy(String host, int port) {
        this.proxy = new Proxy(StringUtils.trim(host), port);
        return this;
    }

    public MoreHttpRequest setProxy(String inetAddress) {
        String proxy = StringUtils.trim(inetAddress);
        if (StringUtils.isEmpty(proxy)) {
            return this;
        }

        String[] values = proxy.split(":");
        if (values.length == 1) {
            return setProxy(values[0], 80);
        } else if (values.length == 2) {
            return setProxy(values[0], Integer.parseInt(values[1]));
        } else {
            throw new IllegalArgumentException("Incorrect proxy!");
        }
    }

    public Charset getCharset() {
        return charset;
    }

    public MoreHttpRequest setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public MoreHttpRequest setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public MoreHttpRequest setContentType(String contentType, Charset charset) {
        this.charset = charset;
        this.contentType = ContentType.create(StringUtils.trim(contentType), this.charset);
        return this;
    }

    public MoreHttpRequest setContentType(ContentType contentType, Charset charset) {
        this.charset = charset;
        this.contentType = contentType.withCharset(this.charset);
        return this;
    }

    public String getCookies() {
        return cookies;
    }

    public MoreHttpRequest setCookies(String cookies) {
        this.cookies = StringUtils.trim(cookies);
        return this;
    }

    public String getReferer() {
        return referer;
    }

    public MoreHttpRequest setReferer(String referer) {
        this.referer = StringUtils.trim(referer);
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public MoreHttpRequest setUserAgent(String userAgent) {
        this.userAgent = StringUtils.trim(userAgent);
        return this;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public MoreHttpRequest setContentEncoding(String contentEncoding) {
        this.contentEncoding = StringUtils.trim(contentEncoding);
        return this;
    }

    public boolean isChunked() {
        return chunked;
    }

    public MoreHttpRequest setChunked(boolean chunked) {
        this.chunked = chunked;
        return this;
    }

    public boolean isGzipCompress() {
        return gzipCompress;
    }

    public MoreHttpRequest setGzipCompress(boolean gzipCompress) {
        this.gzipCompress = gzipCompress;
        return this;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public MoreHttpRequest setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public MoreHttpRequest setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public MoreHttpRequest setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public boolean isRedirectsEnabled() {
        return redirectsEnabled;
    }

    public MoreHttpRequest enableRedirects(boolean redirectsEnabled) {
        this.redirectsEnabled = redirectsEnabled;
        return this;
    }

    public boolean isRelativeRedirectsAllowed() {
        return relativeRedirectsAllowed;
    }

    public MoreHttpRequest allowRelativeRedirects(boolean relativeRedirectsAllowed) {
        this.relativeRedirectsAllowed = relativeRedirectsAllowed;
        return this;
    }

    public boolean isCircularRedirectsAllowed() {
        return circularRedirectsAllowed;
    }

    public MoreHttpRequest allowCircularRedirects(boolean circularRedirectsAllowed) {
        this.circularRedirectsAllowed = circularRedirectsAllowed;
        return this;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public MoreHttpRequest setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
        return this;
    }

    public String getCookieSpec() {
        return cookieSpec;
    }

    public MoreHttpRequest setCookieSpec(String cookieSpec) {
        this.cookieSpec = StringUtils.trim(cookieSpec);
        return this;
    }

    public boolean isExpectContinueEnabled() {
        return expectContinueEnabled;
    }

    public MoreHttpRequest enableExpectContinue(boolean expectContinueEnabled) {
        this.expectContinueEnabled = expectContinueEnabled;
        return this;
    }

    public boolean isContentCompressionEnabled() {
        return contentCompressionEnabled;
    }

    public MoreHttpRequest enableContentCompression(boolean contentCompressionEnabled) {
        this.contentCompressionEnabled = contentCompressionEnabled;
        return this;
    }

}
