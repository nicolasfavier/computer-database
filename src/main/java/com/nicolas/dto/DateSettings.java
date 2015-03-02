package com.nicolas.dto;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DateSettings {
	@Autowired
	private  MessageSource messageSource;

	/**
	 * This method returns the date pattern that is associated with the current
	 * Locale.
	 *
	 * @return The date pattern of the current locale.
	 */
	public String getDatePattern() {
		Locale userLocale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("binding.date.format", null, userLocale);
	}
	
	public String getDateRegex() {
		Locale userLocale = LocaleContextHolder.getLocale();
		return messageSource.getMessage("binding.date.regex", null, userLocale);
	}
}