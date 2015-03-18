package com.nicolas.cli;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nicolas.cli.helper.InputCliUtils;
import com.nicolas.models.Company;
import com.nicolas.service.Interfaces.CompanyService;

/**
 * 
 * Command Line Interface for companies
 *
 */
@Component
public class CompanyCli {
	private static final String MENU_COMPANY_INDEX = "enter the computer index:";
	private static final String MENU_COMPANY_DELETE_HEADER = "############ Company Delete ############";
	
	@Autowired
	private  CompanyService companyService;
	private Client client;
	private WebTarget companyTarget;
	
	public CompanyCli() {
		client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
		companyTarget = client.target("http://localhost:8080/computer-database-webservice/api/companies");
	
	}
	
	/**
	 * show all companies return by the database
	 */
	public  void showCompanies(){
		List<Company> companies = new ArrayList<Company>();
		
		companies = companyTarget
				.request(MediaType.APPLICATION_JSON)
				.get(new GenericType<List<Company>>() {});

		for(Company c : companies){
			System.out.println(c.toString());
		}
	}
		
	/**
	 *  let the user select an id to delete and delte it
	 */
	public  void deleteCompany() {
		System.out.println(MENU_COMPANY_DELETE_HEADER);
		int index = selectValidCompanyIndex();
		
		companyTarget
		.path("/"+index)
		.request(MediaType.APPLICATION_JSON)
		.delete();
		
		System.out.println("deleted with success");

	}
	
	/**
	 * 
	 * @return int
	 * 
	 * ask for a company Id, if the id exist in the db return it else ask again,
	 * if nothing is enter, skip and return null
	 */
	public  int selectValidCompanyIndex() {
		int choice = 0;
		boolean error = false;

		showCompanies();
		Company tmpCompany = null;
		do {
			if(error)
				System.out.println("The index does not exist");
			error = false;
			choice = InputCliUtils.getUserInput(-1, MENU_COMPANY_INDEX, false);
			if(choice == -1)
				return -1;
			tmpCompany = companyService.getByID(choice);
			if (tmpCompany == null)
				error = true;
		} while (error);
		return choice;
	}
}
