package com.cloudfordev.controlpanel.data.transfer;

import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

	private String email = "";
    private String fName = "";
    private String lName = "";
    private int heardHow = 0;
	
	public UserResource() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}	
	
	public int getHeardHow() {
		return heardHow;
	}

	public void setHeardHow(int heardHow) {
		this.heardHow = heardHow;
	}
}
