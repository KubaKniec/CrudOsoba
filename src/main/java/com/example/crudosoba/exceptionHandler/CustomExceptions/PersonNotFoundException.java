package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String message) {
        super(message);
    }
}
