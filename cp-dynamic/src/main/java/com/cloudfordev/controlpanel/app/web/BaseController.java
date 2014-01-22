package com.cloudfordev.controlpanel.app.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.config.EnableEntityLinks;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

@Controller
@EnableEntityLinks
@RequestMapping("/app")
public class BaseController {

	@Bean
	public DispatcherServlet dispatcherServlet() {
	    DispatcherServlet servlet = new DispatcherServlet();
	    servlet.setDispatchOptionsRequest(true);
	    return servlet;
	}
	
	/**
	 * Methods permitted for /app/
	 * @return ResponseEntity<Void> Listing the HttpMethods permitted
	 */
	@RequestMapping(value = "/", method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> getProposalsOptions() {
		HttpMethod[] methods = new HttpMethod[5];
		methods[0] = HttpMethod.GET;
		
		HttpHeaders headers = new HttpHeaders();
	    Set<HttpMethod> allow = new HashSet<>();
	    for(HttpMethod method: methods){
	        allow.add(method);
	    }
	    headers.setAllow(allow);
	    return new ResponseEntity<>(headers, HttpStatus.OK);
	}
	
	/**
	 * GET a list of resources off of / 
	 * @return ResponseEntity<ResourceSupport> Listing the services provided
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
    public HttpEntity<ResourceSupport> post() {
		ResourceSupport resource = new ResourceSupport();
		resource.add(linkTo(methodOn(TokenController.class).get()).withRel("token"));
		resource.add(linkTo(methodOn(UserController.class).advertise()).withRel("user"));
		resource.add(linkTo(methodOn(ResetController.class).advertise()).withRel("reset"));
        return new ResponseEntity<ResourceSupport>(resource, HttpStatus.OK);
    }
}
