package com.nicolas.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.dao.impl.ComputerDaoImpl;
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
@Service
public class CompanyServiceImpl implements CompanyService {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ComputerDao computerDao;

	public CompanyServiceImpl() {
	}

	public CompanyServiceImpl(CompanyDao companyDao, ComputerDao computerDao) {
		this.companyDao = companyDao;
		this.computerDao = computerDao;
	}

	/*
	 * (non-Javadoc)	 * 
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
	@Transactional(rollbackFor = ServiceException.class)
	public void DeleteCompany(int companyId) {
		try {
			computerDao.deleteByCompanyId(companyId);
			companyDao.deleteId(companyId);
		} catch (PersistenceException e) {
			LOGGER.error("[service] " + e);
			throw new ServiceException(e);
		}
	}
}
