package com.kdu.IBE.repository;

import com.kdu.IBE.entity.CustomDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomDealRepository extends JpaRepository<CustomDeal,Long> {
    @Query("select c from CustomDeal c " +
            "where c.promoCode = ?1 and c.roomType.roomTypeId = ?2 and c.startDate <= ?3 and c.endDate >= ?3")
    CustomDeal findByPromoCodeEqualsAndRoomTypeIdEqualsAndStartDateEqualsAndEndDateEquals(String promoCode, Long roomTypeId, String currentDate);
}
