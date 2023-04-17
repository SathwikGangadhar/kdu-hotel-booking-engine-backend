package com.kdu.IBE.service.otp;

import com.kdu.IBE.entity.Otp;
import com.kdu.IBE.repository.BookingRepository;
import com.kdu.IBE.repository.OtpRepository;
import com.kdu.IBE.repository.RoomAvailabilityRepository;
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
    @Autowired
    private BookingRepository bookingRepository;


    public ResponseEntity<Integer> setOtp(String bookingId,String receiverEmail){
        long bookingIdValue=Long.parseLong(bookingId);
        int otpValue=otpUtils.getOtp();
        String senderEmail = "nitesh.gupta@kickdrumtech.com";
        otpRepository.deleteByBookingIdEquals(Long.parseLong(bookingId));
        Otp otp=Otp.builder()
                .bookingId(bookingIdValue)
                .otp(otpValue)
                .build();
        otpRepository.save(otp);
        try {
            sesService.sendOtp(senderEmail,receiverEmail,Integer.toString(otpValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<Integer>(otpValue, HttpStatus.OK);
    }

    public ResponseEntity<String> putOtp(String otp,String bookingId){
        int otpValue=Integer.parseInt(otp);
        long bookingIdValue=Long.parseLong(bookingId);
        int response=otpRepository.deleteByOtpEqualsAndBookingIdEquals(otpValue,bookingIdValue);
        /**
         * making booking id equal to 0 if the otp entered ws correct
         */
        if(response==1){
            deleteBooking(bookingIdValue);
            return new ResponseEntity<String>("Booking canceled successfully",HttpStatus.OK);
        }
        return new ResponseEntity<String>("OTP entered is wrong please try again",HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<String> putOtpForLoggedInUser(String bookingId){
        long bookingIdValue=Long.parseLong(bookingId);
        deleteBooking(bookingIdValue);
        return new ResponseEntity<String>("Booking canceled successfully",HttpStatus.OK);
    }


    public void deleteBooking(Long bookingIdValue){
        roomAvailabilityRepository.updateBookingIdByBookingIdEquals(0l,bookingIdValue);
        bookingRepository.deleteByBookingIdEquals(bookingIdValue);
    }
}