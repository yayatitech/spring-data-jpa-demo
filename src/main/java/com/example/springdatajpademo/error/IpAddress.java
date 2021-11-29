package com.example.springdatajpademo.error;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
//pointing to an implementation of the ConstraintValidator interface
@Constraint(validatedBy = IpAddressValidator.class)
@Documented
public @interface IpAddress {

    //pointing to a property key in ValidationMessages.properties, which is used to resolve a message in case of violation
    String message() default "{IpAddress.invalid}";

    //allowing to define under which circumstances this validation is to be triggered
    Class<?>[] groups() default { };

    //allowing to define a payload to be passed with this validation
    Class<? extends Payload> [] payload() default { };
}
