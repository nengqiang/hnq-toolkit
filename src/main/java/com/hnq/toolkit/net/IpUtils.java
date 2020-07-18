package com.hnq.toolkit.net;

import com.hnq.toolkit.http.servlet.ServletRequests;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class IpUtils {

    private static final Pattern IP_PATTERN = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");

    private IpUtils() {
    }

    public static boolean isIp(@Nonnull final String ip) {
        return IP_PATTERN.matcher(ip).matches();
    }

    public static String getFirst(@Nonnull final String text) {
        Matcher matcher = IP_PATTERN.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }

        return StringUtils.EMPTY;
    }

    public static List<String> getList(@Nonnull final String text) {
        List<String> list = new ArrayList<>();
        Matcher matcher = IP_PATTERN.matcher(text);
        while (matcher.find()) {
            list.add(matcher.group());
        }

        return list;
    }

    public static String getIp(@Nonnull final HttpServletRequest request) {
        return ServletRequests.getIP(request);
    }

    public static String getLocalHost()  {
        return NetUtils.getLocalHost();
    }

}
