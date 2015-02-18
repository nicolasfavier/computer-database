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
		
		boolean error = false;
		String errorMessage="";
		
		// get data about the computer to add
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Utils.getIntFromString(request
				.getParameter("companyId"));

		//check if there is a name
		if(computerName.isEmpty()){
			error = true;
			errorMessage += "name can't be empty  <br/>";
		}		
		
		//check if dates are valid
		if(!Utils.isDate(introduced)){
			error = true;
			errorMessage += "date : "+ introduced +" doesn't follow the format yyyy/mm/dd  <br/>";
		}		
			
		if(!Utils.isDate(discontinued)){
			error = true;
			errorMessage += "date : "+ discontinued +" doesn't follow the format yyyy/mm/dd  <br/>";
		}		
		
		if(error){
			request.setAttribute("errorMessage", errorMessage);
			doGet(request,response);
			return;
		}
		
		ComputerDto computerDto = new ComputerDto(0, computerName, introduced,
				discontinued, companyId);
		
		this.computerService.add(ComputerDtoMapper.ComputerFromDto(computerDto));


		response.sendRedirect(request.getContextPath() + "/dashboard");

	}

}