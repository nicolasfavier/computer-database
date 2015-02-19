package com.nicolas.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.connection.DbConnection;
import com.nicolas.dao.DaoUtils;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.dao.mapper.ComputerRowMapper;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.utils.Utils;

public class ComputerDaoImpl implements ComputerDao {
	static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);

	public final static String DB_TABLE = "computer";
	public final static String DB_TABLE_COMPANY = "company";
	public final static String DB_COLUMN_ID = "id";
	public final static String DB_COLUMN_NAME = "name";
	public final static String DB_COLUMN_INTRODUCED = "introduced";
	public final static String DB_COLUMN_DISCONTINUED = "discontinued";
	public final static String DB_COMPUTER_COLUMN_COMPANY_ID = "company_id";
	public final static String DB_COMPUTER_COLUMN_COMPANY_NAME = "companyName";
	public final static String DB_COLUMN_COMPANY_ID = "companyId";
	public final static String DB_COLUMN_COUNT = "count";

	private final static String ADD_COMPUTER_SQL = "INSERT INTO `" + DB_TABLE + "` " + "("
			+ DB_COLUMN_NAME + "," + DB_COLUMN_INTRODUCED + "," + DB_COLUMN_DISCONTINUED + ","
			+ DB_COMPUTER_COLUMN_COMPANY_ID + ") VALUES" + "(?,?,?,?);";

	private final static String SELECT_ALL_COMPUTERS_SQL = "SELECT " + ComputerDaoImpl.DB_TABLE
			+ ".*, " + CompanyDaoImpl.DB_COMPANY_TABLE + "." + CompanyDaoImpl.DB_COLUMN_NAME
			+ " AS " + DB_COMPUTER_COLUMN_COMPANY_NAME + " FROM " + ComputerDaoImpl.DB_TABLE
			+ " LEFT JOIN " + CompanyDaoImpl.DB_COMPANY_TABLE + " ON " + ComputerDaoImpl.DB_TABLE
			+ "." + ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_ID + "="
			+ CompanyDaoImpl.DB_COMPANY_TABLE + "." + CompanyDaoImpl.DB_COLUMN_ID;

	private final static String FIND_COMPUTER_BY_ID_SQL = SELECT_ALL_COMPUTERS_SQL + " WHERE "
			+ DB_TABLE + "." + DB_COLUMN_ID + " =?";

	private final static String GET_PAGES_SQL = SELECT_ALL_COMPUTERS_SQL + " WHERE " + DB_TABLE
			+ "." + DB_COLUMN_NAME + " LIKE ? OR "+DB_TABLE_COMPANY+"."+DB_COLUMN_NAME+" LIKE ? ORDER BY " + DB_COLUMN_ID + " LIMIT ?,? ";

	private final static String UPDATE_COMPUTER_SQL = "UPDATE `" + DB_TABLE + "` SET `"
			+ DB_COLUMN_NAME + "`=?,`" + DB_COLUMN_INTRODUCED + "`=?,`" + DB_COLUMN_DISCONTINUED
			+ "`=?,`" + DB_COMPUTER_COLUMN_COMPANY_ID + "`=? WHERE " + DB_COLUMN_ID + " = ?";

	private final static String DELETE_COMPUTER_SQL = "DELETE FROM " + DB_TABLE + " WHERE "
			+ DB_COLUMN_ID + " =?";

	private final static String DELETE_COMPUTERS_SQL = "DELETE FROM " + DB_TABLE + " WHERE "
			+ DB_COLUMN_ID + " IN ?";

	private final static String GET_COUNT_SQL = "SELECT COUNT(*) as " + DB_COLUMN_COUNT + " FROM " +ComputerDaoImpl.DB_TABLE
			+ " LEFT JOIN " + CompanyDaoImpl.DB_COMPANY_TABLE + " ON " + ComputerDaoImpl.DB_TABLE
			+ "." + ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_ID + "="
			+ CompanyDaoImpl.DB_COMPANY_TABLE + "." + CompanyDaoImpl.DB_COLUMN_ID 
			+ " WHERE " +DB_TABLE+"."+ DB_COLUMN_NAME + " LIKE  ? OR "+DB_TABLE_COMPANY+"."+DB_COLUMN_NAME+" LIKE ?";

	public ComputerDaoImpl() {
	}

	@Override
	public void add(Computer computer) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();

		try {

			preparedStatement = DbConnection.INSTANCE.getConnection().prepareStatement(
					ADD_COMPUTER_SQL);

			preparedStatement.setString(1, computer.getName());

			preparedStatement.setTimestamp(2, Utils.getTimestamp(computer.getIntroduced()));

			preparedStatement.setTimestamp(3, Utils.getTimestamp(computer.getDiscontinued()));

			if (computer.getCompany() != null && computer.getCompany().getId() != 0)
				preparedStatement.setInt(4, computer.getCompany().getId());
			else
				preparedStatement.setNull(4, java.sql.Types.BIGINT);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}
	}

	@Override
	public Computer getByID(int index) {
		Computer computer = null;
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		ResultSet rs = null;

		try {

			preparedStatement = connection.prepareStatement(FIND_COMPUTER_BY_ID_SQL);
			preparedStatement.setInt(1, index);

			rs = preparedStatement.executeQuery();

			if (rs.first()) {
				computer = ComputerRowMapper.INSTANCE.getObject(rs);
			}

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return computer;
	}

	@Override
	public List<Computer> getAll() {
		List<Computer> computerList = new ArrayList<Computer>();
		Connection connection = DbConnection.INSTANCE.getConnection();
		java.sql.PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(SELECT_ALL_COMPUTERS_SQL);
			rs = preparedStatement.executeQuery();
			computerList = ComputerRowMapper.INSTANCE.getList(rs);
		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return computerList;
	}

	@Override
	public Page getPage(Page page, String name) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(GET_PAGES_SQL);
			preparedStatement.setString(1, "%" + name + "%");
			preparedStatement.setString(2, "%" + name + "%");
			preparedStatement.setInt(3, page.getIndex() * page.getNbComputerPerPage());
			preparedStatement.setInt(4, page.getNbComputerPerPage());

			rs = preparedStatement.executeQuery();

			page.setComputerList(ComputerDtoMapper.ComputerToDto(ComputerRowMapper.INSTANCE
					.getList(rs)));
			rs.close();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return page;
	}

	public int getCount(String name) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();
		ResultSet rs = null;
		int count = 0;

		try {
			preparedStatement = connection.prepareStatement(GET_COUNT_SQL);
			preparedStatement.setString(1, "%" + name + "%");
			preparedStatement.setString(2, "%" + name + "%");
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				count = rs.getInt(DB_COLUMN_COUNT);
			} else {
				System.out.println("error: could not get the record counts");
			}

			rs.close();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closeResultSet(rs);
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}

		return count;
	}

	@Override
	public void update(Computer computer) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();

		try {
			preparedStatement = connection.prepareStatement(UPDATE_COMPUTER_SQL);

			preparedStatement.setString(1, computer.getName());

			preparedStatement.setTimestamp(2, Utils.getTimestamp(computer.getIntroduced()));

			preparedStatement.setTimestamp(3, Utils.getTimestamp(computer.getDiscontinued()));

			if (computer.getCompany() != null && computer.getCompany().getId() != 0)
				preparedStatement.setInt(4, computer.getCompany().getId());
			else
				preparedStatement.setNull(4, java.sql.Types.BIGINT);

			preparedStatement.setInt(5, computer.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DaoUtils.closePreparedStatement(preparedStatement);
			DbConnection.INSTANCE.closeConnection(connection);
		}
	}

	@Override
	public void delete(int index) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();

		try {
			preparedStatement = connection.prepareStatement(DELETE_COMPUTER_SQL);
			preparedStatement.setInt(1, index);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DbConnection.INSTANCE.closeConnection(connection);
		}
	}

	@Override
	public void deleteIds(String computerIds) {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = DbConnection.INSTANCE.getConnection();

		try {
			preparedStatement = connection.prepareStatement(DELETE_COMPUTERS_SQL);
			preparedStatement.setString(1, "(" + computerIds + ")");
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error(e.toString());
			throw new RuntimeErrorException(new Error());
		} finally {
			DbConnection.INSTANCE.closeConnection(connection);
		}
	}
}
