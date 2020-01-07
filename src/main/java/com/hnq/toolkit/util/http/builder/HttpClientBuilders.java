package com.hnq.toolkit.util.http.builder;

import com.hnq.toolkit.util.http.config.Proxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.config.*;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.CookieSpecRegistries;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.ssl.SSLContextBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import static com.hnq.toolkit.util.http.builder.HttpConfigs.CUSTOM_COOKIE_SPEC;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
@Slf4j
public class HttpClientBuilders {

    private static final String[] DATE_PATTERNS = {
            DateUtils.PATTERN_RFC1123,
            DateUtils.PATTERN_RFC1036,
            DateUtils.PATTERN_ASCTIME,
            "EEE, dd-MMM-yyyy HH:mm:ss z",
            "EEE, dd-MMM-yyyy HH-mm-ss z",
            "EEE, dd MMM yy HH:mm:ss z",
            "EEE dd-MMM-yyyy HH:mm:ss z",
            "EEE dd MMM yyyy HH:mm:ss z",
            "EEE dd-MMM-yyyy HH-mm-ss z",
            "EEE dd-MMM-yy HH:mm:ss z",
            "EEE dd MMM yy HH:mm:ss z",
            "EEE,dd-MMM-yy HH:mm:ss z",
            "EEE,dd-MMM-yyyy HH:mm:ss z",
            "EEE, dd-MM-yyyy HH:mm:ss z",
            "EEE MMM dd HH:mm:ss Z yyyy"
    };

    private HttpClientBuilders() {
    }

    public static HttpClientBuilder defaultBuilder() {
        return Holder.DEFAULT_BUILDER;
    }

    public static HttpClientBuilder customBuilder() {
        return Holder.CUSTOM_BUILDER;
    }

    public static HttpClientBuilder createDefault(final int maxTotal, final int maxPerRoute,
                                                  final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                  final int soTimeout, final String userAgent, final Proxy proxy) {
        HttpConfig config = HttpConfigs.defaultHttpConfig(maxTotal, maxPerRoute, defaultSoTimeout,
                connRequestTimeout, connTimeout, soTimeout, userAgent, proxy);

        return create(config);
    }

    public static HttpClientBuilder copyDefault(final int maxTotal, final int maxPerRoute,
                                                final int defaultSoTimeout, final int connRequestTimeout, final int connTimeout,
                                                final int soTimeout, final String userAgent, final Proxy proxy) {
        HttpConfig config = HttpConfigs.defaultHttpConfig();
        config.setDefaultSocketTimeout(defaultSoTimeout)
                .setConnectionRequestTimeout(connRequestTimeout)
                .setConnectTimeout(connTimeout)
                .setSocketTimeout(soTimeout)
                .setUserAgent(userAgent);

        if (proxy != null) {
            config.setProxy(new HttpHost(proxy.getHost(), proxy.getPort()));
        }

        config.usePoolConfig().setMaxTotal(maxTotal).setDefaultMaxPerRoute(maxPerRoute);

        return create(config);
    }

    public static HttpClientBuilder create(@Nonnull final HttpConfig config) {
        HttpClientBuilder builder = HttpClients.custom();

        HttpClientConnectionManager connectionManager = makeConnectionManager(config);
        if (connectionManager != null) {
            builder.setConnectionManager(connectionManager);
        } else {
            if (config.isIgnoreSSLAuth()) {
                builder.setSSLSocketFactory(CustomConfig.SSL_SOCKET_FACTORY);
            } else {
                SSLContext sslContext = makeSSLContext(config.getSslConfig());
                if (sslContext != null) {
                    builder.setSSLContext(sslContext);
                }
            }

            ConnectionConfig connectionConfig = buildConnectionConfig(config);
            if (connectionConfig != null) {
                builder.setDefaultConnectionConfig(connectionConfig);
            }

            SocketConfig socketConfig = buildSocketConfig(config);
            if (socketConfig != null) {
                builder.setDefaultSocketConfig(socketConfig);
            }

            HttpConfig.PoolConfig poolConfig = config.getPoolConfig();
            if (poolConfig != null) {
                builder.setConnectionTimeToLive(poolConfig.getConnTimeToLive(),
                        poolConfig.getConnTimeToLiveTimeUnit());
                builder.setMaxConnTotal(poolConfig.getMaxTotal());
                builder.setMaxConnPerRoute(poolConfig.getDefaultMaxPerRoute());
            }
        }

        RequestConfig defaultRequestConfig = RequestConfigs.build(config);
        builder.setDefaultRequestConfig(defaultRequestConfig).setProxy(config.getProxy());

        if (StringUtils.isNotEmpty(config.getUserAgent())) {
            builder.setUserAgent(config.getUserAgent());
        }

        CookieStore cookieStore = config.getCookieStore();
        if(cookieStore !=null){
            builder.setDefaultCookieStore(cookieStore);
        }

        DefaultCookieSpecProvider custom = new DefaultCookieSpecProvider(null,
                PublicSuffixMatcherLoader.getDefault(), DATE_PATTERNS, false);
        Registry<CookieSpecProvider> registry = CookieSpecRegistries.createDefaultBuilder()
                .register(CUSTOM_COOKIE_SPEC, custom).build();
        builder.setDefaultCookieSpecRegistry(registry);

        return builder;
    }

