package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.service.Interfaces.ComputerService;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;

	private ComputerServiceImpl() {
	}

	@Override
	public boolean add(Computer computer) {
		return ComputerDaoImpl.INSTANCE.add(computer);
	}

	@Override
	public List<Computer> getAll() {
		return ComputerDaoImpl.INSTANCE.getAll();
	}

	@Override
	public Page get(int index) {
		Page page = new Page();
		int totalPages = Math.round(ComputerDaoImpl.INSTANCE.getCount() / Page.NB_COMPUTERS);
		page.setTotalPages(totalPages);
		page.setComputerList( ComputerDaoImpl.INSTANCE.getBoundedList(index));
		page.setIndex(index);
		return page;
	}

	@Override
	public boolean delete(int computerId) {
		return ComputerDaoImpl.INSTANCE.delete(computerId);

	}

	@Override
	public boolean update(Computer computer) {
		return ComputerDaoImpl.INSTANCE.update(computer);

	}

	@Override
	public Computer getByID(int computerId) {
		return ComputerDaoImpl.INSTANCE.getByID(computerId);

	}
}
