package com.example.crudosoba.ExceptionHandler.Exceptions;

public class InvalidCardNumberException extends RuntimeException{
    public InvalidCardNumberException(String message) {
        super(message);
    }
}
