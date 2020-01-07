package com.hnq.toolkit.util.http.builder;

import com.google.common.collect.Lists;
import com.hnq.toolkit.util.http.config.MockHeaders;
import com.hnq.toolkit.util.http.config.Proxy;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.AuthSchemes;

import java.util.Collections;

import static com.hnq.toolkit.util.http.builder.HttpConfig.custom;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpConfigs {

    public static final int CONNECTION_MAX_TOTAL = 200;
    public static final int CONNECTION_MAX_PER_ROUTE = 50;
    public static final int DEFAULT_SO_TIMEOUT = 5000;
    public static final int CONNECTION_REQUEST_TIMEOUT = 5000;
    public static final int CONNECT_TIMEOUT = 10000;
    public static final int SO_TIMEOUT = 10000;
    public static final int MAX_REDIRECTS = 15;
    public static final String CUSTOM_COOKIE_SPEC = "custom";

    private HttpConfigs() {
    }

    public static HttpConfig defaultHttpConfig() {
        return Basic.DEFAULT_HTTP_CONFIG.copy();
    }

    public static HttpConfig defaultHttpConfig(final int maxTotal, final int maxPerRoute,
                                               final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                               final int soTimeout, final String userAgent, final Proxy proxy) {
        HttpConfig config = defaultHttpConfig();

        if (defaultSoTimeout >= 0) {
            config.setDefaultSocketTimeout(defaultSoTimeout);
        }

        if (connRequestTimeout >= 0) {
            config.setConnectionRequestTimeout(connRequestTimeout);
        }

        if (connTimeout >= 0) {
            config.setConnectTimeout(connTimeout);
        }

        if (soTimeout >= 0) {
            config.setSocketTimeout(soTimeout);
        }

        HttpConfig.PoolConfig poolConfig = config.usePoolConfig();
        if (maxTotal >= 0) {
            poolConfig.setMaxTotal(maxTotal);
        }

        if (maxPerRoute >= 0) {
            poolConfig.setDefaultMaxPerRoute(maxPerRoute);
        }

        if (StringUtils.isNotEmpty(userAgent)) {
            config.setUserAgent(userAgent);
        }

        if (proxy != null) {
            config.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }

        return config;
    }

    static class Basic {

        static HttpConfig DEFAULT_HTTP_CONFIG;

        static {
            HttpConfig config = custom()
                    .setDefaultSocketTimeout(DEFAULT_SO_TIMEOUT)
                    .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    .setSocketTimeout(SO_TIMEOUT)
                    .setCookieSpec(CUSTOM_COOKIE_SPEC)
                    .setExpectContinueEnabled(true)
                    .setRedirectsEnabled(true)
                    .setRelativeRedirectsAllowed(true)
                    .setCircularRedirectsAllowed(true)
                    .setMaxRedirects(MAX_REDIRECTS)
                    .setTargetPreferredAuthSchemes(Lists.newArrayList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                    .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                    .setUserAgent(MockHeaders.DEFAULT_USER_AGENT)
                    .setIgnoreSSLAuth(true);

            config.usePoolConfig().setMaxTotal(CONNECTION_MAX_TOTAL)
                    .setDefaultMaxPerRoute(CONNECTION_MAX_PER_ROUTE)
                    .setValidateAfterInactivity(1000);

            config.useMessageConfig().setMaxHeaderCount(200).setMaxLineLength(2000);

            DEFAULT_HTTP_CONFIG = config;
        }
    }

}
