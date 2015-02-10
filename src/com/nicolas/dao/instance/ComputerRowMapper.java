package com.nicolas.dao.instance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

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
		
		computer = new Computer(
				rs.getInt(ComputerDao.DB_COMPUTER_COLUMN_ID),
				rs.getString(ComputerDao.DB_COMPUTER_COLUMN_NAME),
				Utils.getLocalDate(rs
						.getTimestamp(ComputerDao.DB_COMPUTER_COLUMN_INTRODUCED)),
				Utils.getLocalDate(rs
						.getTimestamp(ComputerDao.DB_COMPUTER_COLUMN_DISCONTINUED)),
				rs.getInt(ComputerDao.DB_COMPUTER_COLUMN_COMPANY_ID));

		return computer;
	}
}