    /**
     * Create a connection manager
     */
    private static HttpClientConnectionManager makeConnectionManager(@Nonnull HttpConfig config) {
        HttpConfig.PoolConfig poolConfig = config.getPoolConfig();
        if (poolConfig != null && poolConfig.isCustom()) {
            ConnectionSocketFactory sslSocketFactory = makeSSLSocketFactory(config);

            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslSocketFactory)
                            .build(),
                    null,
                    null,
                    null,
                    poolConfig.getConnTimeToLive(),
                    poolConfig.getConnTimeToLiveTimeUnit() != null ? poolConfig.getConnTimeToLiveTimeUnit()
                            : TimeUnit.MILLISECONDS);

            ConnectionConfig connectionConfig = buildConnectionConfig(config);
            if (connectionConfig != null) {
                connectionManager.setDefaultConnectionConfig(connectionConfig);
            }

            SocketConfig socketConfig = buildSocketConfig(config);
            if (socketConfig != null) {
                connectionManager.setDefaultSocketConfig(socketConfig);
            }

            connectionManager.setMaxTotal(poolConfig.getMaxTotal());
            connectionManager.setDefaultMaxPerRoute(poolConfig.getDefaultMaxPerRoute());
            connectionManager.setValidateAfterInactivity(poolConfig.getValidateAfterInactivity());

            return connectionManager;
        }

        return null;
    }

    /**
     * create global connection config.
     *
     * @return {@link ConnectionConfig}
     */
    private static ConnectionConfig buildConnectionConfig(@Nonnull final HttpConfig config) {
        HttpConfig.MessageConfig messageConfig = config.getMessageConfig();
        if (messageConfig != null) {
            // Create message constraints
            MessageConstraints messageConstraints =
                    MessageConstraints.custom().setMaxHeaderCount(messageConfig.getMaxHeaderCount())
                            .setMaxLineLength(messageConfig.getMaxLineLength()).build();
            // Create connection configuration
            return ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE)
                    .setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                    .setMessageConstraints(messageConstraints).build();
        }

        return null;
    }

    private static SocketConfig buildSocketConfig(@Nonnull final HttpConfig config) {
        int defaultSocketTimeout = config.getDefaultSocketTimeout();
        if (defaultSocketTimeout > 0) {
            return SocketConfig.custom().setSoTimeout(defaultSocketTimeout)
                    .build();
        }

        return null;
    }

    private static ConnectionSocketFactory makeSSLSocketFactory(HttpConfig config) {
        if (config.isIgnoreSSLAuth()) {
            return CustomConfig.SSL_SOCKET_FACTORY;
        }

        SSLContext sslContext = makeSSLContext(config.getSslConfig());
        if (sslContext != null) {
            String[] supportedProtocols = split(System.getProperty("https.protocols"));
            String[] supportedCipherSuites = split(System.getProperty("https.cipherSuites"));

            return new SSLConnectionSocketFactory(
                    sslContext, supportedProtocols, supportedCipherSuites,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        }

        return SSLConnectionSocketFactory.getSocketFactory();
    }

    private static String[] split(final String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        return s.split("\\s*,\\s*");
    }

    private static SSLContext makeSSLContext(@Nullable final HttpConfig.SSLConfig sslConfig) {
        if (sslConfig != null) {
            try {
                KeyStore ks = initKeyStore(sslConfig);
                TrustManagerFactory tmf = TrustManagerFactory
                        .getInstance(TrustManagerFactory.getDefaultAlgorithm());
                tmf.init(ks);
                SSLContext context = SSLContext.getInstance("SSL");
                //这里只指定了受信任的证书（单向认证），如果是双向认证的话，则第一个参数不能为null
                context.init(null, tmf.getTrustManagers(), null);

                return context;
            } catch (Exception e) {
                log.error("Error setting ssl context for http client builder", e);
            }
        }

        return null;
    }

    private static KeyStore initKeyStore(@Nonnull final HttpConfig.SSLConfig sslConfig)
            throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        try (FileInputStream in = new FileInputStream(sslConfig.getKeyStoreFile())) {
            KeyStore ks = KeyStore.getInstance(sslConfig.getKeyStoreType());
            ks.load(in, sslConfig.getPasswordChars());

            return ks;
        }
    }

    static void setCustomRedirectStrategy(@Nonnull final HttpClientBuilder httpBuilder) {
        httpBuilder.setRedirectStrategy(new DefaultRedirectStrategy() {
            @Override
            protected boolean isRedirectable(String method) {
                return super.isRedirectable(method) || HttpPost.METHOD_NAME.equalsIgnoreCase(method);
            }

            @Override
            protected URI createLocationURI(String location) throws ProtocolException {
                String url = location.replace("^", "%5E");

                return super.createLocationURI(url);
            }
        });
    }

    private static class Holder {

        private static final HttpClientBuilder DEFAULT_BUILDER = create(HttpConfigs.Basic.DEFAULT_HTTP_CONFIG);

        private static final HttpClientBuilder CUSTOM_BUILDER;

        static {
            HttpClientBuilder httpClientBuilder = create(HttpConfigs.Basic.DEFAULT_HTTP_CONFIG);
            setCustomRedirectStrategy(httpClientBuilder);
            CUSTOM_BUILDER = httpClientBuilder;
        }
    }

    private static class CustomConfig {

        private static SSLConnectionSocketFactory SSL_SOCKET_FACTORY = null;

        static {
            try {
                SSLContextBuilder builder = new SSLContextBuilder();
                // 全部信任 不做身份鉴定
                builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
                SSLContext sslContext = builder.build();
                SSL_SOCKET_FACTORY = new SSLConnectionSocketFactory(sslContext,
                        new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2"}, null,
                        NoopHostnameVerifier.INSTANCE);
            } catch (Exception e) {
                log.error("Error init default SSLConnectionSocketFactory!", e);
            }
        }

        private CustomConfig() {
        }
    }

}
