package com.nicolas.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public abstract class AbstractSpringHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
}

//public abstract class AbstractSpringHttpServlet extends HttpRequestHandlerServlet {
//	private static final long serialVersionUID = 1L;

