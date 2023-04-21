package com.kdu.IBE.service.myBooking;

import com.kdu.IBE.entity.BookingDetails;
import com.kdu.IBE.repository.BookingDetailsRepository;
import com.kdu.IBE.repository.BookingUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookingsService implements IMyBookingsService{

    @Autowired
    BookingUserInfoRepository bookingUserInfoRepository;
    @Autowired
    BookingDetailsRepository bookingDetailsRepository;
    @Override
    public ResponseEntity<List<BookingDetails>> getMyBookings(String email) {
        List<Long> bookingIdList= bookingUserInfoRepository.findByBillingEmailEquals(email,true);
        return new ResponseEntity<>(getMyBookingsDetails(bookingIdList), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<BookingDetails>> getMyCanceledBookings(String email) {
        List<Long> bookingIdList= bookingUserInfoRepository.findByBillingEmailEquals(email,false);
        return new ResponseEntity<>(getMyBookingsDetails(bookingIdList), HttpStatus.OK);
    }


    public List<BookingDetails> getMyBookingsDetails(List<Long> bookingIdList) {
        return bookingDetailsRepository.findByBookingId(bookingIdList);
    }


}
