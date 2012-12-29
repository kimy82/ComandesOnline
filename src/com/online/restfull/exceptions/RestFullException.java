package com.online.restfull.exceptions;

public class RestFullException extends RuntimeException{

	private static final long	serialVersionUID	= 1L;

	public RestFullException() {

	}

	public RestFullException( String message ) {

		super(message);
	}

	public RestFullException( Exception e, String message ) {

		super(message, e);
	}
}