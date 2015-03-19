package com.nicolas.webservices;

import java.util.List;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.PageDto;
import com.nicolas.models.Computer;
import com.nicolas.models.Page.ComputerSortCriteria;

public interface ComputerWebservice {


	/**
	 * @param id
	 * @return a computer Dto from the given ID
	 */
	public ComputerDto findById(int id);

	/**
	 * @return all computers Dto
	 */
	public List<ComputerDto> findAll();

	/**
	 * @param search, a string to filter on computers names and companies
	 * @param sortCriterion, ASD or DSC
	 * @param nbComputerPerPage, nb of computers to show by page
	 * @param index
	 * @return a pageDTO from the given params
	 */
	public PageDto findPage(String search, ComputerSortCriteria sortCriterion,
			int nbComputerPerPage, int index);

	/**
	 * @param computer 
	 * 
	 * add a computer on the database from the given param
	 */
	public void addComputer(ComputerDto computerDto);

	/**
	 * @param computer, computer to update
	 * 
	 * update the computer on the bd
	 */
	public void updateComputer(ComputerDto computerDto);

	/**
	 * @param id
	 * 
	 * delete a computer from the given ID
	 */
	public void deleteComputer(int id);
}
