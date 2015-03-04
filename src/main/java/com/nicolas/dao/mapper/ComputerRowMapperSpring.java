package com.nicolas.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.utils.Utils;

@Component
public class ComputerRowMapperSpring implements RowMapper<Computer>{
	
	@Override
	public Computer mapRow(ResultSet rs, int arg1) throws SQLException {
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
