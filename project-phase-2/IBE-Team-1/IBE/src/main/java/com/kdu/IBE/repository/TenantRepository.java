package com.kdu.IBE.repository;

import com.kdu.IBE.entity.TenantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<TenantTable, Long> {
    @Query("select t from TenantTable t where t.tenant_id = ?1")
    TenantTable findByTenant_id(Long tenant_id);
    
}
