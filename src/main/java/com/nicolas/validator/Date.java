package com.nicolas.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Constraint(validatedBy = DateValidator.class)
@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 
 * Annotation for custom Date validation
 *
 */
public @interface Date {

	String message() default "Wrong Date format ";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
