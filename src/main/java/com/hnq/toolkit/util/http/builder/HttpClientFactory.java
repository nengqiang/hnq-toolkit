package com.hnq.toolkit.util.http.builder;

import com.hnq.toolkit.util.http.config.Proxy;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpClientFactory {

    private HttpClientFactory() {
    }

    /**
     * 获取默认的HttpClient对象.
     *
     * @return {@link CloseableHttpClient}
     */
    public static CloseableHttpClient create() {
        return defaultClient();
    }

    public static CloseableHttpClient defaultClient(final int maxTotal, final int maxPerRoute,
                                                    final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                    final int soTimeout, final String userAgent, final Proxy proxy) {
        return HttpClientBuilderFactory.defaultBuilder(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout, userAgent, proxy).build();
    }

    public static CloseableHttpClient defaultClient(final int defaultSoTimeout,
                                                    final int connRequestTimeout, final int connTimeout, final int soTimeout) {
        return HttpClientBuilderFactory.defaultBuilder(defaultSoTimeout, connRequestTimeout,
                connTimeout, soTimeout).build();
    }

    public static CloseableHttpClient defaultClient(final int connRequestTimeout,
                                                    final int connTimeout, final int soTimeout) {
        return HttpClientBuilderFactory.defaultBuilder(connRequestTimeout, connTimeout, soTimeout)
                .build();
    }

    public static CloseableHttpClient defaultClient(final int maxTotal, final int maxPerRoute) {
        return HttpClientBuilderFactory.defaultBuilder(maxTotal, maxPerRoute).build();
    }

    public static CloseableHttpClient defaultClient(final String userAgent, final Proxy proxy) {
        return HttpClientBuilderFactory.defaultBuilder(userAgent, proxy).build();
    }

    /**
     * 获取默认设置的HttpClient对象.
     *
     * @return {@link CloseableHttpClient}
     */
    public static CloseableHttpClient defaultClient() {
        return HttpClientBuilderFactory.defaultBuilder().build();
    }

    public static CloseableHttpClient customClient(final int maxTotal, final int maxPerRoute,
                                                   final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                   final int soTimeout, final String userAgent, final Proxy proxy) {
        return HttpClientBuilderFactory.customBuilder(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout, userAgent, proxy).build();
    }

    public static CloseableHttpClient customClient(final int maxTotal, final int maxPerRoute,
                                                   final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                   final int soTimeout) {
        return HttpClientBuilderFactory.customBuilder(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout).build();
    }

    public static CloseableHttpClient customClient(final int defaultSoTimeout,
                                                   final int connRequestTimeout, final int connTimeout, final int soTimeout) {
        return HttpClientBuilderFactory.customBuilder(defaultSoTimeout, connRequestTimeout, connTimeout,
                soTimeout).build();
    }

    public static CloseableHttpClient customClient(final int connRequestTimeout,
                                                   final int connTimeout, final int soTimeout) {
        return HttpClientBuilderFactory.customBuilder(connRequestTimeout, connTimeout, soTimeout)
                .build();
    }

    public static CloseableHttpClient customClient(final int maxTotal, final int maxPerRoute) {
        return HttpClientBuilderFactory.customBuilder(maxTotal, maxPerRoute).build();
    }

    public static CloseableHttpClient customClient(final String userAgent, final Proxy proxy) {
        return HttpClientBuilderFactory.customBuilder(userAgent, proxy).build();
    }

    /**
     * 获取自定义设置的HttpClient对象.
     *
     * @return {@link CloseableHttpClient}
     */
    public static CloseableHttpClient customClient() {
        return HttpClientBuilderFactory.customBuilder().build();
    }

}
