package com.nicolas.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.models.Company;

/**
 * 
 * implementation of CompanyDao to get,add,delete companies
 *
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {
	static Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public CompanyDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.CompanyDao#getByID(int)
	 */
	@Override
	@Transactional
	public Company getByID(int companyId) {
		Company c = null;
		Session session = sessionFactory.getCurrentSession();

		// Get All Employees
		Query query = session.createQuery("from Company where id= :id");
		query.setLong("id", companyId);
		@SuppressWarnings("unchecked")
		List<Company> Company = (List<Company>) query.list();
		if (Company.size() > 0) {
			c = Company.get(0);
		}

		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.CompanyDao#getAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Company> getAll() {
		List<Company> lc = new ArrayList<Company>();
		Session session = sessionFactory.getCurrentSession();

		// Get All Computers
		Query query = session.createQuery("from Company");

		lc = (List<Company>) query.list();

		return lc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.CompanyDao#deleteId(int,
	 * java.sql.Connection)
	 */
	@Override
	public void deleteId(int companyId, Session session) {
		Query query = session.createQuery("delete Company where id= :id");
		query.setLong("id", companyId);
		query.executeUpdate();
	}
}
