package com.nicolas.cli;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nicolas.cli.helper.InputCliUtils;
import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;
import com.nicolas.validator.DtoValidator;

/**
 * 
 * Command Line Interface for computers
 *
 */
@Component
public class ComputerCli {

	private static final String MENU_COMPUTER_CREATION_HEADER = "############ Computer creation ############";
	private static final String MENU_COMPUTER_UPDATE_HEADER = "############ Computer Update ############";
	private static final String MENU_COMPUTER_DELETE_HEADER = "############ Computer Delete ############";
	private static final String MENU_COMPUTER_CREATION_NAME = "enter a name :";
	private static final String MENU_COMPUTER_CREATION_INTRODUCED = "enter the introduced date :";
	private static final String MENU_COMPUTER_CREATION_DISCONTINUED = "enter the discontinued date :";
	private static final String MENU_COMPUTER_UPDATE_INDEX = "enter the computer index:";
	private static final String MENU_COMPUTER_DETAILS_INDEX = "enter the computer index for details :";
	private static final String INVALID_INDEX = "This Index is not Valide";

	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyCli companyCli;
	@Autowired
	private ComputerDtoMapper computerDtoMapper;
	private Client client;

	private ComputerCli() {
		client = ClientBuilder.newClient();
	}

	/**
	 * show all computers return by the database
	 */
	public void showComputers() {
		List<ComputerDto> comps = new ArrayList<ComputerDto>();
		
		String entity = client.target("http://localhost:8080/computer-database-webservice/api/")
				.path("computers/all")
				.request(MediaType.APPLICATION_JSON)
				.get(String.class);
		

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			comps = objectMapper.readValue(entity, new TypeReference<HashMap<String,String>>() {});
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String entity = client.target("http://localhost:8080/computer-database-webservice/api/")
//				.path("computers")
//				//.queryParam("greeting", "Hi World!")
//				.request(MediaType.APPLICATION_JSON)
//				.get(String.class);
		
		showComputers(comps);


	}

	private void showComputers(List<ComputerDto> computerDtos) {
		for (ComputerDto c : computerDtos) {
			System.out.println(c.toString());
		}
	}

	/**
	 * show computers by blocks
	 */
	public void showComputersByPage() {
		boolean exit = false;
		Page p = new Page();

		do {
			p = computerService.getPage(p);
			System.out.println(p.toString());
			String input = InputCliUtils.getStringFromUser(
					"enter for next page q for quit", false);
			if (input.equals("q"))
				exit = true;
			if (p.getComputerList().size() < p.nbComputerPerPage)
				exit = true;
			p.setIndex(p.getIndex() + 1);
		} while (!exit);
	}

	public void createComputer() {
		System.out.println(MENU_COMPUTER_CREATION_HEADER);

		String name = InputCliUtils.getStringFromUser(
				MENU_COMPUTER_CREATION_NAME, true);
		String introducedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_INTRODUCED, false);
		String discontinuedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_DISCONTINUED, false);
		int companyId = companyCli.selectValidCompanyIndex();

		Company tmpCompany = null;
		if (companyId != -1)
			tmpCompany = new Company(companyId, "");

		ComputerDto computerDto = new ComputerDto(0, name, introducedDate,
				discontinuedDate, tmpCompany);

		List<String> validationErrors = new ArrayList<>();
		validationErrors = DtoValidator.validate(computerDto);

		if (validationErrors.size() == 0) {
			computerService.add(computerDtoMapper.ComputerFromDto(computerDto));
			System.out.println("create with success");
		} else {
			System.out.println("error");
			for (String validationError : validationErrors) {
				System.out.println(" - " + validationError);
			}
		}

	}

	public void updateComputer() {
		System.out.println(MENU_COMPUTER_UPDATE_HEADER);
		ComputerDto computerDto = computerDtoMapper
				.ComputerToDto(selectValidComputerIndex());

		String name = InputCliUtils.getStringFromUser(
				MENU_COMPUTER_CREATION_NAME, false);
		if (!name.isEmpty())
			computerDto.setName(name);

		String introducedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_INTRODUCED, false);
		if (introducedDate != null)
			computerDto.setIntroduced(introducedDate);

		String discontinuedDate = InputCliUtils.getDateFromUser(
				MENU_COMPUTER_CREATION_DISCONTINUED, false);
		if (discontinuedDate != null)
			computerDto.setDiscontinued(discontinuedDate);

		int companyId = companyCli.selectValidCompanyIndex();
		Company tmpCompany = null;
		if (companyId != -1) {
			tmpCompany = new Company(companyId, "");
			computerDto.setCompany(tmpCompany);
		}

		List<String> validationErrors = new ArrayList<>();
		validationErrors = DtoValidator.validate(computerDto);

		if (validationErrors.size() == 0) {
			computerService.update(computerDtoMapper
					.ComputerFromDto(computerDto));
			System.out.println("update with success");
		} else {
			System.out.println("error");
			for (String validationError : validationErrors) {
				System.out.println(" - " + validationError);
			}
		}
	}

	public void getComputerDetails() {
		int index = InputCliUtils.getUserInput(-1, MENU_COMPUTER_DETAILS_INDEX,
				false);
		Computer detail = computerService.getByID(index);
		if (detail == null) {
			System.out.println(INVALID_INDEX);
		} else {
			System.out.println(detail.toString());
		}
	}

	public void deleteComputer() {
		System.out.println(MENU_COMPUTER_DELETE_HEADER);
		int index = selectValidComputerIndex().getId();
		computerService.delete(index);
		System.out.println("deleted with success");

	}

	private Computer selectValidComputerIndex() {
		Computer tmpComputer = null;
		boolean error = false;
		do {
			if (error)
				System.out.println("The index does not exist");
			error = false;
			int index = InputCliUtils.getUserInput(-1,
					MENU_COMPUTER_UPDATE_INDEX, false);
			tmpComputer = computerService.getByID(index);
			if (tmpComputer == null)
				error = true;
		} while (error);
		return tmpComputer;
	}

}
