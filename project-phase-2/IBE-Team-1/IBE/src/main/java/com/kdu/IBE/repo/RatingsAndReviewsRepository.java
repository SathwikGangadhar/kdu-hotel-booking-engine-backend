package com.kdu.IBE.repo;

import com.kdu.IBE.entity.RatingsAndReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface RatingsAndReviewsRepository extends JpaRepository<RatingsAndReviews,Long> {
    @Query(value = "SELECT COUNT(id) AS count, AVG(ratings) AS averageRating FROM ratings_and_reviews WHERE room_type_id = ?1", nativeQuery = true)
    Map<String, Object> getCountAndAverageRatingByRoomTypeId(Long roomTypeId);
}
