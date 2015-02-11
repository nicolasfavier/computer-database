package com.nicolas.Service.Impl;

import java.util.List;

import com.nicolas.Service.Interfaces.CompanyService;
import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.models.Company;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	
	private CompanyServiceImpl(){}
	
	@Override
	public Company getByID(int companyId) {
		return CompanyDaoImpl.INSTANCE.getByID(companyId);
	}

	@Override
	public List<Company> getAll() {
		return CompanyDaoImpl.INSTANCE.getAll();
	}


}
