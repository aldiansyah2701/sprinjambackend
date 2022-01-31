package com.aldidb.backenddb.message;

public class ResponseApiException extends BaseResponse {
	
	private Object errors;

	public Object getErrors() {
		return errors;
	}

	public void setErrors(Object errors) {
		this.errors = errors;
	}
	
	
	

}
