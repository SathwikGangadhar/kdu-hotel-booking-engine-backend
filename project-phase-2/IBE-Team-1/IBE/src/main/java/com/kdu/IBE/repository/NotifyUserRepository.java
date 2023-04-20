package com.kdu.IBE.repository;

import com.kdu.IBE.entity.NotifyUser;
import com.kdu.IBE.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotifyUserRepository extends JpaRepository<NotifyUser,Long> {
    @Query(value = "select user_email from notify_user nu where cast (?1 as DATE) <= nu .end_date  and cast (?2 as DATE)>=nu.start_date and nu.room_type_id=?3 and nu.required_room_count<=(\n" +
            "SELECT COUNT(room_id) FROM (\n" +
            "    select ra.room_id from room_availability ra \n" +
            "    INNER JOIN room r ON ra.room_id = r.room_id \n" +
            "    where r.room_type_id =?3 and \n" +
            "    ra.booking_id =0\n" +
            "    AND \"date\" >= CAST(?1 AS DATE) \n" +
            "    AND \"date\" <= CAST(?2 AS DATE)  \n" +
            "    group by ra.room_id \n" +
            "    having count(*)>=?4\n" +
            ") AS subquery); ",nativeQuery = true)
    List<String> getEmailsToNotifyUser(String startDate,String endDate,Long roomTypeId,long numberOfDays);

    @Transactional
    @Modifying
    @Query("delete from NotifyUser n where n.userEmail = ?1 and n.roomTypeId.roomTypeId = ?2")
    int deleteByUserEmailAndRoomTypeId(String userEmail, Long roomTypeId);









}
