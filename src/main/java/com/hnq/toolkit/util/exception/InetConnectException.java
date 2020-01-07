package com.hnq.toolkit.util.exception;

/**
 * @author henengqiang
 * @date 2019/07/01
 */
public class InetConnectException extends Exception {

    public InetConnectException(String message) {
        super(message);
    }

    public InetConnectException(String message, Throwable cause) {
        super(message, cause);
    }

    public InetConnectException(Throwable cause) {
        super(cause);
    }

}
