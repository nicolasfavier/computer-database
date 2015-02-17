package com.nicolas.dto;

import java.util.ArrayList;
import java.util.List;

import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

public final class ComputerDtoMapper {

	private ComputerDtoMapper() {
	}

	public static ComputerDto ComputerToDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();

		computerDto.setId(computer.getId());
		computerDto.setName(computer.getName());

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

		computer.setId(computerDto.getId());
		computer.setName(computerDto.getName());
		computer.setCompany(computerDto.getCompany());
		computer.setIntroduced(Utils.getDateFromString(computerDto
				.getIntroduced()));
		computer.setDiscontinued(Utils.getDateFromString(computerDto
				.getDiscontinued()));
		return computer;
	}

	public static List<ComputerDto> ComputerToDto(List<Computer> computers) {
		List<ComputerDto> computersDto = new ArrayList<ComputerDto>();
		for (Computer computer : computers) {
			computersDto.add(ComputerToDto(computer));
		}
		return computersDto;
	}

	public static List<Computer> ComputerFromDto(List<ComputerDto> ComputerDtos) {
		List<Computer> computers = new ArrayList<Computer>();
		for (ComputerDto computerDto : ComputerDtos) {
			computers.add(ComputerFromDto(computerDto));
		}
		return computers;
	}
}
