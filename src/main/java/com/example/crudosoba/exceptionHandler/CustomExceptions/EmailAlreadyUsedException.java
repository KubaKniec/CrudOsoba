package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
