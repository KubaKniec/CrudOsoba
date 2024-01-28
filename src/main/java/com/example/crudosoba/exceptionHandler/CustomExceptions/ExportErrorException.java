package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class ExportErrorException extends RuntimeException {
    public ExportErrorException (String message) {
        super(message);
    }
}
