package com.kdu.IBE.repo;

import com.kdu.IBE.entity.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability,Long> {

}
