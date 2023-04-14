package com.kdu.IBE.repo;

import com.kdu.IBE.entity.BookingUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingUserInfoRepository extends JpaRepository<BookingUserInfo,Long> {
}
