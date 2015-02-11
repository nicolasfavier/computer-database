package com.nicolas.Service.Impl;

import java.util.List;

import com.nicolas.Service.Interfaces.ComputerService;
import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;

public enum ComputerServiceImpl implements ComputerService {
	INSTANCE;
		
	private ComputerServiceImpl(){}

	public boolean add(Computer computer){
		return ComputerDaoImpl.INSTANCE.add(computer);
	}
	public List<Computer> getAll(){
		return ComputerDaoImpl.INSTANCE.getAll();
	}
	public Page get(int index){
		return ComputerDaoImpl.INSTANCE.get(index);

	}
	public boolean delete(int computerId){
		return ComputerDaoImpl.INSTANCE.delete(computerId);

	}
	public boolean update(Computer computer){
		return ComputerDaoImpl.INSTANCE.update(computer);

	}
	public Computer getByID(int computerId){
		return ComputerDaoImpl.INSTANCE.getByID(computerId);

	}
}
