/**
 * @author Andreas Stavrou, Salman Alghmdi 
 * Introduced in FR 3
 * verison 1.1
 */

package com.team3.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * The Class PlaceJSONParser.
 */
public class PlaceJSONParser {

	/**
	 * Receives a JSONObject and returns a list.
	 * 
	 * @param jObject
	 *            the j object
	 * @return the list
	 */
	public List<HashMap<String, String>> parse(JSONObject jObject) {

		JSONArray jPlaces = null;
		try {
			/** Retrieves all the elements in the 'places' array */
			jPlaces = jObject.getJSONArray("results");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/**
		 * Invoking getPlaces with the array of json object where each json
		 * object represent a place
		 */
		return getPlaces(jPlaces);
	}

	/**
	 * Gets the places.
	 * 
	 * @param jPlaces
	 *            the j places
	 * @return the places
	 */
	private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
		int placesCount = jPlaces.length();
		List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> place = null;

		/** Taking each place, parses and adds to list object */
		for (int i = 0; i < placesCount; i++) {
			try {
				/** Call getPlace with place JSON object to parse the place */
				place = getPlace((JSONObject) jPlaces.get(i));
				placesList.add(place);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return placesList;
	}

	/**
	 * Parsing the Place JSON object.
	 * 
	 * @param jPlace
	 *            the j place
	 * @return the place
	 */
	private HashMap<String, String> getPlace(JSONObject jPlace) {

		HashMap<String, String> place = new HashMap<String, String>();
		String placeName = "-NA-";
		String vicinity = "-NA-";
		String latitude = "";
		String longitude = "";
		String address = "";

		try {
			// Extracting Place name, if available
			if (!jPlace.isNull("name")) {
				placeName = jPlace.getString("name");
			}

			// Extracting Place Vicinity, if available
			if (!jPlace.isNull("vicinity")) {
				vicinity = jPlace.getString("vicinity");
			}
			
			// Extracting Address, if available
			if (!jPlace.isNull("formatted_address")) {
				address = jPlace.getString("formatted_address");
			}

			latitude = jPlace.getJSONObject("geometry")
					.getJSONObject("location").getString("lat");
			longitude = jPlace.getJSONObject("geometry")
					.getJSONObject("location").getString("lng");
			
			place.put("place_name", placeName);
			place.put("vicinity", vicinity);
			place.put("lat", latitude);
			place.put("lng", longitude);
			place.put("address", address);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return place;
	}
	
	public String downloadUrl(String strUrl) throws IOException {
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
	
}
