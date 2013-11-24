package com.team3.presentation;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.team3.R;
import com.team3.business.UserBusiness;
import com.team3.dataaccess.MySQLConnection;

public class UserProfileDetails extends ListActivity implements
		View.OnClickListener {

	private UserBusiness userBUS;
	// Progress Dialog
	private ProgressDialog pDialog;

	private TextView tvEmail;
	public String emailstring;
	private Button btEditDetails;
	private MySQLConnection dbConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile_details);
		tvEmail = (TextView) findViewById(R.id.textView10);
		tvEmail.setText(getIntent().getExtras().getString("useremail"));
		tvEmail = (TextView) findViewById(R.id.textView10);

		dbConnection = new MySQLConnection();
		userBUS = new UserBusiness(dbConnection);

		emailstring = tvEmail.getText().toString();

		new GetUserProfileDetails().execute(emailstring);

		findViewById(R.id.btnEditDetails).setOnClickListener(this);

		dbConnection
				.setUserProfileList(new ArrayList<HashMap<String, String>>());

	}

	private class GetUserProfileDetails extends
			AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(UserProfileDetails.this);
			pDialog.setMessage("Loading user details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {

			try {
				userBUS.GetUserProfile(params[0]);
				return "success";

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				// / Log.d(TAG, "Error" + e);

				return "Error";
			}
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(
							UserProfileDetails.this,
							dbConnection.getUserProfileList(),
							R.layout.list_item, new String[] {
									MySQLConnection.TAG_USER_NAME,
									MySQLConnection.TAG_USER_INTERESTS,
									MySQLConnection.TAG_USER_GOOGLE_ACCOUNT,
									MySQLConnection.TAG_USER_WHATSAPP_ACOUNT },
							new int[] { R.id.username, R.id.userinterest,
									R.id.usergogoleplus, R.id.userwhatsapp });
					// updating listview
					setListAdapter(adapter);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnEditDetails) {
			Intent intent = new Intent(getBaseContext(), UserProfile.class);
			finish();
			startActivity(intent);
		}

	}
}
