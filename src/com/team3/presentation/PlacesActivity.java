package com.team3.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.team3.R;
import com.team3.utils.AddressConversion;
import com.team3.utils.DateTimeManipulator;
import com.team3.utils.PlaceJSONParser;

public class PlacesActivity extends FragmentActivity implements
		LocationListener {

	GoogleMap mGoogleMap;
	Spinner mSprPlaceType;

	String[] mPlaceType = null;
	String[] mPlaceTypeName = null;

	double mLAT = 0;
	double mLONG = 0;
	double LAT = 50.865017;
	double LONG = -0.089661;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Array of place types
		mPlaceType = getResources().getStringArray(R.array.place_type);

		// Array of place type names
		mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

		// Creating an array adapter with an array of Place types
		// to populate the spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);

		// Getting reference to the Spinner
		mSprPlaceType = (Spinner) findViewById(R.id.spr_place_type);

		// Setting adapter on Spinner to set place types
		mSprPlaceType.setAdapter(adapter);

		Button btnFind;

		// Getting reference to Find Button
		btnFind = (Button) findViewById(R.id.btn_find);

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}
			// Getting reference to the SupportMapFragment
			SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);

			// Getting Google Map
			mGoogleMap = fragment.getMap();

			// Enabling MyLocation in Google Map
			mGoogleMap.setMyLocationEnabled(true);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location From GPS
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location);
			}

			locationManager.requestLocationUpdates(provider, 20000, 0, this);

			// Setting click event lister for the find button
			btnFind.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					int selectedPosition = mSprPlaceType
							.getSelectedItemPosition();
					String type = mPlaceType[selectedPosition];

					StringBuilder sb = new StringBuilder(
							"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
					sb.append("location=" + LAT + "," + LONG);
					sb.append("&radius=250");
					sb.append("&types=" + type);
					sb.append("&sensor=true");
					sb.append("&key=AIzaSyAJ4Xdc6Nn4aNUdl7ZLLU5zEwFsk3VRmrg");
					try {
						String response = downloadUrl(sb.toString());

						List<HashMap<String, String>> places = null;
						PlaceJSONParser placeJsonParser = new PlaceJSONParser();
						JSONObject jObject = new JSONObject(response);
						places = placeJsonParser.parse(jObject);
						mGoogleMap.clear();

						for (int i = 0; i < places.size(); i++) {

							// Creating a marker
							MarkerOptions markerOptions = new MarkerOptions();

							// Getting a place from the places list
							HashMap<String, String> hmPlace = places.get(i);

							// Getting latitude of the place
							double lat = Double.parseDouble(hmPlace.get("lat"));

							// Getting longitude of the place
							double lng = Double.parseDouble(hmPlace.get("lng"));

							// Getting name
							String name = hmPlace.get("place_name");

							// Getting vicinity
							String vicinity = hmPlace.get("vicinity");

							LatLng latLng = new LatLng(lat, lng);

							// Setting the position for the marker
							markerOptions.position(latLng);

							// Setting the title for the marker.
							// This will be displayed on taping the marker
							markerOptions.title(name + " : " + vicinity);

							// Placing a marker on the touched position
							mGoogleMap.addMarker(markerOptions);
						}

						String token = response.substring(response
								.lastIndexOf("{") + 1);
						// sb.append("&pagetoken=" + token);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Creating a new non-ui thread task to download json data
					// PlacesTask placesTask = new PlacesTask();

					// Invokes the "doInBackground()" method of the class
					// PlaceTask
					// placesTask.execute(sb.toString());

				}
			});

		}

	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();
		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}

	/** A class, to download Google Places */
	private class PlacesTask extends AsyncTask<String, Integer, String> {

		String data = null;

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();

			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}

	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {

		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {

			List<HashMap<String, String>> places = null;
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				/** Getting the parsed data as a List construct */
				// places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {

			// Clears all the existing markers
			/*
			 * mGoogleMap.clear();
			 * 
			 * for (int i = 0; i < list.size(); i++) {
			 * 
			 * // Creating a marker MarkerOptions markerOptions = new
			 * MarkerOptions();
			 * 
			 * // Getting a place from the places list HashMap<String, String>
			 * hmPlace = list.get(i);
			 * 
			 * // Getting latitude of the place double lat =
			 * Double.parseDouble(hmPlace.get("lat"));
			 * 
			 * // Getting longitude of the place double lng =
			 * Double.parseDouble(hmPlace.get("lng"));
			 * 
			 * // Getting name String name = hmPlace.get("place_name");
			 * 
			 * // Getting vicinity String vicinity = hmPlace.get("vicinity");
			 * 
			 * LatLng latLng = new LatLng(lat, lng);
			 * 
			 * // Setting the position for the marker
			 * markerOptions.position(latLng);
			 * 
			 * // Setting the title for the marker. // This will be displayed on
			 * taping the marker markerOptions.title(name + " : " + vicinity);
			 * 
			 * // Placing a marker on the touched position
			 * mGoogleMap.addMarker(markerOptions); }
			 */
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		LinearLayout linearLayout = (LinearLayout) this
				.findViewById(R.id.LinearLayout3);
		switch (item.getItemId()) {
		case R.id.mapTypeNone:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
			break;
		case R.id.mapTypeNormal:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			setTextViewColor(Color.BLACK);
			linearLayout.setBackgroundColor(Color.parseColor("#22000000"));
			break;
		case R.id.mapTypeSatellite:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			setTextViewColor(Color.WHITE);
			linearLayout.setBackgroundColor(Color.parseColor("#22FFFFFF"));
			break;
		case R.id.mapTypeTerrain:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			setTextViewColor(Color.BLACK);
			linearLayout.setBackgroundColor(Color.parseColor("#22000000"));
			break;
		case R.id.mapTypeHybrid:
			mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			setTextViewColor(Color.WHITE);
			linearLayout.setBackgroundColor(Color.parseColor("#22FFFFFF"));
			break;
		/*
		 * case R.id.UserProfileEdit: Intent intent1 = new Intent(this,
		 * UserProfile.class); this.startActivity(intent1); break;
		 */
		default:
			break;

		}// Ends Switch

		return super.onOptionsItemSelected(item);
	}// End onOptionsItemSelected

	private void setTextViewColor(int color) {
		TextView tvLat = (TextView) this.findViewById(R.id.txLatitude);
		TextView tvLon = (TextView) this.findViewById(R.id.txLongitude);
		TextView tvTime = (TextView) this.findViewById(R.id.txtTime);
		TextView tvAddress = (TextView) this.findViewById(R.id.txtAddress);
		tvLon.setTextColor(color);
		tvLat.setTextColor(color);
		tvTime.setTextColor(color);
		tvAddress.setTextColor(color);
	}// END setTextViewColor

	@Override
	public void onLocationChanged(Location loc) {
		LAT = loc.getLatitude();
		LONG = loc.getLongitude();
		LatLng latLng = new LatLng(LAT, LONG);

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

		String Date = DateTimeManipulator.getCurrentDate();
		String Time = DateTimeManipulator.getCurrentTime();

		// Text View for showing Latitude
		TextView tvLat = (TextView) this.findViewById(R.id.txLatitude); // NEW
																		// ONE
		tvLat.setText("Latitude: " + String.valueOf(LAT));

		// Text View for showing Longitude
		TextView tvLot = (TextView) this.findViewById(R.id.txLongitude);
		tvLot.setText("Longitude: " + String.valueOf(LONG));

		// Text View for showing Date and Time
		TextView tvDateTime = (TextView) this.findViewById(R.id.txtTime);
		tvDateTime.setText("Date/Time: " + Date + "," + " " + Time);

		// Text View for showing the Address
		TextView tvAddress = (TextView) this.findViewById(R.id.txtAddress);

		// This gets the Address of the current location through
		// AddressConversion.java using Json.
		JSONObject ret = AddressConversion.getLocationInfo(LAT, LONG);
		JSONObject location;
		String location_string;
		try {
			location = ret.getJSONArray("results").getJSONObject(0);
			location_string = location.getString("formatted_address");
			Log.d("test", "formattted address:" + location_string);

			tvAddress.setText(location_string);

			// CurrentLocation = new LocationVO(location_string, LAT, LONG);

			// This gives the device a UNIQUE ID (NOTE: Try to add this to
			// another Class).
			String deviceId = Secure.getString(this.getContentResolver(),
					Secure.ANDROID_ID);

			/*
			 * IMPORTANT!!! Left this part of the code commented for now,
			 * because we have limited requests to the server
			 */
			// Calls generateXML method in order to save the details to an XML
			// file
			/*
			 * try { xmlGenerator.generate(Date, Time, deviceId, loc, Address);
			 * Toast.makeText(this, "File has been created.",
			 * Toast.LENGTH_SHORT).show(); } catch (Exception e) {
			 * Toast.makeText(this, "An error has occured. Restart the app.",
			 * Toast.LENGTH_SHORT).show(); }
			 * 
			 * // Calls uploadToServer in order to upload the xml file to the //
			 * server. try { fileUploader.upload(Date, Time, deviceId); } catch
			 * (Exception e) { Toast.makeText(this, "An error has occured" +
			 * e.getMessage(), Toast.LENGTH_SHORT).show(); }
			 * Toast.makeText(this, "File Uploaded", Toast.LENGTH_SHORT).show();
			 */
		} catch (JSONException e1) {
			Toast.makeText(this, "Error: " + e1.getMessage(),
					Toast.LENGTH_SHORT).show();
			e1.printStackTrace();

		}

	}// Ends onLocationChanged

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
}
