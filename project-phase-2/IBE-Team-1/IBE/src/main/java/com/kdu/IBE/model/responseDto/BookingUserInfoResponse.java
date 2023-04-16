package com.kdu.IBE.model.responseDto;

import com.kdu.IBE.entity.Booking;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
public class BookingUserInfoResponse {
    private Booking bookingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double dealPrice;
    private String dealTitle;
    private String dealDescription;
    private Long roomTypeId;
    private String roomImage;
    private Integer adultCount;
    private Integer childCount;
    private Double averagePrice;
    private Double subTotal;
    private Double taxPrice;
    private Double vatPrice;
    private Double dueNow;
    private Double dueAtResort;
    private Double totalAmount;
    private String travellerFirstName;
    private String travellerMiddleName;
    private String travellerLastName;
    private String travellerPhoneNumber;
    private String travellerAlternatePhone;
    private String travellerEmail;
    private String travellerAlternateEmail;
    private String billingFirstName;
    private String billingMiddleName;
    private String billingLastName;
    private String mailingAddress;
    private String alternateMailingAddress;
    private String billingEmail;
    private String billingAlternateEmail;
    private String billingPhoneNumber;
    private String billingAlternatePhone;
    private String country;
    private String city;
    private String state;
    private String zip;
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private Boolean isSendOffers;

}
