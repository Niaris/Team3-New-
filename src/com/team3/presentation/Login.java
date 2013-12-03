package com.team3.presentation;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
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
import com.team3.business.UserBusiness;
import com.team3.dataaccess.MySQLConnection;

/**
 * The Class Login.
 */
public class Login extends Activity implements View.OnClickListener,
		ConnectionCallbacks, OnConnectionFailedListener,
		PlusClient.OnPeopleLoadedListener {

	/** The Constant TAG. */
	private static final String TAG = "Login";

	/** The Constant REQUEST_CODE_RESOLVE_ERR. */
	private static final int REQUEST_CODE_RESOLVE_ERR = 9000;

	/** The m connection progress dialog. */
	private ProgressDialog mConnectionProgressDialog;

	/** The m plus client. */
	private PlusClient mPlusClient;

	/** The m connection result. */
	private ConnectionResult mConnectionResult;

	/** The name tv. */
	public TextView nameTV;

	/** The email tv. */
	public TextView emailTV;

	/** The email. */
	public String email;

	/** The name. */
	public String name;

	/** The user bus. */
	private UserBusiness userBUS;

	/** The success. */
	int success;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		MySQLConnection dbConnection = new MySQLConnection();
		userBUS = new UserBusiness(dbConnection);

		findViewById(R.id.sign_in_button).setOnClickListener(this);
		findViewById(R.id.sign_out_button).setOnClickListener(this);

		mPlusClient = new PlusClient.Builder(this, this, this).setActions(
				"http://schemas.google.com/AddActivity",
				"http://schemas.google.com/BuyActivity").build();
		mConnectionProgressDialog = new ProgressDialog(this);
		mConnectionProgressDialog.setMessage("Signing in...");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
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
	 * Creates a dialog with user details after sign in.
	 * 
	 * @param accountEmail
	 *            user email
	 * @param personName
	 *            user name
	 * @param personPhoto
	 *            user photo url
	 */

	private void createUserDialog(String accountEmail, String personName,
			String personPhoto) {
		final Dialog dialog = new Dialog(Login.this);
		dialog.setContentView(R.layout.activity_user_details);
		dialog.setTitle("User Details");

		emailTV = (TextView) dialog.findViewById(R.id.txt_email);
		emailTV.setText(accountEmail);
		nameTV = (TextView) dialog.findViewById(R.id.txt_name);
		nameTV.setText(personName);

		ImageView photo = (ImageView) dialog.findViewById(R.id.userimage);
		UrlImageViewHelper.setUrlDrawable(photo, personPhoto);
		dialog.show();
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				name = nameTV.getText().toString();
				email = emailTV.getText().toString();

				new RegisterUserTask().execute(email, name);
				// new GetUser().execute(email);

			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.plus.PlusClient.OnPeopleLoadedListener#onPeopleLoaded
	 * (com.google.android.gms.common.ConnectionResult,
	 * com.google.android.gms.plus.model.people.PersonBuffer, java.lang.String)
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStart()
	 */
	@Override
	protected void onStart() {
		super.onStart();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		if (mPlusClient.isConnected()) {
			mPlusClient.disconnect();

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.gms.common.GooglePlayServicesClient.
	 * OnConnectionFailedListener
	 * #onConnectionFailed(com.google.android.gms.common.ConnectionResult)
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (mConnectionProgressDialog.isShowing()) {
			if (result.hasResolution()) {
				try {
					result.startResolutionForResult(this,
							REQUEST_CODE_RESOLVE_ERR);
				} catch (SendIntentException e) {
					mPlusClient.connect();
				}
			}
		}

		mConnectionResult = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		if (requestCode == REQUEST_CODE_RESOLVE_ERR
				&& responseCode == RESULT_OK) {
			mConnectionResult = null;
			mPlusClient.connect();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
	 * #onConnected(android.os.Bundle)
	 */
	@Override
	public void onConnected(Bundle connectionHint) {
		mConnectionProgressDialog.dismiss();
		String accountName = mPlusClient.getAccountName();
		Toast.makeText(this, accountName + " is connected.", Toast.LENGTH_SHORT)
				.show();

		if (mPlusClient.getCurrentPerson() != null) {
			Person currentPerson = mPlusClient.getCurrentPerson();
			String personName = currentPerson.getDisplayName();
			String personPhoto = currentPerson.getImage().getUrl();

			Log.d(TAG, "User Details \n" + "Email=" + accountName + " Name="
					+ personName + " Photo URL=" + personPhoto);

			createUserDialog(accountName, personName, personPhoto);
		}
	}

	/**
	 * The Class RegisterUserTask.
	 */
	private class RegisterUserTask extends AsyncTask<String, String, String> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			try {
				success = userBUS.RegisterUser(params[0], params[1]);
				return "success";

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "Error" + e);
				return "Error";
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		protected void onPostExecute(String result) {

			if (success == 1) {
				Intent intent = new Intent(getBaseContext(), UserProfile.class);
				intent.putExtra("UserEmail", email);

				// intent.putExtra("UserName", name);

				finish();
				startActivity(intent);
			} else {
				Intent intent = new Intent(getBaseContext(), MainActivity.class);
				intent.putExtra("UserEmail", email);

				// intent.putExtra("UserName", name);

				finish();
				startActivity(intent);
			}
		}
	}

	/**
	 * The Class GetUser.
	 */
	private class GetUser extends AsyncTask<String, String, String> {

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(String... params) {
			try {
				userBUS.GetUser(params[1]);
				return "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.d(TAG, "Error" + e);
				return "Error";
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks
	 * #onDisconnected()
	 */
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}
}
