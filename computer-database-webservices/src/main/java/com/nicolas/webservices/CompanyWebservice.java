package com.nicolas.webservices;

import java.util.List;

import com.nicolas.models.Company;

public interface CompanyWebservice {
	public List<Company> findAll();

	public void deleteCompany(int id);
}