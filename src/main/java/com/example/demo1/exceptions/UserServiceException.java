package com.example.demo1.exceptions;

public class UserServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2560473380514865446L;

	public UserServiceException(String message) {
		super(message);
	}
}
