package com.nicolas.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nicolas.service.Impl.ComputerServiceImpl;
import com.nicolas.service.Impl.ServiceManagerImpl;
import com.nicolas.utils.Utils;

/**
 * load all computers on get and delete them on post call
 */
@WebServlet("/delete")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerServiceImpl computerService;

	public DeleteComputerServlet() {
		this.computerService = ServiceManagerImpl.INSTANCE.getComputerServiceImpl();
	}

	/**
	 * load all computers and send them back to the view
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * To delete computers
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idsToDelete = request.getParameter("selection");

		if (idsToDelete != null) {
			String[] array = idsToDelete.split(",");
			// TODO send an unique command for delete
			for (String idString : array) {
				int id = Utils.getIntFromString(idString);
				this.computerService.delete(id);
			}
		}

		request.setAttribute("message", "Computer(s) deleted with success");
		doGet(request, response);
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/dashboard");
		rd.forward(request, response);
	}

}
