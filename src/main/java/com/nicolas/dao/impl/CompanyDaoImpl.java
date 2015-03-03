package com.nicolas.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.mapper.CompanyRowMapperSpring;
import com.nicolas.models.Company;
import com.nicolas.runtimeException.PersistenceException;

/**
 * 
 * implementation of CompanyDao to get,add,delete companies
 *
 */
@Repository
public class CompanyDaoImpl implements CompanyDao {
	static Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);

	public final static String DB_COMPANY_TABLE = "company";
	public final static String DB_COLUMN_ID = "id";
	public final static String DB_COLUMN_NAME = "name";

	private final static String GET_COMPANY_BY_ID = "SELECT * FROM " + DB_COMPANY_TABLE
			+ " WHERE id=?";
	private final static String GET_ALL_COMPANY = "SELECT * FROM " + DB_COMPANY_TABLE + ";";
	private final static String DELETE_COMPANY_SQL = "DELETE FROM " + DB_COMPANY_TABLE + " WHERE "
			+ DB_COLUMN_ID + " = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	CompanyRowMapperSpring companyRowMapperSpring;

	public CompanyDaoImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.CompanyDao#getByID(int)
	 */
	@Override
	public Company getByID(int companyId) {
		Company c = null;
		try {
			c = this.jdbcTemplate.queryForObject(GET_COMPANY_BY_ID, new Object[] { companyId },
					companyRowMapperSpring);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
		return c;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.CompanyDao#getAll()
	 */
	@Override
	public List<Company> getAll() {
		List<Company> lc = new ArrayList<Company>();
		try {
			lc = this.jdbcTemplate.query(GET_ALL_COMPANY, companyRowMapperSpring);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
		return lc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.dao.interfaces.CompanyDao#deleteId(int,
	 * java.sql.Connection)
	 */
	@Override
	public void deleteId(int companyId) {
		try {
			this.jdbcTemplate.update(DELETE_COMPANY_SQL, companyId);
		} catch (DataAccessException e) {
			LOGGER.error("[sql error] " + e);
			throw new PersistenceException(e);
		}
	}
}
