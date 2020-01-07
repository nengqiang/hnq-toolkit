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
public class MoreHttpClient extends SimpleHttpClient implements MoreHttp {

    private MoreHttpClient(@Nonnull HttpClient httpClient) {
        super(httpClient);
    }

    public static MoreHttpClient getInstance(HttpClient httpClient) {
        return new MoreHttpClient(httpClient);
    }

    @Override
    public HttpResponse requestWithPost(@Nonnull String url, Map<String, String> headers,
                                        Map<String, Object> params, String proxy, boolean multipart) throws HttpException {
        HttpUriRequest request = HttpRequests.post(url, headers, params, proxy, multipart);

        return sendRequest(request);
    }

    @Override
    public <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      String proxy, boolean multipart, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        HttpUriRequest request = HttpRequests.post(url, headers, params, proxy, multipart);

        return send(request, responseHandler);
    }

    @Override
    public <T> T post(@Nonnull String url, Map<String, String> headers, Map<String, Object> params,
                      String proxy, boolean multipart, Class<T> clazz) throws HttpException {
        HttpUriRequest request = HttpRequests.post(url, params, proxy, multipart);

        return send(request, clazz);
    }

}
