package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.MoreHttpRequest;
import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public interface IFlexibleHttp extends IGenericHttp {

    // ------------------------------- 灵活，多配置

    HttpResponse sendRequest(@Nonnull MoreHttpRequest request) throws HttpException;

    HttpResponse sendRequest(@Nonnull MoreHttpRequest request, @Nullable HttpContext context)
            throws HttpException;

    <T> T send(@Nonnull MoreHttpRequest request,
               @Nullable ResponseHandler<? extends T> responseHandler)
            throws HttpException;

    <T> T send(@Nonnull MoreHttpRequest request,
               @Nullable ResponseHandler<? extends T> responseHandler,
               HttpContext context) throws HttpException;

    <T> T send(@Nonnull MoreHttpRequest request, @Nullable Class<T> clazz) throws HttpException;

    <T> T send(@Nonnull MoreHttpRequest request, @Nullable HttpContext context,
               @Nullable Class<T> clazz) throws HttpException;

    default String send(@Nonnull MoreHttpRequest request) throws HttpException {
        return send(request, String.class);
    }

    default String send(@Nonnull MoreHttpRequest request, @Nullable HttpContext context)
            throws HttpException {
        return send(request, context, String.class);
    }

}
