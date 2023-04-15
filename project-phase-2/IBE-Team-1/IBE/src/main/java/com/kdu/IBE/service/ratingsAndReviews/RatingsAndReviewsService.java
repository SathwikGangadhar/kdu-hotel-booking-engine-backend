package com.kdu.IBE.service.ratingsAndReviews;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.entity.RatingsAndReviews;
import com.kdu.IBE.entity.RoomType;
import com.kdu.IBE.model.requestDto.RatingsAndReviewsReceiveModel;
import com.kdu.IBE.model.responseDto.RoomRatingReturnModel;
import com.kdu.IBE.repository.BookingUserInfoRepository;
import com.kdu.IBE.repository.RatingsAndReviewsRepository;
import com.kdu.IBE.repository.RoomTypeRepository;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import com.kdu.IBE.service.sesService.SesService;
import com.kdu.IBE.utils.EmailValidator;
import com.kdu.IBE.utils.RatingsAndReviewUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.*;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RatingsAndReviewsService implements IRatingsAndReviewsService {
    @Autowired
    private SesService sesService;
    @Autowired
    private RatingsAndReviewsRepository ratingsAndReviewsRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    public GraphQlWebClient graphQlWebClient;
    @Autowired
    public RatingsAndReviewUtils ratingsAndReviewUtils;
    @Autowired
    public BookingUserInfoRepository bookingUserInfoRepository;
    public ResponseEntity<String> sendEmail(String receiverEmail, String roomTypeId) {
        /**
         * validating the email
         */
        boolean isEmailValid =emailValidator.validateEmail(receiverEmail);
        if(!isEmailValid){
            return new ResponseEntity<>("Email Passed is invalid",HttpStatus.BAD_REQUEST);
        }

        /**
         *  getting the room type with id
         */
        RoomType roomType = roomTypeRepository.findById(Long.parseLong(roomTypeId))
                .orElseThrow(() -> new ObjectNotFoundException("Room type id given is invalid", "Exception"));
        /**
         * building the entity to post request with just id and room type id
         */
        RatingsAndReviews ratingsAndReviews = RatingsAndReviews.builder()
                .roomType(roomType)
                .ratings(0d)
                .reviews("")
                .build();

        ratingsAndReviewsRepository.save(ratingsAndReviews);

        /**
         * getting the rating id to send in the param of the url sent in the email
         */

        Long ratingAndReviewsId = ratingsAndReviews.getId();

//        public void sesMessageSender(String sender,String recipient,Long ratingsAndReviewsId) throws IOException {
        String sender = "sathwik.shetty@kickdrumtech.com";
        try {
            sesService.sesMessageSender(sender, receiverEmail, ratingAndReviewsId.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<String>("Email sent successfully", HttpStatus.OK);
    }

    /**
     * to check in the form whether it is submitted or not present
     * @param ratingId rating id
     * @return validity of rating presence
     */
    public ResponseEntity<Integer> checkIfRatingsIsValid(String ratingId) {
        if (!ratingsAndReviewsRepository.existsById(Long.parseLong(ratingId))) {
            return new ResponseEntity<Integer>(0, HttpStatus.OK);
        } else {
            Map<String, Object> ratingMap = ratingsAndReviewsRepository.getRatingIdAndReview(Long.parseLong(ratingId));
            System.out.println("rating till 1 =");
            if (!ratingMap.get("ratings").toString().equals("0.0")) {
                return new ResponseEntity<Integer>(1, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Integer>(2, HttpStatus.OK);
    }

    /**
     * to add ratings and review in the database
     * @param ratingsAndReviewsReceiveModel model which represents  the data is passed
     * @return required message
     */
    public ResponseEntity<String> putRatingsAndReviews(RatingsAndReviewsReceiveModel ratingsAndReviewsReceiveModel, BindingResult result) {
        if (result.hasErrors()) {
            throw new ObjectNotFoundException("Object passed in request body is invalid", "Error");
        }
        ratingsAndReviewsRepository.updateRatingsAndReviewsByIdEquals(ratingsAndReviewsReceiveModel.getRatings(), ratingsAndReviewsReceiveModel.getReviews(), ratingsAndReviewsReceiveModel.getRoomTypeId());

        return new ResponseEntity<String>("ratings added successfully", HttpStatus.OK);
    }

    /**
     * to get the ratings and reviews
     * @param roomTypeId (id of which the ratings are to be fetched)
     * @return ratings
     */
    public ResponseEntity<?> getRatingsAndReview(String roomTypeId) {
        Map<String, Object> roomRatingReturnModelMap = ratingsAndReviewsRepository.getCountAndAverageRatingByRoomTypeId(Long.parseLong(roomTypeId));
        RoomRatingReturnModel roomRatingReturnModel = new RoomRatingReturnModel();
        if(roomRatingReturnModelMap.get("count")==null || roomRatingReturnModelMap.get("averageRating")==null){
            roomRatingReturnModel.setCount(0l);
            roomRatingReturnModel.setAverageRating(0d);
            return new ResponseEntity<RoomRatingReturnModel>(roomRatingReturnModel,HttpStatus.OK);
        }
        roomRatingReturnModel.setCount(Long.parseLong(roomRatingReturnModelMap.get("count").toString()));
        roomRatingReturnModel.setAverageRating(Double.parseDouble(roomRatingReturnModelMap.get("averageRating").toString()));
        return new ResponseEntity<RoomRatingReturnModel>(roomRatingReturnModel, HttpStatus.OK);
    }
    @Scheduled(cron = "0 0 12 * * ?") // runs every day at 12pm
    public ResponseEntity<?> sendMailCheckedOutGuest(){
        Map<String, Object> requestBody = new HashMap<>();
        long skip=0;
        int take=10000000;
        /**
         * getting the current date
         */
        String formattedDate = ratingsAndReviewUtils.getCurrentDate();
        while (true){
            /**
             * making the graphQl api call to fetch the booking id of all the bookings that are not submitted ratings
             */
            requestBody.put("query", ratingsAndReviewUtils.getRatingBookingIdQuery(Long.toString(skip),formattedDate));
            skip+=take;
            JsonNode jsonNode;
            jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
            /**
             * getting the list of booking id
             */
            Collection<Long> bookingIdList=new ArrayList<>();
            if(jsonNode.get("data").get("listBookings").size()==0){
                break;
            }
            for(JsonNode booking:jsonNode.get("data").get("listBookings")){
                bookingIdList.add(booking.get("booking_id").asLong());
            }
            List<List<String>> reviewSenderList=bookingUserInfoRepository.findByBookingIdIn(bookingIdList);
            System.out.println("list = "+reviewSenderList);
            /**
             * implemented the task of sending the email to be run in treads so that all the mails to be sent at a time without waiting for the other
             */
            ExecutorService executorService = Executors.newFixedThreadPool(10000);
            for (List<String> reviewInformation : reviewSenderList) {
                Callable<Void> task = () -> {
                    this.sendEmail(reviewInformation.get(0), reviewInformation.get(1));
                    return null;
                };
                executorService.submit(task);
            }
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                // Handle the exception as needed
            }
        }
        return new ResponseEntity<>("Emails sent successfully",HttpStatus.OK);
    }
}
