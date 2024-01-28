package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class ImportErrorException extends RuntimeException {
    public ImportErrorException(String message) {
        super(message);
    }
}
