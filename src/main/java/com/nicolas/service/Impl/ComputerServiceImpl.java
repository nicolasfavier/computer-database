package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.connection.ConnectionManager;
import com.nicolas.dao.impl.DaoManager;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;

public class ComputerServiceImpl implements ComputerService {

	private ComputerDao computerDao = DaoManager.INSTANCE.getComputerDaoImpl();

	public ComputerServiceImpl() {
	}

	public ComputerServiceImpl(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}
	
	@Override
	public void add(Computer computer) {
		ConnectionManager.openConnection(true);
		computerDao.add(computer);
		ConnectionManager.closeConnection(true);
	}

	@Override
	public List<Computer> getAll() {
		ConnectionManager.openConnection(true);
		List<Computer> comps = computerDao.getAll();
		ConnectionManager.closeConnection(true);
		return comps;
	}

	@Override
	public Page getPage(Page page, String name) {
		ConnectionManager.openConnection(true);

		int totalComputers = computerDao.getCount(name); 
		int totalPages = Math.round(computerDao.getCount(name) / page.getNbComputerPerPage());
		
		page.setTotalPages(totalPages);
		page.setTotalComputers(totalComputers);
		page = computerDao.getPage(page, name);
		
		ConnectionManager.closeConnection(true);

		return page;
	}

	@Override
	public void delete(int computerId) {
		ConnectionManager.openConnection(true);
		computerDao.delete(computerId);
		ConnectionManager.closeConnection(true);
	}

	@Override
	public void update(Computer computer) {
		ConnectionManager.openConnection(true);
		computerDao.update(computer);
		ConnectionManager.closeConnection(true);
	}

	@Override
	public Computer getByID(int computerId) {
		ConnectionManager.openConnection(true);
		Computer comp = computerDao.getByID(computerId);
		ConnectionManager.closeConnection(true);
		return comp;
	}

	@Override
	public void deleteIds(String computerIds) {
		ConnectionManager.openConnection(true);
		computerDao.deleteIds(computerIds);
		ConnectionManager.closeConnection(true);
	}
}
