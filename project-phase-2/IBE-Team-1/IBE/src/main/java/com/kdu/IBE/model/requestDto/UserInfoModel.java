package com.kdu.IBE.model.requestDto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class UserInfoModel {
    @NotNull
    private Long bookingId;
    @Valid
    private TravellerInfoModel travellerInfoModel;
    @Valid
    private BillingInfoModel billingInfoModel;
    @Valid
    private PaymentInfoModel paymentInfoModel;
    private Boolean isSendOffers;
    private Long roomTypeId;
}

