package com.nicolas.service.Interfaces;

import java.util.List;

import com.nicolas.models.Company;

/**
 * 
 * API to handle the business layer for Companies
 *
 */
public interface CompanyService {
	/**
	 * get a company by ID
	 * 
	 * @param companyId
	 *            the ID of the company needed
	 * @return the company find, or null if not found
	 */
	public Company getByID(int companyId);

	/**
	 * get all companies present in the database
	 * 
	 * @return a list of companies, or an empty List if there is no companies
	 */
	public List<Company> getAll();

	/**
	 * delete the company of the given Id, all computers using this company will
	 * be deleted too
	 * 
	 * @param companyId
	 *            to delete
	 */
	public void DeleteCompany(int companyId);
}
