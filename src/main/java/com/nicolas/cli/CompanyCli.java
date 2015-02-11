package com.nicolas.cli;

import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.instance.CompanyDao;
import com.nicolas.models.Company;

public class CompanyCli {
	private static final String MENU_COMPANY_INDEX = "enter the computer index:";

	private CompanyCli(){
	}
	
	public static void showCompanies(){
		List<Company> companies = new ArrayList<Company>();
		companies = CompanyDao.INSTANCE.getAll();
		for(Company c : companies){
			System.out.println(c.toString());
		}
	}
	
	public static int selectValidCompanyIndex() {
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
			tmpCompany = CompanyDao.INSTANCE.getByID(choice);
			if (tmpCompany == null)
				error = true;
		} while (error);
		return choice;
	}
}
