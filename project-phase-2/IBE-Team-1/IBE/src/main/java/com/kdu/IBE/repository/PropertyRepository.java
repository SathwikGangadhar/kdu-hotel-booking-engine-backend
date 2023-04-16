package com.kdu.IBE.repository;

import com.kdu.IBE.entity.PropertyTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyTable,Long> {
    @Query("select p from PropertyTable p where p.property_id = ?1")
    PropertyTable findByProperty_id(Long property_id);
}
