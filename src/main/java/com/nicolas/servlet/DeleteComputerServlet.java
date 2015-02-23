package com.nicolas.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nicolas.dao.impl.ComputerDaoImpl;
import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * load all computers on get and delete them on post call
 */
@WebServlet("/delete")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(ComputerDaoImpl.class);
	private ComputerServiceImpl computerService;

	public DeleteComputerServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
	}

	/**
	 * To delete computers
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String idsToDelete = request.getParameter("selection");

		if (idsToDelete != null) {
			String[] array = idsToDelete.split(",");
			// TODO send an unique command for delete
			for (String idString : array) {
				int id = Utils.getIntFromString(idString);
				this.computerService.delete(id);
			}
		}

		LOGGER.info("Computer(s) have been delete, redirecting to the view ");

		request.setAttribute("message", "Computer(s) deleted with success");
		response.sendRedirect(request.getContextPath() + "/dashboard");
	}

}
