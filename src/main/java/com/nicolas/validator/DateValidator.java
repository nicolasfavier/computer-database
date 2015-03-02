package com.nicolas.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.nicolas.utils.Utils;

/**
 * 
 * define the condition a date must fulfill 
 *
 */
public class DateValidator implements ConstraintValidator<Date, String> {

	@Autowired
	private Utils utils;
	
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
		if(utils.isDate(value))
		{
			return true;
		}
		return false;
	}

}