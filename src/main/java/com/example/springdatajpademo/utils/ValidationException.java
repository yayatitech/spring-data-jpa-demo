package com.example.springdatajpademo.utils;

public class ValidationException extends RuntimeException {

    public ValidationException(String message, Throwable cause) {
        super(message, cause);

    }
}
