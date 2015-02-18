package com.nicolas.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nicolas.dto.ComputerDto;
import com.nicolas.dto.ComputerDtoMapper;
import com.nicolas.models.Company;
import com.nicolas.service.Impl.CompanyServiceImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * Servlet implementation show and add computers when the uri: /addComputer is call
 */
@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerServiceImpl computerService;
	private CompanyServiceImpl companyService;

	public AddComputerServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE
				.getComputerServiceImpl();
		this.companyService = ServiceManagerImpl.INSTANCE
				.getCompanyServiceImpl();
	}

	/**
	 * get all the company and resent them to the view
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch;

		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Utils.getIntFromString(request
				.getParameter("companyId"));

		//check if data are valid
		if(computerName.isEmpty()){
			request.setAttribute("message", "name can't be empty");
			doGet(request,response);
			return;
		}		
		
		if(!Utils.isDate(introduced) || !Utils.isDate(discontinued)){
			request.setAttribute("message", "date(s) doesn't follow the format yyyy/mm/dd");
			doGet(request,response);
			return;
		}		
				
		ComputerDto computerDto = new ComputerDto(0, computerName, introduced,
				discontinued, companyId);
		
		if (!this.computerService.add(ComputerDtoMapper.ComputerFromDto(computerDto))) {
			dispatch = getServletContext().getRequestDispatcher(
					"/views/500.jsp");
			dispatch.forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/dashboard");
		}
	}

}