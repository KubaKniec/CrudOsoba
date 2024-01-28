package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class NotAnAdminException extends RuntimeException{
    public NotAnAdminException(String message) {
        super(message);
    }
}
