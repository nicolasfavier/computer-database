package com.nicolas.dao.interfaces;

import java.util.List;

import com.nicolas.models.Company;

public interface CompanyDao {
	public Company getByID(int companyId) ;
	public List<Company> getAll();
}
