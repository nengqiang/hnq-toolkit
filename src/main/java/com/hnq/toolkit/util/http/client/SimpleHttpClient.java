package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.HttpRequests;
import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class SimpleHttpClient extends FlexibleHttpClient implements ISimpleHttp {
    public SimpleHttpClient(@Nonnull HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public HttpResponse sendMultipartRequest(@Nonnull String url, Map<String, String> headers,
                                             Map<String, Object> params, String proxy) throws HttpException {
        HttpUriRequest request = HttpRequests.createMultipart(url, headers, params, proxy);

        return sendRequest(request);
    }

    @Override
    public <T> T sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                                   Map<String, Object> params, String proxy, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        HttpUriRequest request = HttpRequests.createMultipart(url, headers, params, proxy);

        return send(request, responseHandler);
    }

    @Override
    public <T> T sendMultipartForm(@Nonnull String url, Map<String, String> headers,
                                   Map<String, Object> params, String proxy, @Nonnull Class<T> clazz) throws HttpException {
        HttpUriRequest request = HttpRequests.createMultipart(url, headers, params, proxy);

        return send(request, clazz);
    }
}
