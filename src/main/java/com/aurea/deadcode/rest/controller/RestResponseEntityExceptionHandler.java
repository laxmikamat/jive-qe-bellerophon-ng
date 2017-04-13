package com.aurea.deadcode.rest.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.aurea.deadcode.rest.dto.ErrorResponse;
import com.aurea.deadcode.service.exception.ServiceException;
import com.google.gson.Gson;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ServiceException.class, RuntimeException.class })
    protected ResponseEntity<Object> handleServerError(final RuntimeException ex, final WebRequest request) {
        if (ex instanceof ServiceException) {
            final ServiceException se = (ServiceException) ex;
            final HttpStatus status = HttpStatus.valueOf(se.getErrorCode());
            return handleExceptionInternal(ex, 
                    new Gson().toJson(new ErrorResponse(ex.getMessage())), 
                    new HttpHeaders(),
                    status, 
                    request);
        }

        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
