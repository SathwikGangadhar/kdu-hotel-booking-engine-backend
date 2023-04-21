package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.entity.BookingDetails;
import com.kdu.IBE.service.myBooking.MyBookingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping(EndPointConstants.MY_BOOKINGS)
public class MyBookingsController {
    @Autowired
    MyBookingsService myBookingsService;
    @GetMapping(EndPointConstants.GET_MY_BOOKINGS)
    ResponseEntity<List<BookingDetails>> getMyBookingDetails(@RequestParam(name="email") String email){
        return myBookingsService.getMyBookings(email);
    }
    @GetMapping(EndPointConstants.GET_MY_CANCELED_BOOKING)
    ResponseEntity<List<BookingDetails>> getMyCanceledBookingDetails(@RequestParam(name="email") String email){
        return myBookingsService.getMyCanceledBookings(email);
    }
}
