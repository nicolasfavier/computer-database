package com.nicolas.dao.instance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.connection.DbConnection;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.utils.Utils;

public enum ComputerDao {
	INSTANCE;

	public final static String DB_TABLE = "computer";
	public final static String DB_COLUMN_ID = "id";
	public final static String DB_COLUMN_NAME = "name";
	public final static String DB_COLUMN_INTRODUCED = "introduced";
	public final static String DB_COLUMN_DISCONTINUED = "discontinued";
	public final static String DB_COMPUTER_COLUMN_COMPANY_ID = "company_id";
	public final static String DB_COMPUTER_COLUMN_COMPANY_NAME = "companyName";
	public final static String DB_COLUMN_COMPANY_ID = "companyId";

	private final static String ADD_COMPUTER_SQL = "INSERT INTO `" + DB_TABLE
			+ "` " + "(" + DB_COLUMN_NAME + "," + DB_COLUMN_INTRODUCED + ","
			+ DB_COLUMN_DISCONTINUED + "," + DB_COMPUTER_COLUMN_COMPANY_ID
			+ ") VALUES" + "(?,?,?,?);";

	private final static String SELECT_ALL_COMPUTERS_SQL = "SELECT "
			+ ComputerDao.DB_TABLE + ".*, " + CompanyDao.DB_COMPANY_TABLE + "."
			+ CompanyDao.DB_COLUMN_NAME + " AS "
			+ DB_COMPUTER_COLUMN_COMPANY_NAME + " FROM " + ComputerDao.DB_TABLE
			+ " LEFT JOIN " + CompanyDao.DB_COMPANY_TABLE + " ON "
			+ ComputerDao.DB_TABLE + "."
			+ ComputerDao.DB_COMPUTER_COLUMN_COMPANY_ID + "="
			+ CompanyDao.DB_COMPANY_TABLE + "." + CompanyDao.DB_COLUMN_ID;

	private final static String FIND_COMPUTER_BY_ID_SQL = SELECT_ALL_COMPUTERS_SQL
			+ " WHERE " + DB_TABLE + "." + DB_COLUMN_ID + " =?";

	private final static String GET_PAGES_SQL = SELECT_ALL_COMPUTERS_SQL
			+ " ORDER BY " + DB_COLUMN_ID + " LIMIT ?,?";

	private final static String UPDATE_COMPUTER_SQL = "UPDATE `" + DB_TABLE
			+ "` SET `" + DB_COLUMN_NAME + "`=?,`" + DB_COLUMN_INTRODUCED
			+ "`=?,`" + DB_COLUMN_DISCONTINUED + "`=?,`"
			+ DB_COMPUTER_COLUMN_COMPANY_ID + "`=? WHERE " + DB_COLUMN_ID
			+ " = ?";

	private final static String DELETE_COMPUTER_SQL = "DELETE FROM " + DB_TABLE
			+ " WHERE " + DB_COLUMN_ID + " =?";

	private ComputerDao() {
	}

	public boolean add(Computer computer) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		boolean res = false;

		try {

			preparedStatement = DbConnection.INSTANCE.getConnection()
					.prepareStatement(ADD_COMPUTER_SQL);

			preparedStatement.setString(1, computer.getName());

			preparedStatement.setTimestamp(2,
					Utils.getTimestamp(computer.getIntroduced()));

			preparedStatement.setTimestamp(3,
					Utils.getTimestamp(computer.getDiscontinued()));

			if (computer.getCompany() != null)
				preparedStatement.setInt(4, computer.getCompany().getId());
			else
				preparedStatement.setNull(4, java.sql.Types.BIGINT);

			if (preparedStatement.executeUpdate() > 0)
				res = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}
		return res;
	}

	public Computer getByID(int index) {
		Computer computer = null;
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		ResultSet rs = null;

		try {

			preparedStatement = connection
					.prepareStatement(FIND_COMPUTER_BY_ID_SQL);
			preparedStatement.setInt(1, index);

			rs = preparedStatement.executeQuery();

			if (rs.first()) {
				computer = ComputerRowMapper.INSTANCE.getObject(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return computer;
	}

	public List<Computer> getAll() {
		List<Computer> computerList = new ArrayList<Computer>();
		Connection connection = DbConnection.INSTANCE.getConnection();
		java.sql.PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			preparedStatement = connection
					.prepareStatement(SELECT_ALL_COMPUTERS_SQL);
			rs = preparedStatement.executeQuery();
			computerList = ComputerRowMapper.INSTANCE.getList(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return computerList;
	}

	public Page get(int index) {
		Page page = new Page();
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(GET_PAGES_SQL);
			preparedStatement.setInt(1, index * Page.NB_COMPUTERS);
			preparedStatement.setInt(2, Page.NB_COMPUTERS);

			rs = preparedStatement.executeQuery();

			page.getComputerList().addAll(
					ComputerRowMapper.INSTANCE.getList(rs));
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return page;
	}

	public boolean update(Computer computer) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		boolean res = false;

		try {
			preparedStatement = connection
					.prepareStatement(UPDATE_COMPUTER_SQL);

			preparedStatement.setString(1, computer.getName());

			preparedStatement.setTimestamp(2,
					Utils.getTimestamp(computer.getIntroduced()));

			preparedStatement.setTimestamp(3,
					Utils.getTimestamp(computer.getDiscontinued()));

			if (computer.getCompany() != null)
				preparedStatement.setInt(4, computer.getCompany().getId());
			else
				preparedStatement.setNull(4, java.sql.Types.BIGINT);

			preparedStatement.setInt(5, computer.getId());

			if (preparedStatement.executeUpdate() > 0)
				res = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}
		return res;
	}

	public boolean delete(int index) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		boolean res = false;

		try {
			preparedStatement = connection
					.prepareStatement(DELETE_COMPUTER_SQL);
			preparedStatement.setInt(1, index);
			preparedStatement.executeUpdate();
			res = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbConnection.INSTANCE.closeConnection(connection);
		}
		return res;
	}
}
