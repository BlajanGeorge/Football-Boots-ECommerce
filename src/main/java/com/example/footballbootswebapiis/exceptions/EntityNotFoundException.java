package com.example.footballbootswebapiis.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String errorMessage)
    {
        super(errorMessage);
    }
}
