package com.hnq.toolkit.util.http.servlet;

import com.hnq.toolkit.util.http.HttpHeaders;
import com.hnq.toolkit.util.net.IpUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Static utilities relating to {@link javax.servlet.ServletRequest}.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class ServletRequests {

    private static final String[] IP_HEADERS =
            {HttpHeaders.X_FORWARDED_FOR, "Proxy-Client-IP", "WL-Proxy-Client-IP", null};


    private ServletRequests() {
    }

    /**
     * Get IP from the header of {@link HttpServletRequest}.
     *
     * @param request {@link HttpServletRequest}
     * @return IP
     */
    public static String getIP(@Nonnull final HttpServletRequest request) {
        Objects.requireNonNull(request);
        String ip = request.getHeader(IP_HEADERS[0]);

        if (StringUtils.isNotBlank(ip)) {
            ip = IpUtils.getFirst(ip);
        }

        for (int i = 1, length = IP_HEADERS.length; i < length; i++) {
            if (StringUtils.isNotBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }

            if (IP_HEADERS[i] != null) {
                ip = request.getHeader(IP_HEADERS[i]);
            } else {
                ip = request.getRemoteAddr();
            }
        }

        return StringUtils.trimToEmpty(ip);
    }

}
