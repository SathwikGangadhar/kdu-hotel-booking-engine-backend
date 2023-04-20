package com.kdu.IBE.service.otp;

import com.kdu.IBE.constants.SenderEmail;
import com.kdu.IBE.entity.BookingDetails;
import com.kdu.IBE.entity.BookingUserDetails;
import com.kdu.IBE.entity.Otp;
import com.kdu.IBE.repository.*;
import com.kdu.IBE.service.sesService.SesService;
import com.kdu.IBE.utils.DateConverter;
import com.kdu.IBE.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class OtpService implements IOtpService {
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
    @Autowired
    private DateConverter dateConverter;
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;
    @Autowired
    private BookingUserInfoRepository bookingUserInfoRepository;

    /**
     * @param bookingId
     * @param receiverEmail
     * @return
     */
    public ResponseEntity<String> setOtp(String bookingId, String receiverEmail) {
        long bookingIdValue = Long.parseLong(bookingId);
        int otpValue = otpUtils.getOtp();
        String senderEmail = SenderEmail.SENDER_EMAIL;
        otpRepository.deleteByBookingIdEquals(Long.parseLong(bookingId));
        Otp otp = Otp.builder().bookingId(bookingIdValue).otp(otpValue).build();
        otpRepository.save(otp);
        try {
            sesService.sendOtp(senderEmail, receiverEmail, Integer.toString(otpValue));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<String>("Otp generated successfully", HttpStatus.OK);
    }

    /**
     * @param otp
     * @param bookingId
     * @return
     */
    public ResponseEntity<String> putOtp(String otp, String bookingId) {
        int otpValue = Integer.parseInt(otp);
        long bookingIdValue = Long.parseLong(bookingId);
        int response = otpRepository.deleteByOtpEqualsAndBookingIdEquals(otpValue, bookingIdValue);
        /**
         * making booking id equal to 0 if the otp entered ws correct
         */
        if (response == 1) {
            deleteBooking(bookingIdValue);
            return new ResponseEntity<String>("Booking canceled successfully", HttpStatus.OK);
        }


        return new ResponseEntity<String>("OTP entered is wrong please try again", HttpStatus.BAD_REQUEST);
    }

    /**
     * @param bookingId
     * @return
     */
    public ResponseEntity<String> putOtpForLoggedInUser(String bookingId) {
        long bookingIdValue = Long.parseLong(bookingId);
        deleteBooking(bookingIdValue);
        return new ResponseEntity<String>("Booking canceled successfully", HttpStatus.OK);
    }

    /**
     * @param bookingIdValue
     */
    public void deleteBooking(Long bookingIdValue) {
        BookingDetails bookingDetails = bookingDetailsRepository.findByBookingIdEquals(bookingIdValue);
        BookingUserDetails bookingUserDetails = bookingUserInfoRepository.findByBookingIdEquals(bookingIdValue);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        /**
         * calling in thread inorder to proceed the other process
         */
        Callable<Void> task = () -> {
            this.notifyUser(bookingDetails.getStartDate().toString().substring(0, 10), bookingDetails.getEndDate().toString().substring(0, 10), bookingDetails.getRoomTypeId().getRoomTypeId(), bookingUserDetails.getBillingEmail());
            return null;
        };
        executorService.submit(task);
        executorService.shutdown();
        roomAvailabilityRepository.updateBookingIdByBookingIdEquals(0l, bookingIdValue);
        bookingRepository.deleteByBookingIdEquals(bookingIdValue);
    }

    /**
     * @param startDate
     * @param roomTypeId
     * @return
     * @throws IOException
     */
    public void notifyUser(String startDate, String endDate, Long roomTypeId, String userEmail) throws IOException {
        LocalDate startDateForCount = dateConverter.convertStringToDate(startDate);
        LocalDate endDateForCount = dateConverter.convertStringToDate(endDate);
        long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount) + 1;
        List<String> notifyEmailList = notifyUserRepository.getEmailsToNotifyUser(startDate, endDate, roomTypeId, daysBetween);
        String senderEmail = SenderEmail.SENDER_EMAIL;

        for (String email : notifyEmailList) {
            ExecutorService executorService = Executors.newFixedThreadPool(10000);
            if (email != userEmail) {
                Callable<Void> task = () -> {
                    notifyUserRepository.deleteByUserEmailAndRoomTypeId(email,roomTypeId);
                    sesService.sendRoomAvailabilityNotification(senderEmail, email, roomTypeId.toString());
                    return null;
                };
                executorService.submit(task);
            }
        }
    }
}