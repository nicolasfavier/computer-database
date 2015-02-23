package com.nicolas.service.Impl;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.impl.DaoManager;
import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Company;
import com.nicolas.runtimeException.PersistenceException;
import com.nicolas.service.Interfaces.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private CompanyDao companyDao = DaoManager.INSTANCE.getCompanyDaoImpl();
	private ComputerDao computerDao = DaoManager.INSTANCE.getComputerDaoImpl();

	public CompanyServiceImpl() {
	}

	public CompanyServiceImpl(CompanyDao companyDao, ComputerDao computerDao) {
		this.companyDao = companyDao;
		this.computerDao = computerDao;
	}

	@Override
	public Company getByID(int companyId) {
		return companyDao.getByID(companyId);
	}

	@Override
	public List<Company> getAll() {
		return companyDao.getAll();
	}

	/**
	 * Delete a company, all computers with this id are deleted and then 
	 * the company is deleted. the operation is placed in a transaction
	 */
	@Override
	public void DeleteCompany(int companyId) {
		Connection connection = ConnectionManager.getConnection(false);

		try {
			computerDao.deleteByCompanyId(companyId, connection);
			companyDao.deleteId(companyId, connection);
		} catch (PersistenceException e) {
			ConnectionManager.rollback(connection);
		} finally {
			ConnectionManager.closeConnection(connection, true);
		}
	}
}
