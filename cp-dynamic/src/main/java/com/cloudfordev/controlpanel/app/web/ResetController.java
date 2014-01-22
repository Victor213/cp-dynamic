package com.cloudfordev.controlpanel.app.web;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudfordev.controlpanel.app.exception.ResourceNotFoundException;
import com.cloudfordev.controlpanel.app.exception.ServerErrorException;
import com.cloudfordev.controlpanel.data.transfer.ResetResource;
import com.cloudfordev.controlpanel.service.ResetService;

/**
 * This controller exposes the ResetService, used to perform password resets.   
 * 
 * Information is required from the client to perform a password reset.  When a 
 * client wants to perform a reset, it POSTs a request containing that data to this controller.
 * 
 * That data is mapped to a ResetResource, and provided to the ResetService to perform the reset.
 * 
 * When the reset is complete, the ResetService returns a ResetResource confirming the action.  
 * That ResetResource is returned to the client along with an HTTP 200
 * 
 * @author paner
 */
@Controller
@RequestMapping("/app/reset")
public class ResetController {
	
	@Inject
    private ResetService resetService;

	/**
	 * Methods permitted for /app/reset/
	 * 
	 * @return ResponseEntity<Void> Listing the HttpMethods permitted
	 */
	@RequestMapping(value = "/", method = RequestMethod.OPTIONS)
	public ResponseEntity<Void> getProposalsOptions() {
		HttpMethod[] methods = new HttpMethod[5];
		methods[0] = HttpMethod.GET;
		methods[1] = HttpMethod.POST;
		methods[2] = HttpMethod.OPTIONS;
		
		HttpHeaders headers = new HttpHeaders();
	    Set<HttpMethod> allow = new HashSet<>();
	    for(HttpMethod method: methods){
	        allow.add(method);
	    }
	    headers.setAllow(allow);
	    return new ResponseEntity<>(headers, HttpStatus.OK);
	}

	/**
	 * Advertises this service.  GET /app/reset/ returns a blank ResetResource
	 * 
	 * @return ResetResource as a pattern of what the server expects to receive
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<ResetResource> advertise() {
		ResetResource resource = new ResetResource();
        return new ResponseEntity<ResetResource>(resource, HttpStatus.OK);
    }	
	
	/**
     * Accept a ResetResource DTO which provides the information required to perform a password reset.  
     * 
     * Perform that reset, and return a ResetResource to the client confirming success details.
     * 
     * @param resetResource ResetResource 
     * @throws ResourceNotFoundException In the case that the user specified by the ResetResource could not be found
     * @throws ServerErrorException If failures were detected communicating to the DB or sending the reset email
     */
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
    public HttpEntity<ResetResource> post(@RequestBody ResetResource resetResource) throws ResourceNotFoundException, ServerErrorException {
    	ResetResource resource;
		resource = resetService.reset(resetResource);	
    	return new ResponseEntity<ResetResource>(resource, HttpStatus.OK);
    }
}
