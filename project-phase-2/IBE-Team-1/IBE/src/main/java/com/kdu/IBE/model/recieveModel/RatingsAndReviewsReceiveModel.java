package com.kdu.IBE.model.recieveModel;

import lombok.Data;

import java.util.UUID;

@Data
public class RatingsAndReviewsReceiveModel {
    private UUID roomTypeId;
    private Double ratings;
    private String reviews;
}
