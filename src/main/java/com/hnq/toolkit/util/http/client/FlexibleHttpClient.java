package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.HttpRequests;
import com.hnq.toolkit.util.http.MoreHttpRequest;
import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import javax.annotation.Nonnull;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class FlexibleHttpClient extends GenericHttpClient implements IFlexibleHttp {

    public FlexibleHttpClient(@Nonnull HttpClient httpClient) {
        super(httpClient);
    }

    @Override
    public HttpResponse sendRequest(@Nonnull MoreHttpRequest request) throws HttpException {
        HttpUriRequest httpRequest = HttpRequests.from(request);

        return sendRequest(httpRequest);
    }

    @Override
    public HttpResponse sendRequest(@Nonnull MoreHttpRequest request, HttpContext context)
            throws HttpException {
        HttpUriRequest httpRequest = HttpRequests.from(request);

        return sendRequest(httpRequest, context);
    }

    @Override
    public <T> T send(@Nonnull MoreHttpRequest request, ResponseHandler<? extends T> responseHandler)
            throws HttpException {
        HttpUriRequest httpRequest = HttpRequests.from(request);

        return send(httpRequest, responseHandler);
    }

    @Override
    public <T> T send(@Nonnull MoreHttpRequest request, ResponseHandler<? extends T> responseHandler,
                      HttpContext context) throws HttpException {
        HttpUriRequest httpRequest = HttpRequests.from(request);

        return send(httpRequest, responseHandler, context);
    }

    @Override
    public <T> T send(@Nonnull MoreHttpRequest request, Class<T> clazz) throws HttpException {
        HttpUriRequest httpRequest = HttpRequests.from(request);

        return send(httpRequest, clazz);
    }

    @Override
    public <T> T send(@Nonnull MoreHttpRequest request, HttpContext context, Class<T> clazz)
            throws HttpException {
        HttpUriRequest httpRequest = HttpRequests.from(request);

        return send(httpRequest, context, clazz);
    }

}
