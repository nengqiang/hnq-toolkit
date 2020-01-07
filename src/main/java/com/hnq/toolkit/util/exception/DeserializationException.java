package com.hnq.toolkit.util.exception;

/**
 * 反序列化异常
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class DeserializationException extends RuntimeException {

    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

}
