package com.hnq.toolkit.util.http.builder;

import org.apache.http.client.config.RequestConfig;

import javax.annotation.Nonnull;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class RequestConfigs {

    private RequestConfigs() {
    }

    public static RequestConfig defaultRequestConfig() {
        return build(HttpConfigs.Basic.DEFAULT_HTTP_CONFIG);
    }

    public static RequestConfig.Builder defaultRequestConfigBuilder() {
        return newBuilder(HttpConfigs.Basic.DEFAULT_HTTP_CONFIG);
    }

    /**
     * Create global request configuration.
     *
     * @return {@link RequestConfig}
     */
    public static RequestConfig build(@Nonnull final HttpConfig config) {
        return newBuilder(config).build();
    }

    private static RequestConfig.Builder newBuilder(@Nonnull HttpConfig config) {
        return RequestConfig.custom().setCookieSpec(config.getCookieSpec())
                .setExpectContinueEnabled(config.isExpectContinueEnabled())
                .setRedirectsEnabled(config.isRedirectsEnabled())
                .setRelativeRedirectsAllowed(config.isRelativeRedirectsAllowed())
                .setCircularRedirectsAllowed(config.isCircularRedirectsAllowed())
                .setMaxRedirects(config.getMaxRedirects())
                .setAuthenticationEnabled(config.isAuthenticationEnabled())
                .setContentCompressionEnabled(config.isContentCompressionEnabled())
                .setLocalAddress(config.getLocalAddress())
                .setProxy(config.getProxy())
                .setProxyPreferredAuthSchemes(config.getProxyPreferredAuthSchemes())
                .setTargetPreferredAuthSchemes(config.getTargetPreferredAuthSchemes())
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnectTimeout())
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout());
    }

}
