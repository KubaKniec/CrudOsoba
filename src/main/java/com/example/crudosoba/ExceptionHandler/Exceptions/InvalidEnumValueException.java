package com.example.crudosoba.ExceptionHandler.Exceptions;

public class InvalidEnumValueException extends RuntimeException{
    public InvalidEnumValueException(String message) {
        super(message);
    }
}
