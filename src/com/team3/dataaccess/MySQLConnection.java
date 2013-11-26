package com.team3.dataaccess;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.team3.entities.LocationVO;
import com.team3.entities.ReviewVO;
import com.team3.presentation.Login;
import com.team3.utils.JSONParser;

public class MySQLConnection {

	@SuppressWarnings("unused")
	private String DBConnection;
	@SuppressWarnings("unused")
	private int serverResponseCode;
	JSONParser jsonParser;
	private static String SERVER_URL = "http://54.246.220.68/";
	private static String ADD_USER_PHP = "AddUsers.php";
	private static String GET_USER_ID_PHP = "GetUser.php";
	private static String ADD_OR_UPDATE_USER_PROFILE_PHP = "AddandUpdateUserProfileTEST.php";
	private static String GET_USER_PROFILE_DETAILS_PHP = "GetUserProfileTEST.php";

	public static final String TAG_USER_NAME = "User_Name";
	public static final String TAG_USER_INTERESTS = "User_Interests";
	public static final String TAG_USER_GOOGLE_ACCOUNT = "User_Google_Account";
	public static final String TAG_USER_WHATSAPP_ACOUNT = "User_WhatsApp_Account";
	public static final String TAG_USER_PROFILE = "usrprofile";
	public static final String TAG_SUCCESS = "success";

	public JSONArray userProfile = null;
	private ArrayList<HashMap<String, String>> userProfileList;

	public void setUserProfileList(
			ArrayList<HashMap<String, String>> userProfileList) {
		this.userProfileList = userProfileList;
	}

	public ArrayList<HashMap<String, String>> getUserProfileList() {
		return userProfileList;
	}

	public MySQLConnection() {
		try {
			jsonParser = new JSONParser();
			userProfileList = new ArrayList<HashMap<String, String>>();
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

	public List<LocationVO> retrieveLocationsByUserPosition(double latitude,
			double longitude) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addReview(ReviewVO review) {
		// TODO Auto-generated method stub

	}

	public void AddUser(Login UserDetails) {

	}

	public void AddUserProfile(String name, String interest, String UserId,
			String useremail) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("interest", interest));
		params.add(new BasicNameValuePair("useremail", useremail));
		params.add(new BasicNameValuePair("UserId", UserId));

		JSONObject json = jsonParser.makeHttpRequest(SERVER_URL
				+ ADD_OR_UPDATE_USER_PROFILE_PHP, "POST", params);

		Log.d("Create Response", json.toString());

		try {
			int success = json.getInt("success");
			String message = json.getString("message");
			Log.d("USER PROFILE POST", message);
			if (success == 1) {
				Log.d("USER PROFILE", "Details POST");
			} else if (success == 0) {
				Log.d("USER PROFILE EXISTS", "Details POST");
			}
		} catch (JSONException e) {
			Log.e("USER PROFILE", e.getMessage());

		}
	}// Ends AddUserProfile

	public void GetUserProfile(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("emailAddress", email));

		JSONObject json = jsonParser.makeHttpRequest(SERVER_URL
				+ GET_USER_PROFILE_DETAILS_PHP, "GET", params);

		Log.d("Create Response", json.toString());

		try {
			int success = json.getInt(TAG_SUCCESS);
			String message = json.getString("message");
			Log.d("USER PROFILE DETAILS GET", message);
			if (success == 1) {
				Log.d("USER PROFILE DETAILS GET", "Details GET");

				// Getting Array of Products
				userProfile = json.getJSONArray(TAG_USER_PROFILE);
				for (int i = 0; i < userProfile.length(); i++) {
					JSONObject c = userProfile.getJSONObject(i);

					// Storing each json item in variable
					String username = c.getString(TAG_USER_NAME);
					String userinterests = c.getString(TAG_USER_INTERESTS);
					String usergoogleaccount = c
							.getString(TAG_USER_GOOGLE_ACCOUNT);
					String userwhatsappaccount = c
							.getString(TAG_USER_WHATSAPP_ACOUNT);
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_USER_NAME, username);
					map.put(TAG_USER_INTERESTS, userinterests);
					map.put(TAG_USER_GOOGLE_ACCOUNT, usergoogleaccount);
					map.put(TAG_USER_WHATSAPP_ACOUNT, userwhatsappaccount);

					// adding HashList to ArrayList
					userProfileList.add(map);
				}

			} else {

			}
		} catch (JSONException e) {
			Log.e("USER PROFILE DETAILS GET Exception", e.getMessage());
			e.printStackTrace();
		}
	}// Ends GetUserProfile

	public void RegisterUser(String email, String name) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("emailAddress", email));

		JSONObject json = jsonParser.makeHttpRequest(SERVER_URL + ADD_USER_PHP,
				"POST", params);

		Log.d("Create Response", json.toString());

		try {
			int success = json.getInt("success");
			String message = json.getString("message");
			Log.d("USER", message);
			if (success == 1) {
				Log.d("USER", "Details POST");
			} else {

			}
		} catch (JSONException e) {
			Log.e("USER", e.getMessage());
		}
	}// Ends RegisterUser

	public void GetUser(String email) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("emailAddress", email));

		JSONObject json = jsonParser.makeHttpRequest(SERVER_URL
				+ GET_USER_ID_PHP, "GET", params);

		Log.d("Create Response", json.toString());

		try {
			int success = json.getInt("success");
			String message = json.getString("message");
			Log.d("USER", message);
			if (success == 1) {
				Log.d("USER", "Details GET");
			} else {
				// failed to create product
			}
		} catch (JSONException e) {
			Log.e("USER", e.getMessage());
		}

	}// Ends GetUser

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