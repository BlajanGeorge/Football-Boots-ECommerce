package com.example.footballbootswebapiis.customvalidators;

import org.springframework.http.HttpStatus;

public class ErrorModel {

    private final HttpStatus httpStatus;

    private final String message;

    public ErrorModel(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
