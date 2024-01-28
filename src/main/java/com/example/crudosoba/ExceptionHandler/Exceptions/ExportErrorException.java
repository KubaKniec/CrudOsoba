package com.example.crudosoba.ExceptionHandler.Exceptions;

public class ExportErrorException extends RuntimeException {
    public ExportErrorException (String message) {
        super(message);
    }
}
