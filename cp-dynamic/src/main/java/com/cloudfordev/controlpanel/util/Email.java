package com.cloudfordev.controlpanel.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.cloudfordev.controlpanel.Configuration;

public class Email {
	
	private String emailServerId;	
	private String emailServerPwd;	
	private String emailServerAddr;	
	private String emailServerPort;	
	private String toAddr;
	private String fromAddr;
	private String fromName;
	private String body = "";
	private String subject = "";
	
	public Email(String toAddr, String fromAddr) {
		this.toAddr = toAddr;
		this.fromAddr = fromAddr;
	}
	
	public void send() throws MessagingException, UnsupportedEncodingException {
        Session session = null;
        
        if (this.fromName == null) {
        	this.fromName = this.fromAddr;
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", Configuration.emailServerAddr);
        props.put("mail.smtp.port", Configuration.emailServerPort);
        
        // Get a session to the mail relay
        session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(Configuration.emailServerId, Configuration.emailServerPwd);
                }
        });

        // Build a MIME message based on the session
		Message msg = new MimeMessage(session);

        // Set the MIME fields
		msg.setFrom(new InternetAddress(this.fromAddr, this.fromName));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
		msg.setSubject(this.subject);
		msg.setText(this.body);

		// Send the email
		Transport.send(msg);
	}		
	
	public String getEmailServerId() {
		return emailServerId;
	}

	public void setEmailServerId(String emailServerId) {
		this.emailServerId = emailServerId;
	}

	public String getEmailServerPwd() {
		return emailServerPwd;
	}

	public void setEmailServerPwd(String emailServerPwd) {
		this.emailServerPwd = emailServerPwd;
	}

	public String getEmailServerAddr() {
		return emailServerAddr;
	}

	public void setEmailServerAddr(String emailServerAddr) {
		this.emailServerAddr = emailServerAddr;
	}

	public String getEmailServerPort() {
		return emailServerPort;
	}

	public void setEmailServerPort(String emailServerPort) {
		this.emailServerPort = emailServerPort;
	}

	public String getToAddr() {
		return toAddr;
	}

	public void setToAddr(String toAddr) {
		this.toAddr = toAddr;
	}

	public String getFromAddr() {
		return fromAddr;
	}

	public void setFromAddr(String fromAddr) {
		this.fromAddr = fromAddr;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
