package com.kdu.IBE.controller;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.requestDto.BillingInfoModel;
import com.kdu.IBE.service.otp.IOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(EndPointConstants.OTP)
public class OtpController {
    @Autowired
    public IOtpService otpService;
    @PostMapping(EndPointConstants.SET_OTP)
    ResponseEntity<String> setOtp(@RequestParam(name="booking_id") String bookingId,@RequestParam(name="receiverEmail") String receiverEmail){
                return otpService.setOtp(bookingId,receiverEmail);
    }
    @PostMapping(EndPointConstants.PUT_OTP)
    ResponseEntity<String> putOtp(@RequestParam(name = "otp") String otp,@RequestParam(name = "booking_id") String bookingId){
        return otpService.putOtp(otp,bookingId);
    }
    @PostMapping(EndPointConstants.PUT_OTP_FOR_LOGGED_USER)
    ResponseEntity<String> putOtpForLoggedInUser(@RequestParam(name = "booking_id") String bookingId){
        return otpService.putOtpForLoggedInUser(bookingId);
    }

    @GetMapping("/notify/email")
    ResponseEntity<List<String>> geNotifyEmail(@RequestParam(name = "room_type_id") Long roomTypeId, @RequestParam(name = "start_date") String startDate) throws IOException {
        return otpService.notifyUser(startDate,roomTypeId);
    }
}
