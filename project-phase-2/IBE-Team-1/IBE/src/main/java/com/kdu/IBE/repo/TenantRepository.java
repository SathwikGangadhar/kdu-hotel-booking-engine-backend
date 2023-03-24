package com.kdu.IBE.repo;

import com.kdu.IBE.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    @Query("select t from TenantTable t where t.tenant_id = ?1")
    Tenant findByTenant_id(Long tenant_id);
    
}
