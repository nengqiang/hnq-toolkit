package com.hnq.toolkit.util.http.exception;

import org.apache.http.Header;

import java.net.URI;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class HttpServerException extends HttpException {

    private final URI uri;
    private final int statusCode;
    private final String responseBody;
    private final Header[] headers;
    private final String reasonPhrase;

    public HttpServerException(String message, Throwable cause, URI uri, int statusCode,
                               String reasonPhrase) {
        this(message, cause, uri, statusCode, null, null, reasonPhrase);
    }

    public HttpServerException(String message, Throwable cause, URI uri, int statusCode,
                               String responseBody, Header[] headers) {
        this(message, cause, uri, statusCode, responseBody, headers, null);
    }

    public HttpServerException(String message, Throwable cause, URI uri, int statusCode,
                               String responseBody, Header[] headers, String reasonPhrase) {
        super(message, cause);
        this.uri = uri;
        this.statusCode = statusCode;
        this.responseBody = responseBody;
        this.headers = headers;
        this.reasonPhrase = reasonPhrase;
    }

    public URI getUri() {
        return uri;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

}
