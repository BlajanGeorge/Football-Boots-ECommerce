package com.example.footballbootswebapiis.exceptions;

public class BadCredentialsException extends RuntimeException{
    public BadCredentialsException(String msgError)
    {
        super(msgError);
    }
}
