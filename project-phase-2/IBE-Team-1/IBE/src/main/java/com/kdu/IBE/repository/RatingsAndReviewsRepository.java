package com.kdu.IBE.repository;

import com.kdu.IBE.entity.RatingsAndReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;

@Repository
public interface RatingsAndReviewsRepository extends JpaRepository<RatingsAndReviews, Long> {
    @Transactional
    @Modifying
    @Query("update RatingsAndReviews r set r.ratings = ?1, r.reviews = ?2 where r.id = ?3")
    int updateRatingsAndReviewsByIdEquals(Double ratings, String reviews, Long id);

    @Transactional
    @Modifying
    @Query("update RatingsAndReviews r set r.ratings = ?1, r.reviews = ?2, r.booking.bookingId = ?3 where r.id = ?4")
    int updateRatingsAndReviewsAndBookingById(Double ratings, String reviews, Long bookingId, Long id);

    @Query(value = "SELECT COUNT(id) AS count, AVG(ratings) AS averageRating FROM ratings_and_reviews WHERE room_type_id = ?1 AND ratings > 0", nativeQuery = true)
    Map<String, Object> getCountAndAverageRatingByRoomTypeId(Long roomTypeId);

    @Query(value = "SELECT  * FROM ratings_and_reviews WHERE id = ?1", nativeQuery = true)
    Map<String, Object> getRatingIdAndReview(Long roomTypeId);

    @Override
    boolean existsById(Long aLong);

    @Query(value = "select room_type_id as roomTypeId, count(id) as count , avg(ratings) as averageRating from ratings_and_reviews rar where ratings >0 group by room_type_id having rar.room_type_id in ?1",nativeQuery = true)
    List<Map<String, Object>> getCountAndAverageRatingListByRoomTypeId(List<Long> roomTypeIdList);

    /**
     * getting the average ratings in list
     */


}
