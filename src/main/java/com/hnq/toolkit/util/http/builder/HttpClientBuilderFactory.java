package com.hnq.toolkit.util.http.builder;

import com.hnq.toolkit.util.http.config.Proxy;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpClientBuilderFactory {

    private static final int NOT_SETTING = -1;

    private HttpClientBuilderFactory() {
    }

    public static HttpClientBuilder defaultBuilder(final int maxTotal, final int maxPerRoute,
                                                   final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                   final int soTimeout, final String userAgent, final Proxy proxy) {
        return HttpClientBuilders.createDefault(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout, userAgent, proxy);
    }

    public static HttpClientBuilder defaultBuilder(final int maxTotal, final int maxPerRoute,
                                                   final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                   final int soTimeout) {
        return defaultBuilder(maxTotal, maxPerRoute, defaultSoTimeout, connRequestTimeout, connTimeout,
                soTimeout, null, null);
    }

    public static HttpClientBuilder defaultBuilder(final int defaultSoTimeout,
                                                   final int connRequestTimeout, final int connTimeout, final int soTimeout) {
        return defaultBuilder(NOT_SETTING, NOT_SETTING, defaultSoTimeout, connRequestTimeout,
                connTimeout, soTimeout);
    }

    public static HttpClientBuilder defaultBuilder(final int connRequestTimeout,
                                                   final int connTimeout, final int soTimeout) {
        return defaultBuilder(NOT_SETTING, connRequestTimeout, connTimeout, soTimeout);
    }

    public static HttpClientBuilder defaultBuilder(final int maxTotal, final int maxPerRoute) {
        return defaultBuilder(maxTotal, maxPerRoute, NOT_SETTING, NOT_SETTING, NOT_SETTING,
                NOT_SETTING);
    }

    public static HttpClientBuilder defaultBuilder(final String userAgent, final Proxy proxy) {
        return defaultBuilder(NOT_SETTING, NOT_SETTING, NOT_SETTING, NOT_SETTING, NOT_SETTING,
                NOT_SETTING, userAgent, proxy);
    }

    public static HttpClientBuilder defaultBuilder() {
        return HttpClientBuilders.defaultBuilder();
    }

    public static HttpClientBuilder customBuilder(final int maxTotal, final int maxPerRoute,
                                                  final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                  final int soTimeout, final String userAgent, final Proxy proxy) {
        HttpClientBuilder httpBuilder = defaultBuilder(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout, userAgent, proxy);

        HttpClientBuilders.setCustomRedirectStrategy(httpBuilder);

        return httpBuilder;
    }

    public static HttpClientBuilder customBuilder(final int maxTotal, final int maxPerRoute,
                                                  final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                  final int soTimeout) {
        return customBuilder(maxTotal, maxPerRoute, defaultSoTimeout, connRequestTimeout, connTimeout,
                soTimeout, null, null);
    }

    public static HttpClientBuilder customBuilder(final int defaultSoTimeout,
                                                  final int connRequestTimeout, final int connTimeout, final int soTimeout) {
        return customBuilder(NOT_SETTING, NOT_SETTING, defaultSoTimeout, connRequestTimeout,
                connTimeout, soTimeout);
    }

    public static HttpClientBuilder customBuilder(final int connRequestTimeout, final int connTimeout,
                                                  final int soTimeout) {
        return customBuilder(NOT_SETTING, connRequestTimeout, connTimeout, soTimeout);
    }

    public static HttpClientBuilder customBuilder(final int maxTotal, final int maxPerRoute) {
        return customBuilder(maxTotal, maxPerRoute, NOT_SETTING, NOT_SETTING, NOT_SETTING,
                NOT_SETTING);
    }

    public static HttpClientBuilder customBuilder(final String userAgent, final Proxy proxy) {
        return customBuilder(NOT_SETTING, NOT_SETTING, NOT_SETTING, NOT_SETTING, NOT_SETTING,
                NOT_SETTING, userAgent, proxy);
    }

    public static HttpClientBuilder customBuilder() {
        return HttpClientBuilders.customBuilder();
    }

    public static HttpConfig defaultHttpConfig(final int maxTotal, final int maxPerRoute,
                                               final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                               final int soTimeout, final String userAgent, final Proxy proxy) {
        return HttpConfigs.defaultHttpConfig(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout, userAgent, proxy);
    }

    public static HttpConfig defaultHttpConfig() {
        return HttpConfigs.defaultHttpConfig();
    }

}
