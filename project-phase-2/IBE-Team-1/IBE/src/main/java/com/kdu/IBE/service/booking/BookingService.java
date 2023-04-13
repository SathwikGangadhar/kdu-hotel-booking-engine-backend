package com.kdu.IBE.service.booking;

import com.kdu.IBE.exception.RoomsNotFoundException;
import com.kdu.IBE.model.recieveModel.BookingDetails;
import com.kdu.IBE.repo.RoomAvailabilityRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService implements IBookingService{
    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;
    @Transactional
    public ResponseEntity<?> bookRoom(BookingDetails bookingDetails, BindingResult result){
        if(result.hasErrors()){
            throw  new ObjectNotFoundException("Request Body passed is invalid","Invalid");
        }
        String startDateValue=bookingDetails.getStartDate().substring(0,10);
        String endDateValue=bookingDetails.getEndDate().substring(0,10);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDateForCount = LocalDate.parse(startDateValue, formatter);
        LocalDate endDateForCount = LocalDate.parse(endDateValue, formatter);
        long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount)+1;

        long numberOfDataRequired=daysBetween*bookingDetails.getNumberOfRooms();

        List<List<Object>> roomAvailabilityResults=roomAvailabilityRepository.getRoomAvailabilityResult(bookingDetails.getRoomTypeId(),startDateValue,endDateValue,numberOfDataRequired);

        long numberOfDataReceived=roomAvailabilityResults.size();

        /**
         * checking if all the rooms are locked
         */

        if(numberOfDataReceived!=numberOfDataRequired){
            throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
        }

        Collection<Long> availabilityIdList=new ArrayList<>();
            for (List<Object> value : roomAvailabilityResults) {
                availabilityIdList.add(Long.parseLong(value.get(0).toString()));
            }
        System.out.println("availability list = "+availabilityIdList);

        int updatedNumberOfRows=roomAvailabilityRepository.updateBookingIdByAvailabilityIdIn(1l,availabilityIdList);

        if(updatedNumberOfRows!=availabilityIdList.size()){
            throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
        }
        return new ResponseEntity<>(roomAvailabilityResults, HttpStatus.OK);
    }
}




