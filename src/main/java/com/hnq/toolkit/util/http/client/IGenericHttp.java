package com.hnq.toolkit.util.http.client;

import com.hnq.toolkit.util.http.exception.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Closeable;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public interface IGenericHttp extends Closeable {

    // ------------------------------- 原始封装

    HttpResponse sendRequest(@Nonnull HttpUriRequest request) throws HttpException;

    HttpResponse sendRequest(@Nonnull HttpUriRequest request, @Nullable HttpContext context)
            throws HttpException;

    <T> T send(@Nonnull HttpUriRequest request, @Nonnull ResponseHandler<? extends T> responseHandler)
            throws HttpException;

    <T> T send(@Nonnull HttpUriRequest request, @Nonnull ResponseHandler<? extends T> responseHandler,
               @Nullable HttpContext context) throws HttpException;

    <T> T send(@Nonnull HttpUriRequest request, @Nullable Class<T> clazz) throws HttpException;

    <T> T send(@Nonnull HttpUriRequest request, @Nullable HttpContext context,
               @Nullable Class<T> clazz) throws HttpException;

    default String send(@Nonnull HttpUriRequest request) throws HttpException {
        return send(request, String.class);
    }

    default String send(@Nonnull HttpUriRequest request, @Nullable HttpContext context)
            throws HttpException {
        return send(request, context, String.class);
    }

}
