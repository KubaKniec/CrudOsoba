package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class CustomMessageNotReadable extends RuntimeException {
    public CustomMessageNotReadable(String message) {
        super(message);
    }
}
