package com.nicolas.cli;

import java.util.ArrayList;
import java.util.List;

import com.nicolas.dao.fabric.DaoFabric;
import com.nicolas.dao.instance.CompanyDao;
import com.nicolas.dao.instance.ComputerDao;
import com.nicolas.models.Computer;

public class Launcher {

	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		CompanyDao companyDao = DaoFabric.getInstance().createCompanyDao();
		ComputerDao computerDao = DaoFabric.getInstance().createComputerDao();

		List<Computer> computers = new ArrayList<Computer>();
		computers = computerDao.getAllComputers();
		
		System.out.println("Finish !");

	}

}
