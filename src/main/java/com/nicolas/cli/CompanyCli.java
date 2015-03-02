package com.nicolas.cli;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	private  CompanyService companyService; // = ServiceManagerImpl.INSTANCE.getCompanyServiceImpl();
	@Autowired
	private InputCliUtils inputCliUtils;
	
	private CompanyCli(){
	}
	
	/**
	 * show all companies return by the database
	 */
	public  void showCompanies(){
		List<Company> companies = new ArrayList<Company>();
		companies = companyService.getAll();
		for(Company c : companies){
			System.out.println(c.toString());
		}
	}
		
	public  void deleteCompany() {
		System.out.println(MENU_COMPANY_DELETE_HEADER);
		int index = selectValidCompanyIndex();
		companyService.DeleteCompany(index);
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
			choice = inputCliUtils.getUserInput(-1, MENU_COMPANY_INDEX, false);
			if(choice == -1)
				return -1;
			tmpCompany = companyService.getByID(choice);
			if (tmpCompany == null)
				error = true;
		} while (error);
		return choice;
	}
}
