/**
 * @author Andreas Stavrou
 */

package com.team3.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.team3.R;
import com.team3.business.LocationBusiness;
import com.team3.dataaccess.MySQLConnection;
import com.team3.entities.LocationVO;

// TODO: Auto-generated Javadoc
/**
 * The Class UserProfileDetails.
 */
public class UserProfileDetails extends Activity implements
		View.OnClickListener {

	/** The json result. */
	private String jsonResult;

	/** The list view. */
	private ListView listView;

	/** The s user id. */
	public String sUserEmail;
	public String userEmail;
	public String selectedUser;
	/** The Tvuser id. */
	public TextView TvuserId;
	public TextView TVemail;
	private LocationBusiness LocationBUS;

	/*
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile_details);
		LocationBUS = new LocationBusiness(new MySQLConnection());
		// getIntentDetails();
		listView = (ListView) findViewById(R.id.listView1);
		getIntentDetails();
		accessWebService();
		loadFavouriteLocations();
		findViewById(R.id.btnEditDetails).setOnClickListener(this);

	}

	private void loadFavouriteLocations() {
		List<LocationVO> locations = LocationBUS.getFavouriteLocations(selectedUser);
		ListView favListView = (ListView) this.findViewById(R.id.listViewLocations);
		final FavouritesArrayAdapter adapter = new FavouritesArrayAdapter(this,
                android.R.layout.simple_list_item_1, locations);
		favListView.setAdapter(adapter);
	}
	
	public void getIntentDetails() {
		TVemail = (TextView) findViewById(R.id.UserId);

		Intent intent = getIntent();
		userEmail = intent.getStringExtra("UserEmail");
		selectedUser = intent.getStringExtra("UserSelected");
		if(!userEmail.equals(selectedUser)) {
			Button editDetails = (Button) this.findViewById(R.id.btnEditDetails);
			editDetails.setVisibility(View.GONE);
		}
		TVemail.setText(selectedUser);
	}

	/*
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Async Task to access the web
	/**
	 * The Class JsonReadTask.
	 */
	private class JsonReadTask extends AsyncTask<String, Void, String> {

		/*
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(params[0]);
			try {
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
			}

			catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * Input stream to string.
		 * 
		 * @param inStream
		 *            the in stream
		 * @return the string builder
		 */
		private StringBuilder inputStreamToString(InputStream inStream) {
			String rLine = "";
			StringBuilder strngBuilder = new StringBuilder();
			BufferedReader bfrReader = new BufferedReader(
					new InputStreamReader(inStream));

			try {
				while ((rLine = bfrReader.readLine()) != null) {
					strngBuilder.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return strngBuilder;
		}

		/*
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			ListDrwaer();
		}
	}// end async task

	/**
	 * Access web service.
	 */
	public void accessWebService() {

		TvuserId = (TextView) findViewById(R.id.UserId);
		String useremail = TvuserId.getText().toString();

		JsonReadTask task = new JsonReadTask();

		task.execute(new String[] { "http://54.246.220.68/GetUserProfileDetails.php"
				+ "?useremail=" + useremail });
	}

	/**
	 * List drwaer. builds hash set for the list view
	 */
	public void ListDrwaer() {
		List<Map<String, String>> userProfileList = new ArrayList<Map<String, String>>();

		try {
			JSONObject jsonResponse = new JSONObject(jsonResult);
			JSONArray jsonMainNode = jsonResponse.optJSONArray("userprofiles");

			for (int i = 0; i < jsonMainNode.length(); i++) {
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String name = jsonChildNode.optString("User_Name");
				String interests = jsonChildNode.optString("User_Interests");
				String googleplus = jsonChildNode
						.optString("User_Google_Account");

				String outPut = "User Name: " + name + "\n" + "\n"
						+ "Interests: " + interests + "\n" + "\n" + "Google+: "
						+ googleplus;
				userProfileList.add(createUserProfile("users", outPut));
			}
		} catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();

		}

		SimpleAdapter simpleAdapter = new SimpleAdapter(this, userProfileList,
				android.R.layout.simple_list_item_1, new String[] { "users" },
				new int[] { android.R.id.text1 });
		listView.setAdapter(simpleAdapter);
	}

	/**
	 * Creates the User Profile.
	 * 
	 * @param name
	 *            the name
	 * @param interests
	 *            the interests
	 * @return the hash map
	 */
	private HashMap<String, String> createUserProfile(String name,
			String interests) {
		HashMap<String, String> usersUnqProfile = new HashMap<String, String>();
		usersUnqProfile.put(name, interests);
		return usersUnqProfile;
	}

	/*
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnEditDetails) {
			Intent intent = new Intent(getBaseContext(), UserProfile.class);
			intent.putExtra("UserEmail", userEmail);

			// intent.putExtra("UserName", name);

			finish();
			startActivity(intent);
		}

	}
	
	@Override
	public void onBackPressed() {
	    Intent mIntent = new Intent();
	    mIntent.putExtra("UserEmail", userEmail);
	    setResult(RESULT_OK, mIntent);
	    super.onBackPressed();
	}

}
