package com.nicolas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nicolas.models.Page;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerServiceImpl computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int index =0;
		int nbComputerPerPage = 10;
		String search ="";
		
		String strIndex = request.getParameter("page");
		if(strIndex != null)
		index = Utils.getIntFromString(strIndex);

		search = request.getParameter("search");
		if(search == null)
			search = "";
		
		request.setAttribute("search", search);
		
		String strNbPerPage = request.getParameter("nbPerPage");
		if(strNbPerPage != null)
		{
			nbComputerPerPage = Utils.getIntFromString(strNbPerPage);
		}
		
		Page page = this.computerService.getPage(index, nbComputerPerPage, search);
		request.setAttribute("page", page);
		
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(
				"/views/dashboard.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
