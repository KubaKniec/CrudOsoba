package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class InvalidCardNumberException extends RuntimeException{
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
