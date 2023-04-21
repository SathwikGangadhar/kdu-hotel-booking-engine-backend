package com.kdu.IBE.service.otp;

import com.kdu.IBE.constants.SenderEmail;
import com.kdu.IBE.entity.Otp;
import com.kdu.IBE.repository.BookingRepository;
import com.kdu.IBE.repository.NotifyUserRepository;
import com.kdu.IBE.repository.OtpRepository;
import com.kdu.IBE.repository.RoomAvailabilityRepository;
import com.kdu.IBE.service.sesService.SesService;
import com.kdu.IBE.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

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

    @Autowired
    private NotifyUserRepository notifyUserRepository;

    /**
     *
     * @param bookingId
     * @param receiverEmail
     * @return
     */
    public ResponseEntity<String> setOtp(String bookingId,String receiverEmail){
        long bookingIdValue=Long.parseLong(bookingId);
        int otpValue=otpUtils.getOtp();
        String senderEmail = SenderEmail.SENDER_EMAIL;
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
        return new ResponseEntity<String>("Otp generated successfully", HttpStatus.OK);
    }

    /**
     *
     * @param otp
     * @param bookingId
     * @return
     */
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

    /**
     * @param bookingId
     * @return
     */
    public ResponseEntity<String> putOtpForLoggedInUser(String bookingId){
        long bookingIdValue=Long.parseLong(bookingId);
        deleteBooking(bookingIdValue);
        return new ResponseEntity<String>("Booking canceled successfully",HttpStatus.OK);
    }

    /**
     * @param bookingIdValue
     */
    public void deleteBooking(Long bookingIdValue){
        roomAvailabilityRepository.updateBookingIdByBookingIdEquals(0l,bookingIdValue);

        bookingRepository.updateIsActiveByBookingId(false,bookingIdValue);
    }
    public ResponseEntity<List<String>> notifyUser(String startDate,Long roomTypeId) throws IOException {
       List<String> notifyEmailList =notifyUserRepository.getEmailsToNotifyUser(startDate,roomTypeId);
        String senderEmail=SenderEmail.SENDER_EMAIL;

        /**
         * threading has to be implemented
         */
        for(String email:notifyEmailList){
            sesService.sesMessageSender(senderEmail,email,roomTypeId.toString());
        }

        return new ResponseEntity<>(notifyEmailList,HttpStatus.OK);
    }
}