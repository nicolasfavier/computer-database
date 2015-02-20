package com.nicolas.runtimeException;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PersistenceException(Exception exception){
		super(exception);
	}
}
