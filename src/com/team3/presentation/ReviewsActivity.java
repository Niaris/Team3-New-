package com.team3.presentation;

import java.util.HashMap;
import java.util.List;

import com.team3.R;
import com.team3.business.ReviewBusiness;
import com.team3.dataaccess.MySQLConnection;
import com.team3.entities.LocationVO;
import com.team3.entities.ReviewVO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewsActivity extends Activity {

	private MySQLConnection DBConnection;
	private LocationVO Location;
	private ReviewBusiness ReviewBUS;
	private String UserEmail;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DBConnection = new MySQLConnection();
		ReviewBUS = new ReviewBusiness(DBConnection);
		setContentView(R.layout.activity_reviews);
		getIntentContent();
		loadLocationAndReviews();
	}

	@SuppressWarnings("unchecked")
	private void loadLocationAndReviews() {
		setTitle("Reviews of " + Location.getName());
        TextView locationNameTV = (TextView) findViewById(R.id.locationName);
        TextView locationAddressTV = (TextView) findViewById(R.id.locationAddress);
        TextView reviewsCount = (TextView) findViewById(R.id.numberOfReviews);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.locationRating);
        ListView reviewsListView = (ListView) findViewById(R.id.reviewsList);
        
        Object[] retrieveReviewsList = ReviewBUS.retrieveReviewsList(Location.getID());
		List<ReviewVO> reviews = (List<ReviewVO>) retrieveReviewsList[0];
		int avgRating = (Integer) retrieveReviewsList[1];
        locationAddressTV.setText(Location.getAddress());
        locationNameTV.setText(Location.getName());
        ratingBar.setRating(avgRating);
        if(reviews.size() < 2)
        	reviewsCount.setText(reviews.size() + " review");
        else
        	reviewsCount.setText(reviews.size() + " reviews");
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                        android.R.layout.simple_list_item_1, reviews);
        reviewsListView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reviews, menu);
		return true;
	}
	
	/** Gets the content passed by the previous activity
	 * 	@return void
	 */
	private void getIntentContent() {
		Intent intent = getIntent();
		Location = (LocationVO) intent.getSerializableExtra("LocationVO");
		UserEmail = intent.getStringExtra("UserEmail");
	}
	
	private class StableArrayAdapter extends ArrayAdapter<ReviewVO> {

	    HashMap<ReviewVO, Integer> mIdMap = new HashMap<ReviewVO, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<ReviewVO> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	    @Override
	    public long getItemId(int position) {
	      ReviewVO item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() {
	      return true;
	    }
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	LayoutInflater inflater = (LayoutInflater) getBaseContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View rowView = inflater.inflate(R.layout.review_item, parent, false);
                ReviewVO review = getItem(position);
                TextView reviewUserTV = (TextView) rowView.findViewById(R.id.reviewUser);
                reviewUserTV.setText(review.getUser().getFirstName() + getItem(position).getUser().getLastName());
                TextView reviewDateTV = (TextView) rowView.findViewById(R.id.reviewDate);
                reviewDateTV.setText(review.getDate());
                TextView reviewCommentTV = (TextView) rowView.findViewById(R.id.reviewComment);
                reviewCommentTV.setText(review.getComment());
                RatingBar rating = (RatingBar) rowView.findViewById(R.id.reviewRating);
                rating.setRating(review.getRating());
                return rowView;
	    }

	  }
	
	@Override
	public void onBackPressed() {
	    Intent mIntent = new Intent();
	    mIntent.putExtra("UserEmail", UserEmail);
	    setResult(RESULT_OK, mIntent);
	    super.onBackPressed();
	}

}
