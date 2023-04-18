package com.kdu.IBE.utils;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingDetails;
import com.kdu.IBE.entity.BookingUserDetails;
import com.kdu.IBE.entity.RoomType;
import com.kdu.IBE.model.requestDto.BookingDetailsModel;
import com.kdu.IBE.model.requestDto.BookingModel;
import com.kdu.IBE.model.requestDto.UserInfoModel;
import com.kdu.IBE.model.responseDto.RoomRateDetailModel;
import com.kdu.IBE.repository.BookingDetailsRepository;
import com.kdu.IBE.repository.BookingRepository;
import com.kdu.IBE.repository.BookingUserInfoRepository;
import com.kdu.IBE.repository.RoomTypeRepository;
import com.kdu.IBE.service.room.RoomService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDate;

@Component
public class BookingUtils {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingUserInfoRepository bookingUserInfoRepository;
    @Autowired
    private DateConverter dateConverter;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    private RoomService roomService;

    /**
     * @param userInfoModel
     */
    public void putBookingUserInfo(UserInfoModel userInfoModel){
        Booking booking=bookingRepository.findById(userInfoModel.getBookingId())
                .orElseThrow(() -> new ObjectNotFoundException("Booking Id given is invalid", "Exception"));
        /**
         * making entry in the database
         */
        BookingUserDetails bookingUserDetails = BookingUserDetails.builder()
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
                .country(userInfoModel.getBillingInfoModel().getCountry())
                .city(userInfoModel.getBillingInfoModel().getCity())
                .state(userInfoModel.getBillingInfoModel().getState())
                .zip(userInfoModel.getBillingInfoModel().getZip())
                .billingPhoneNumber(userInfoModel.getBillingInfoModel().getPhone())
                .billingAlternatePhone(userInfoModel.getBillingInfoModel().getAlternatePhone())
                .cardNumber(userInfoModel.getPaymentInfoModel().getCardNumber())
                .expiryMonth(userInfoModel.getPaymentInfoModel().getExpiryMonth())
                .expiryYear(userInfoModel.getPaymentInfoModel().getExpiryYear())
                .isSendOffers(userInfoModel.getIsSendOffers())
                .roomTypeId(userInfoModel.getRoomTypeId())
                .build();
        bookingUserInfoRepository.save(bookingUserDetails);

    }


    /**
     * @param bookingDetailsModel
     * @param bookingId
     */
    public void putToBookingDetails(BookingDetailsModel bookingDetailsModel,Long bookingId){
        Booking booking=bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ObjectNotFoundException("Booking Id given is invalid", "Exception"));
        String startDate=bookingDetailsModel.getStartDate().substring(0,10);
        String endDate=bookingDetailsModel.getEndDate();
        LocalDate startDateValue=dateConverter.convertStringToDate(startDate);
        LocalDate endDateValue=dateConverter.convertStringToDate(endDate);
        RoomType roomType=roomTypeRepository.findById(bookingDetailsModel.getRoomTypeId())
                .orElseThrow(() -> new ObjectNotFoundException("Booking Id given is invalid", "Exception"));
        BookingDetails bookingDetails=BookingDetails.builder()
                .bookingId(booking)
                .startDate(startDateValue)
                .endDate(endDateValue)
                .dealPrice(bookingDetailsModel.getDealPrice())
                .dealTitle(bookingDetailsModel.getDealTitle())
                .dealDescription(bookingDetailsModel.getDealDescription())
                .roomTypeId(roomType)
                .roomImage(bookingDetailsModel.getRoomImage())
                .adultCount(bookingDetailsModel.getAdultCount())
                .childCount(bookingDetailsModel.getChildCount())
                .averagePrice(bookingDetailsModel.getAveragePrice())
                .subTotal(bookingDetailsModel.getSubTotal())
                .taxPrice(bookingDetailsModel.getTaxPrice())
                .vatPrice(bookingDetailsModel.getVatPrice())
                .dueNow(bookingDetailsModel.getDueNow())
                .dueAtResort(bookingDetailsModel.getDueAtResort())
                .totalAmount(bookingDetailsModel.getTotalAmount())
                .build();
        bookingDetailsRepository.save(bookingDetails);
    }


    /**
     * @param bookingDetailsModel
     * @return
     */
    public boolean validateBookingDetails(BookingModel bookingDetailsModel){
        RoomRateDetailModel roomRateDetailModel= roomService.getRoomRatePerDate(bookingDetailsModel.getBookingDetailsModel().getRoomTypeId(), bookingDetailsModel.getBookingDetailsModel().getStartDate(), bookingDetailsModel.getBookingDetailsModel().getEndDate(), bookingDetailsModel.getBookingDetailsModel().getTax(), bookingDetailsModel.getBookingDetailsModel().getSurcharges(), bookingDetailsModel.getBookingDetailsModel().getVat(),bookingDetailsModel.getBookingDetailsModel().getDueNow(), bookingDetailsModel.getBookingDetailsModel().getNumberOfRooms()).getBody();
        if(bookingDetailsModel.getBookingDetailsModel().getTotalAmount()==roomRateDetailModel.getGrandTotal()){
            return true;
        }
        return false;
    }
}



