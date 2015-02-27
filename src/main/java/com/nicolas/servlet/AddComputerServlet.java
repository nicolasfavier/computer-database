package com.nicolas.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.service.Interfaces.ComputerService;
import com.nicolas.utils.Utils;
import com.nicolas.validator.DtoValidator;

/**
 * Servlet implementation show and add computers when the uri: /add-computer is
 * called
 */
@WebServlet("/add-computerold")
public class AddComputerServlet extends AbstractSpringHttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	@Autowired	
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;

	public AddComputerServlet() {
	}

	/**
	 * used to get all the companies and resent them to the view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatch;

		String redirectView = "/views/addComputer.jsp";

		List<Company> companies = this.companyService.getAll();
		request.setAttribute("companies", companies);

		LOGGER.info("get the view for add Computer");
		dispatch = getServletContext().getRequestDispatcher(redirectView);
		dispatch.forward(request, response);
	}

	/**
	 * add a computer 
	 * 		if success call the doGet 
	 * 		if not redirect to a 505 error page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get data about the computer to add
		final String computerName = request.getParameter("computerName");
		final String introduced = request.getParameter("introduced");
		final String discontinued = request.getParameter("discontinued");
		final int companyId = Utils.getIntFromString(request.getParameter("companyId"));

		ComputerDto computerDto = new ComputerDto(0, computerName, introduced, discontinued,
				companyId);

		List<String> validationErrors = new ArrayList<>();
		validationErrors = DtoValidator.validate(computerDto);

		if (validationErrors.size() == 0) {
			this.computerService.add(ComputerDtoMapper.ComputerFromDto(computerDto));
			LOGGER.info("Computer added with success, redirecting to the Dashboard");
			response.sendRedirect(request.getContextPath() + "/dashboard");
		} else {
			LOGGER.info("Wrong input, redirecting to the view");
			request.setAttribute("validationErrors", validationErrors);
			doGet(request, response);
		}
	}

}