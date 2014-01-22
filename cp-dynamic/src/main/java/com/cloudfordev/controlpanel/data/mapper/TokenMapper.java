package com.cloudfordev.controlpanel.data.mapper;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.cloudfordev.controlpanel.app.web.TokenController;
import com.cloudfordev.controlpanel.data.transfer.TokenResource;
import com.cloudfordev.controlpanel.domain.Token;

public class TokenMapper extends ResourceAssemblerSupport<Token, TokenResource> {

	public TokenMapper() {
		super(TokenController.class, TokenResource.class);
	}
	
	@Override
	public TokenResource toResource(Token token) {	
		TokenResource resource = createResourceWithId(token.getId(), token);
		resource.setToken(token.getToken());
		resource.setExpires(token.getExpires());
		return resource;
	}
}
