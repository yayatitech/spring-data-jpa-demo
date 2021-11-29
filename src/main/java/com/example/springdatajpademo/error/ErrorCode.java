package com.example.springdatajpademo.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    //INPUT_VALUE_INVALID("IN_21", "Invalid input value "),
    ENTITY_NOT_FOUND("3424", "Entity not found", 404);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }



}
