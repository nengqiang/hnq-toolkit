package com.hnq.toolkit.util.http.simple;

import com.hnq.toolkit.util.http.HttpRequests;
import com.hnq.toolkit.util.http.cookie.HttpCookies;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpHelper {

    private static final String DEFAULT_PATH = "/";

    private HttpHelper() {
    }

    public static BasicCookieStore createCookieStore(final Map<String, String> cookies,
                                                     final String... domains) {
        BasicCookieStore cookieStore = new BasicCookieStore();

        addCookies(cookieStore, cookies, domains);

        return cookieStore;
    }

    public static void addCookies(final CookieStore cookieStore, final String cookies,
                                  final String... domains) {
        Map<String, String> cookieMap = HttpCookies.parseCookiesAsMap(cookies);

        addCookies(cookieStore, cookieMap, domains);
    }

    public static void addCookies(final CookieStore cookieStore, final Map<String, String> cookies,
                                  final String... domains) {
        if (cookieStore == null || MapUtils.isEmpty(cookies)) {
            return;
        }

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            if (ArrayUtils.isEmpty(domains)) {
                cookieStore.addCookie(makeCookie(entry, null));
            } else {
                for (String domain : domains) {
                    cookieStore.addCookie(makeCookie(entry, domain));
                }
            }
        }
    }

    private static BasicClientCookie makeCookie(final Map.Entry<String, String> entry,
                                                final String domain) {
        String name = entry.getKey();
        String value = entry.getValue();
        return makeCookie(name, value, domain);
    }

    public static BasicClientCookie makeCookie(final String name, final String value,
                                               final String domain) {
        BasicClientCookie clientCookie = new BasicClientCookie(name, value);
        clientCookie.setVersion(0);
        clientCookie.setPath(DEFAULT_PATH);
        clientCookie.setAttribute(ClientCookie.PATH_ATTR, DEFAULT_PATH);
        if (StringUtils.isNotEmpty(domain)) {
            clientCookie.setDomain(domain);
            clientCookie.setAttribute(ClientCookie.DOMAIN_ATTR, domain);
        }
        return clientCookie;
    }

    public static String getCookieValue(@Nonnull final CookieStore cookieStore,
                                        @Nonnull final String name, final boolean strict) {
        Objects.requireNonNull(cookieStore);
        Objects.requireNonNull(name);

        List<Cookie> cookies = cookieStore.getCookies();
        String ctoken = null;
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                ctoken = cookie.getValue();
                break;
            }
        }

        if (ctoken == null && strict) {
            throw new IllegalArgumentException("Can not find necessary parameter '" + name + "'!");
        }

        return ctoken;
    }

    public static BasicCookieStore createCookieStore(final String cookie, final String... domains) {
        Map<String, String> cookieMap = HttpCookies.parseCookiesAsMap(cookie);

        return createCookieStore(cookieMap, domains);
    }

    public static HttpClientContext createContext(final String cookie, final String... domains) {
        BasicCookieStore cookieStore = createCookieStore(cookie, domains);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        return context;
    }

    public static HttpClientContext createContext(final Map<String, String> cookies,
                                                  final String... domains) {
        BasicCookieStore cookieStore = createCookieStore(cookies, domains);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        return context;
    }

    public static HttpUriRequest buildGetRequest(@Nonnull final String url,
                                                 @Nullable final String referer, @Nullable final String userAgent) {
        return HttpRequests.get(url, referer, userAgent);
    }

    public static void setDefaultRedirectStrategy(HttpClientBuilder httpBuilder) {
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

    public static CloseableHttpClient customClient() {
        HttpClientBuilder builder = HttpClients.custom();
        HttpHelper.setDefaultRedirectStrategy(builder);
        return builder.build();
    }

}
