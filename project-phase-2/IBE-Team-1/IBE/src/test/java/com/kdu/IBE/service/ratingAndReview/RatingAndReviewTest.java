package com.kdu.IBE.service.ratingAndReview;

import com.kdu.IBE.service.ratingsAndReviews.IRatingsAndReviewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RatingAndReviewTest {

    @Autowired
    IRatingsAndReviewsService ratingsAndReviewsService;

    /**
     * testing the fetching of the ratings and reviews
     */
    @Test
    public void testRatingsAndReview() {
        // Setup
        String roomTypeId = "1";
        ResponseEntity<?> response = ratingsAndReviewsService.getRatingsAndReview(roomTypeId);
        // Test
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
