package com.team3.business;

import java.util.List;

import com.team3.dataaccess.MySQLConnection;
import com.team3.entities.LocationVO;
import com.team3.entities.UserProfileVO;

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

/*	public void GetUserProfile(String email) {
		DBConnection.GetUserProfile(email);
	}*/

	public void GetUser(String email) {
		DBConnection.GetUser(email);
	}
	
	public UserProfileVO GetUserProfileDetails(String userEmail) {
		return DBConnection.getUserProfileDetails(userEmail);
	}

	public List<LocationVO> getFavouriteLocations(String userEmail) {
		return DBConnection.getFavouriteLocations(userEmail);
	}
}
