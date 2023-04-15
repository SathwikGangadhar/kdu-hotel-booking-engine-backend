package com.kdu.IBE.repository;

import com.kdu.IBE.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
