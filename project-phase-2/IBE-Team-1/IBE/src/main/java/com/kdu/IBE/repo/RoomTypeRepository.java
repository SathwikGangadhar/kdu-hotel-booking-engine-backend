package com.kdu.IBE.repo;

import com.kdu.IBE.entity.PropertyTable;
import com.kdu.IBE.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType,Long> {
}
