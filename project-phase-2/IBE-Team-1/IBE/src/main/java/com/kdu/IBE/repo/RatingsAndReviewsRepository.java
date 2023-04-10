package com.kdu.IBE.repo;

import com.kdu.IBE.entity.RatingsAndReviews;
import com.kdu.IBE.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Repository
public interface RatingsAndReviewsRepository extends JpaRepository<RatingsAndReviews, Long> {
    @Transactional
    @Modifying
    @Query("update RatingsAndReviews r set r.ratings = ?1, r.reviews = ?2 where r.id = ?3")
    int updateRatingsAndReviewsByIdEquals(Double ratings, String reviews, Long id);

    @Query(value = "SELECT COUNT(id) AS count, AVG(ratings) AS averageRating FROM ratings_and_reviews WHERE room_type_id = ?1", nativeQuery = true)
    Map<String, Object> getCountAndAverageRatingByRoomTypeId(Long roomTypeId);

    @Query(value = "SELECT  * FROM ratings_and_reviews WHERE id = ?1", nativeQuery = true)
    Map<String, Object> getRatingIdAndReview(Long roomTypeId);

    @Override
    boolean existsById(Long aLong);
}
