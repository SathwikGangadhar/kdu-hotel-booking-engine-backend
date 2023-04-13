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
    @Query(value = RepositoryQuery.CONCURRENCY_HANDLING_QUERY,nativeQuery = true)
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

