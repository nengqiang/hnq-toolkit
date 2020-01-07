package com.hnq.toolkit.util.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by shiyc on 1/6/15.
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;

    private HttpStatus httpStatus;

    public ServiceException(int code) {
        this.code = code;
    }

    public ServiceException(int code, Throwable exp) {
        super(exp);
        this.code = code;
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable exp) {
        super(message, exp);
        this.code = code;
    }

    public ServiceException(int code, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public ServiceException(int code, HttpStatus httpStatus, Throwable exp) {
        super(exp);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public ServiceException(int code, HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public ServiceException(int code, HttpStatus httpStatus, String message, Throwable exp) {
        super(message, exp);
        this.httpStatus = httpStatus;
        this.code = code;
    }


    public int getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus == null ? HttpStatus.BAD_REQUEST : httpStatus;
    }
}
