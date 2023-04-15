package com.kdu.IBE.model.requestDto;

import lombok.Data;

@Data
public class UserInfoModel {
    private Long bookingId;
    private TravellerInfoModel travellerInfoModel;
    private BillingInfoModel billingInfoModel;
    private PaymentInfoModel paymentInfoModel;
    private Boolean isSendOffers;
    private Long roomTypeId;
}

