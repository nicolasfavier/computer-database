package com.nicolas.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.DaoUtils;
import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.mapper.CompanyRowMapper;
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

	private final static String GET_COMPANY_BY_ID = "SELECT * FROM "
			+ DB_COMPANY_TABLE + " WHERE id=?";
	private final static String GET_ALL_COMPANY = "SELECT * FROM "
			+ DB_COMPANY_TABLE + ";";
	private final static String DELETE_COMPANY_SQL = "DELETE FROM " + DB_COMPANY_TABLE + " WHERE "+ DB_COLUMN_ID + " = ?";
			
	public CompanyDaoImpl() {
	}

	/* (non-Javadoc)
	 * @see com.nicolas.dao.interfaces.CompanyDao#getByID(int)
	 */
	@Override
	public Company getByID(int companyId) {
		PreparedStatement preparedStatement = null;
		java.sql.ResultSet rs = null;
		Connection connection = ConnectionManager.getConnection();
		Company company = null;

		try {
			preparedStatement = connection.prepareStatement(GET_COMPANY_BY_ID);
			preparedStatement.setInt(1, companyId);

			rs = preparedStatement.executeQuery();

			if (rs.first()) {
				company = CompanyRowMapper.INSTANCE.getObject(rs);
			}

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection();
		}
		return company;
	}

	/* (non-Javadoc)
	 * @see com.nicolas.dao.interfaces.CompanyDao#getAll()
	 */
	@Override
	public List<Company> getAll() {
		PreparedStatement preparedStatement = null;
		List<Company> CompanyList = new ArrayList<Company>();
		Connection connection = ConnectionManager.getConnection();
		java.sql.ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(GET_ALL_COMPANY);
			rs = preparedStatement.executeQuery();

			CompanyList = CompanyRowMapper.INSTANCE.getList(rs);

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection();
		}
		return CompanyList;
	}
	
	/* (non-Javadoc)
	 * @see com.nicolas.dao.interfaces.CompanyDao#deleteId(int, java.sql.Connection)
	 */
	@Override
	public void deleteId(int companyId) {
		java.sql.PreparedStatement preparedStatement = null;

		try {
			preparedStatement = ConnectionManager.getConnection().prepareStatement(DELETE_COMPANY_SQL);
			preparedStatement.setInt(1, companyId );
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new PersistenceException(e);
		}finally {
			DaoUtils.closePreparedStatement(preparedStatement);
			ConnectionManager.closeConnection();
		}
	}
}
