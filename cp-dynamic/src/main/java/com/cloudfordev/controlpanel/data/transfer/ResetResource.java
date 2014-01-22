package com.cloudfordev.controlpanel.data.transfer;

import org.springframework.hateoas.ResourceSupport;

public class ResetResource extends ResourceSupport {
	
	private String email = "";
	
	public ResetResource() {
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
