/**
 * @author Andreas Stavrou
 */

package com.team3.presentation;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.team3.R;
import com.team3.business.UserBusiness;
import com.team3.dataaccess.MySQLConnection;
import com.team3.entities.LocationVO;
import com.team3.entities.UserProfileVO;

// TODO: Auto-generated Javadoc
/**
 * The Class UserProfileDetails.
 */
public class UserProfileDetails extends Activity implements
		View.OnClickListener {
	public String userEmail;
	public String selectedUser;
	private UserBusiness UserBUS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile_details);
		MySQLConnection dbConnection = new MySQLConnection();
		UserBUS = new UserBusiness(dbConnection);
		getIntentDetails();
		loadProfile();
		loadFavouriteLocations();
	}

	private void loadProfile() {
		UserProfileVO profile = UserBUS.GetUserProfileDetails(selectedUser);
		TextView userNameTV = (TextView) findViewById(R.id.userProfile);
		userNameTV.setText("User Name: " + profile.getUserName() + 
				"\nGoogle Account: " + profile.getGoogleAccount() +
				"\nInterests: " + profile.getInterests());
	}

	private void loadFavouriteLocations() {
		List<LocationVO> locations = UserBUS.getFavouriteLocations(selectedUser);
		ListView favListView = (ListView) this.findViewById(R.id.listViewLocations);
		final FavouritesArrayAdapter adapter = new FavouritesArrayAdapter(this,
                android.R.layout.simple_list_item_1, locations);
		favListView.setAdapter(adapter);
	}
	
	public void getIntentDetails() {
		Intent intent = getIntent();
		userEmail = intent.getStringExtra("UserEmail");
		selectedUser = intent.getStringExtra("UserSelected");
		Button editDetails = (Button) this.findViewById(R.id.btnEditDetails);
		if(!userEmail.equals(selectedUser)) {
			editDetails.setVisibility(View.GONE);
		} else {
			editDetails.setOnClickListener(this);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnEditDetails) {
			Intent intent = new Intent(getBaseContext(), UserProfile.class);
			intent.putExtra("UserEmail", userEmail);
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
