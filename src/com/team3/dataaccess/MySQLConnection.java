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
import com.team3.entities.UserVO;
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
	private static String ADD_OR_UPDATE_USER_PROFILE_PHP = "AddOrUpdateUserProfile.php";
	
	/*New Additions*/
	private static String ADD_LOCATION_PHP = "AddLocationAndReview.php";
	private static String GET_LOCATIONS_BY_RADIUS = "GetLocationByRadius.php";
	private static String GET_REVIEW_BASED_ON_LOCATION = "GetReviewsBasedOnLocation.php";
	private static String GET_REVIEWS_LOCATIONS_AND_SUM_OF_LIKES = "GetRevLikesSumOfLikes.php";
    
    
    
    private static String GET_LOCATION_ID_PHP = "GetLocationID.php";
    private static String GET_LOCATIONS_PHP = "GetLocations.php";
    private static String ADD_REVIEW_PHP = "AddReview.php";
    private static String GET_REVIEWS_PHP = "GetReviews.php";


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


	public int registerLocation(LocationVO location/*, ReviewVO review , String email*/) 
    {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", location.getName()));
            params.add(new BasicNameValuePair("latitude", String.valueOf(location.getLatitude())));
            params.add(new BasicNameValuePair("longitude", String.valueOf(location.getLongitude())));
            params.add(new BasicNameValuePair("address", location.getAddress()));
/*            params.add(new BasicNameValuePair("comment",review.getComment()));
            params.add(new BasicNameValuePair("rating",String.valueOf(review.getRating())));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("locationid", String.valueOf(location.getID())));*/


            JSONObject json = jsonParser.makeHttpRequest(SERVER_URL + ADD_LOCATION_PHP,"POST", params);
            Log.d("Create Response", json.toString());
            int locationID = 0;
            try 
            {
                    int success = json.getInt("success");
                    locationID = getLocationID(location.getLatitude(), location.getLongitude());
                    String message = json.getString("message");
                    Log.d("Location", message);
                    if (success == 1) 
                    {
                            Log.d("Location", "Details POST");
                            //TOASTE K.O
                    } 
                    else if ((success == 0))
                    {
                            //TOASTE NO K.O
                    }
            } 
            catch (JSONException e) 
            {
                    Log.e("Location", e.getMessage());
            }
            System.out.println(locationID);
            return locationID; 
    }
    
    public int getLocationID(double latitude, double longitude)
    {
            int locationID = 0;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
            params.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
            
            JSONObject json = jsonParser.makeHttpRequest(SERVER_URL+ GET_LOCATION_ID_PHP, "GET", params);
            
            try 
            {
                    
                    locationID = json.getInt("locationID");
                    int success = json.getInt("success");
                    String message = json.getString("message");
                    Log.d("USER", message);
                    if (success == 1) 
                    {
                            Log.d("USER", "Details GET");
                    } 
                    else 
                    {
                            // failed to create product
                    }
            } 
            catch (JSONException e) 
            {
                    Log.e("USER", e.getMessage());
            }
            
            return locationID;
    }
    
    public List<LocationVO> retrieveLocationsNearByUser(double latitude,double longitude) 
    {
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
            params.add(new BasicNameValuePair("longitude", String.valueOf(longitude)));
            


            JSONObject json = jsonParser.makeHttpRequest(SERVER_URL+ GET_LOCATIONS_PHP, "GET", params);


            Log.d("Create Response", json.toString());
            List<LocationVO> locationsNearBy= new ArrayList<LocationVO>();
            //String address, double latitude, double longitude, String name
            
            try 
            {
                    String LocationAddress = json.getString("address");
                    double LocationLatitude = Double.parseDouble(json.getString("latitude"));
                    double LocationLongitude = Double.parseDouble(json.getString("longitude"));
                    String LocationName = json.getString("name");
                    
                    LocationVO locationNearBy = new LocationVO(LocationAddress,LocationLatitude, LocationLongitude, LocationName);
                    locationsNearBy.add(locationNearBy);
                    
                    int success = json.getInt("success");
                    String message = json.getString("message");
                    Log.d("USER", message);
                    if (success == 1) 
                    {
                            Log.d("USER", "Details GET");
                    } 
                    else 
                    {
                            // failed to create product
                    }
            } 
            catch (JSONException e) 
            {
                    Log.e("USER", e.getMessage());
            }
            return locationsNearBy;
    }


    public void addReview(ReviewVO review) 
    {
            
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("comment", review.getComment()));
            params.add(new BasicNameValuePair("rating", String.valueOf(review.getRating())));
            params.add(new BasicNameValuePair("date", review.getDate()));
            params.add(new BasicNameValuePair("time", review.getTime()));
            params.add(new BasicNameValuePair ("location", String.valueOf(review.getLocationID())));
            params.add(new BasicNameValuePair("publisher", String.valueOf(review.getUser().getID())));




            JSONObject json = jsonParser.makeHttpRequest(SERVER_URL + ADD_REVIEW_PHP,"POST", params);
            Log.d("Create Response", json.toString());
            //int locationID = 0;
            try 
            {
                    int success = json.getInt("success");
                    //locationID = json.getInt("");
                    String message = json.getString("message");
                    Log.d("Review", message);
                    if (success == 1) 
                    {
                            Log.d("Review", "Details POST");
                            //TOASTE K.O
                    } 
                    else if (success == 0)
                    {
                            //TOASTE NO K.O
                    }
            } 
            catch (JSONException e) 
            {
                    Log.e("Review", e.getMessage());
            }




    }


    public List<ReviewVO> retrieveReviewsList(int locationID) 
    {
            List<NameValuePair> params = new ArrayList<NameValuePair>();


            params.add(new BasicNameValuePair("LocationID", String.valueOf(locationID)));


            JSONObject json = jsonParser.makeHttpRequest(SERVER_URL+ GET_REVIEWS_PHP, "GET", params);


            


            Log.d("Create Response", json.toString());
            
            List<ReviewVO> ListOfReviews = new ArrayList<ReviewVO>();
            JSONArray JSONReviews =  new JSONArray();
            String review_Comment = "";
            int review_Rating = 0; 
            String review_ImagePath= "";
            String review_Date = "";
            String review_Time = "";
            int location_ID = 0;
            String firstName = "";
            String emailAddress = "";
            
            try 
            {
                    int success = json.getInt("success");
                    String message = json.getString("message");
                    Log.d("USER", message);
                    if (success == 1) 
                    {
                            JSONReviews = json.getJSONArray("reviews");
                            for (int i = 0 ; i<=json.getInt("reviewsNumbers") ; i++ )
                            {
                                    JSONObject JSONReview= (JSONObject)JSONReviews.get(i);
                                    JSONReview.getJSONArray("review");
                                    review_Comment = json.getString("comment");
                                    review_Rating = 0; json.getInt("rating");
                                    review_ImagePath= json.getString("imagePath");
                                    review_Date = json.getString("date");
                                    review_Time = json.getString("time");
                                    location_ID = json.getInt("locationID");
                            
                                    firstName = json.getString("name");
                                    emailAddress = json.getString("email");
                                    
                                    
                                    UserVO user = new UserVO(firstName, "", emailAddress);                
                            
                                    ReviewVO review = new ReviewVO(user, location_ID, review_Rating, review_Date, review_Time, review_Comment, review_ImagePath);
                                    ListOfReviews.add(review);
                            }
                            
                            Log.d("USER", "Details GET");
                    } 
                    else 
                    {
                            // failed to create product
                    }
            } 
            catch (JSONException e) 
            {
                    Log.e("USER", e.getMessage());
            }


            return ListOfReviews;
    }


    public void open() 
    {
            // TODO Auto-generated method stub


    }


    public void close() 
    {
            // TODO Auto-generated method stub


    }



	public void AddUser(Login UserDetails) {

	}

	public int AddUserProfile(String name, String interest, String useremail) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("interest", interest));
		params.add(new BasicNameValuePair("useremail", useremail));

		JSONObject json = jsonParser.makeHttpRequest(SERVER_URL
				+ ADD_OR_UPDATE_USER_PROFILE_PHP, "POST", params);

		Log.d("Create Response", json.toString());
		int success = 0;
		try {
			success = json.getInt("success");
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
		return success;
	}// Ends AddUserProfile

	/*public void GetUserProfile(String email) {
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
*/
	public int RegisterUser(String email, String name) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("emailAddress", email));

		JSONObject json = jsonParser.makeHttpRequest(SERVER_URL + ADD_USER_PHP,
				"POST", params);

		Log.d("Create Response", json.toString());
		int success = 0;
		try {
			success = json.getInt("success");
			String message = json.getString("message");
			Log.d("USER", message);
			if (success == 1) {
				Log.d("USER", "Details POST");
			} else {

			}
		} catch (JSONException e) {
			Log.e("USER", e.getMessage());
		}
		return success;
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

	
}