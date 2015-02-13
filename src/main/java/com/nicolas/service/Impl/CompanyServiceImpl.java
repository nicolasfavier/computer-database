package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.models.Company;
import com.nicolas.service.Interfaces.CompanyService;

public enum CompanyServiceImpl implements CompanyService {
	INSTANCE;
	
	private CompanyDaoImpl companyDaoImpl =DaoManagerImpl.INSTANCE.getCompanyDaoImpl();
	
	private CompanyServiceImpl(){}
	
	@Override
	public Company getByID(int companyId) {
		return companyDaoImpl.getByID(companyId);
	}

	@Override
	public List<Company> getAll() {
		return companyDaoImpl.getAll();
	}


}
