package com.kdu.IBE.model.requestDto;

import lombok.Data;

@Data
public class RatingsAndReviewsReceiveModel {
    private Long roomTypeId;
    private Double ratings;
    private String reviews;
    private Long bookingId;
}
