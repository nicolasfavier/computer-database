package com.nicolas.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.nicolas.dto.ComputerDto;

public class ComputerDtoValidator {
	private static Validator validator;

	public final static List<String> validate(ComputerDto computerDto) {
		List<String> validationErrors = new ArrayList<>();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		Set<ConstraintViolation<ComputerDto>> constraintViolations = validator
				.validate(computerDto);

		for (ConstraintViolation<ComputerDto> constraintViolation : constraintViolations) {
			String error = constraintViolation.getMessage() + " : '"
					+ constraintViolation.getInvalidValue() + "' is not valid for "
					+ constraintViolation.getPropertyPath();
			validationErrors.add(error);
		}

		return validationErrors;
	}
}
