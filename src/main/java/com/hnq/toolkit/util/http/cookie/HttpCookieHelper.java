package com.hnq.toolkit.util.http.cookie;

import com.hnq.toolkit.util.http.HttpRequests;
import com.hnq.toolkit.util.http.HttpResponses;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

/**
 * Static utilities relating to the cookie.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpCookieHelper {

    private HttpCookieHelper() {
    }

    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static List<String> getCookies(@Nonnull final HttpRequest request) {
        return HttpRequests.getCookies(request);
    }

    /**
     * 获取cookie.
     *
     * @param response 响应对象 {@link HttpResponse}
     * @return 响应头中Set-Cookie项解析出的值
     */
    public static List<String> getCookies(@Nonnull final HttpResponse response) {
        return HttpResponses.getSetCookies(response);
    }

    /**
     * 获取cookie.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @return 请求头中Cookie项解析出的值
     */
    public static Map<String, String> getCookieMap(@Nonnull final HttpRequest request) {
        return HttpRequests.getCookieMap(request);
    }

    /**
     * 获取cookie.
     *
     * @param response 响应对象 {@link HttpResponse}
     * @return 响应头中Set-Cookie项解析出的值
     */
    public static Map<String, String> getCookieMap(@Nonnull final HttpResponse response) {
        return HttpResponses.getSetCookieMap(response);
    }

    /**
     * 获取指定cookie的值.
     *
     * @param request 请求对象 {@link HttpRequest}
     * @param key cookie键值对中想要取出的键
     * @return 请求头中Cookie项中给定key对应的值
     */
    public static String getCookieValue(@Nonnull final HttpRequest request,
                                        @Nonnull final String key) {
        return HttpRequests.getCookieValue(request, key);
    }

    /**
     * 获取指定cookie的值.
     *
     * @param response 响应对象 {@link HttpResponse}
     * @param key cookie键值对中想要取出的键
     * @return 响应头中Set-Cookie项中给定key对应的值
     */
    public static String getCookieValue(@Nonnull final HttpResponse response,
                                        @Nonnull final String key) {
        return HttpResponses.getSetCookieValue(response, key);
    }

}
