package com.nicolas.dao.instance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;
/**
 * 
 * @author nicolas
 *
 *map a row from the db to a computer
 */
public enum ComputerRowMapper implements RowMappable<Computer> {
	INSTANCE;
	private ComputerRowMapper() {
	}

	public List<Computer> getList(ResultSet rs) throws SQLException {
		List<Computer> computerList = new ArrayList<Computer>();
		while (rs.next()) {
			Computer computer = getObject(rs);
			computerList.add(computer);
		}
		return computerList;
	}

	public Computer getObject(ResultSet rs) throws SQLException {
		Computer computer = null;
		Company company = null;
		
		//if the company is not null we have to map it too
		if (rs.getInt(ComputerDao.DB_COMPUTER_COLUMN_COMPANY_ID) != 0) {
			company = new Company(
					rs.getInt(ComputerDao.DB_COMPUTER_COLUMN_COMPANY_ID),
					rs.getString(ComputerDao.DB_COMPUTER_COLUMN_COMPANY_NAME));
		}
		computer = new Computer(rs.getInt(ComputerDao.DB_COLUMN_ID),
				rs.getString(ComputerDao.DB_COLUMN_NAME), Utils.getLocalDate(rs
						.getTimestamp(ComputerDao.DB_COLUMN_INTRODUCED)),
				Utils.getLocalDate(rs
						.getTimestamp(ComputerDao.DB_COLUMN_DISCONTINUED)),
				company);

		return computer;
	}
}
