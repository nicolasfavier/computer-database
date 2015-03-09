package com.nicolas.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

/**
 * 
 * run the validators on DTOs and return errors in a List of string
 *
 */
public class DtoValidator  {

	public final static <T> List<String> validate(T objectDTO) {
		List<String> validationErrors = new ArrayList<>();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Set<ConstraintViolation<T>> constraintViolations =  factory.getValidator().validate(objectDTO);

		for (ConstraintViolation<T> constraintViolation : constraintViolations) {
			String error = constraintViolation.getMessage() + " : '"
					+ constraintViolation.getInvalidValue() + "' is not valid for "
					+ constraintViolation.getPropertyPath();
			validationErrors.add(error);
		}

		return validationErrors;
	}
}
