package com.nicolas.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nicolas.utils.Utils;

/**
 * 
 * define the condition a date must fulfill 
 *
 */
public class DateValidator implements ConstraintValidator<Date, String> {

	@Override
	public void initialize(Date constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return false;
		}
		if (value.isEmpty()) {
			return true;
		}
		if(Utils.isDate(value))
		{
			return true;
		}
		return false;
	}

}