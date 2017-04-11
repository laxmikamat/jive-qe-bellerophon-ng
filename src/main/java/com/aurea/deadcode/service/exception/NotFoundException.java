package com.aurea.deadcode.service.exception;

public class NotFoundException extends ServiceException {
    private static final long serialVersionUID = -7452423129979194560L;

    public NotFoundException(final String uuid) {
        super("Repo with '" + uuid + "' UUID does not exist", 404);
    }

}
