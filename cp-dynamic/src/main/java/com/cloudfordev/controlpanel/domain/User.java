package com.cloudfordev.controlpanel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.Identifiable;

@Entity
@Table(name="users")
public class User implements Identifiable<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
	
	@Column(name="email")
	private String email = "";
	
	@Column(name="fname")
    private String fName = "";
	
	@Column(name="lname")
    private String lName = "";
	
	@Column(name="heardhow")
    private int heardHow = 0;
	
	@Column(name="isdeleted")
    private boolean isDeleted = false;

    public User() {

    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int getHeardHow() {
		return heardHow;
	}

	public void setHeardHow(int heardHow) {
		this.heardHow = heardHow;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
