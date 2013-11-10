package com.team3.business;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.team3.R;
import com.team3.dataaccess.JSONParser;
import com.team3.presentation.MainActivity;

public class UserBusiness extends Activity {
	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	TextView inputName;
	TextView inputEmail;

	public String name;
	public String email;

	// url to create new product
	private static String url_add_user = "http://54.246.220.68/AddUsers.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);

		// Edit Text
		inputName = (TextView) findViewById(R.id.TextView1);
		inputEmail = (EditText) findViewById(R.id.TextView2);

		// Create button
		Button btnRegisterUser = (Button) findViewById(R.id.dialogButtonOK);

		// button click event
		btnRegisterUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new product in background thread
				// new RegisterUser().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	// class RegisterUser extends AsyncTask<String, String, String>
	public void RegisterUser(String email, String name) {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */

		/*
		 * pDialog = new ProgressDialog(UserBusiness.this);
		 * pDialog.setMessage("Registering User..");
		 * pDialog.setIndeterminate(false); pDialog.setCancelable(true);
		 * pDialog.show();
		 */

		/**
		 * Creating product
		 * */

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));

		// getting JSON Object
		// Note that create product url accepts POST method
		JSONObject json = jsonParser.makeHttpRequest(url_add_user, "POST",
				params);

		// check log cat fro response
		Log.d("Create Response", json.toString());

		// check for success tag
		try {
			int success = json.getInt(TAG_SUCCESS);

			if (success == 1) {
				// successfully created product
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);

				// closing this screen
				finish();
			} else {
				// failed to create product
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * After completing background task Dismiss the progress dialog
	 * **/
	protected void onPostExecute(String file_url) {
		// dismiss the dialog once done
		pDialog.dismiss();
	}

}
