package com.aurea.bellerophon.rest.dto;

import java.util.Objects;

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
        return Objects.toString(this);
    }
}