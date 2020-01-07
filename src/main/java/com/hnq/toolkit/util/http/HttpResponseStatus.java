package com.hnq.toolkit.util.http;

import org.apache.http.HttpResponse;

/**
 * Static utilities relating to {@link org.apache.http.HttpStatus}
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpResponseStatus {

    private HttpResponseStatus() {
    }

    /**
     * @return true if request sent successful,that is to say the {@code status} is between 200 and
     * 300(exclusive)
     */
    public static boolean isOk(int status) {
        return status >= 200 && status < 300;
    }

    /**
     * @return true if request sent successful,that is to say the {@code response}'s status code is
     * between 200 and 300(exclusive)
     */
    public static boolean isOk(HttpResponse response) {
        int status = HttpResponses.getStatus(response);
        return isOk(status);
    }
}
