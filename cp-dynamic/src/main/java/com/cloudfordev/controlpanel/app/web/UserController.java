package com.cloudfordev.controlpanel.app.web;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudfordev.controlpanel.app.exception.ServerErrorException;
import com.cloudfordev.controlpanel.data.transfer.UserResource;
import com.cloudfordev.controlpanel.domain.User;
import com.cloudfordev.controlpanel.service.ProfileService;
import com.cloudfordev.controlpanel.service.RegistrationService;

@Controller
@RequestMapping("/app/user")
@ExposesResourceFor(User.class)
public class UserController {
	
	@Inject
    private ProfileService profileService;
	
	@Inject
    private RegistrationService regService;
	
	/**
	 * Methods permitted for /app/user/
	 * @return ResponseEntity<Void> Listing the HttpMethods permitted
	 */
	@RequestMapping(value = "/", method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> getProposalsOptions() {
		HttpMethod[] methods = new HttpMethod[5];
		methods[0] = HttpMethod.GET;
		methods[1] = HttpMethod.PUT;
		methods[2] = HttpMethod.POST;
		methods[3] = HttpMethod.OPTIONS;
		
		HttpHeaders headers = new HttpHeaders();
	    Set<HttpMethod> allow = new HashSet<>();
	    for(HttpMethod method: methods){
	        allow.add(method);
	    }
	    headers.setAllow(allow);
	    return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	/**
	 * Advertises this service.  GET /app/user/ returns a blank UserResource
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<UserResource> advertise() {
		UserResource resource = new UserResource();
        return new ResponseEntity<UserResource>(resource, HttpStatus.OK);
    }	
	
	/**
	 * Get a specified user profile
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{userid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public HttpEntity<UserResource> get(@PathVariable("userid") long id) {
		UserResource resource = profileService.get(id);
        return new ResponseEntity<UserResource>(resource, HttpStatus.OK);
    }
	
    /**
     * Register a new user
     * @param userResource
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
    public HttpEntity<UserResource> post(@RequestHeader("Authorization") String auth, @RequestBody UserResource userResource) throws ServerErrorException {
    	String authHash = auth.split(" ")[1];
   		userResource = regService.register(userResource, authHash);	
    	return new ResponseEntity<UserResource>(userResource, HttpStatus.OK);
    }
    
    /**
     * Update an existing user
     * @param userResource
     */
    @RequestMapping(value = "/{userid}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public void put(@RequestBody final UserResource userResource) {
    	profileService.update(userResource);
    }
}
