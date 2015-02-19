package com.nicolas.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.service.Impl.CompanyServiceImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;
import com.nicolas.validator.ComputerDtoValidator;

/**
 * update a computer
 */
@WebServlet("/edit-computer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerServiceImpl computerService;
	private CompanyServiceImpl companyService;

	public EditComputerServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
		this.companyService = ServiceManagerImpl.INSTANCE.getCompanyServiceImpl();
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
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Utils.getIntFromString(request.getParameter("companyId"));
		int id = Utils.getIntFromString(request.getParameter("id"));

		ComputerDto computerDto = new ComputerDto(id, computerName, introduced, discontinued,
				companyId);

		List<String> validationErrors = new ArrayList<>();
		validationErrors = ComputerDtoValidator.validate(computerDto);

		if (validationErrors.size() == 0) {
			this.computerService.update(ComputerDtoMapper.ComputerFromDto(computerDto));
			response.sendRedirect(request.getContextPath() + "/dashboard");
		} else {
			request.setAttribute("validationErrors", validationErrors);
			doGet(request, response);
		}
	}
}
