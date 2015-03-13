package com.nicolas.webservices;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import com.nicolas.webservices.impl.ComputerWebserviceImpl;


public class JerseyApplication extends ResourceConfig {
	/**
	 * Register JAX-RS application components.
	 */
	public JerseyApplication() {
		register(RequestContextFilter.class);
		register(ComputerWebserviceImpl.class);
	}
}	