package com.nicolas.dao.instance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	private final static String ADD_COMPUTER_SQL = "INSERT INTO `"
			+ DB_COMPUTER_TABLE + "` " + "(" + DB_COMPUTER_COLUMN_NAME + ","
			+ DB_COMPUTER_COLUMN_INTRODUCED + ","
			+ DB_COMPUTER_COLUMN_DISCONTINUED + ","
			+ DB_COMPUTER_COLUMN_COMPANY_ID + ") VALUES" + "(?,?,?,?);";
	
	private final static String FIND_COMPUTER_BY_ID_SQL = "SELECT * FROM "
			+ DB_COMPUTER_TABLE + " WHERE " + DB_COMPUTER_COLUMN_ID + " =?";

	private final static String SELECT_ALL_COMPUTERS_SQL = "SELECT * FROM "
			+ DB_COMPUTER_TABLE + ";";

	private final static String GET_PAGES_SQL = "SELECT * FROM "
			+ DB_COMPUTER_TABLE + " ORDER BY " + DB_COMPUTER_COLUMN_ID
			+ " LIMIT ?,?";

	private final static String UPDATE_COMPUTER_SQL = "UPDATE `"
			+ DB_COMPUTER_TABLE + "` SET `" + DB_COMPUTER_COLUMN_NAME + "`=?,`"
			+ DB_COMPUTER_COLUMN_INTRODUCED + "`=?,`"
			+ DB_COMPUTER_COLUMN_DISCONTINUED + "`=?,`"
			+ DB_COMPUTER_COLUMN_COMPANY_ID + "`=? WHERE "
			+ DB_COMPUTER_COLUMN_ID + " = ?";

	private final static String DELETE_COMPUTER_SQL = "DELETE FROM "
			+ DB_COMPUTER_TABLE + " WHERE " + DB_COMPUTER_COLUMN_ID + " =?";

	public ComputerDao() {
	}

	public void addComputer(Computer computer) {
		java.sql.Statement query;
		java.sql.PreparedStatement preparedStatement = null;

		// create connection
		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();

			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(ADD_COMPUTER_SQL);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2,
					Utils.getTimestamp(computer.getIntroduced()));
			preparedStatement.setTimestamp(3,
					Utils.getTimestamp(computer.getDisconected()));
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

			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(FIND_COMPUTER_BY_ID_SQL);
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

	public List<Computer> getAllComputers() {
		List<Computer> computerList = new ArrayList<Computer>();
		java.sql.Statement query;

		try {
			query = DbConnection.INSTANCE.getConnection().createStatement();
			java.sql.ResultSet rs = query
					.executeQuery(SELECT_ALL_COMPUTERS_SQL);

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
			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(GET_PAGES_SQL);
			preparedStatement.setInt(1, index * Page.NB_COMPUTERS);
			preparedStatement.setInt(2,  Page.NB_COMPUTERS);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {

				Computer computer = new Computer(
						rs.getInt(DB_COMPUTER_COLUMN_ID),
						rs.getString(DB_COMPUTER_COLUMN_NAME),
						Utils.getLocalDate(rs
								.getTimestamp(DB_COMPUTER_COLUMN_INTRODUCED)),
						Utils.getLocalDate(rs
								.getTimestamp(DB_COMPUTER_COLUMN_DISCONTINUED)),
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
			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(UPDATE_COMPUTER_SQL);
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setTimestamp(2,
					Utils.getTimestamp(computer.getIntroduced()));
			preparedStatement.setTimestamp(3,
					Utils.getTimestamp(computer.getDisconected()));
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
			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(DELETE_COMPUTER_SQL);
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
