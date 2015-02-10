package com.nicolas.dao.instance;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.connection.DbConnection;
import com.nicolas.models.Company;

public enum CompanyDao {
	INSTANCE;
	
	public final static String DB_COMPANY_TABLE = "company";
	public final static String DB_COLUMN_ID = "id";
	public final static String DB_COLUMN_NAME = "name";

	private final static String GET_COMPANY_BY_ID = "SELECT * FROM "
			+ DB_COMPANY_TABLE + " WHERE id=?";
	private final static String GET_ALL_COMPANY = "SELECT * FROM "
			+ DB_COMPANY_TABLE + ";";

	private CompanyDao() {
	}

	public Company getByID(int companyId) {
		java.sql.Statement query;
		PreparedStatement preparedStatement = null;
		Company company = null;

		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();
			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(GET_COMPANY_BY_ID);
			preparedStatement.setInt(1, companyId);

			java.sql.ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.first()) {
				company = CompanyRowMapper.INSTANCE.getObject(rs);
			}
			
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	public List<Company> getAll() {
		List<Company> CompanyList = new ArrayList<Company>();
		java.sql.Statement query;

		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();
			java.sql.ResultSet rs = query.executeQuery(GET_ALL_COMPANY);

			CompanyList = CompanyRowMapper.INSTANCE.getList(rs);
			
			rs.close();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CompanyList;
	}
}
