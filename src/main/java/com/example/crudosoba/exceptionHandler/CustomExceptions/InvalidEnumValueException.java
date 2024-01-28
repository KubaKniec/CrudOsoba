package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class InvalidEnumValueException extends RuntimeException{
    public InvalidEnumValueException(String message) {
        super(message);
    }
}
