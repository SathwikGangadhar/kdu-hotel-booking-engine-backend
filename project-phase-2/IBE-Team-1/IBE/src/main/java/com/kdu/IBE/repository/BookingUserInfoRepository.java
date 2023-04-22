package com.kdu.IBE.repository;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingUserInfoRepository extends JpaRepository<BookingUserDetails,Long> {
    @Query("select b.travellerEmail,b.roomTypeId ,b.bookingId.bookingId from BookingUserDetails b where b.bookingId.bookingId in ?1")
    List<List<String>>  findByBookingIdIn(Collection<Long> bookingIds);
    @Query("select b from BookingUserDetails b where b.bookingId.bookingId = ?1")
    BookingUserDetails findByBookingIdEquals(Long bookingId);
    @Query("select b.bookingId.bookingId from BookingUserDetails b where b.billingEmail = ?1 and b.bookingId.isActive=?2")
    List<Long> findByBillingEmailEquals(String billingEmail,Boolean isActive);



}
