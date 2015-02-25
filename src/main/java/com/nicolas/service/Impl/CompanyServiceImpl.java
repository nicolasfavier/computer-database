package com.nicolas.service.Impl;

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
import com.nicolas.runtimeException.ServiceException;
import com.nicolas.service.Interfaces.CompanyService;

/**
 * implementation of Company service
 *
 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.service.Interfaces.CompanyService#getByID(int)
	 */
	@Override
	public Company getByID(int companyId) {
		Company comp = companyDao.getByID(companyId);
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.service.Interfaces.CompanyService#getAll()
	 */
	@Override
	public List<Company> getAll() {
		List<Company> comps = companyDao.getAll();
		return comps;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.service.Interfaces.CompanyService#DeleteCompany(int)
	 */
	@Override
	public void DeleteCompany(int companyId) {
		ConnectionManager.initTransactionConnection();
		try {
			computerDao.deleteByCompanyId(companyId);
			companyDao.deleteId(companyId);
		} catch (PersistenceException e) {
			ConnectionManager.rollback();
			throw new ServiceException(e);
		} finally {
			ConnectionManager.closeTransactionConnection();
		}
	}
}
