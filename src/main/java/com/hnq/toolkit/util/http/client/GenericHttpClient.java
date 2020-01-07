package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.HttpResponseStatus;
import com.hnq.toolkit.util.http.HttpResponses;
import com.hnq.toolkit.util.http.exception.*;
import com.hnq.toolkit.util.http.exception.internal.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.apache.http.protocol.HttpContext;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Objects;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
@Slf4j
public class GenericHttpClient implements IGenericHttp {
    private final HttpClient httpClient;

    public GenericHttpClient(@Nonnull HttpClient httpClient) {
        this.httpClient = Objects.requireNonNull(httpClient);
    }

    @Override
    public HttpResponse sendRequest(@Nonnull HttpUriRequest request) throws HttpException {
        logEntry(request);
        try {
            return httpClient.execute(request);
        } catch (Exception e) {
            handleException(e, request);
            return null;
        }
    }

    private void handleException(@Nonnull Exception e, @Nonnull HttpUriRequest request)
            throws HttpException {
        if (e instanceof ServerException) {
            ServerException ex = (ServerException) e;
            throw new HttpServerException(ex.getMessage(), ex.getCause(), ex.getUrl(), ex.getStatus(),
                    ex.getResponseBody(), ex.getHeaders());
        } else if (e instanceof HttpResponseException) {
            throw new HttpServerException(e.getMessage(), e.getCause(), request.getURI(),
                    ((HttpResponseException) e).getStatusCode(),
                    ((HttpResponseException) e).getReasonPhrase());
        } else if (e instanceof ConnectionPoolTimeoutException) {
            throw new TimeoutHttpException(
                    "Acquire connection timeout! - request[url=" + request.getURI() +
                            ", " + "method=" + request.getMethod() + "]", e);
        } else if (e instanceof ConnectTimeoutException) {
            throw new TimeoutHttpException("Connect timeout! - request[url=" + request.getURI() + ", "
                    + "method=" + request.getMethod() + "]", e);
        } else if (e instanceof SocketTimeoutException) {
            throw new TimeoutHttpException("Socket timeout! - request[url=" + request.getURI() + ", "
                    + "method=" + request.getMethod() + "]", e);
        } else if (e instanceof RequestAbortedException) {
            throw new AbortedHttpException(
                    "Request aborted! - request[url=" + request.getURI() + ", method=" + request.getMethod()
                            + "]", e);
        } else if (e instanceof InterruptedIOException) {
            throw new InterruptedHttpException(
                    "Interrupted when sending request[url=" + request.getURI() + ", method=" + request
                            .getMethod() + "]", e);
        } else if (e instanceof UnknownHostException) {
            throw new UnknownHostException(
                    "Unknown host when sending request[url=" + request.getURI() + ", method=" + request
                            .getMethod() + "]", e);
        } else if (e instanceof ConnectException) {
            throw new HttpConnectException(
                    "Error connecting when sending request[url=" + request.getURI() + ", method=" + request
                            .getMethod() + "]", e);
        } else if (e instanceof SSLException) {
            throw new SSLHttpException(
                    "Error connecting with ssl when sending request[url=" + request.getURI() + ", method="
                            + request.getMethod() + "]", e);
        } else if (e instanceof ClientProtocolException) {
            throw new HttpClientException(
                    "Failed to send request[url=" + request.getURI() + ", method=" + request.getMethod()
                            + "]", e);
        }

        throw new UnexpectedHttpException(
                "Failed to send request[url=" + request.getURI() + ", method=" + request.getMethod() + "]",
                e);
    }

    @Override
    public HttpResponse sendRequest(@Nonnull HttpUriRequest request, HttpContext context)
            throws HttpException {
        logEntry(request);
        try {
            return httpClient.execute(request, context);
        } catch (Exception e) {
            handleException(e, request);
            return null;
        }
    }

    @Override
    public <T> T send(@Nonnull HttpUriRequest request,
                      @Nonnull ResponseHandler<? extends T> responseHandler) throws HttpException {
        logEntry(request);
        try {
            return httpClient.execute(request, responseHandler);
        } catch (Exception e) {
            handleException(e, request);
            return null;
        }
    }

    @Override
    public <T> T send(@Nonnull HttpUriRequest request,
                      @Nonnull ResponseHandler<? extends T> responseHandler, HttpContext context)
            throws HttpException {
        logEntry(request);
        try {
            return httpClient.execute(request, responseHandler, context);
        } catch (Exception e) {
            handleException(e, request);
            return null;
        }
    }

    private void logEntry(HttpUriRequest request) {
        log.debug("[HttpClient] >> start to send request[url={}, method={}]", request.getURI(),
                request.getMethod());
    }

    @Override
    public <T> T send(@Nonnull HttpUriRequest request, final Class<T> clazz) throws HttpException {
        return send(request, readResponseBody(clazz, request));
    }

    @Override
    public <T> T send(@Nonnull HttpUriRequest request, HttpContext context, Class<T> clazz)
            throws HttpException {
        return send(request, readResponseBody(clazz, request), context);
    }

    private <T> ResponseHandler<T> readResponseBody(Class<T> clazz, @Nonnull HttpUriRequest request) {
        return response -> {
            int status = HttpResponses.getStatus(response);
            if (HttpResponseStatus.isOk(status)) {
                return HttpResponses.readEntity(response, clazz);
            }

            String responseBody;
            try {
                responseBody = HttpResponses.readEntity(response);
            } catch (Exception e) {
                log.warn("Error reading http response entity!", e);
                responseBody = "RESPONSE_STREAM_ERROR";
            }

            throw new ServerException(request.getURI(), status, responseBody, response.getAllHeaders());
        };
    }

    @Override
    public void close() throws IOException {
        if (httpClient instanceof CloseableHttpClient) {
            ((CloseableHttpClient) httpClient).close();
        }
    }
}
