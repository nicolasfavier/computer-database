package com.nicolas.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

/**
 * 
 * Mapper to pass from Computer to computerDto and the other way
 *
 */
@Component
public final class ComputerDtoMapper {
	@Autowired
	private MessageSource messageSource;

	private ComputerDtoMapper() {
	}

	public ComputerDto ComputerToDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();

		computerDto.setId(computer.getId());
		computerDto.setName(computer.getName());
		computerDto.setCompany(computer.getCompany());

		// Create an instance of SimpleDateFormat used for formatting
		DateTimeFormatter df = DateTimeFormatter.ofPattern(getDatePattern());

		String introduced = "";
		if (computer.getIntroduced() != null)
			introduced = df.format(computer.getIntroduced());

		String discontinued = "";
		if (computer.getDiscontinued() != null)
			discontinued = df.format(computer.getDiscontinued());

		computerDto.setIntroduced(introduced);
		computerDto.setDiscontinued(discontinued);

		return computerDto;
	}

	public Computer ComputerFromDto(ComputerDto computerDto) {
		Computer computer = new Computer();
		Company company = null;

		if (computerDto == null)
			return computer;

		if(computerDto.getCompany() != null && computerDto.getCompany().getId() != 0)
			company = computerDto.getCompany();
		
		computer = new Computer.Builder()
		.id(computerDto.getId())
		.name(computerDto.getName())
		.company(company)
		.introduced(Utils.getDateFromString(computerDto
				.getIntroduced(),getDatePattern()))
		.discontinued(Utils.getDateFromString(computerDto
				.getDiscontinued(),getDatePattern()))
		.build();
		
		return computer;
	}

	// use stream to go faster on loop
	public List<ComputerDto> ComputerToDto(List<Computer> computers) {
		return computers.stream().map(c -> ComputerToDto(c))
				.collect(Collectors.toList());
	}

	public List<Computer> ComputerFromDto(List<ComputerDto> computerDtos) {
		return computerDtos.stream().map(c -> ComputerFromDto(c))
				.collect(Collectors.toList());
	}

	private String getDatePattern() {
		Locale userLocale = LocaleContextHolder.getLocale();
		return messageSource
				.getMessage("binding.date.format", null, userLocale);
	}
}
