package com.team3.business;

import com.team3.dataaccess.MySQLConnection;

public class UserBusiness {

	private MySQLConnection DBConnection;

	public UserBusiness(MySQLConnection dbConnection) {
		DBConnection = dbConnection;
	}

	public void loginUser() {
		// TODO

	}

	public void RegisterUser(String email, String name) {
		DBConnection.RegisterUser(email, name);
	}

	public void AddUserProfile(String name, String interest, String googleplus,
			String whatsapp, String email) {
		DBConnection
				.AddUserProfile(name, interest, googleplus, whatsapp, email);
	}

	public void GetUserProfile(String email) {
		DBConnection.GetUserProfile(email);
	}

	public void GetUser(String email) {
		DBConnection.GetUser(email);
	}

}
