package com.nicolas.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nicolas.models.Company;
import com.nicolas.models.Computer;
import com.nicolas.service.Impl.CompanyServiceImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * Servlet implementation class ComputerServlet
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
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
		 *      response)
		 */
		protected void doPost(HttpServletRequest request,
				HttpServletResponse response) throws ServletException, IOException {
			RequestDispatcher dispatch; 
			String redirectView = "/dashboard";
			
			String computerName = request.getParameter("computerName");
			LocalDate introduced  = Utils.getDateFromString(request.getParameter("introduced"));
			LocalDate discontinued  = Utils.getDateFromString(request.getParameter("discontinued"));
			int companyId =  Utils.getIntFromString(request.getParameter("companyId"));

			Computer computer = new Computer(0, computerName,introduced,discontinued, companyId);
			if(!this.computerService.add(computer)){
				dispatch = getServletContext().getRequestDispatcher(
						"/views/500.jsp");
				dispatch.forward(request, response);
			}
			else{
			request.setAttribute("message", "Computer added with success");
			response.sendRedirect(request.getContextPath()+"/dashboard");
			}
		}

	}