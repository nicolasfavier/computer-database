package com.nicolas.dao.interfaces;

import java.util.List;

import com.nicolas.models.Computer;
import com.nicolas.models.Page;


public interface ComputerDao {
	public boolean add(Computer computer);
	public Computer getByID(int index);
	public List<Computer> getAll();
	public Page get(int index);
	public boolean update(Computer computer);
	public boolean delete(int index);
}
