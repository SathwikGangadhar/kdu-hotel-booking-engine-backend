package com.kdu.IBE.repository;

import com.kdu.IBE.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferralRepository extends JpaRepository<Referral,Long> {
    @Query("select (count(r) > 0) from Referral r where r.referralCode = ?1")
    boolean existsByReferralCode(String referralCode);
}
