package com.team3.presentation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.team3.R;

public class Login extends Activity implements View.OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener,
		PlusClient.OnPeopleLoadedListener {

	private static final String TAG = "Login";
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	private ProgressDialog mConnectionProgressDialog;
	private PlusClient mPlusClient;
	private ConnectionResult mConnectionResult;

	public TextView name;
	public TextView email;

	public/* static */String personName;
	public/* static */String accountName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.sign_out_button).setOnClickListener(this);

		mPlusClient = new PlusClient.Builder(this, this, this).setActions(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();
		// Progress bar to be displayed if the connection failure is not
		// resolved.
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");

	}

	@Override
	public void onClick(View view) {

		if (view.getId() == R.id.sign_in_button && !mPlusClient.isConnected()) {
			if (mConnectionResult == null) {
				mPlusClient.connect();
				mConnectionProgressDialog.show();
			} else {
				try {
					mConnectionResult.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					// Try connecting again.
					mConnectionResult = null;
					mPlusClient.connect();
				}
			}
		}
		if (view.getId() == R.id.sign_out_button) {
			Log.d(TAG, "sign out pressed");
			if (mPlusClient.isConnected()) {
				mPlusClient.clearDefaultAccount();
				mPlusClient.disconnect();
				Toast.makeText(this, "You are disconnected!", Toast.LENGTH_LONG)
						.show();

				Log.d(TAG, "disconnected=" + !mPlusClient.isConnected());
			}
		}

	}

	/**
	 * Creates a dialog with user details after sign in
	 * 
	 * @param personPhoto
	 *            user photo url
	 * @param personName
	 *            user name
	 * @param accountEmail
	 *            user email
	 * 
	 */

	private void createUserDialog(String accountEmail, String personName,
			String personPhoto) {
		// custom dialog
		final Dialog dialog = new Dialog(Login.this);
		dialog.setContentView(R.layout.activity_user_details);
		dialog.setTitle("User Details");

		// set the custom dialog components - text, image and button
		email = (TextView) dialog.findViewById(R.id.txt_email);
		email.setText(accountEmail);
		name = (TextView) dialog.findViewById(R.id.txt_name);
		name.setText(personName);

		ImageView photo = (ImageView) dialog.findViewById(R.id.userimage);
		UrlImageViewHelper.setUrlDrawable(photo, personPhoto);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Adduser();
					Intent i = new Intent(Login.this, MainActivity.class);
					startActivity(i);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					Log.d(TAG, "Error" + e);
					e.printStackTrace();
				}

				/* dialog.dismiss(); */

			}
		});

		dialog.show();
	}

	@Override
	public void onPeopleLoaded(ConnectionResult status,
			PersonBuffer personBuffer, String nextPageToken) {
		if (status.getErrorCode() == ConnectionResult.SUCCESS) {
			try {
				int count = personBuffer.getCount();
				for (int i = 0; i < count; i++) {
					Log.d(TAG, "Display Name: "
							+ personBuffer.get(i).getDisplayName());
				}
			} finally {
				personBuffer.close();
			}
		} else {
			Log.e(TAG, "Error listing people: " + status.getErrorCode());
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mPlusClient.isConnected()) {
			mPlusClient.disconnect();
		}

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mConnectionProgressDialog.isShowing()) {
			// The user clicked the sign-in button already. Start to resolve
			// connection errors. Wait until onConnected() to dismiss the
			// connection dialog.
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}

		// Save the intent so that we can start an activity when the user clicks
		// the sign-in button.
		mConnectionResult = result;
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mConnectionProgressDialog.dismiss();
		accountName = mPlusClient.getAccountName();
		Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_SHORT)
				.show();
		// mPlusClient.loadVisiblePeople(this, Person.Collection.VISIBLE);

		if (mPlusClient.getCurrentPerson() != null) {
			Person currentPerson = mPlusClient.getCurrentPerson();
			personName = currentPerson.getDisplayName();
			String personPhoto = currentPerson.getImage().getUrl();

			Log.d(TAG, "User Details \n" + "Email=" + accountName + " Name="
					+ personName + " Photo URL=" + personPhoto);

			createUserDialog(accountName, personName, personPhoto);

			/*
			 * // DO NOT DELETE THIS UserBusiness usrBusiness = new
			 * UserBusiness(); usrBusiness.RegisterUser(accountName,
			 * personName);
			 */

			// example savetoDb(accountName,personName,personPhoto.getUrl());
		}
	}

	// Create GetText Metod
	public void Adduser() throws UnsupportedEncodingException {
		String Name;
		String Email;
		// Get user defined values
		Name = name.getText().toString();
		Email = email.getText().toString();

		// Create data variable for sent values to server

		String data = URLEncoder.encode("name", "UTF-8") + "="
				+ URLEncoder.encode(Name, "UTF-8");

		data += "&" + URLEncoder.encode("email", "UTF-8") + "="
				+ URLEncoder.encode(Email, "UTF-8");

		String text = "";
		BufferedReader reader = null;

		// Send data
		try {

			// Defined URL where to send data
			URL url = new URL("http://54.246.220.68/AddUsers1.php");

			// Send POST data request

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the server response

			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while ((line = reader.readLine()) != null) {
				// Append server response in string
				sb.append(line + "\n");
			}

			text = sb.toString();
		} catch (Exception ex) {

		} finally {
			try {

				reader.close();
			}

			catch (Exception ex) {
			}
		}

	}

	/*
	 * public static String getAccountName() { return accountName; }
	 * 
	 * public static String getPersonName() { return personName; }
	 */

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}
}
