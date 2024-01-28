package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
