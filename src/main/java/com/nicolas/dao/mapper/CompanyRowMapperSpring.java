package com.nicolas.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.models.Company;

@Component
public class CompanyRowMapperSpring implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet rs, int arg1) throws SQLException {
		Company company = null;
		company = new Company(rs.getInt(CompanyDaoImpl.DB_COLUMN_ID),
				rs.getString(CompanyDaoImpl.DB_COLUMN_NAME));
		return company;
	}
	
}
