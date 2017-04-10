package com.aurea.deadcode.rest.dto;

public class ReposResponse<T> {

    private Integer code;

    private T response;

    public ReposResponse() {
    }

    public ReposResponse(final Integer code, final T response) {
        this.code = code;
        this.response = response;
    }

    public ReposResponse(final Integer code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public T getResponse() {
        return response;
    }
}
