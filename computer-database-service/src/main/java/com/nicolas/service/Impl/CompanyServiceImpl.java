package com.nicolas.service.Impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Company;
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
	@Autowired
	private SessionFactory sessionFactory;

	public CompanyServiceImpl() {
	}

	public CompanyServiceImpl(CompanyDao companyDao, ComputerDao computerDao) {
		this.companyDao = companyDao;
		this.computerDao = computerDao;
	}

	/*
	 * (non-Javadoc) *
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
		Session session = null;
		Transaction tx = null;

		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			tx.setTimeout(20);

			computerDao.deleteByCompanyId(companyId, session);
			companyDao.deleteId(companyId, session);

			tx.commit();

		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				LOGGER.error("Couldn’t roll back transaction", rbe);
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
}
