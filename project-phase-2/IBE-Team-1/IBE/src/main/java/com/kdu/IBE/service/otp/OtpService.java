package com.kdu.IBE.service.otp;

import com.kdu.IBE.entity.Otp;
import com.kdu.IBE.repo.OtpRepository;
import com.kdu.IBE.repo.RoomAvailabilityRepository;
import com.kdu.IBE.service.sesService.SesService;
import com.kdu.IBE.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OtpService implements IOtpService{
    @Autowired
    public OtpUtils otpUtils;
    @Autowired
    public SesService sesService;
    @Autowired
    public OtpRepository otpRepository;
    @Autowired
    public RoomAvailabilityRepository roomAvailabilityRepository;
    public ResponseEntity<?> setOtp(String bookingId,String receiverEmail){
        long bookingIdValue=Long.parseLong(bookingId);
        int otpValue=otpUtils.getOtp();
        String senderEmail = "sathwik.shetty@kickdrumtech.com";
        otpRepository.deleteByBookingIdEquals(Long.parseLong(bookingId));
        Otp otp=Otp.builder()
                .bookingId(bookingIdValue)
                .otp(otpValue)
                .build();
        otpRepository.save(otp);
        try {
            sesService.sendOtp(senderEmail,receiverEmail,otp.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Integer>(otpValue, HttpStatus.OK);
    }

    public ResponseEntity<?> putOtp(String otp,String bookingId){
        int otpValue=Integer.parseInt(otp);
        long bookingIdValue=Long.parseLong(bookingId);
        int response=otpRepository.deleteByOtpEqualsAndBookingIdEquals(otpValue,bookingIdValue);
        /**
         * making booking id equal to 0 if the otp entered ws correct
         */
        if(response==1){
            roomAvailabilityRepository.updateBookingIdByBookingIdEquals(bookingIdValue,0l);
            return new ResponseEntity<>("Booking canceled successfully",HttpStatus.OK);
        }
        return new ResponseEntity<>("OTP entered is wrong please try again",HttpStatus.OK);
    }
}