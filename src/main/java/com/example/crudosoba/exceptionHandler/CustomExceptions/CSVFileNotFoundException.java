package com.example.crudosoba.exceptionHandler.CustomExceptions;

public class CSVFileNotFoundException extends RuntimeException {
    public CSVFileNotFoundException(String message) {
        super(message);
    }

}
