package com.nicolas.webservices;

import java.util.List;

import com.nicolas.models.Company;

public interface CompanyWebservice {
		
		/**
		 * @return all companies present in the BD
		 */
		public List<Company> findAll();
		
		/**
		 * @param id
		 * 
		 * delete a company of the given id
		 */
		public void deleteCompany(int id);
}