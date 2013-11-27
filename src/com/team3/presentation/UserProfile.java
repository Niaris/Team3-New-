/**
 * @author Andreas Stavrou
 */

package com.team3.presentation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.team3.R;
import com.team3.business.UserBusiness;
import com.team3.dataaccess.MySQLConnection;

/**
 * The Class UserProfile.
 */
public class UserProfile extends Activity implements View.OnClickListener {

	private static final String TAG = "AddUser";
	public String UserId;
	public String useremail;
	public String name;
	public String interest;
	public String googleplus;
	public String whatsapp;
	public TextView TVemail;
	public TextView TVUserId;
	public EditText ETname;
	public EditText ETinterest;
	private UserBusiness userBUS;
	int success;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);

		getIntentDetails();

		MySQLConnection dbConnection = new MySQLConnection();
		userBUS = new UserBusiness(dbConnection);

		findViewById(R.id.btSave).setOnClickListener(this);
		findViewById(R.id.btCancelEditDetails).setOnClickListener(this);
	}

	private void getIntentDetails() {
		TVemail = (TextView) findViewById(R.id.tvShowEmailAddress);

		Intent intent = getIntent();
		String userEmail = intent.getStringExtra("UserEmail");
		TVemail.setText(userEmail);

	}

	private class AddUserProfile extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				success = userBUS.AddUserProfile(params[0], params[1],
						params[2]);
				return "success";

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "Error" + e);

				return "Error";
			}
		}

		protected void onPostExecute(String result) {
			if (result.equals("success")) {
				if (success == 0) {
					Toast.makeText(getBaseContext(),
							"User Name already exists.", Toast.LENGTH_LONG)
							.show();

				} else {
					Toast.makeText(getBaseContext(), "Saved!",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(getBaseContext(),
							UserProfileDetails.class);
					intent.putExtra("UserEmail", useremail);

					finish();
					startActivity(intent);
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btSave) {

			ETname = (EditText) findViewById(R.id.etUserName);
			ETinterest = (EditText) findViewById(R.id.etAboutMe);
			TVemail = (TextView) findViewById(R.id.tvShowEmailAddress);
			TVUserId = (TextView) findViewById(R.id.tvUserID);

			name = ETname.getText().toString();
			interest = ETinterest.getText().toString();
			useremail = TVemail.getText().toString();
			UserId = TVUserId.getText().toString();

			if (ETname.getText().toString().isEmpty()) {
				try {
					throw new Exception("User Name cannot be empty.");

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(this, "User Name cannot be empty.",
						Toast.LENGTH_LONG).show();
			} else {

				new AddUserProfile().execute(name, interest, useremail);

			}

		} else if (v.getId() == R.id.btCancelEditDetails) {
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			intent.putExtra("UserEmail", useremail);

			finish();
			startActivity(intent);
		}

	}// Ends onClick

	public void saveUserProfile(View view) throws Exception {
		if (ETname.getText().toString().isEmpty()) {
			throw new Exception("User Name cannot be empty.");
		}
	}
}
