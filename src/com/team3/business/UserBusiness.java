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

	public int RegisterUser(String email, String name) {
		return DBConnection.RegisterUser(email, name);
	}

	public int AddUserProfile(String name, String interest, String email) {
		return DBConnection.AddUserProfile(name, interest, email);
	}

	public void GetUserProfile(String email) {
		DBConnection.GetUserProfile(email);
	}

	public void GetUser(String email) {
		DBConnection.GetUser(email);
	}

}
