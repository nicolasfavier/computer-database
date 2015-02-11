package com.nicolas.Service.Interfaces;

import java.util.List;

import com.nicolas.models.Company;

public interface CompanyService {
	/**
	 * get a company by ID
	 * @param companyId the ID of the company needed
	 * @return the company find or null if not found
	 */
	public Company getByID(int companyId) ;
	
	/**
	 * get all company present in the database
	 * @return a list of companies or an empty List if there is no companies
	 */
	public List<Company> getAll();
}
