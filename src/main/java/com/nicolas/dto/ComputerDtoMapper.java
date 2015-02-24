package com.nicolas.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

/**
 * 
 * Mapper to pass from Computer to computerDto and the other way
 *
 */
public final class ComputerDtoMapper {

	private ComputerDtoMapper() {
	}

	public static ComputerDto ComputerToDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();

		computerDto.setId(computer.getId());
		computerDto.setName(computer.getName());
		computerDto.setCompany(computer.getCompany());

		String introduced = "";
		if (computer.getIntroduced() != null)
			introduced = computer.getIntroduced().toString();

		String discontinued = "";
		if (computer.getDiscontinued() != null)
			discontinued = computer.getDiscontinued().toString();

		computerDto.setIntroduced(introduced);
		computerDto.setDiscontinued(discontinued);

		return computerDto;
	}

	public static Computer ComputerFromDto(ComputerDto computerDto) {
		Computer computer = new Computer();

		if (computerDto == null)
			return computer;

		computer = new Computer.Builder()
		.id(computerDto.getId())
		.name(computerDto.getName())
		.company(computerDto.getCompany())
		.introduced(Utils.getDateFromString(computerDto
				.getIntroduced()))
		.discontinued(Utils.getDateFromString(computerDto
				.getDiscontinued()))
		.build();
		
		return computer;
	}

	//use stream to go faster on loop
	public static List<ComputerDto> ComputerToDto(List<Computer> computers) {
	     return computers.stream().map(c->ComputerToDto(c)).collect(Collectors.toList());
	}

	public static List<Computer> ComputerFromDto(List<ComputerDto> computerDtos) {	
	     return computerDtos.stream().map(c->ComputerFromDto(c)).collect(Collectors.toList());
	}
}
