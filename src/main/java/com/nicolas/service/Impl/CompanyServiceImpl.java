package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.models.Company;
import com.nicolas.service.Interfaces.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	
	private CompanyDaoImpl companyDaoImpl =DaoManagerImpl.INSTANCE.getCompanyDaoImpl();
	
	public CompanyServiceImpl(){}
	
	public CompanyServiceImpl(CompanyDaoImpl companyDaoImpl) {
		this.companyDaoImpl = companyDaoImpl;
	}
	
	@Override
	public Company getByID(int companyId) {
		return companyDaoImpl.getByID(companyId);
	}

	@Override
	public List<Company> getAll() {
		return companyDaoImpl.getAll();
	}


}
