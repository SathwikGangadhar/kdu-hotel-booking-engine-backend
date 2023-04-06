package com.kdu.IBE.service.ratingsAndReviews;

import com.kdu.IBE.model.recieveModel.RatingsAndReviewsReceiveModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IRatingsAndReviewsService {
    public ResponseEntity<String> putRatingsAndReviews(RatingsAndReviewsReceiveModel ratingsAndReviewsReceiveModel, BindingResult result);
    public ResponseEntity<?> getRatingsAndReview(String roomTypeId);
}
