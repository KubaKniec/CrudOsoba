package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
