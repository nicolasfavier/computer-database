package com.nicolas.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

/**
 * 
 * @author nicolas
 *
 *         map a row from the db to a computer
 */
public enum ComputerRowMapper implements RowMappable<Computer> {
	INSTANCE;
	private ComputerRowMapper() {
	}

	/**
	 * get a list of computers from a result set
	 */
	public List<Computer> getList(ResultSet rs) throws SQLException {
		List<Computer> computerList = new ArrayList<Computer>();
		while (rs.next()) {
			Computer computer = getObject(rs);
			computerList.add(computer);
		}
		return computerList;
	}

	/**
	 * get a Computer from a result set
	 */
	public Computer getObject(ResultSet rs) throws SQLException {
		Computer computer = null;
		Company company = null;

		// if the company is not null we have to map it too
		if (rs.getInt(ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_ID) != 0) {
			company = new Company(rs.getInt(ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_ID),
					rs.getString(ComputerDaoImpl.DB_COMPUTER_COLUMN_COMPANY_NAME));
		}
		computer = new Computer.Builder()
				.id(rs.getInt(ComputerDaoImpl.DB_COLUMN_ID))
				.name(rs.getString(ComputerDaoImpl.DB_COLUMN_NAME))
				.company(company)
				.introduced(
						Utils.getLocalDate(rs.getTimestamp(ComputerDaoImpl.DB_COLUMN_INTRODUCED)))
				.discontinued(
						Utils.getLocalDate(rs.getTimestamp(ComputerDaoImpl.DB_COLUMN_DISCONTINUED)))
				.build();

		return computer;
	}
}
