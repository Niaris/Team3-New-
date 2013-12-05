package com.team3.entities;

public class UserProfileVO {

	private String googleAccount;
	private String userName;
	private String interests;
	private int ID;
	
	public UserProfileVO(String googleAccount, String userName,
			String interests, int iD) {
		super();
		this.googleAccount = googleAccount;
		this.userName = userName;
		this.interests = interests;
		ID = iD;
	}

	public String getGoogleAccount() {
		return googleAccount;
	}

	public String getUserName() {
		return userName;
	}

	public String getInterests() {
		return interests;
	}

	public int getID() {
		return ID;
	}
	
}
