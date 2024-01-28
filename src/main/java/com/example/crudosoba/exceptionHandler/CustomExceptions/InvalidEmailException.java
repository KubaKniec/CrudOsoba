package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String message) {
        super(message);
    }
}
