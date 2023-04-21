package com.kdu.IBE.repository;

import com.kdu.IBE.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Transactional
    @Modifying
    @Query("delete from Booking b where b.bookingId = ?1")
    int deleteByBookingIdEquals(Long bookingId);

    @Query("select b.isActive from Booking b where b.bookingId = ?1")
    Boolean findByBookingId(Long bookingId);

    @Transactional
    @Modifying
    @Query("update Booking b set b.isActive = ?1 where b.bookingId = ?2")
    int updateIsActiveByBookingId(Boolean isActive, Long bookingId);




}
