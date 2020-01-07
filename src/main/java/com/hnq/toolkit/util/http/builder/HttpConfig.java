package com.hnq.toolkit.util.http.builder;

import com.hnq.toolkit.util.kryo.KryoUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpConfig implements Serializable {

    private HttpHost proxy;
    private InetAddress localAddress;

    private String cookieSpec;
    private CookieStore cookieStore;
    private boolean expectContinueEnabled;
    private boolean redirectsEnabled;
    private boolean relativeRedirectsAllowed;
    private boolean circularRedirectsAllowed;
    private int maxRedirects;
    private boolean authenticationEnabled;
    private Collection<String> targetPreferredAuthSchemes;
    private Collection<String> proxyPreferredAuthSchemes;
    private boolean contentCompressionEnabled;

    private int defaultSocketTimeout;
    private int socketTimeout;
    private int connectTimeout;
    private int connectionRequestTimeout;

    private PoolConfig poolConfig;
    private MessageConfig messageConfig;
    private SSLConfig sslConfig;
    private boolean ignoreSSLAuth;

    private String userAgent;

    private HttpConfig() {
        this.redirectsEnabled = true;
        this.maxRedirects = 50;
        this.relativeRedirectsAllowed = true;
        this.authenticationEnabled = true;
        this.connectionRequestTimeout = -1;
        this.connectTimeout = -1;
        this.socketTimeout = -1;
        this.contentCompressionEnabled = true;
    }

    public static HttpConfig custom() {
        return new HttpConfig();
    }

    public PoolConfig usePoolConfig() {
        if (this.poolConfig == null) {
            this.poolConfig = new PoolConfig();
        }

        return this.poolConfig;
    }

    public PoolConfig getPoolConfig() {
        return poolConfig;
    }

    public MessageConfig useMessageConfig() {
        if (this.messageConfig == null) {
            this.messageConfig = new MessageConfig();
        }

        return this.messageConfig;
    }

    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    public SSLConfig getSslConfig() {
        return sslConfig;
    }

    public SSLConfig useSslConfig() {
        if (this.sslConfig == null) {
            this.sslConfig = new SSLConfig();
        }

        return this.sslConfig;
    }


    public HttpHost getProxy() {
        return proxy;
    }

    public HttpConfig setProxy(HttpHost proxy) {
        this.proxy = proxy;
        return this;
    }

    public InetAddress getLocalAddress() {
        return localAddress;
    }

    public HttpConfig setLocalAddress(InetAddress localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public String getCookieSpec() {
        return cookieSpec;
    }

    public HttpConfig setCookieSpec(String cookieSpec) {
        this.cookieSpec = cookieSpec;
        return this;
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public HttpConfig setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
        return this;
    }

    public boolean isExpectContinueEnabled() {
        return expectContinueEnabled;
    }

    public HttpConfig setExpectContinueEnabled(boolean expectContinueEnabled) {
        this.expectContinueEnabled = expectContinueEnabled;
        return this;
    }

    public boolean isRedirectsEnabled() {
        return redirectsEnabled;
    }

    public HttpConfig setRedirectsEnabled(boolean redirectsEnabled) {
        this.redirectsEnabled = redirectsEnabled;
        return this;
    }

    public boolean isRelativeRedirectsAllowed() {
        return relativeRedirectsAllowed;
    }

    public HttpConfig setRelativeRedirectsAllowed(boolean relativeRedirectsAllowed) {
        this.relativeRedirectsAllowed = relativeRedirectsAllowed;
        return this;
    }

    public boolean isCircularRedirectsAllowed() {
        return circularRedirectsAllowed;
    }

    public HttpConfig setCircularRedirectsAllowed(boolean circularRedirectsAllowed) {
        this.circularRedirectsAllowed = circularRedirectsAllowed;
        return this;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public HttpConfig setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
        return this;
    }

    public boolean isAuthenticationEnabled() {
        return authenticationEnabled;
    }

    public HttpConfig setAuthenticationEnabled(boolean authenticationEnabled) {
        this.authenticationEnabled = authenticationEnabled;
        return this;
    }

    public Collection<String> getTargetPreferredAuthSchemes() {
        return targetPreferredAuthSchemes;
    }

    public HttpConfig setTargetPreferredAuthSchemes(
            Collection<String> targetPreferredAuthSchemes) {
        this.targetPreferredAuthSchemes = targetPreferredAuthSchemes;
        return this;
    }

    public Collection<String> getProxyPreferredAuthSchemes() {
        return proxyPreferredAuthSchemes;
    }

    public HttpConfig setProxyPreferredAuthSchemes(
            Collection<String> proxyPreferredAuthSchemes) {
        this.proxyPreferredAuthSchemes = proxyPreferredAuthSchemes;
        return this;
    }

    public boolean isContentCompressionEnabled() {
        return contentCompressionEnabled;
    }

    public HttpConfig setContentCompressionEnabled(boolean contentCompressionEnabled) {
        this.contentCompressionEnabled = contentCompressionEnabled;
        return this;
    }

    public int getDefaultSocketTimeout() {
        return defaultSocketTimeout;
    }

    public HttpConfig setDefaultSocketTimeout(int defaultSocketTimeout) {
        this.defaultSocketTimeout = defaultSocketTimeout;
        return this;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public HttpConfig setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public HttpConfig setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public HttpConfig setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
        return this;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public HttpConfig setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    public boolean isIgnoreSSLAuth() {
        return ignoreSSLAuth;
    }

    public HttpConfig setIgnoreSSLAuth(boolean ignoreSSLAuth) {
        this.ignoreSSLAuth = ignoreSSLAuth;
        return this;
    }

    public static class PoolConfig implements Serializable {

        private long connTimeToLive;
        private TimeUnit connTimeToLiveTimeUnit;
        private int maxTotal;
        private int defaultMaxPerRoute;
        private int validateAfterInactivity;


        PoolConfig() {
            this.connTimeToLive = -1;
            this.connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;
            this.maxTotal = 100;
            this.defaultMaxPerRoute = 20;
            this.validateAfterInactivity = -1;
        }

        public long getConnTimeToLive() {
            return connTimeToLive;
        }

        public PoolConfig setConnTimeToLive(long connTimeToLive) {
            this.connTimeToLive = connTimeToLive;
            return this;
        }

        public TimeUnit getConnTimeToLiveTimeUnit() {
            return connTimeToLiveTimeUnit;
        }

        public PoolConfig setConnTimeToLiveTimeUnit(TimeUnit connTimeToLiveTimeUnit) {
            this.connTimeToLiveTimeUnit = connTimeToLiveTimeUnit;
            return this;
        }

        public int getMaxTotal() {
            return maxTotal;
        }

        public PoolConfig setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
            return this;
        }

        public int getDefaultMaxPerRoute() {
            return defaultMaxPerRoute;
        }

        public PoolConfig setDefaultMaxPerRoute(int defaultMaxPerRoute) {
            this.defaultMaxPerRoute = defaultMaxPerRoute;
            return this;
        }

        public int getValidateAfterInactivity() {
            return validateAfterInactivity;
        }

        public PoolConfig setValidateAfterInactivity(int validateAfterInactivity) {
            this.validateAfterInactivity = validateAfterInactivity;
            return this;
        }

        public boolean isCustom() {
            return validateAfterInactivity > 0;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("maxTotal", maxTotal)
                    .append("defaultMaxPerRoute", defaultMaxPerRoute)
                    .append("validateAfterInactivity", validateAfterInactivity)
                    .toString();
        }
    }

    public static class MessageConfig implements Serializable {

        private int maxLineLength;
        private int maxHeaderCount;

        MessageConfig() {
            this.maxLineLength = -1;
            this.maxHeaderCount = -1;
        }

        public int getMaxLineLength() {
            return maxLineLength;
        }

        public MessageConfig setMaxLineLength(final int maxLineLength) {
            this.maxLineLength = maxLineLength;
            return this;
        }

        public int getMaxHeaderCount() {
            return maxHeaderCount;
        }

        public MessageConfig setMaxHeaderCount(final int maxHeaderCount) {
            this.maxHeaderCount = maxHeaderCount;
            return this;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("maxLineLength", maxLineLength)
                    .append("maxHeaderCount", maxHeaderCount)
                    .toString();
        }
    }

    public class SSLConfig implements Serializable {

        private String keyStoreType;
        private String keyStoreFile;
        private String password;

        SSLConfig() {
        }

        public String getKeyStoreType() {
            return keyStoreType;
        }

        public void setKeyStoreType(String keyStoreType) {
            this.keyStoreType = keyStoreType;
        }

        public String getKeyStoreFile() {
            return keyStoreFile;
        }

        public void setKeyStoreFile(String keyStoreFile) {
            this.keyStoreFile = keyStoreFile;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public char[] getPasswordChars() {
            return getPassword().toCharArray();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                    .append("keyStoreType", keyStoreType)
                    .append("keyStoreFile", keyStoreFile)
                    .append("password", password)
                    .toString();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("proxy", proxy)
                .append("localAddress", localAddress)
                .append("cookieSpec", cookieSpec)
                .append("expectContinueEnabled", expectContinueEnabled)
                .append("redirectsEnabled", redirectsEnabled)
                .append("relativeRedirectsAllowed", relativeRedirectsAllowed)
                .append("circularRedirectsAllowed", circularRedirectsAllowed)
                .append("maxRedirects", maxRedirects)
                .append("authenticationEnabled", authenticationEnabled)
                .append("targetPreferredAuthSchemes", targetPreferredAuthSchemes)
                .append("proxyPreferredAuthSchemes", proxyPreferredAuthSchemes)
                .append("contentCompressionEnabled", contentCompressionEnabled)
                .append("defaultSocketTimeout", defaultSocketTimeout)
                .append("socketTimeout", socketTimeout)
                .append("connectTimeout", connectTimeout)
                .append("connectionRequestTimeout", connectionRequestTimeout)
                .append("poolConfig", poolConfig)
                .append("messageConfig", messageConfig)
                .append("sslConfig", sslConfig)
                .append("ignoreSSLAuth", ignoreSSLAuth)
                .append("userAgent", userAgent)
                .toString();
    }

    public HttpConfig copy() {
        return KryoUtils.copy(this);
    }

}
