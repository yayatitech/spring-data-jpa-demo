package com.example.springdatajpademo.service;

import com.example.springdatajpademo.dto.Booking;
import com.example.springdatajpademo.entity.OnCreate;
import com.example.springdatajpademo.entity.OnUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@Service
@Validated //needed at class level for @valid on method level to trigger
public class ValidatingServiceWithGroups {

    @Autowired
    private Validator validator; //programatic validation

    @Validated(OnCreate.class) //on create validation are triggered
    void validateForCreate(@Valid Booking booking) {
        //do something

    }

    @Validated(OnUpdate.class) //on update validation are triggered
    void validateForUpdate(@Valid Booking booking) {
        //do something
    }
}
