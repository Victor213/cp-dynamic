package com.cloudfordev.controlpanel.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.cloudfordev.controlpanel.Configuration;
import com.cloudfordev.controlpanel.app.exception.ServerErrorException;
import com.cloudfordev.controlpanel.data.mapper.UserMapper;
import com.cloudfordev.controlpanel.data.repository.CredentialRepository;
import com.cloudfordev.controlpanel.data.repository.UserRepository;
import com.cloudfordev.controlpanel.data.transfer.UserResource;
import com.cloudfordev.controlpanel.domain.Credential;
import com.cloudfordev.controlpanel.domain.User;
import com.cloudfordev.controlpanel.util.Email;
import com.cloudfordev.controlpanel.util.RequestFetcher;

@Service
public class RegistrationService {
	
	@Inject
	private UserRepository users;
	
	@Inject
	private CredentialRepository creds;
	
	/**
	 * Register a new user.
	 * 
	 * @param resource The user object provided by backbone
	 * @param authHash The authorization hash provided by the headers
	 * @return UserResource As validation of the user created
	 * @throws ServerErrorException In the case of errors communicating with the backend or sending emails
	 */
	public UserResource register(UserResource resource, String authHash) throws ServerErrorException {	
		// Assemble an entity from the DTO (UserResource) provided
		UserMapper assembler = new UserMapper();
		User user = assembler.toEntity(resource);		
		
		// Persist the user
		try {
			user = users.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}
		
		// Decode the password provided 
		String clearPwd = new String(Base64.decodeBase64(authHash));
		
		// Generate a verification code
		Random rng = new Random();
		int length = 8;
		String characters = "abcdefghijklmnopqrstuvwxyz1234567890";
		
		char[] code = new char[length];
	    for (int i = 0; i < length; i++) {
	    	code[i] = characters.charAt(rng.nextInt(characters.length()));
	    }
	    
	    String verificationCode = new String(code);
	    	    
		// Instantiate a Credential entity to persist the password
		Credential cred = new Credential();
		cred.setUserId(user.getId());
		cred.setPassHash(BCrypt.hashpw(clearPwd, BCrypt.gensalt()));
		cred.setVerifyCode(new String(verificationCode));
		
		// Persist the password 
		try {
			creds.save(cred);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}
		
		// Determine this application's domain name
		HttpServletRequest request = RequestFetcher.getCurrentRequest();
		//String baseUrl = String.format("%s://%s",request.getScheme(),  request.getServerName());
		URL verificationURL = null;
		
		try {
			verificationURL = new URL(request.getScheme(), request.getServerName(), Configuration.verifyRegFile);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		// Email out the verification link
	    String emailBody = "Thank you for registering with Cloud For Dev! \n\rPlease browse to the following "
	    		+ "link to complete your registration: \n\r" + verificationURL.toString() + "?v=" + verificationCode;
	    Email email = new Email(resource.getEmail(), "webmaster@cloudfordev.com");
	    email.setFromName("Cloud For Dev");
	    email.setSubject("Registration action required");
	    email.setBody(emailBody);
	    
	    // Send the email
	    try {
			email.send();
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			throw new ServerErrorException();
		}
		
		return resource;
	}
}
