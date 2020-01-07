package com.hnq.toolkit.util.exception;

/**
 * 序列化异常
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class SerializationException extends RuntimeException {

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
