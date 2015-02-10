package com.nicolas.dao.fabric;

import com.nicolas.dao.instance.CompanyDao;
import com.nicolas.dao.instance.ComputerDao;


public final class DaoFabric {
	private static volatile DaoFabric instance = null;

		
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
		CompanyDao companyDao = new CompanyDao();
		return companyDao;
	}

	public ComputerDao createComputerDao() {
		ComputerDao computerDao = new ComputerDao();
		return computerDao;
	}
}
