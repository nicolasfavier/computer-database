package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;

public class ComputerServiceImpl implements ComputerService {

	private ComputerDaoImpl computerDaoImpl = DaoManagerImpl.INSTANCE.getComputerDaoImpl();

	public ComputerServiceImpl() {
	}

	@Override
	public boolean add(Computer computer) {
		return computerDaoImpl.add(computer);
	}

	@Override
	public List<Computer> getAll() {
		return computerDaoImpl.getAll();
	}

	@Override
	public Page get(int index) {
		Page page = new Page();
		int totalPages = Math.round(computerDaoImpl.getCount() / Page.NB_COMPUTERS);
		page.setTotalPages(totalPages);
		page.setComputerList( computerDaoImpl.getBoundedList(index));
		page.setIndex(index);
		return page;
	}

	@Override
	public boolean delete(int computerId) {
		return computerDaoImpl.delete(computerId);

	}

	@Override
	public boolean update(Computer computer) {
		return computerDaoImpl.update(computer);

	}

	@Override
	public Computer getByID(int computerId) {
		return computerDaoImpl.getByID(computerId);

	}
}
