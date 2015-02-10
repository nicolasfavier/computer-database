package com.nicolas.dao.instance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.nicolas.connection.DbConnection;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.utils.Utils;

public class ComputerDao {
	private final static String DB_COMPUTER_TABLE = "computer";
	private final static String DB_COMPUTER_COLUMN_ID = "id";
	private final static String DB_COMPUTER_COLUMN_NAME = "name";
	private final static String DB_COMPUTER_COLUMN_INTRODUCED = "introduced";
	private final static String DB_COMPUTER_COLUMN_DISCONTINUED = "discontinued";
	private final static String DB_COMPUTER_COLUMN_COMPANY_ID = "company_id";

	public ComputerDao() {}


	public void addComputer(Computer computer) {
		java.sql.Statement query;
		java.sql.PreparedStatement preparedStatement = null;

		// create connection
		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();

			String addComputerSQL = "INSERT INTO `" 
					+ DB_COMPUTER_TABLE + "` " + "(" + DB_COMPUTER_COLUMN_NAME
					+ "," + DB_COMPUTER_COLUMN_INTRODUCED + ","
					+ DB_COMPUTER_COLUMN_DISCONTINUED + ","
					+ DB_COMPUTER_COLUMN_COMPANY_ID + ") VALUES" + "(?,?,?,?);";

			preparedStatement = DbConnection.INSTANCE.getConnection().prepareStatement(addComputerSQL);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2,Utils.getTimestamp(computer.getIntroduced()));
			preparedStatement.setTimestamp(3,Utils.getTimestamp(computer.getDisconected()));
			if (computer.getCompany_id() != 0)
				preparedStatement.setInt(4, computer.getCompany_id());
			else
				preparedStatement.setNull(4, 0);

			preparedStatement.executeUpdate();

			query.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Computer getComputerByID(int index) {
		Computer computer = null;
		java.sql.PreparedStatement preparedStatement = null;

		try {
			String findById = "SELECT * FROM " + DB_COMPUTER_TABLE + " WHERE "
					+ DB_COMPUTER_COLUMN_ID + " =?";

			preparedStatement = DbConnection.INSTANCE.getConnection().prepareStatement(findById);
			preparedStatement.setInt(1, index);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.first()) {
				computer = new Computer(
						rs.getInt(DB_COMPUTER_COLUMN_ID),
						rs.getString(DB_COMPUTER_COLUMN_NAME),
						Utils.getLocalDate(rs
								.getTimestamp(DB_COMPUTER_COLUMN_INTRODUCED)),
						Utils.getLocalDate(rs
								.getTimestamp(DB_COMPUTER_COLUMN_DISCONTINUED)),
						rs.getInt(DB_COMPUTER_COLUMN_COMPANY_ID));
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computer;
	}

	public ArrayList<Computer> getAllComputers() {
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		java.sql.Statement query;

		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();
			java.sql.ResultSet rs = query.executeQuery("SELECT * FROM "
					+ DB_COMPUTER_TABLE + ";");

			while (rs.next()) {

				Computer computer = new Computer(
						rs.getInt(DB_COMPUTER_COLUMN_ID),
						rs.getString(DB_COMPUTER_COLUMN_NAME),
						Utils.getLocalDate(rs
								.getTimestamp(DB_COMPUTER_COLUMN_INTRODUCED)),
						Utils.getLocalDate(rs
								.getTimestamp(DB_COMPUTER_COLUMN_DISCONTINUED)),
						rs.getInt(DB_COMPUTER_COLUMN_COMPANY_ID));

				computerList.add(computer);
			}

			rs.close();
			query.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return computerList;
	}

	public Page getPage(int index) {
		Page page = new Page();
		java.sql.PreparedStatement preparedStatement = null;

		try {
			String sqlPage = "SELECT * FROM "+DB_COMPUTER_TABLE+" ORDER BY "+DB_COMPUTER_COLUMN_ID+" LIMIT "+index * Page.NB_COMPUTERS +","+Page.NB_COMPUTERS;
			preparedStatement = DbConnection.INSTANCE.getConnection().prepareStatement(sqlPage);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Computer computer = new Computer(
						rs.getInt(DB_COMPUTER_COLUMN_ID),
						rs.getString(DB_COMPUTER_COLUMN_NAME),
						Utils.getLocalDate(rs.getTimestamp(DB_COMPUTER_COLUMN_INTRODUCED)),
						Utils.getLocalDate(rs.getTimestamp(DB_COMPUTER_COLUMN_DISCONTINUED)),
						rs.getInt(DB_COMPUTER_COLUMN_COMPANY_ID));

				page.getComputerList().add(computer);
			}

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	public void updateComputer(Computer computer) {
		java.sql.PreparedStatement preparedStatement = null;

		try {
			String updateComputerSQL = "UPDATE `" 
					+ DB_COMPUTER_TABLE + "` SET `" + DB_COMPUTER_COLUMN_NAME
					+ "`=?,`" + DB_COMPUTER_COLUMN_INTRODUCED + "`=?,`"
					+ DB_COMPUTER_COLUMN_DISCONTINUED + "`=?,`"
					+ DB_COMPUTER_COLUMN_COMPANY_ID + "`=? WHERE "
					+ DB_COMPUTER_COLUMN_ID + " = ?";

			preparedStatement = DbConnection.INSTANCE.getConnection().prepareStatement(updateComputerSQL);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2,Utils.getTimestamp(computer.getIntroduced()));
			preparedStatement.setTimestamp(3,Utils.getTimestamp(computer.getDisconected()));
			if (computer.getCompany_id() != 0)
				preparedStatement.setInt(4, computer.getCompany_id());
			else
				preparedStatement.setNull(4, 0);
			preparedStatement.setInt(5, computer.getId());
			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteComputer(int index) {
			java.sql.PreparedStatement preparedStatement = null;

			try {
				String deleteById = "DELETE FROM " + DB_COMPUTER_TABLE + " WHERE "
						+ DB_COMPUTER_COLUMN_ID + " =?";

				preparedStatement = DbConnection.INSTANCE.getConnection().prepareStatement(deleteById);
				preparedStatement.setInt(1, index);
				preparedStatement.executeUpdate();
				
				DbConnection.INSTANCE.getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			
		return true;
		}
}
