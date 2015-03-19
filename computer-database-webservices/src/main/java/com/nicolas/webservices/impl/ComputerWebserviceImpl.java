package com.nicolas.webservices.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.dto.PageDto;
import com.nicolas.dto.PageDtoMapper;
import com.nicolas.models.Computer;
import com.nicolas.models.Page;
import com.nicolas.models.Page.ComputerSortCriteria;
import com.nicolas.service.Interfaces.ComputerService;
import com.nicolas.webservices.ComputerWebservice;

@Path("/computers")
public class ComputerWebserviceImpl implements ComputerWebservice {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerDtoMapper computerDtoMapper;
	@Autowired
	private PageDtoMapper pageDtoMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.webservices.ComputerWebservice#findById(int)
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ComputerDto findById(@PathParam("id") int id) {
		return computerDtoMapper.ComputerToDto(computerService.getByID(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.webservices.ComputerWebservice#findAll()
	 */
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComputerDto> findAll() {
		return computerDtoMapper.ComputerToDto(computerService.getAll());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.webservices.ComputerWebservice#findPage(java.lang.String,
	 * com.nicolas.models.Page.ComputerSortCriteria, int, int)
	 */
	@GET
	@Path("/page")
	@Produces(MediaType.APPLICATION_JSON)
	public PageDto findPage(
			@DefaultValue("") @QueryParam("search") String search,
			@DefaultValue("ID") @QueryParam("sortCriterion") ComputerSortCriteria sortCriterion,
			@DefaultValue("50") @QueryParam("nbComputerPerPage") int nbComputerPerPage,
			@DefaultValue("0") @QueryParam("index") int index) {

		Page page = new Page();
		page.setSearch(search);
		page.setSortCriterion(sortCriterion);
		page.setNbComputerPerPage(nbComputerPerPage);
		page.setIndex(index);

		page = computerService.getPage(page);

		return pageDtoMapper.toDto(page);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.webservices.ComputerWebservice#addComputer(com.nicolas.models
	 * .Computer)
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComputer(ComputerDto computerDto) {
		Computer computer = computerDtoMapper.ComputerFromDto(computerDto);
		computerService.add(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nicolas.webservices.ComputerWebservice#updateComputer(com.nicolas
	 * .models.Computer)
	 */
	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateComputer(ComputerDto computerDto) {
		Computer computer = computerDtoMapper.ComputerFromDto(computerDto);
		computerService.update(computer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.webservices.ComputerWebservice#deleteComputer(int)
	 */
	@DELETE
	@Path("/{id}")
	public void deleteComputer(@PathParam("id") int id) {
		computerService.delete(id);
	}

}
