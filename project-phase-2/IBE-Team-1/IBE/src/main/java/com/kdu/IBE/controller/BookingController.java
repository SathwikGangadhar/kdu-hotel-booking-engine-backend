package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.entity.BookingUserInfo;
import com.kdu.IBE.model.recieveModel.BookingDetails;
import com.kdu.IBE.model.recieveModel.BookingModel;
import com.kdu.IBE.model.recieveModel.FilterSort;
import com.kdu.IBE.model.recieveModel.UserInfoModel;
import com.kdu.IBE.service.booking.BookingService;
import com.kdu.IBE.service.booking.IBookingService;
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
    private IBookingService bookingService;
    @PostMapping(EndPointConstants.GET_BOOKING)
    ResponseEntity<?> getBooking(@Valid @RequestBody BookingModel bookingModel, BindingResult result){
        System.out.println("booking model"+bookingModel);
      return bookingService.bookRoom(bookingModel,result);
    }
//    @PostMapping("put/booking/user/info")
//    ResponseEntity<?> putBookingUserInfo(@Valid @RequestBody UserInfoModel userInfoModel, BindingResult result){
//        return bookingService.putBookingUserInfo(userInfoModel,result);
//    }
}
