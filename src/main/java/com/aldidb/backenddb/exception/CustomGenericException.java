package com.aldidb.backenddb.exception;

public class CustomGenericException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	private static final long serialVersionUID = 6102084118760739028L;

	private String error;
	
	private int status;
	
	private String message;
	
	public CustomGenericException(String error, int status, String message) {
		this.error = error;
		this.status = status;
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
