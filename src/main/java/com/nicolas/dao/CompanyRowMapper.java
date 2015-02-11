package com.nicolas.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.impl.CompanyDaoImpl;
import com.nicolas.models.Company;

/**
 * 
 * @author nicolas
 *
 *map a row from the db to a company
 */
public enum CompanyRowMapper implements RowMappable<Company> {
	INSTANCE;
	private CompanyRowMapper() {
	}

	public List<Company> getList(ResultSet rs) throws SQLException {
		List<Company> companyList = new ArrayList<Company>();
		while (rs.next()) {
			Company company = getObject(rs);
			companyList.add(company);
		}
		return companyList;
	}

	public Company getObject(ResultSet rs) throws SQLException {
		Company company = null;
		company = new Company(rs.getInt(CompanyDaoImpl.DB_COLUMN_ID),
				rs.getString(CompanyDaoImpl.DB_COLUMN_NAME));
		return company;
	}
}
