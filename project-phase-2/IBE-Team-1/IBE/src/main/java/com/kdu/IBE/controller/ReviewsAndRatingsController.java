package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.requestDto.RatingsAndReviewsReceiveModel;
import com.kdu.IBE.service.ratingsAndReviews.IRatingsAndReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndPointConstants.RATINGS_AND_REVIEWS_MAPPING)
public class ReviewsAndRatingsController {
    @Autowired
    private IRatingsAndReviewsService ratingsAndReviewsService;

    @GetMapping(EndPointConstants.SEND_REVIEW_DEALS)
    public ResponseEntity<String> sendEmail(@RequestParam(name = "receiver_email") String receiverEmail, @RequestParam(name = "room_type_id") String roomTypeId,@RequestParam(name = "booking_id") String bookingId) {
        return ratingsAndReviewsService.sendEmail(receiverEmail.toString(), roomTypeId,bookingId);
    }

    @GetMapping(EndPointConstants.IS_RATING_VALID)
    public ResponseEntity<Integer> ratingIsValid(@RequestParam(name = "rating_id") String ratingId) {
        return ratingsAndReviewsService.checkIfRatingsIsValid(ratingId);
    }

    @PostMapping(EndPointConstants.PUT_RATINGS_AND_REVIEWS)
    public ResponseEntity<String> putRatingsAndReview(@Valid @RequestBody RatingsAndReviewsReceiveModel ratingsAndReviewsReceiveModel, BindingResult result) {
        return ratingsAndReviewsService.putRatingsAndReviews(ratingsAndReviewsReceiveModel, result);
    }
    @GetMapping(EndPointConstants.GET_RATINGS_AND_REVIEWS)
    public ResponseEntity<?> putRatingsAndReview(@RequestParam(name = "room_type_id") String roomTypeId) {
        return ratingsAndReviewsService.getRatingsAndReview(roomTypeId);
    }
    @GetMapping("/send/mail/to")
    public ResponseEntity<?> sendMailTo(){
        return ratingsAndReviewsService.sendMailCheckedOutGuest();
    }

    @PostMapping ("/get/ratings-and-review-list")
    public ResponseEntity<?> getRatingsAndReviewList(@Valid @RequestBody List<Long> roomTypeIdList,BindingResult result){
        return ratingsAndReviewsService.getRatingAndReviewList(roomTypeIdList);
    }
}
