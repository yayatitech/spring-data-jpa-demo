package com.example.springdatajpademo.error;


import com.example.springdatajpademo.utils.ValidationException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
@ResponseBody //else error will be standard error format instead of string
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public void validationExceptionHandler(EntityNotFoundException ex) {

    }

    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookingNotFoundException(BookingNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //thrown by @Valid on request body
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, String> errors = new HashMap();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class) //thrown by @Valid on path variable / request param / method param
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        Map<String, String> errors = new HashMap();
        ex.getConstraintViolations().forEach(error -> {
            errors.put(((ConstraintViolation) error).getPropertyPath().toString(), error.getMessage());
        });

        return errors;
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(PersistenceException ex) {
        log.error(ex.getMessage(), ex);
        return ex.getMessage();
    }
}
