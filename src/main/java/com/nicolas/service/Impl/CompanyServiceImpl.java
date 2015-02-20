package com.nicolas.service.Impl;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.models.Company;
import com.nicolas.runtimeException.PersistenceException;
import com.nicolas.service.Interfaces.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private CompanyDaoImpl companyDaoImpl = DaoManagerImpl.INSTANCE.getCompanyDaoImpl();
	private ComputerDaoImpl computerDaoImpl = DaoManagerImpl.INSTANCE.getComputerDaoImpl();

	public CompanyServiceImpl() {
	}

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

	@Override
	public void DeleteCompany(int companyId) {
		Connection connection = ConnectionManager.getConnection(false);

		try {
			computerDaoImpl.deleteByCompanyId(companyId, connection);
			companyDaoImpl.deleteId(companyId, connection);
		} catch (PersistenceException e) {
			ConnectionManager.rollback(connection);
		} finally {
			ConnectionManager.closeConnection(connection, true);
		}
	}
}
