package com.cloudfordev.controlpanel.data.transfer;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

public class TokenResource extends ResourceSupport {

	private String token;
	private Timestamp expires;
	
	public TokenResource() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getExpires() {
		return expires;
	}

	public void setExpires(Timestamp expires) {
		this.expires = expires;
	}	
	
	
}
