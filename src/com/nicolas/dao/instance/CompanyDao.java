package com.nicolas.dao.instance;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.connection.DbConnection;
import com.nicolas.models.Company;

public class CompanyDao {
	private final static String DB_COMPANY_TABLE = "company";
	private final static String dB_COLUMN_ID = "id";
	private final static String dB_COLUMN_NAME = "name";

	private final static String GET_COMPANY_BY_ID = "SELECT * FROM "
			+ DB_COMPANY_TABLE + " WHERE id=?";
	private final static String GET_ALL_COMPANY = "SELECT * FROM "
			+ DB_COMPANY_TABLE + ";";

	public CompanyDao() {
	}

	public Company getCompanyByID(int companyId) {
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
				company = new Company(rs.getInt(dB_COLUMN_ID),
						rs.getString(dB_COLUMN_NAME));
			}
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	public List<Company> getAllCompanies() {
		List<Company> CompanyList = new ArrayList<Company>();
		java.sql.Statement query;

		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();
			java.sql.ResultSet rs = query.executeQuery(GET_ALL_COMPANY);

			while (rs.next()) {
				Company company = new Company(rs.getInt(dB_COLUMN_ID),
						rs.getString(dB_COLUMN_NAME));
				CompanyList.add(company);
			}
			rs.close();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CompanyList;
	}
}
