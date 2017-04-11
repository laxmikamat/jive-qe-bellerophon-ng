package com.aurea.deadcode.rest.dto;

import com.aurea.deadcode.util.ToString;

public class ErrorResponse {
    private final String errorMessage;

    public ErrorResponse(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String toString() {
        return ToString.toString(this);
    }
}
