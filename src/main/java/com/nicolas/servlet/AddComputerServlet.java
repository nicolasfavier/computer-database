package com.nicolas.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.service.Impl.CompanyServiceImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * Servlet implementation show and add computers when the uri: /add-computer is
 * call
 */
@WebServlet("/add-computer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerServiceImpl computerService;
	private CompanyServiceImpl companyService;
	private static Validator validator;

	public AddComputerServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
		this.companyService = ServiceManagerImpl.INSTANCE.getCompanyServiceImpl();
	}

	/**
	 * get all the company and resent them to the view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatch;

		String redirectView = "/views/addComputer.jsp";

		List<Company> companies = this.companyService.getAll();
		request.setAttribute("companies", companies);

		dispatch = getServletContext().getRequestDispatcher(redirectView);
		dispatch.forward(request, response);
	}

	/**
	 * add a computer if success call the doGet if not redirect to a 505error
	 * page
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get data about the computer to add
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Utils.getIntFromString(request.getParameter("companyId"));

		ComputerDto computerDto = new ComputerDto(0, computerName, introduced, discontinued,
				companyId);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		Set<ConstraintViolation<ComputerDto>> constraintViolations = validator
				.validate(computerDto);
		List<String> validationErrors = new ArrayList<>();

		if (constraintViolations.size() == 0) {
			this.computerService.add(ComputerDtoMapper.ComputerFromDto(computerDto));
			response.sendRedirect(request.getContextPath() + "/dashboard");
		} else {
			for (ConstraintViolation<ComputerDto> constraintViolation : constraintViolations) {
				String error = constraintViolation.getMessage() + " : '"
						+ constraintViolation.getInvalidValue() + "' is not valid for "
						+ constraintViolation.getPropertyPath();
				validationErrors.add(error);
			}
			request.setAttribute("validationErrors", validationErrors);
			doGet(request, response);
		}
	}

}