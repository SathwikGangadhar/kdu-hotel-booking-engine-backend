package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.recieveModel.RatingsAndReviewsReceiveModel;
import com.kdu.IBE.service.ratingsAndReviews.IRatingsAndReviewsService;
import com.kdu.IBE.service.sesService.SesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(EndPointConstants.RATINGS_AND_REVIEWS_MAPPING)
public class ReviewsAndRatingsController {
    @Autowired
    private IRatingsAndReviewsService ratingsAndReviewsService;

    @GetMapping(EndPointConstants.SEND_REVIEW_DEALS)
    public ResponseEntity<String> sendEmail(@RequestParam(name = "receiver_email") String receiverEmail, @RequestParam(name = "room_type_id") String roomTypeId) {
        return ratingsAndReviewsService.sendEmail(receiverEmail, roomTypeId);
    }

    @GetMapping(EndPointConstants.IS_RATING_VALID)
    public ResponseEntity<Integer> ratingIsValid(@RequestParam(name = "rating_id") String ratingId) {
        return ratingsAndReviewsService.checkIfRatingsIsValid(ratingId);
    }

    @PostMapping(EndPointConstants.PUT_RATINGS_AND_REVIEWS)
    public ResponseEntity<?> putRatingsAndReview(@Valid @RequestBody RatingsAndReviewsReceiveModel ratingsAndReviewsReceiveModel, BindingResult result) {
        return ratingsAndReviewsService.putRatingsAndReviews(ratingsAndReviewsReceiveModel, result);
    }

    @GetMapping(EndPointConstants.GET_RATINGS_AND_REVIEWS)
    public ResponseEntity<?> putRatingsAndReview(@RequestParam(name = "room_type_id") String roomTypeId) {
        return ratingsAndReviewsService.getRatingsAndReview(roomTypeId);
    }
}
