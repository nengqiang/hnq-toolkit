package com.hnq.toolkit.util.http.cookie;

import com.google.common.collect.Maps;
import com.google.common.net.HttpHeaders;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;

import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpCookies {

    private static final Pattern COOKIE_PATTERN = Pattern.compile("\\s*(\\w+)\\s*=\\s*([^;\\s]*?)\\s*;");
    private static final String DELIMITER = ";";
    private static final String CONCAT = "=";

    private HttpCookies() {
    }

    public static String toString(final HttpCookie cookie) {
        return toString(cookie, true);
    }

    public static String toString(final HttpCookie cookie, boolean simple) {
        if (cookie == null) {
            return StringUtils.EMPTY;
        }

        return simple ? (cookie.getName() + CONCAT + cookie.getValue()) : cookie.toString();
    }

    public static List<HttpCookie> parse(final String cookie) {
        if (StringUtils.isNotEmpty(cookie)) {
            return HttpCookie.parse(cookie);
        }
        return Collections.emptyList();
    }

    public static List<HttpCookie> convert(final Map<String, String> cookies) {
        if (MapUtils.isEmpty(cookies)) {
            return Collections.emptyList();
        }

        return cookies.entrySet().stream()
                .map(entry -> new HttpCookie(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static Map<String, String> parseSimple(final String... cookies) {
        Map<String, String> map = Maps.newHashMap();

        for (String cookie : cookies) {
            Matcher m = COOKIE_PATTERN.matcher(cookie);
            while (m.find()) {
                map.put(m.group(1), m.group(2));
            }
        }

        return map;
    }

    public static List<HttpCookie> getHttpCookies(final Header... headers) {
        if (ArrayUtils.isEmpty(headers)) {
            return Collections.emptyList();
        }

        return streamOfCookieHeader(headers).collect(Collectors.toList());
    }

    private static Stream<HttpCookie> streamOfCookieHeader(final Header... headers) {
        return Arrays.stream(headers)
                .filter(header -> StringUtils.isNotEmpty(header.getName()) && StringUtils
                        .isNotEmpty(header.getValue())).flatMap(header -> {
                    if (HttpHeaders.SET_COOKIE.equalsIgnoreCase(header.getName())) {
                        return streamOfSetCookies(header.getValue());
                    } else if (HttpHeaders.COOKIE.equalsIgnoreCase(header.getName())) {
                        return streamOfCookies(header.getValue());
                    }
                    return null;
                });
    }

    public static List<String> getCookies(final Header... headers) {
        if (ArrayUtils.isEmpty(headers)) {
            return Collections.emptyList();
        }

        return streamOfCookieHeader(headers).map(HttpCookies::toString).collect(Collectors.toList());
    }

    public static Map<String, String> getCookieMap(final Header... headers) {
        if (ArrayUtils.isEmpty(headers)) {
            return Collections.emptyMap();
        }

        return streamOfCookieHeader(headers)
                .collect(Collectors.toMap(HttpCookie::getName, HttpCookie::getValue, (a, b) -> b));
    }

    public static String getCookieString(final Header... headers) {
        if (ArrayUtils.isEmpty(headers)) {
            return StringUtils.EMPTY;
        }

        return streamOfCookieHeader(headers).map(HttpCookies::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    public static List<HttpCookie> parseCookies(final String... cookies) {
        if (ArrayUtils.isEmpty(cookies)) {
            return Collections.emptyList();
        }

        return streamOfCookies(cookies).collect(Collectors.toList());
    }

    public static Map<String, String> parseCookiesAsMap(final String... cookies) {
        return parseCookiesAsMap(cookies, false);
    }

    public static Map<String, String> parseCookiesAsMap(final String[] cookies,
                                                        final boolean filter) {
        if (ArrayUtils.isEmpty(cookies)) {
            return Collections.emptyMap();
        }

        Stream<HttpCookie> stream = streamOfCookies(cookies);

        if (filter) {
            stream = stream.filter(cookie -> !cookie.hasExpired());
        }

        return stream.collect(Collectors.toMap(HttpCookie::getName, HttpCookie::getValue, (a, b) -> b));
    }

    private static Stream<HttpCookie> streamOfCookies(final String... cookies) {
        return Stream.of(cookies).filter(StringUtils::isNotEmpty)
                .flatMap(cookie -> Stream.of(cookie.trim().split(DELIMITER)))
                .flatMap(cookie -> HttpCookie.parse(cookie).stream()).distinct();
    }

    public static List<HttpCookie> parseSetCookies(final String... setCookies) {
        if (ArrayUtils.isEmpty(setCookies)) {
            return Collections.emptyList();
        }

        return streamOfSetCookies(setCookies).collect(Collectors.toList());
    }

    public static Map<String, String> parseSetCookiesAsMap(final String... setCookies) {
        return parseSetCookiesAsMap(setCookies, false);
    }

    public static Map<String, String> parseSetCookiesAsMap(final String[] cookies,
                                                           final boolean filter) {
        if (ArrayUtils.isEmpty(cookies)) {
            return Collections.emptyMap();
        }

        Stream<HttpCookie> stream = streamOfSetCookies(cookies);

        if (filter) {
            stream = stream.filter(cookie -> !cookie.hasExpired());
        }

        return stream.collect(Collectors.toMap(HttpCookie::getName, HttpCookie::getValue, (a, b) -> b));
    }

    private static Stream<HttpCookie> streamOfSetCookies(final String... setCookies) {
        return Stream.of(setCookies).filter(StringUtils::isNotEmpty)
                .flatMap(cookie -> HttpCookie.parse(cookie).stream()).distinct();
    }

    public static String format(final HttpCookie... cookies) {
        if (ArrayUtils.isEmpty(cookies)) {
            return StringUtils.EMPTY;
        }

        return Stream.of(cookies).map(HttpCookies::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    public static String format(final Map<String, String> cookies) {
        if (MapUtils.isEmpty(cookies)) {
            return StringUtils.EMPTY;
        }

        return cookies.entrySet().stream().map(entry -> entry.getKey() + CONCAT + entry.getValue())
                .collect(Collectors.joining(DELIMITER));
    }

    public static String formatCookies(final String... cookies) {
        if (ArrayUtils.isEmpty(cookies)) {
            return StringUtils.EMPTY;
        }

        return streamOfCookies(cookies).map(HttpCookies::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    public static String formatSetCookies(String... setCookies) {
        if (ArrayUtils.isEmpty(setCookies)) {
            return StringUtils.EMPTY;
        }

        return streamOfSetCookies(setCookies).map(HttpCookies::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    public static String[] standardizeCookies(final String... cookies) {
        return standardizeCookies(cookies, false);
    }

    public static String[] standardizeCookies(final String[] cookies, final boolean simple) {
        if (ArrayUtils.isEmpty(cookies)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        return streamOfCookies(cookies).filter(cookie -> !cookie.hasExpired())
                .map(cookie -> toString(cookie, simple)).toArray(String[]::new);
    }

    public static String[] standardizeSetCookies(final String... setCookies) {
        return standardizeSetCookies(setCookies, false);
    }

    public static String[] standardizeSetCookies(final String[] setCookies, final boolean simple) {
        if (ArrayUtils.isEmpty(setCookies)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        return streamOfSetCookies(setCookies).filter(cookie -> !cookie.hasExpired())
                .map(cookie -> toString(cookie, simple)).toArray(String[]::new);
    }

    public static String[] split(final String... cookies) {
        if (ArrayUtils.isEmpty(cookies)) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }

        return Stream.of(cookies).flatMap(cookie -> Stream.of(cookie.split(DELIMITER)))
                .toArray(String[]::new);
    }

}
