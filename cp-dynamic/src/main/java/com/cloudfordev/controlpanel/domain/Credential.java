package com.cloudfordev.controlpanel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.Identifiable;

@Entity
@Table(name="credentials")
public class Credential implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
	
	@Column(name="userid")
	private Long userId = new Long(0);
	
	@Column(name="passhash")
	private String passHash = "";
	
	@Column(name="verifycode")
	private String verifyCode = "";
	
	@Column(name="isvalidated")
	private boolean isValidated = false;
	
	@Column(name="istemp")
	private boolean isTemp = true;
	
	@Column(name="isdeleted")
	private boolean isDeleted = false;
	
	public Credential() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}

	public boolean getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}

	public boolean getIsTemp() {
		return isTemp;
	}

	public void setIsTemp(boolean isTemp) {
		this.isTemp = isTemp;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}	
}
