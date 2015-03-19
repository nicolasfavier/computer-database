package com.nicolas.webservices.impl;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.nicolas.models.Company;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.webservices.CompanyWebservice;

@Path("/companies")
public class CompanyWebserviceImpl implements CompanyWebservice {

	@Autowired
	private CompanyService companyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.webservices.CompanyWebservice#findAll()
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> findAll() {
		List<Company> companies = companyService.getAll();
		return companies;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nicolas.webservices.CompanyWebservice#deleteCompany(int)
	 */
	@DELETE
	@Path("/{id}")
	public void deleteCompany(@PathParam("id") int id) {
		companyService.DeleteCompany(id);
	}
}