package com.nicolas.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public CompanyDao(String DB_HOST, String DB_PORT, String DB_NAME, String DB_TIME_BEHAVIOR,
			String DB_USER, String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD  = DB_PWD;
		dB_TIME_BEHAVIOR = DB_TIME_BEHAVIOR; 
	}

	/*public void addCompany(Company company) {
		java.sql.Statement query;
		
		// create connection
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			
			query = connection.createStatement();
			
			String sql = "INSERT INTO `"
					+ dB_NAME
					+ "`.`"
					+ DB_COMPANY_TABLE
					+ "` (`id`, `name`) VALUES ('"
					+ company.getId() + "', '" + company.getName()
					+ "');";
			
			int rs = query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/

	public ArrayList<Company> getAllCompanies() {
		ArrayList<Company> CompanyList = new ArrayList<Company>();
		java.sql.Statement query;
		
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME + dB_TIME_BEHAVIOR, dB_USER, dB_PWD);
			
			query = connection.createStatement();
			
			java.sql.ResultSet rs = query.executeQuery("SELECT * FROM "	+ DB_COMPANY_TABLE + ";");
			
			while (rs.next()) {
				// Cr�ation de la company
				Company company = new Company( rs.getInt("id"), rs.getString("name"));
				//System.out.println("Company : " + company);
				// ajout de la recette r�cup�r�e � la liste
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
