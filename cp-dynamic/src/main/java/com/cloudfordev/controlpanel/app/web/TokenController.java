package com.cloudfordev.controlpanel.app.web;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudfordev.controlpanel.data.mapper.TokenMapper;
import com.cloudfordev.controlpanel.data.transfer.TokenResource;
import com.cloudfordev.controlpanel.domain.Token;

@Controller
@RequestMapping("/app")
@ExposesResourceFor(Token.class)
public class TokenController {
	
	@RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<TokenResource> advertise() {
		TokenMapper assembler = new TokenMapper();
		TokenResource resource = assembler.toResource(new Token());
        return new ResponseEntity<TokenResource>(resource, HttpStatus.OK);
    }	
	
	/*
	 * Get an authentication token
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
    public HttpEntity<TokenResource> get() {
		TokenMapper assembler = new TokenMapper();
		TokenResource resource = assembler.toResource(new Token());
        return new ResponseEntity<TokenResource>(resource, HttpStatus.OK);
    }	
	
	// Uses AuthenticationService only
	// TODO implement DELETE to kill a token (logout), PUT to update expires time, GET to get a token (login), POST tests to 
	// see if it's still good by sending it to the controller.  if good, the controller will say OK.  
}