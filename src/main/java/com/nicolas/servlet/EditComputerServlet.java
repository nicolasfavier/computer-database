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
import com.nicolas.models.Computer;
import com.nicolas.service.Interfaces.CompanyService;
import com.nicolas.service.Interfaces.ComputerService;
import com.nicolas.utils.Utils;
import com.nicolas.validator.DtoValidator;

/**
 * update a computer
 */
@WebServlet("/edit-computerold")
public class EditComputerServlet extends AbstractSpringHttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;

	public EditComputerServlet() {
//		this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
//		this.companyService = ServiceManagerImpl.INSTANCE.getCompanyServiceImpl();
	}

	/**
	 * Send all information about the computer to update in the view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatch;
		String strIndex = request.getParameter("id");
		
		if (Utils.checkInt(strIndex)) {
			int index = Integer.parseInt(strIndex);
			Computer computer = this.computerService.getByID(index);
			
			if (computer != null) {
				List<Company> companies = this.companyService.getAll();

				request.setAttribute("computer", ComputerDtoMapper.ComputerToDto(computer));
				request.setAttribute("companies", companies);
				
				dispatch = getServletContext().getRequestDispatcher("/views/editComputer.jsp");
			} else {
				dispatch = getServletContext().getRequestDispatcher("/views/404.jsp");
			}
		} else {
			dispatch = getServletContext().getRequestDispatcher("/views/404.jsp");
		}
		dispatch.forward(request, response);
	}

	/**
	 * update a computer
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get data about the computer to update
		final String computerName = request.getParameter("computerName");
		final String introduced = request.getParameter("introduced");
		final String discontinued = request.getParameter("discontinued");
		final int companyId = Utils.getIntFromString(request.getParameter("companyId"));
		final int id = Utils.getIntFromString(request.getParameter("id"));

		ComputerDto computerDto = new ComputerDto(id, computerName, introduced, discontinued,
				companyId);

		List<String> validationErrors = new ArrayList<>();
		validationErrors = DtoValidator.validate(computerDto);

		if (validationErrors.size() == 0) {
			this.computerService.update(ComputerDtoMapper.ComputerFromDto(computerDto));
			LOGGER.info("Computer edit with success, redirecting to the Dashboard");
			response.sendRedirect(request.getContextPath() + "/dashboard");
		} else {
			LOGGER.info("Wrong input, redirecting to the view");
			request.setAttribute("validationErrors", validationErrors);
			doGet(request, response);
		}
	}
}
