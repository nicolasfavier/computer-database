package com.nicolas.service.Impl;

import java.util.List;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dao.impl.DaoManagerImpl;
import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
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
	public Page getPage(int index, int nbComputerPerPage, String name) {
		Page page = new Page();
		int totalComputers = computerDaoImpl.getCount(name); 
		int totalPages = Math.round(computerDaoImpl.getCount(name) / nbComputerPerPage);
		page.setTotalPages(totalPages);
		page.setTotalComputers(totalComputers);
		List<Computer> computers = computerDaoImpl.getBoundedList(index, nbComputerPerPage, name);
		page.setComputerList(ComputerDtoMapper.ComputerToDto(computers));
		page.setIndex(index);
		page.setNbComputerPerPage(nbComputerPerPage);
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

	@Override
	public boolean deleteIds(String computerIds) {
		return computerDaoImpl.deleteIds(computerIds);
	}
}
