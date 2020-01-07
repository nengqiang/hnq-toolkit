package com.hnq.toolkit.util.http.exception.internal;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;

import java.net.URI;

/**
 * 响应异常
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class ServerException extends ClientProtocolException {

    private static final String MSG_TEMPLATE = "Unexpected response of HttpRequest[%s] >> status: %s, body:%s";
    private final URI url;
    private final int status;
    private final String responseBody;
    private final Header[] headers;

    public ServerException(URI url, int status, String responseBody, Header[] headers) {
        super(String.format(MSG_TEMPLATE, url, status, responseBody));
        this.url = url;
        this.status = status;
        this.responseBody = responseBody;
        this.headers = headers;
    }

    public URI getUrl() {
        return url;
    }

    public int getStatus() {
        return status;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Header[] getHeaders() {
        return headers;
    }

}
