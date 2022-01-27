package com.aldidb.backenddb.message;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

public class RequestCreateOrganization {
	

	@NotNull(message = "Name cannot be null")
	private String name;
	
	@NotNull(message = "type cannot be null")
	private String type;
	
	@NotNull(message = "user cannot be null")
	private String userName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@AssertTrue(message = "name is empty")
    public boolean isValidName() {
        return "".equals(this.name) ? false
                : true;
    }
	
}
