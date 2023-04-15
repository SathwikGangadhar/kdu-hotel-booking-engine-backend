package com.kdu.IBE.service.booking;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingUserInfo;
import com.kdu.IBE.exception.BookingIdDoesNotExistException;
import com.kdu.IBE.exception.RoomsNotFoundException;
import com.kdu.IBE.model.requestDto.BookingModel;
import com.kdu.IBE.model.requestDto.BookingResponse;
import com.kdu.IBE.model.responseDto.BookingUserInfoResponse;
import com.kdu.IBE.model.responseDto.RoomBookedModel;
import com.kdu.IBE.repository.BookingRepository;
import com.kdu.IBE.repository.BookingUserInfoRepository;
import com.kdu.IBE.repository.RoomAvailabilityRepository;
import com.kdu.IBE.utils.BookingUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

@Service
public class BookingService implements IBookingService{
    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingUserInfoRepository bookingUserInfoRepository;
    @Autowired
    private BookingUtils bookingUtils;

    @Transactional
    public ResponseEntity<BookingResponse> bookRoom(BookingModel bookingModel, BindingResult result){
        if(result.hasErrors()){
            throw  new ObjectNotFoundException("Request Body passed is invalid","Invalid");
        }
        Booking booking=new Booking();
        bookingRepository.save(booking);
        bookingModel.getUserInfoModel().setBookingId(booking.getBookingId());
//        try {
            String startDateValue = bookingModel.getBookingDetails().getStartDate().substring(0, 10);
            String endDateValue = bookingModel.getBookingDetails().getEndDate().substring(0, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate startDateForCount = LocalDate.parse(startDateValue, formatter);
            LocalDate endDateForCount = LocalDate.parse(endDateValue, formatter);
            long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount) + 1;

            long numberOfDataRequired = daysBetween * bookingModel.getBookingDetails().getNumberOfRooms();

            List<List<Object>> roomAvailabilityResults = roomAvailabilityRepository.getRoomAvailabilityResult(bookingModel.getBookingDetails().getRoomTypeId(), startDateValue, endDateValue, numberOfDataRequired);

            long numberOfDataReceived = roomAvailabilityResults.size();

            /**
             * checking if all the rooms are locked
             */
            if (numberOfDataReceived != numberOfDataRequired) {
                throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
            }
            Collection<Long> availabilityIdList = new ArrayList<>();
            List<RoomBookedModel> roomBookedList=new ArrayList<>();
        Map<Long,Boolean> isRoomPresentCheckMap=new HashMap<>();
        for (List<Object> value : roomAvailabilityResults) {
                availabilityIdList.add(Long.parseLong(value.get(0).toString()));
                if(isRoomPresentCheckMap.get(Long.parseLong(value.get(1).toString()))==null){
                    RoomBookedModel roomBookedModel=RoomBookedModel.builder()
                            .roomNumber(Long.parseLong(value.get(1).toString()))
                            .build();
                    roomBookedList.add(roomBookedModel);
                    isRoomPresentCheckMap.put(Long.parseLong(value.get(1).toString()),true);
                }
            }
        int updatedNumberOfRows = roomAvailabilityRepository.updateBookingIdByAvailabilityIdIn(booking.getBookingId(), availabilityIdList);
            if (updatedNumberOfRows != availabilityIdList.size()) {
                throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
            }
            bookingUtils.putBookingUserInfo(bookingModel.getUserInfoModel());
        BookingResponse bookingResponse=BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .roomList(roomBookedList)
                .build();
            return new ResponseEntity<BookingResponse>(bookingResponse, HttpStatus.OK);
//        }catch (Exception exception){
//            throw new UnexpectedErrorException("Unexpected error occurred");
//        }
    }

    public ResponseEntity<BookingUserInfoResponse> getBookingUserInfo(String bookingId) throws BookingIdDoesNotExistException {
        BookingUserInfo bookingUserInfo=bookingUserInfoRepository.findByBookingIdEquals(Long.parseLong(bookingId));
        if(bookingUserInfo==null){
            throw new BookingIdDoesNotExistException("The booking id given is not present in the database");
        }
        BookingUserInfoResponse bookingUserInfoResponse=BookingUserInfoResponse.builder()
                .travellerFirstName(bookingUserInfo.getTravellerFirstName())
                .travellerMiddleName(bookingUserInfo.getTravellerMiddleName())
                .travellerLastName(bookingUserInfo.getTravellerLastName())
                .travellerPhoneNumber(bookingUserInfo.getTravellerPhoneNumber())
                .travellerAlternatePhone(bookingUserInfo.getTravellerAlternatePhone())
                .travellerEmail(bookingUserInfo.getTravellerEmail())
                .travellerAlternateEmail(bookingUserInfo.getTravellerAlternateEmail())
                .billingFirstName(bookingUserInfo.getBillingFirstName())
                .billingMiddleName(bookingUserInfo.getBillingMiddleName())
                .billingLastName(bookingUserInfo.getBillingLastName())
                .mailingAddress(bookingUserInfo.getMailingAddress())
                .alternateMailingAddress(bookingUserInfo.getAlternateMailingAddress())
                .billingEmail(bookingUserInfo.getBillingEmail())
                .billingAlternateEmail(bookingUserInfo.getBillingAlternateEmail())
                .billingPhoneNumber(bookingUserInfo.getBillingPhoneNumber())
                .billingAlternatePhone(bookingUserInfo.getBillingAlternatePhone())
                .cardNumber(bookingUserInfo.getCardNumber())
                .expiryMonth(bookingUserInfo.getExpiryMonth())
                .expiryYear(bookingUserInfo.getExpiryYear())
                .isSendOffers(bookingUserInfo.getIsSendOffers())
                .roomTypeId(bookingUserInfo.getRoomTypeId())
                .build();
        return new ResponseEntity<BookingUserInfoResponse>(bookingUserInfoResponse,HttpStatus.OK);
    }
}





