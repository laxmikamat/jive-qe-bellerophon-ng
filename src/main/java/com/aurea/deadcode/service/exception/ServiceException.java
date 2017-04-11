package com.aurea.deadcode.service.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 4689824299451271448L;
    private int errorCode = 500;

    public ServiceException(final Throwable t) {
        super(t);
    }

    public ServiceException(final String message, final Throwable t) {
        super(message, t);
    }

    ServiceException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    ServiceException(final String message, final Throwable t, final int errorCode) {
        super(message, t);
        this.errorCode = errorCode;
    }

    /**
     * Maps to HTTP error code.
     * @return HTTP error code.
     * @see HttpStatus
     */
    public int getErrorCode() {
        return errorCode;
    }
}
