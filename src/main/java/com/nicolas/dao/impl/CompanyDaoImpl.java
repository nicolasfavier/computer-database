package com.nicolas.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.connection.DbConnection;
import com.nicolas.dao.DaoUtils;
import com.nicolas.dao.interfaces.CompanyDao;
import com.nicolas.dao.mapper.CompanyRowMapper;
import com.nicolas.models.Company;

public class CompanyDaoImpl implements CompanyDao {
    static Logger LOGGER = LoggerFactory.getLogger(CompanyDaoImpl.class);

	public final static String DB_COMPANY_TABLE = "company";
	public final static String DB_COLUMN_ID = "id";
	public final static String DB_COLUMN_NAME = "name";

	private final static String GET_COMPANY_BY_ID = "SELECT * FROM "
			+ DB_COMPANY_TABLE + " WHERE id=?";
	private final static String GET_ALL_COMPANY = "SELECT * FROM "
			+ DB_COMPANY_TABLE + ";";

	public CompanyDaoImpl() {
	}

	@Override
	public Company getByID(int companyId) {
		PreparedStatement preparedStatement = null;
		java.sql.ResultSet rs = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		Company company = null;

		try {
			preparedStatement = connection.prepareStatement(GET_COMPANY_BY_ID);
			preparedStatement.setInt(1, companyId);

			rs = preparedStatement.executeQuery();

			if (rs.first()) {
				company = CompanyRowMapper.INSTANCE.getObject(rs);
			}

		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}
		return company;
	}

	@Override
	public List<Company> getAll() {
		PreparedStatement preparedStatement = null;
		List<Company> CompanyList = new ArrayList<Company>();
		Connection connection = DbConnection.INSTANCE.getConnection();
		java.sql.ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(GET_ALL_COMPANY);
			rs = preparedStatement.executeQuery();

			CompanyList = CompanyRowMapper.INSTANCE.getList(rs);

		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}
		return CompanyList;
	}
}
