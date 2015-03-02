package com.nicolas.dto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

/**
 * 
 * Mapper to pass from Computer to computerDto and the other way
 *
 */
@Component
public final class ComputerDtoMapper {

	private ComputerDtoMapper() {
	}

	@Autowired
	private Utils utils;
	@Autowired 
	private DateSettings dateSettings;
	
	public  ComputerDto ComputerToDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();

		computerDto.setId(computer.getId());
		computerDto.setName(computer.getName());
		computerDto.setCompany(computer.getCompany());

		// Create an instance of SimpleDateFormat used for formatting 
		DateTimeFormatter df = DateTimeFormatter.ofPattern(dateSettings.getDatePattern());
		
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

		if (computerDto == null)
			return computer;

		computer = new Computer.Builder()
		.id(computerDto.getId())
		.name(computerDto.getName())
		.company(computerDto.getCompany())
		.introduced(utils.getDateFromString(computerDto
				.getIntroduced()))
		.discontinued(utils.getDateFromString(computerDto
				.getDiscontinued()))
		.build();
		
		return computer;
	}

	//use stream to go faster on loop
	public  List<ComputerDto> ComputerToDto(List<Computer> computers) {
	     return computers.stream().map(c->ComputerToDto(c)).collect(Collectors.toList());
	}

	public  List<Computer> ComputerFromDto(List<ComputerDto> computerDtos) {	
	     return computerDtos.stream().map(c->ComputerFromDto(c)).collect(Collectors.toList());
	}
}
