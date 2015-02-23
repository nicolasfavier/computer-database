package com.nicolas.service.Impl;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Company;
import com.nicolas.runtimeException.PersistenceException;
import com.nicolas.service.Interfaces.CompanyService;

public class CompanyServiceImpl implements CompanyService {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	private CompanyDao companyDao = DaoManagerImpl.INSTANCE.getCompanyDaoImpl();
	private ComputerDao computerDao = DaoManagerImpl.INSTANCE.getComputerDaoImpl();

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
