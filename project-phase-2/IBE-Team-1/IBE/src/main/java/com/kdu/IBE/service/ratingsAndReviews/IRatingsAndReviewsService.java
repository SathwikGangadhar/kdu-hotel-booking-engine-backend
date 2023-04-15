package com.kdu.IBE.service.ratingsAndReviews;

import com.kdu.IBE.model.requestDto.RatingsAndReviewsReceiveModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IRatingsAndReviewsService {
    public ResponseEntity<String> sendEmail(String receiverEmail,String roomTypeId);
    public ResponseEntity<String> putRatingsAndReviews(RatingsAndReviewsReceiveModel ratingsAndReviewsReceiveModel, BindingResult result);
    public ResponseEntity<?> getRatingsAndReview(String roomTypeId);
    public ResponseEntity<Integer> checkIfRatingsIsValid(String ratingId);
    public ResponseEntity<?> sendMailCheckedOutGuest();
    }
