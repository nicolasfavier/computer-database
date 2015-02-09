package com.nicolas.dao.instance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nicolas.models.Company;

public class CompanyDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	private String dB_TIME_BEHAVIOR;

	private final static String DB_COMPANY_TABLE = "company";
	private final static String dB_COLUMN_ID = "id";
	private final static String dB_COLUMN_NAME = "name";

	public CompanyDao(String DB_HOST, String DB_PORT, String DB_NAME,
			String DB_TIME_BEHAVIOR, String DB_USER, String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
		dB_TIME_BEHAVIOR = DB_TIME_BEHAVIOR;
	}

	public Company getCompanyByID(int companyId) {
		java.sql.Statement query;
		PreparedStatement preparedStatement = null;
		Company company = null;

		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME
					+ dB_TIME_BEHAVIOR, dB_USER, dB_PWD);

			query = connection.createStatement();

			String sql = "SELECT * FROM " + DB_COMPANY_TABLE + " WHERE id=?";
			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, companyId);
			java.sql.ResultSet rs = preparedStatement.executeQuery();
			if (rs.first()) {
				company = new Company(rs.getInt(dB_COLUMN_ID),
						rs.getString(dB_COLUMN_NAME));
			}
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return company;
	}

	public ArrayList<Company> getAllCompanies() {
		ArrayList<Company> CompanyList = new ArrayList<Company>();
		java.sql.Statement query;

		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME
					+ dB_TIME_BEHAVIOR, dB_USER, dB_PWD);

			query = connection.createStatement();

			java.sql.ResultSet rs = query.executeQuery("SELECT * FROM "
					+ DB_COMPANY_TABLE + ";");

			while (rs.next()) {
				Company company = new Company(rs.getInt(dB_COLUMN_ID),
						rs.getString(dB_COLUMN_NAME));
				CompanyList.add(company);
			}
			rs.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CompanyList;
	}
}
