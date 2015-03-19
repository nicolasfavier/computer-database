package com.nicolas.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.dao.interfaces.ComputerDao;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;

@Service
public class ComputerServiceImpl implements ComputerService {

	@Autowired
	private ComputerDao computerDao;

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
		List<Computer> comps = computerDao.getAll();
		return comps;
	}

	@Override
	public Page getPage(Page page) {

		long totalComputers = computerDao.getCount(page.getSearch());
		int totalPages = Math.round(totalComputers
				/ page.getNbComputerPerPage());

		page.setTotalPages(totalPages);
		page.setTotalComputers(totalComputers);
		page = computerDao.getPage(page);

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
		Computer comp = computerDao.getByID(computerId);
		return comp;
	}

	@Override
	public void deleteIds(String computerIds) {
		computerDao.deleteIds(computerIds);
	}
}
