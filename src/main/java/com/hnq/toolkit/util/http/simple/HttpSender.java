package com.hnq.toolkit.util.http.simple;

import com.hnq.toolkit.util.http.builder.HttpClientBuilderFactory;
import com.hnq.toolkit.util.http.builder.HttpConfigs;
import com.hnq.toolkit.util.http.cookie.HttpCookies;
import com.hnq.toolkit.util.net.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
@Slf4j
public class HttpSender {

    private final CloseableHttpClient httpClient;
    private final CookieStore cookieStore;
    private volatile String userAgent;
    private volatile String proxy;

    public HttpSender() {
        this(StringUtils.EMPTY);
    }

    public HttpSender(String userAgent) {
        this(userAgent, null);
    }

    public HttpSender(String userAgent, String proxy) {
        this(null, userAgent, proxy);
    }

    public HttpSender(CookieStore cookieStore, String userAgent, String proxy) {
        this.cookieStore = cookieStore == null ? new BasicCookieStore() : cookieStore;
        this.userAgent = userAgent;
        this.proxy = proxy;
        this.httpClient = getDefaultHttpClient(this.cookieStore);
    }

    /**
     * 获取默认的HttpClient对象.
     *
     * @return {@link CloseableHttpClient}
     */
    private CloseableHttpClient getDefaultHttpClient(CookieStore cookieStore) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilderFactory.customBuilder();
        httpClientBuilder.setDefaultCookieStore(cookieStore);

        return httpClientBuilder.build();
    }

    private static RequestConfig.Builder requestBuilder(HttpHost proxy) {
        return RequestConfig.custom().setCookieSpec(HttpConfigs.CUSTOM_COOKIE_SPEC)
                .setExpectContinueEnabled(true).setRedirectsEnabled(true).setCircularRedirectsAllowed(true)
                .setMaxRedirects(HttpConfigs.MAX_REDIRECTS)
                .setConnectionRequestTimeout(HttpConfigs.CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(HttpConfigs.CONNECT_TIMEOUT).setSocketTimeout(HttpConfigs.SO_TIMEOUT)
                .setProxy(proxy);
    }

    public String send(String url) throws IOException {
        return send(url, null);
    }

    public String send(String url, String referer) throws IOException {
        CloseableHttpResponse response;

        for (int i = 0; ; i++) {
            try {
                response = sendRequest(url, referer);
                break;
            } catch (IOException e) {
                if (i < 2) {
                    if (e instanceof ConnectTimeoutException) {
                        if (this.proxy != null) {
                            this.proxy = null;
                        }
                        log.warn("The proxy was not connected. Drop proxy and retry! >>> {}", e.getMessage());
                        continue;
                    } else if (e instanceof SocketTimeoutException) {
                        log.warn("Retry to sendRequest request when socket timed out. >>> {}", e.getMessage());
                        continue;
                    }
                }

                throw e;
            }
        }

        try {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (statusCode == HttpStatus.SC_OK) {
                Charset charset = getCharset(entity);
                if (charset == null) {
                    charset = Consts.UTF_8;
                }

                return EntityUtils.toString(entity, charset);
            }

            log.warn("Unexpected response after sending request[{}], response.statusCode: {}", url,
                    statusCode);
            EntityUtils.consume(entity);

            return StringUtils.EMPTY;
        } catch (Exception e) {
            try {
                EntityUtils.consume(response.getEntity());
            } catch (IOException ex) {
                log.debug("I/O error while releasing connection", ex);
            }
            throw e;
        } finally {
            response.close();
        }
    }

    private Charset getCharset(HttpEntity entity) {
        try {
            ContentType contentType = ContentType.get(entity);
            return contentType.getCharset();
        } catch (ParseException | UnsupportedCharsetException e) {
            log.warn("Error parsing charset from response entity.", e);
        }

        return null;
    }

    private CloseableHttpResponse sendRequest(String url, String referer) throws IOException {
        HttpUriRequest request = buildRequest(url, referer, this.userAgent, this.proxy);

        return httpClient.execute(request);
    }

    private static HttpUriRequest buildRequest(String url, String referer, String userAgent,
                                               String proxy) {
        RequestBuilder requestBuilder = RequestBuilder.get(url);
        if (StringUtils.isNotEmpty(userAgent)) {
            requestBuilder.setHeader(HttpHeaders.USER_AGENT, userAgent);
        }
        if (StringUtils.isNotEmpty(referer)) {
            requestBuilder.setHeader(HttpHeaders.REFERER, referer);
        }

        if (StringUtils.isNotEmpty(proxy)) {
            HttpHost proxyHost = parseProxy(proxy);

            if (proxyHost != null) {
                RequestConfig requestConfig = requestBuilder(proxyHost).build();
                requestBuilder.setConfig(requestConfig);
            }
        }

        return requestBuilder.build();
    }

    private static HttpHost parseProxy(String proxy) {
        HttpHost proxyHost = null;
        String[] values = proxy.split(":");
        if (values.length == 2) {
            String host = values[0];
            int port = Integer.parseInt(values[1]);
            if (NetUtils.isConnectable(host, port, 5000)) {
                proxyHost = new HttpHost(host, port);
            }
        }
        return proxyHost;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setCookies(Map<String, String> cookies, String... domains) {
        HttpHelper.addCookies(cookieStore, cookies, domains);
    }

    public void setCookies(String cookie, String... domains) {
        Map<String, String> cookieMap = HttpCookies.parseCookiesAsMap(cookie);
        setCookies(cookieMap, domains);
    }

    public void close() {
        if (this.httpClient != null) {
            try {
                this.httpClient.close();
            } catch (IOException e) {
                log.error("Error closing http client.", e);
            }
        }
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

}
