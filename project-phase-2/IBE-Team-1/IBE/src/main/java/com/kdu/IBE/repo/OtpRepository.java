package com.kdu.IBE.repo;

import com.kdu.IBE.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OtpRepository extends JpaRepository<Otp,Long> {
    @Transactional
    @Modifying
    @Query("delete from Otp o where o.bookingId = ?1")
    int deleteByBookingIdEquals(Long bookingId);

    @Transactional
    @Modifying
    @Query("delete from Otp o where o.otp = ?1 and o.bookingId = ?2")
    int deleteByOtpEqualsAndBookingIdEquals(Integer otp, Long bookingId);

}
