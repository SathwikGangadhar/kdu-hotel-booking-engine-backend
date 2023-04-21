package com.kdu.IBE.service.myBooking;

import com.kdu.IBE.entity.BookingDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMyBookingsService {
    public ResponseEntity<List<BookingDetails>> getMyBookings(String email);
    public ResponseEntity<List<BookingDetails>> getMyCanceledBookings(String email);
}
