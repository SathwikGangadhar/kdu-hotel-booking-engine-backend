package com.kdu.IBE.model.responseDto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Builder
@Data
public class BookingUserInfoResponse {
    @NotNull
    private String travellerFirstName;
    private String travellerMiddleName;
    private String travellerLastName;
    @NotNull
    private String travellerPhoneNumber;
    private String travellerAlternatePhone;
    @NotNull
    private String travellerEmail;
    private String travellerAlternateEmail;
    @NotNull
    private String billingFirstName;
    private String billingMiddleName;
    private String billingLastName;
    @NotNull
    private String mailingAddress;
    private String alternateMailingAddress;
    @NotNull
    private String billingEmail;
    private String billingAlternateEmail;
    @NotNull
    private String billingPhoneNumber;
    private String billingAlternatePhone;
    @NotNull
    private String cardNumber;
    @NotNull
    private String expiryMonth;
    @NotNull
    private String expiryYear;
    private Boolean isSendOffers;
    private Long roomTypeId;
}
