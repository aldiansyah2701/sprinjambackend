package com.aldidb.backenddb.message;

import java.util.List;

public class RequestRegisterUser {
	
	private String fullName;
	
	private String password;
	
	private List<String> roles;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
