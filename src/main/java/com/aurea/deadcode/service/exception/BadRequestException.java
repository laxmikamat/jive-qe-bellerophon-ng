package com.aurea.deadcode.service.exception;

public class BadRequestException extends ServiceException {
    private static final long serialVersionUID = 2887009713903280810L;

    public BadRequestException(final String message, final Throwable t) {
        super(message, t, 400);
    }
}
