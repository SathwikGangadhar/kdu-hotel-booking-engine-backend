package com.kdu.IBE.repository;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails,Long> {
    @Query("select b from BookingDetails b where b.bookingId.bookingId = ?1")
    BookingDetails findByBookingIdEquals(Long bookingId);
    @Query("select b from BookingDetails b where b.bookingId.bookingId in ?1")
    List<BookingDetails> findByBookingId(List<Long> bookingIds);
}
