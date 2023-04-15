package com.kdu.IBE.repository;

import com.kdu.IBE.entity.Booking;
import com.kdu.IBE.entity.BookingUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BookingUserInfoRepository extends JpaRepository<BookingUserInfo,Long> {
    @Query("select b.travellerEmail,b.roomTypeId from BookingUserInfo b where b.bookingId.bookingId in ?1")
    List<List<String>> findByBookingIdIn(Collection<Long> bookingIds);

}
