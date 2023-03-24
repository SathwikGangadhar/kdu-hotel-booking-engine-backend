package com.kdu.IBE.repo;

import com.kdu.IBE.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
    @Query("select p from PropertyTable p where p.property_id = ?1")
    Property findByProperty_id(Long property_id);
}
