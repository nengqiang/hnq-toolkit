package com.hnq.toolkit.util.http.exception;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class InterruptedHttpException extends HttpClientException {

    public InterruptedHttpException(String message, Throwable cause) {
        super(message, cause);
    }

}
