package com.kdu.IBE.service.ratingsAndReviews;

import com.kdu.IBE.entity.RatingsAndReviews;
import com.kdu.IBE.entity.RoomType;
import com.kdu.IBE.model.recieveModel.RatingsAndReviewsReceiveModel;
import com.kdu.IBE.model.returnDto.RoomRatingReturnModel;
import com.kdu.IBE.repo.RatingsAndReviewsRepository;
import com.kdu.IBE.repo.RoomTypeRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Optional;

@Service
public class RatingsAndReviewsService implements IRatingsAndReviewsService{
    @Autowired
    private RatingsAndReviewsRepository ratingsAndReviewsRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    public ResponseEntity<String> putRatingsAndReviews(RatingsAndReviewsReceiveModel ratingsAndReviewsReceiveModel, BindingResult result){
        if(result.hasErrors()){
            throw new ObjectNotFoundException("Object passed in request body is invalid","Error");
        }

        RoomType roomType = roomTypeRepository.findById(ratingsAndReviewsReceiveModel.getRoomTypeId())
                .orElseThrow(() -> new ObjectNotFoundException("", "Exception"));
        RatingsAndReviews ratingsAndReviews=RatingsAndReviews.builder()
                .roomType(roomType)
                .ratings(ratingsAndReviewsReceiveModel.getRatings())
                .reviews(ratingsAndReviewsReceiveModel.getReviews())
                .build();
        ratingsAndReviewsRepository.save(ratingsAndReviews);
        return new ResponseEntity<String>("ratings added successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> getRatingsAndReview(String roomTypeId) {
        Map<String, Object> roomRatingReturnModelMap=ratingsAndReviewsRepository.getCountAndAverageRatingByRoomTypeId(Long.parseLong(roomTypeId));
        RoomRatingReturnModel roomRatingReturnModel=new RoomRatingReturnModel();
        roomRatingReturnModel.setCount(Long.parseLong(roomRatingReturnModelMap.get("count").toString()));
        roomRatingReturnModel.setAverageRating(Double.parseDouble(roomRatingReturnModelMap.get("averageRating").toString()));
        return new ResponseEntity<RoomRatingReturnModel >(roomRatingReturnModel,HttpStatus.OK);
    }
    }
