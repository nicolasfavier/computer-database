package com.nicolas.runtimeException;

/**
 * 
 * when an sql error is catch send a PersistenceException to stop the application
 *
 */
public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PersistenceException(Exception exception){
		super(exception);
	}
}
