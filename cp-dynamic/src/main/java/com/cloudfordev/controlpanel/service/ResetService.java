package com.cloudfordev.controlpanel.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cloudfordev.controlpanel.app.exception.ResourceNotFoundException;
import com.cloudfordev.controlpanel.app.exception.ServerErrorException;
import com.cloudfordev.controlpanel.data.repository.CredentialRepository;
import com.cloudfordev.controlpanel.data.repository.UserRepository;
import com.cloudfordev.controlpanel.data.transfer.ResetResource;
import com.cloudfordev.controlpanel.domain.Credential;
import com.cloudfordev.controlpanel.domain.User;
import com.cloudfordev.controlpanel.util.Email;

/**
 * The resets the password for users and marks their password as temporary.  
 * 
 * @author Lynn Owens
 */
@Service
public class ResetService {
	
	@Inject
	private UserRepository users;
	
	@Inject
	private CredentialRepository creds;
	
	private final Logger logger = LoggerFactory.getLogger(ResetService.class);
	
	/**
	 * @param resource ResetResource Provided by the client providing the details of the reset to perform
	 * @return ResetResource Verifying to the client the reset that was performed
	 * 
	 * @throws ResourceNotFoundException If the user was not found
	 * @throws ServerErrorException If there was a failure with DB or email
	 */
	public ResetResource reset(ResetResource resource) throws ResourceNotFoundException, ServerErrorException {
		// Locate the User and the Credential entities
		User user = null;
		Credential cred = null;
		String userEmail = resource.getEmail();
		
		try {
			user = users.findByEmail(userEmail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}
		
		if (user == null) {
			// We could not find this user
			throw new ResourceNotFoundException();
		}
		
		try {
			cred = creds.findByUserId(user.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}
		
		if (cred == null) {
			// The user exists but credential doesn't?  Server error.
			logger.error("User [ " + userEmail + " ] was found, but their Credential was not");
			throw new ServerErrorException();
		}
		
		// Generate new password
		Random rng = new Random();
		int length = 12;
		String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
		
		char[] text = new char[length];
	    for (int i = 0; i < length; i++) {
	        text[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    
	    String newPasswd = new String(text);
	    	    
	    // Store the new password
	    cred.setPassHash(BCrypt.hashpw(newPasswd, BCrypt.gensalt()));  
	    cred.setIsTemp(true);
	    
	    // Email out the new password
	    String emailBody = "Your new password is: " + newPasswd;
	    Email email = new Email(userEmail, "webmaster@cloudfordev.com");
	    email.setFromName("Cloud For Dev");
	    email.setSubject("Your password has been reset");
	    email.setBody(emailBody);
	    
	    // Send the email
	    try {
			email.send();
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}
	    
		// Persist the credential
	    try {
    		creds.save(cred);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}

		return resource;
	}
}
