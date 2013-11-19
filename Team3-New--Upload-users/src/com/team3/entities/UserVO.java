package com.team3.entities;

public class UserVO {

	private int ID;

	private String FirstName;
	private String EmailAddress;
	
	public UserVO(String firstName, String emailAddress) {
		
		this.FirstName = firstName;
		this.EmailAddress = emailAddress;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getEmailAddress() {
		return EmailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		EmailAddress = emailAddress;
	}

}
