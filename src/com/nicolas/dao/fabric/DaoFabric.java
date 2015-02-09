package com.nicolas.dao.fabric;

import com.nicolas.dao.instance.CompanyDao;
import com.nicolas.dao.instance.ComputerDao;


public final class DaoFabric {
	private static volatile DaoFabric instance = null;
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "computer-database-db";
	private static final String DB_TIME_BEHAVIOR = "?zeroDateTimeBehavior=convertToNull";
	private static final String DB_USER = "admincdb";
	private static final String DB_PWD = "qwerty1234";
		
	private DaoFabric() {
		super();

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public final synchronized static DaoFabric getInstance() {
		if (DaoFabric.instance == null) {
			synchronized (DaoFabric.class) {
				if (DaoFabric.instance == null) {
					DaoFabric.instance = new DaoFabric();
				}
			}
		}
		return DaoFabric.instance;
	}

	public CompanyDao createCompanyDao() {
		CompanyDao companyDao = new CompanyDao(this.DB_HOST, this.DB_PORT, this.DB_NAME, this.DB_TIME_BEHAVIOR,
				this.DB_USER, this.DB_PWD);
		return companyDao;
	}

	public ComputerDao createComputerDao() {
		ComputerDao computerDao = new ComputerDao(this.DB_HOST, this.DB_PORT,
				this.DB_NAME,this.DB_TIME_BEHAVIOR, this.DB_USER, this.DB_PWD);
		return computerDao;
	}
}
