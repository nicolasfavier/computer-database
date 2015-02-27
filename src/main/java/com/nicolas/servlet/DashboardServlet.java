package com.nicolas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.models.Page;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.utils.Utils;

/**
 * Servlet implementation show computers when the uri: /dashboard is
 * called
 */
@WebServlet("/dashboardold")
public class DashboardServlet extends AbstractSpringHttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	
	@Autowired
	private ComputerServiceImpl computerService;

	public DashboardServlet() {
		//this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
	}

	/**
	 * load all computers and send them back to the view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page p = new Page();
		String search = "";

		String strIndex = request.getParameter("page");
		if (strIndex != null)
			p.setIndex( Utils.getIntFromString(strIndex));

		search = request.getParameter("search");
		if (search == null)
			search = "";

		// the view need it put in back in the search input
		request.setAttribute("search", search);

		String strNbPerPage = request.getParameter("nbPerPage");
		if (strNbPerPage != null) {
			p.setNbComputerPerPage(Utils.getIntFromString(strNbPerPage));
		}

		Page page = this.computerService.getPage(p, search);
		request.setAttribute("page", page);

		LOGGER.info("The page has been prepared, redirecting to the view");

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/views/dashboard.jsp");
		dispatch.forward(request, response);
	}
}
