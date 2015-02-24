package com.nicolas.runtimeException;

/**
 * 
 * when an sql error is catch send a PersistenceException to stop the application
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ServiceException(Exception exception){
		super(exception);
	}
}
