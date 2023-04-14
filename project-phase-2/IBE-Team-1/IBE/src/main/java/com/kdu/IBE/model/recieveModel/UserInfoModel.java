package com.kdu.IBE.model.recieveModel;

import lombok.Data;

@Data
public class UserInfoModel {
    private Long bookingId;
    private TravellerInfoModel travellerInfoModel;
    private BillingInfoModel billingInfoModel;
    private PaymentInfoModel paymentInfoModel;
}

