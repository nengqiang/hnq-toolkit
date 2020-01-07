package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.builder.HttpClientFactory;
import com.hnq.toolkit.util.http.config.Proxy;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class MoreHttpFactory {

    private MoreHttpFactory() {
    }

    public static MoreHttp createDefault() {
        return MoreHttpClient.getInstance(HttpClientFactory.defaultClient());
    }

    public static MoreHttp createDefault(final int maxTotal, final int maxPerRoute,
                                         final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                         final int soTimeout, final String userAgent, final Proxy proxy) {
        CloseableHttpClient httpClient = HttpClientFactory
                .defaultClient(maxTotal, maxPerRoute, defaultSoTimeout, connRequestTimeout, connTimeout,
                        soTimeout, userAgent, proxy);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createDefault(final int defaultSoTimeout, final int connRequestTimeout,
                                         final int connTimeout, final int soTimeout) {
        CloseableHttpClient httpClient = HttpClientFactory
                .defaultClient(defaultSoTimeout, connRequestTimeout, connTimeout, soTimeout);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createDefault(final int connRequestTimeout, final int connTimeout,
                                         final int soTimeout) {
        CloseableHttpClient httpClient = HttpClientFactory
                .defaultClient(connRequestTimeout, connTimeout, soTimeout);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createDefault(final int maxTotal, final int maxPerRoute) {
        CloseableHttpClient httpClient = HttpClientFactory.defaultClient(maxTotal, maxPerRoute);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createDefault(final String userAgent, final Proxy proxy) {
        CloseableHttpClient httpClient = HttpClientFactory.defaultClient(userAgent, proxy);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createCustom() {
        return MoreHttpClient.getInstance(HttpClientFactory.customClient());
    }

    public static MoreHttp createCustom(final int maxTotal, final int maxPerRoute,
                                        final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                        final int soTimeout, final String userAgent, final Proxy proxy) {
        CloseableHttpClient httpClient = HttpClientFactory
                .customClient(maxTotal, maxPerRoute, defaultSoTimeout, connRequestTimeout, connTimeout,
                        soTimeout, userAgent, proxy);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createCustom(final int maxTotal, final int maxPerRoute,
                                        final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                        final int soTimeout) {
        CloseableHttpClient httpClient = HttpClientFactory
                .customClient(maxTotal, maxPerRoute, defaultSoTimeout, connRequestTimeout, connTimeout,
                        soTimeout);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createCustom(final int defaultSoTimeout, final int connRequestTimeout,
                                        final int connTimeout, final int soTimeout) {
        CloseableHttpClient httpClient = HttpClientFactory
                .customClient(defaultSoTimeout, connRequestTimeout, connTimeout, soTimeout);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createCustom(final int connRequestTimeout, final int connTimeout,
                                        final int soTimeout) {
        CloseableHttpClient httpClient = HttpClientFactory
                .customClient(connRequestTimeout, connTimeout, soTimeout);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createCustom(final int maxTotal, final int maxPerRoute) {
        CloseableHttpClient httpClient = HttpClientFactory.customClient(maxTotal, maxPerRoute);
        return MoreHttpClient.getInstance(httpClient);
    }

    public static MoreHttp createCustom(final String userAgent, final Proxy proxy) {
        CloseableHttpClient httpClient = HttpClientFactory.customClient(userAgent, proxy);
        return MoreHttpClient.getInstance(httpClient);
    }

}
