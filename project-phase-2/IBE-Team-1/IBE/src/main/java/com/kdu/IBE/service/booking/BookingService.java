package com.kdu.IBE.service.booking;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingUserInfo;
import com.kdu.IBE.exception.RoomsNotFoundException;
import com.kdu.IBE.model.recieveModel.BookingDetails;
import com.kdu.IBE.model.recieveModel.BookingModel;
import com.kdu.IBE.model.recieveModel.TravellerInfoModel;
import com.kdu.IBE.model.recieveModel.UserInfoModel;
import com.kdu.IBE.model.returnDto.RoomBookedModel;
import com.kdu.IBE.repo.BookingRepository;
import com.kdu.IBE.repo.BookingUserInfoRepository;
import com.kdu.IBE.repo.RoomAvailabilityRepository;
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
    public ResponseEntity<?> bookRoom(BookingModel bookingModel, BindingResult result){
        if(result.hasErrors()){
            throw  new ObjectNotFoundException("Request Body passed is invalid","Invalid");
        }
        Booking booking=new Booking();
        bookingModel.getUserInfoModel().setBookingId(booking.getBookingId());
        System.out.println("booking id"+booking.getBookingId());
        bookingRepository.save(booking);
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
        System.out.println("yes-4");
        int updatedNumberOfRows = roomAvailabilityRepository.updateBookingIdByAvailabilityIdIn(booking.getBookingId(), availabilityIdList);
            if (updatedNumberOfRows != availabilityIdList.size()) {
                throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
            }
//            bookingUtils.putBookingUserInfo(bookingModel.getUserInfoModel());
            return new ResponseEntity<>(roomBookedList, HttpStatus.OK);
//        }catch (Exception exception){
//            throw new UnexpectedErrorException("Unexpected error occurred");
//        }
    }
}





