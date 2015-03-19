package com.nicolas.webservices;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.nicolas.webservices.impl.CompanyWebserviceImpl;
import com.nicolas.webservices.impl.ComputerWebserviceImpl;

/**
 * @author nicolas define which
 */
public class JerseyApplication extends ResourceConfig {
	/**
	 * Register JAX-RS application components.
	 */
	public JerseyApplication() {
		register(RequestContextFilter.class);
		register(ComputerWebserviceImpl.class);
		register(CompanyWebserviceImpl.class);
	}
}