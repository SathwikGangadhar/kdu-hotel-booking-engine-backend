package com.kdu.IBE.repo;

import com.kdu.IBE.constants.RepositoryQuery;
import com.kdu.IBE.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.List;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability,Long> {
    @Query(value = "SELECT ra.availability_id, ra.room_id  \n" +
            "FROM room_availability ra \n" +
            "INNER JOIN room r ON ra.room_id = r.room_id  \n" +
            "WHERE r.room_type_id = ?1 \n" +
            "  AND ra.booking_id = 0 \n" +
            "  AND \"date\" >= CAST(?2 AS DATE) \n" +
            "  AND \"date\" <= CAST(?3 AS DATE) \n" +
            "  AND ra.room_id NOT IN (\n" +
            "    SELECT ra2.room_id \n" +
            "    FROM room_availability ra2 \n" +
            "    INNER JOIN room r2 ON ra2.room_id = r2.room_id  \n" +
            "    WHERE r2.room_type_id = ?1 \n" +
            "      AND ra2.booking_id > 0 \n" +
            "      AND \"date\" >= CAST(?2 AS DATE) \n" +
            "      AND \"date\" <= CAST(?3 AS DATE)\n" +
            "  ) \n" +
            "ORDER BY ra.room_id \n" +
            "LIMIT ?4 \n" +
            "for UPDATE SKIP LOCKED",nativeQuery = true)
    List<List<Object>> getRoomAvailabilityResult(Long roomTypeId,String startDate,String endDate,long numberOfDataRequired);
    @Transactional
    @Modifying
    @Query("update RoomAvailability r set r.bookingId = ?1 where r.availabilityId in ?2")
    int updateBookingIdByAvailabilityIdIn(Long bookingId, Collection<Long> availabilityIds);

    @Transactional
    @Modifying
    @Query("update RoomAvailability r set r.bookingId = ?1 where r.bookingId = ?2")
    int updateBookingIdByBookingIdEquals(Long bookingId, Long bookingId1);

    
}

