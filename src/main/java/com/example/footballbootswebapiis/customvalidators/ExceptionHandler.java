package com.example.footballbootswebapiis.customvalidators;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorModel error = new ErrorModel(HttpStatus.BAD_REQUEST,  ex.getBindingResult().toString().substring(ex.getBindingResult().toString().lastIndexOf('[') + 1,
                ex.getBindingResult().toString().lastIndexOf(']')));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
