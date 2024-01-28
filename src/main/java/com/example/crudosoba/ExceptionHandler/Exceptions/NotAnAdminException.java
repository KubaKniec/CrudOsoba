package com.example.crudosoba.ExceptionHandler.Exceptions;

public class NotAnAdminException extends RuntimeException{
    public NotAnAdminException(String message) {
        super(message);
    }
}
