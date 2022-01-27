package com.aldidb.backenddb.message;

public class BaseResponse {
	
	public static final String SUCCESS = "Success";
	public static final String FAILED = "Failed";
	public static final String ALREADY_EXIST = "Already Exist";
	public static final String NOT_FOUND = "Not Found";
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
