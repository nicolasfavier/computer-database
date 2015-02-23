package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;

public class ComputerServiceImpl implements ComputerService {

	private ComputerDao computerDao = DaoManagerImpl.INSTANCE.getComputerDaoImpl();

	public ComputerServiceImpl() {
	}

	public ComputerServiceImpl(ComputerDao computerDao) {
		this.computerDao = computerDao;
	}
	
	@Override
	public void add(Computer computer) {
		computerDao.add(computer);
	}

	@Override
	public List<Computer> getAll() {
		return computerDao.getAll();
	}

	@Override
	public Page getPage(Page page, String name) {
		int totalComputers = computerDao.getCount(name); 
		int totalPages = Math.round(computerDao.getCount(name) / page.getNbComputerPerPage());
		page.setTotalPages(totalPages);
		page.setTotalComputers(totalComputers);
		page = computerDao.getPage(page, name);
		return page;
	}

	@Override
	public void delete(int computerId) {
		computerDao.delete(computerId);

	}

	@Override
	public void update(Computer computer) {
		computerDao.update(computer);

	}

	@Override
	public Computer getByID(int computerId) {
		return computerDao.getByID(computerId);

	}

	@Override
	public void deleteIds(String computerIds) {
		computerDao.deleteIds(computerIds);
	}
}
