package com.nicolas.cli;

import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.fabric.DaoFabric;
import com.nicolas.dao.instance.CompanyDao;
import com.nicolas.models.Company;

public class CompanyCli {
	private CompanyDao companyDao;
	
	public CompanyCli(){
		companyDao = DaoFabric.getInstance().createCompanyDao();
	}
	
	public void showCompanies(){
		List<Company> companies = new ArrayList<Company>();
		companies = companyDao.getAllCompanies();
		for(Company c : companies){
			System.out.println(c.toString());
		}
		
	}
}
