package com.nicolas.validator;

import java.util.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.nicolas.utils.Utils;


/**
 * 
 * define the condition a date must fulfill 
 *
 */
 @Component
public class DateValidator implements ConstraintValidator<Date, String> {

	@Autowired
	private  MessageSource messageSource;
	
	@Override
	public void initialize(Date constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		if (value.isEmpty()) {
			return true;
		}
		if(Utils.isDate(value, getDateRegex()))
		{
			return true;
		}
		return false;
	}
	
	private String getDateRegex() {
		Locale userLocale = LocaleContextHolder.getLocale();
		String str = messageSource.getMessage("binding.date.regex", null, userLocale);
		return str;
	}

}