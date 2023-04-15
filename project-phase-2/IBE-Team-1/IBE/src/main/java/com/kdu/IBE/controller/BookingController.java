package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.requestDto.BookingModel;
import com.kdu.IBE.model.requestDto.BookingResponse;
import com.kdu.IBE.model.responseDto.RoomBookedModel;
import com.kdu.IBE.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(EndPointConstants.BOOKING)
public class BookingController {
    @Autowired
    private IBookingService bookingService;
    @PostMapping(EndPointConstants.GET_BOOKING)
    ResponseEntity<BookingResponse> getBooking(@Valid @RequestBody BookingModel bookingModel, BindingResult result){
        System.out.println("booking model"+bookingModel);
      return bookingService.bookRoom(bookingModel,result);
    }
}
