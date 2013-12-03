package com.team3.entities;

import java.util.List;

public class ReviewsListVO {

	private List<ReviewVO> listOfReviews;
	private int avgRating;
	private int numberOfLikes;
	private List<String> usersWhoLiked;
	
	public ReviewsListVO(List<ReviewVO> listOfReviews, int avgRating,
			int numberOfLikes, List<String> usersWhoLiked) {
		this.listOfReviews = listOfReviews;
		this.avgRating = avgRating;
		this.numberOfLikes = numberOfLikes;
		this.usersWhoLiked = usersWhoLiked;
	}
	
	public List<ReviewVO> getListOfReviews() {
		return listOfReviews;
	}


	public int getAvgRating() {
		return avgRating;
	}


	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public List<String> getUsersWhoLiked() {
		return usersWhoLiked;
	}

}
