package com.cloudfordev.controlpanel;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
	
	// Logging
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	// Email stuff
	public static String emailServerId = "email@example.com";
	public static String emailServerPwd = "password";
	public static String emailServerAddr = "smtp.example.com";
	public static String emailServerPort = "587";
	
	// Other
	public static String verifyRegFile = "/verify.html";
	
	public static void loadConfig() {
		// Read the config file
		Properties properties = new Properties() ;  
		try {
			properties.load(Configuration.class.getResourceAsStream("/cp.properties"));
			
			// Email stuff
			Configuration.emailServerId = properties.getProperty("emailServerId");	
			Configuration.emailServerPwd = properties.getProperty("emailServerPwd");	
			Configuration.emailServerAddr = properties.getProperty("emailServerAddr");
			Configuration.emailServerPort = properties.getProperty("emailServerPort");
			
			// Other
			Configuration.verifyRegFile = properties.getProperty("verifyRegFile");	
		} catch (IOException e) {
			logger.warn("Unable to load configuration file");		
			e.printStackTrace();
		}
	}
}
