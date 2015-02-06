package com.nicolas.dao.instance;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.models.Computer;

public class ComputerDao {
	private Connection connection;
	private String dB_HOST;
	private String dB_PORT;
	private String dB_NAME;
	private String dB_USER;
	private String dB_PWD;
	private final static String DB_COMPUTER_TABLE = "computer";
	private final static String DB_COMPUTER_COLUMN_ID = "id";
	private final static String DB_COMPUTER_COLUMN_NAME = "name";
	private final static String DB_COMPUTER_COLUMN_INTRODUCED = "introduced";
	private final static String DB_COMPUTER_COLUMN_DISCONTINUED = "discontinued";
	private final static String DB_COMPUTER_COLUMN_COMPANY_ID = "company_id";

	
	public ComputerDao(String DB_HOST, String DB_PORT, String DB_NAME,
			String DB_USER, String DB_PWD) {
		dB_HOST = DB_HOST;
		dB_PORT = DB_PORT;
		dB_NAME = DB_NAME;
		dB_USER = DB_USER;
		dB_PWD = DB_PWD;
	}

	public void addComputer(Computer computer) {
		java.sql.Statement query;
		// create connection
		try {
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			query = connection.createStatement();
			String sql = "INSERT INTO `"
					+ dB_NAME
					+ "`.`"
					+ DB_COMPUTER_TABLE
					+ "` (`"+ DB_COMPUTER_COLUMN_ID +"`, `"+ DB_COMPUTER_COLUMN_NAME +"`, `"+ DB_COMPUTER_COLUMN_INTRODUCED +"`, `"+ DB_COMPUTER_COLUMN_DISCONTINUED +"` , `"+ DB_COMPUTER_COLUMN_COMPANY_ID +"`) VALUES ('"
					+ computer.getId() 
					+ "', '"+ computer.getName()
					+ "', '" + computer.getIntroduced()
					+ "', '" + computer.getDisconected()
					+ "', '" + computer.getCompany_id()  + "');";
			
			int rs = query.executeUpdate(sql);
			
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Computer> getAllComputers() {
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			query = connection.createStatement();
			java.sql.ResultSet rs = query.executeQuery("SELECT * FROM "
					+ DB_COMPUTER_TABLE + ";");
			while (rs.next()) {
				// Cr�ation de la recette
				Computer computer = new Computer(
						rs.getInt(DB_COMPUTER_COLUMN_ID), rs.getString(DB_COMPUTER_COLUMN_NAME),
						rs.getDate(DB_COMPUTER_COLUMN_INTRODUCED), rs.getDate(DB_COMPUTER_COLUMN_DISCONTINUED),
						rs.getInt(DB_COMPUTER_COLUMN_COMPANY_ID));
				System.out.println("Computer : " + computer);
				// ajout de la recette r�cup�r�e � la liste
				computerList.add(computer);
			}
						
			rs.close();
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}


	public void updateComputer(Computer computer) {
		// Cr�ation de la requ�te
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			
			// Creation de l'�l�ment de requ�te
			query = connection.createStatement();
			
			String sql = "UPDATE `" + dB_NAME + "`.`" + DB_COMPUTER_TABLE
					+ "` SET `" + DB_COMPUTER_COLUMN_ID + "`='" + computer.getId()
					+ "',`" + DB_COMPUTER_COLUMN_NAME + "`='" + computer.getName()
					+ "',`" + DB_COMPUTER_COLUMN_INTRODUCED + "`='" + computer.getIntroduced()
					+ "',`" + DB_COMPUTER_COLUMN_DISCONTINUED + "`='" + computer.getDisconected()
					+ "',`" + DB_COMPUTER_COLUMN_COMPANY_ID + "`='" + computer.getCompany_id()
					+ "' WHERE `computers`.`id` = '" + computer.getId() + "'";
			
			query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(Computer computer) {
		// Cr�ation de la requ�te
		java.sql.Statement query;
		try {
			// create connection
			connection = java.sql.DriverManager.getConnection("jdbc:mysql://"
					+ dB_HOST + ":" + dB_PORT + "/" + dB_NAME, dB_USER, dB_PWD);
			
			// Creation de l'�l�ment de requ�te
			query = connection.createStatement();
			
			// Executer puis parcourir les r�sultats
			String sql = "DELETE FROM`" + dB_NAME + "`.`" + DB_COMPUTER_TABLE
					+ "` WHERE `computers`.`id` = '" + computer.getId() + "'";
			
		    query.executeUpdate(sql);
			query.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
