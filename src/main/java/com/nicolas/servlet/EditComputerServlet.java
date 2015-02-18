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
import com.nicolas.models.Computer;
import com.nicolas.service.Impl.CompanyServiceImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * update a computer
 */
@WebServlet("/editComputer")
public class EditComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerServiceImpl computerService;
	private CompanyServiceImpl companyService;

	public EditComputerServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE
				.getComputerServiceImpl();
		this.companyService = ServiceManagerImpl.INSTANCE
				.getCompanyServiceImpl();
	}

	/**
	 * Send all information about the computer to update in the view
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch;
		String strIndex = request.getParameter("id");
		if (Utils.checkInt(strIndex)) {
			int index = Integer.parseInt(strIndex);
			Computer computer = this.computerService.getByID(index);
			if (computer != null) {
				List<Company> companies = this.companyService.getAll();

				request.setAttribute("computer", ComputerDtoMapper.ComputerToDto(computer));
				request.setAttribute("companies", companies);
				dispatch = getServletContext().getRequestDispatcher(
						"/views/editComputer.jsp");
			} else {
				dispatch = getServletContext().getRequestDispatcher(
						"/views/404.jsp");
			}
		} else {
			dispatch = getServletContext().getRequestDispatcher(
					"/views/404.jsp");
		}
		dispatch.forward(request, response);
	}

	/**
	 * update a computer
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatch;
		
		boolean error = false;
		String errorMessage="";
		// get data about the computer to update
		String computerName = request.getParameter("computerName");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		int companyId = Utils.getIntFromString(request.getParameter("companyId"));
		int id = Utils.getIntFromString(request.getParameter("id"));
		
		//check if there is a name
		if(computerName.isEmpty()){
			error = true;
			errorMessage += "name can't be empty <br/>";
		}		
		
		//check if dates are valid
		if(!Utils.isDate(introduced)){
			error = true;
			errorMessage += "date : "+ introduced +" doesn't follow the format yyyy/mm/dd <br/>";
		}		
			
		if(!Utils.isDate(discontinued)){
			error = true;
			errorMessage += "date : "+ discontinued +" doesn't follow the format yyyy/mm/dd <br/>";
		}		
		
		if(error){
			request.setAttribute("errorMessage", errorMessage);
			doGet(request,response);
			return;
		}
		

		ComputerDto computerDto = new ComputerDto(id, computerName, introduced,
				discontinued, companyId);

		// if the update didn't go through
		if (!this.computerService.update(ComputerDtoMapper.ComputerFromDto(computerDto))) {
			dispatch = getServletContext().getRequestDispatcher(
					"/views/500.jsp");
			dispatch.forward(request, response);
		}
		// else redirect to the dashboard page
		else {
			response.sendRedirect(request.getContextPath() + "/dashboard");
		}
	}

}
