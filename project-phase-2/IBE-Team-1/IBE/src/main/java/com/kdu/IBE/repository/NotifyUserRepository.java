package com.kdu.IBE.repository;

import com.kdu.IBE.entity.NotifyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotifyUserRepository extends JpaRepository<NotifyUser,Long> {
    @Query(value = "select user_email  from notify_user nu where start_date > cast (?1 as DATE) and room_type_id =?2",nativeQuery = true)
    List<String> getEmailsToNotifyUser(String date,Long roomTypeId);
}
