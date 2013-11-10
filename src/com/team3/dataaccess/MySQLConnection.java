package com.team3.dataaccess;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.util.Log;

import com.team3.entities.LocationVO;
import com.team3.entities.ReviewVO;
import com.team3.presentation.Login;

public class MySQLConnection {

	@SuppressWarnings("unused")
	private String DBConnection;
	@SuppressWarnings("unused")
	private int serverResponseCode;

	public MySQLConnection() {
		try {
			connectToServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connectToServer() throws Exception {
		HttpURLConnection connection = null;
		String DBConnection = "http://54.246.220.68/config.inc.php";
		String boundary = "*****";
		try {
			URL url = new URL(DBConnection);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			serverResponseCode = connection.getResponseCode();
		} catch (Exception e) {
			Log.e("Upload File", " " + e.getMessage().toString());
			// Exception handling
		}
	}

	public void registerLocation(LocationVO location) {
		// TODO Auto-generated method stub

	}

	public List<LocationVO> retrieveLocationsByUserPosition(int latitude,
			int longitude) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addReview(ReviewVO review) {
		// TODO Auto-generated method stub

	}

	public void AddUser(Login UserDetails) {

	}

	public List<ReviewVO> retrieveReviewsList(int locationID) {
		// TODO Auto-generated method stub
		return null;
	}

	public void open() {
		// TODO Auto-generated method stub

	}

	public void close() {
		// TODO Auto-generated method stub

	}
}