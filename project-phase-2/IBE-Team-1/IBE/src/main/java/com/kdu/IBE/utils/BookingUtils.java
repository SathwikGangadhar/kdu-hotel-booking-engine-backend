package com.kdu.IBE.utils;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingUserInfo;
import com.kdu.IBE.model.requestDto.UserInfoModel;
import com.kdu.IBE.repository.BookingRepository;
import com.kdu.IBE.repository.BookingUserInfoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BookingUtils {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingUserInfoRepository bookingUserInfoRepository;
    public ResponseEntity<?> putBookingUserInfo(UserInfoModel userInfoModel){
        System.out.println("userInfoModel = "+userInfoModel);
        Booking booking=bookingRepository.findById(userInfoModel.getBookingId())
                .orElseThrow(() -> new ObjectNotFoundException("Booking Id given is invalid", "Exception"));
        System.out.println("----"+booking);
        BookingUserInfo bookingUserInfo=BookingUserInfo.builder()
                .bookingId(booking)
                .travellerFirstName(userInfoModel.getTravellerInfoModel().getFirstName())
                .travellerMiddleName(userInfoModel.getTravellerInfoModel().getMiddleName())
                .travellerLastName(userInfoModel.getTravellerInfoModel().getLastName())
                .travellerPhoneNumber(userInfoModel.getTravellerInfoModel().getPhone())
                .travellerAlternatePhone(userInfoModel.getTravellerInfoModel().getAlternatePhone())
                .travellerEmail(userInfoModel.getTravellerInfoModel().getEmail())
                .travellerAlternateEmail(userInfoModel.getTravellerInfoModel().getAlternateEmail())
                .billingFirstName(userInfoModel.getBillingInfoModel().getFirstName())
                .billingMiddleName(userInfoModel.getBillingInfoModel().getMiddleName())
                .billingLastName(userInfoModel.getBillingInfoModel().getLastName())
                .mailingAddress(userInfoModel.getBillingInfoModel().getMailingAddress())
                .alternateMailingAddress(userInfoModel.getBillingInfoModel().getAlternateMailingAddress())
                .billingEmail(userInfoModel.getBillingInfoModel().getEmail())
                .billingAlternateEmail(userInfoModel.getBillingInfoModel().getAlternateEmail())
                .billingPhoneNumber(userInfoModel.getBillingInfoModel().getPhone())
                .billingAlternatePhone(userInfoModel.getBillingInfoModel().getAlternatePhone())
                .cardNumber(userInfoModel.getPaymentInfoModel().getCardNumber())
                .expiryMonth(userInfoModel.getPaymentInfoModel().getExpiryMonth())
                .expiryYear(userInfoModel.getPaymentInfoModel().getExpiryYear())
                .isSendOffers(userInfoModel.getIsSendOffers())
                .roomTypeId(userInfoModel.getRoomTypeId())
                .build();
        System.out.println("yes-2");
        bookingUserInfoRepository.save(bookingUserInfo);
        return new ResponseEntity<>("Billing info added successfully", HttpStatus.OK);
    }

}

