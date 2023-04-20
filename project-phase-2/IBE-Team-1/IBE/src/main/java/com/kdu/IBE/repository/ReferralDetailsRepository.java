package com.kdu.IBE.repository;
import com.kdu.IBE.entity.ReferralDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralDetailsRepository extends JpaRepository<ReferralDetails,Long> {
}
