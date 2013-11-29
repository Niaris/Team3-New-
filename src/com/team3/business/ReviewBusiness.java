package com.team3.business;

import java.util.List;

import com.team3.dataaccess.MySQLConnection;
import com.team3.entities.LocationVO;
import com.team3.entities.ReviewVO;

public class ReviewBusiness {
	
	private MySQLConnection DBConnection;
	private LocationBusiness LocationBUS;
	
	public ReviewBusiness(MySQLConnection mySQLConnection) {
		this.DBConnection = mySQLConnection;
		this.LocationBUS = new LocationBusiness(DBConnection);
	}
	
	public int addReviewToLocation(ReviewVO review, LocationVO location) throws Exception {
		if(location.getID() == 0) {
			LocationBUS.validateLocation(location);
		}
		validateReviewVO(review);
		return DBConnection.addLocationAndReview(location, review);
	}
	
	private void validateReviewVO(ReviewVO review) throws Exception {
		if(review.getComment().isEmpty() || review.getRating() < 1) {
			throw new Exception("Rating and Comment are mandatory fields");
		}
		if(review.getUser().getEmailAddress().isEmpty()) {
			throw new Exception("User is not logged in");
		}
	}

	public Object[] retrieveReviewsList (int locationID) {
		return DBConnection.retrieveReviewsList(locationID);
	}
	
}
