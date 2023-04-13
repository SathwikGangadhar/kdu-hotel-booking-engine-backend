package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.recieveModel.BookingDetails;
import com.kdu.IBE.model.recieveModel.FilterSort;
import com.kdu.IBE.service.booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(EndPointConstants.BOOKING)
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @PostMapping(EndPointConstants.GET_BOOKING)
    ResponseEntity<?> getBooking(@Valid @RequestBody BookingDetails bookingDetails, BindingResult result){
      return bookingService.bookRoom(bookingDetails,result);
    }
}
