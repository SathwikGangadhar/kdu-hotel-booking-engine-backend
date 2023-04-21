package com.kdu.IBE.service.booking;

import com.kdu.IBE.constants.SenderEmail;
import com.kdu.IBE.entity.*;
import com.kdu.IBE.exception.BookingIdDoesNotExistException;
import com.kdu.IBE.exception.RoomsNotFoundException;
import com.kdu.IBE.model.requestDto.BookingModel;
import com.kdu.IBE.model.requestDto.BookingResponse;
import com.kdu.IBE.model.requestDto.NotifyUserRequestDto;
import com.kdu.IBE.model.responseDto.BookingUserInfoResponse;
import com.kdu.IBE.model.responseDto.RoomBookedModel;
import com.kdu.IBE.repository.*;
import com.kdu.IBE.service.sesService.SesService;
import com.kdu.IBE.utils.BookingUtils;
import com.kdu.IBE.utils.DateConverter;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private RoomAvailabilityRepository roomAvailabilityRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingUserInfoRepository bookingUserInfoRepository;
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;
    @Autowired
    private DateConverter dateConverter;
    @Autowired
    private BookingUtils bookingUtils;

    @Autowired
    private SesService sesService;

    @Autowired
    private NotifyUserRepository notifyUserRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Transactional
    public ResponseEntity<BookingResponse> bookRoom(BookingModel bookingModel, BindingResult result) {
        if (result.hasErrors()) {
            throw new ObjectNotFoundException("Request Body passed is invalid", "Invalid");
        }
        /**
         * Applying validation on booking details
         */
        Booking booking = new Booking();
        bookingRepository.save(booking);
        bookingModel.getUserInfoModel().setBookingId(booking.getBookingId());
        String startDateValue = bookingModel.getBookingDetailsModel().getStartDate().substring(0, 10);
        String endDateValue = bookingModel.getBookingDetailsModel().getEndDate().substring(0, 10);
        LocalDate startDateForCount = dateConverter.convertStringToDate(startDateValue);
        LocalDate endDateForCount = dateConverter.convertStringToDate(endDateValue);
        long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount) + 1;

        long numberOfDataRequired = daysBetween * bookingModel.getBookingDetailsModel().getNumberOfRooms();

        List<List<Object>> roomAvailabilityResults = roomAvailabilityRepository.getRoomAvailabilityResult(bookingModel.getBookingDetailsModel().getRoomTypeId(), startDateValue, endDateValue, numberOfDataRequired);

        long numberOfDataReceived = roomAvailabilityResults.size();

        /**
         * checking if the number of rooms that were required is present
         */
        if (numberOfDataReceived != numberOfDataRequired) {
            throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
        }

        Collection<Long> availabilityIdList = new ArrayList<>();
        List<RoomBookedModel> roomBookedList = new ArrayList<>();
        Map<Long, Boolean> isRoomPresentCheckMap = new HashMap<>();
        for (List<Object> value : roomAvailabilityResults) {
            availabilityIdList.add(Long.parseLong(value.get(0).toString()));
            if (isRoomPresentCheckMap.get(Long.parseLong(value.get(1).toString())) == null) {
                RoomBookedModel roomBookedModel = RoomBookedModel.builder()
                        .roomNumber(Long.parseLong(value.get(1).toString()))
                        .build();
                roomBookedList.add(roomBookedModel);
                isRoomPresentCheckMap.put(Long.parseLong(value.get(1).toString()), true);
            }
        }
        int updatedNumberOfRows = roomAvailabilityRepository.updateBookingIdByAvailabilityIdIn(booking.getBookingId(), availabilityIdList);
        if (updatedNumberOfRows != availabilityIdList.size()) {
            throw new RoomsNotFoundException("Oops there are no rooms present for now try again");
        }

        bookingUtils.putBookingUserInfo(bookingModel.getUserInfoModel());
        bookingUtils.putToBookingDetails(bookingModel.getBookingDetailsModel(), booking.getBookingId());


        BookingResponse bookingResponse = BookingResponse.builder()
                .bookingId(booking.getBookingId())
                .roomList(roomBookedList)
                .build();
        return new ResponseEntity<BookingResponse>(bookingResponse, HttpStatus.OK);
    }

    /**
     * to get the information of the user while booking
     *
     * @param bookingId
     * @return
     * @throws BookingIdDoesNotExistException
     */

    public ResponseEntity<BookingUserInfoResponse> getBookingUserInfo(String bookingId) throws BookingIdDoesNotExistException {
        Long bookingIdValue = Long.parseLong(bookingId);
        /**
         * to check whether the booking is active or not
         */

        Boolean isBookingIdActive=bookingRepository.findByBookingId(bookingIdValue);

        if(isBookingIdActive==false){
            throw new BookingIdDoesNotExistException("The booking id given is not present in the database");
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        final BookingUserDetails[] bookingUserDetails = {new BookingUserDetails()};
        final BookingDetails[] bookingDetails = {new BookingDetails()};
        Callable<Void> task1 = () -> {
            bookingUserDetails[0] = bookingUserInfoRepository.findByBookingIdEquals(bookingIdValue);
            return null;
        };
        executorService.submit(task1);
        Callable<Void> task2 = () -> {
            bookingDetails[0] = bookingDetailsRepository.findByBookingIdEquals(bookingIdValue);
            return null;
        };
        executorService.submit(task2);
        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // Handle the exception as needed
        }


        if (bookingUserDetails[0] == null || bookingDetails[0] == null) {
            throw new BookingIdDoesNotExistException("The booking id given is not present in the database");
        }

        BookingUserInfoResponse bookingUserInfoResponse = BookingUserInfoResponse.builder()
                .bookingId(bookingDetails[0].getBookingId())
                .startDate(bookingDetails[0].getStartDate())
                .endDate(bookingDetails[0].getEndDate())
                .dealPrice(bookingDetails[0].getDealPrice())
                .dealTitle(bookingDetails[0].getDealTitle())
                .dealDescription(bookingDetails[0].getDealDescription())
                .roomTypeId(bookingUserDetails[0].getRoomTypeId())
                .roomImage(bookingDetails[0].getRoomImage())
                .adultCount(bookingDetails[0].getAdultCount())
                .childCount(bookingDetails[0].getChildCount())
                .averagePrice(bookingDetails[0].getAveragePrice())
                .subTotal(bookingDetails[0].getSubTotal())
                .taxPrice(bookingDetails[0].getTaxPrice())
                .vatPrice(bookingDetails[0].getVatPrice())
                .dueNow(bookingDetails[0].getDueNow())
                .dueAtResort(bookingDetails[0].getDueAtResort())
                .totalAmount(bookingDetails[0].getTotalAmount())
                .travellerFirstName(bookingUserDetails[0].getTravellerFirstName())
                .travellerMiddleName(bookingUserDetails[0].getTravellerMiddleName())
                .travellerLastName(bookingUserDetails[0].getTravellerLastName())
                .travellerPhoneNumber(bookingUserDetails[0].getTravellerPhoneNumber())
                .travellerAlternatePhone(bookingUserDetails[0].getTravellerAlternatePhone())
                .travellerEmail(bookingUserDetails[0].getTravellerEmail())
                .travellerAlternateEmail(bookingUserDetails[0].getTravellerAlternateEmail())
                .billingFirstName(bookingUserDetails[0].getBillingFirstName())
                .billingMiddleName(bookingUserDetails[0].getBillingMiddleName())
                .billingLastName(bookingUserDetails[0].getBillingLastName())
                .mailingAddress(bookingUserDetails[0].getMailingAddress())
                .alternateMailingAddress(bookingUserDetails[0].getAlternateMailingAddress())
                .billingEmail(bookingUserDetails[0].getBillingEmail())
                .billingAlternateEmail(bookingUserDetails[0].getBillingAlternateEmail())
                .billingPhoneNumber(bookingUserDetails[0].getBillingPhoneNumber())
                .billingAlternatePhone(bookingUserDetails[0].getBillingAlternatePhone())
                .country(bookingUserDetails[0].getCountry())
                .city(bookingUserDetails[0].getCity())
                .state(bookingUserDetails[0].getState())
                .zip(bookingUserDetails[0].getZip())
                .cardNumber(bookingUserDetails[0].getCardNumber())
                .expiryMonth(bookingUserDetails[0].getExpiryMonth())
                .expiryYear(bookingUserDetails[0].getExpiryYear())
                .isSendOffers(bookingUserDetails[0].getIsSendOffers())
                .roomTypeId(bookingUserDetails[0].getRoomTypeId())
                .build();
        return new ResponseEntity<BookingUserInfoResponse>(bookingUserInfoResponse, HttpStatus.OK);
    }

    public ResponseEntity<String> sendBookingEmail(String recipient, String image, String bookingId, String roomType, String startDate, String endDate) {
        sesService.sendBookingEmail(SenderEmail.SENDER_EMAIL, recipient, image, bookingId, roomType, startDate, endDate);
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }

    public ResponseEntity<String> putNotifyUser(NotifyUserRequestDto notifyUserRequestDto, BindingResult result) {
        if (result.hasErrors()) {
            throw new ObjectNotFoundException("Request body passes is invalid", "Invalid");
        }
        String startDateValue = notifyUserRequestDto.getStartDate().substring(0, 10);
        LocalDate startDate = dateConverter.convertStringToDate(startDateValue);
        RoomType roomType = roomTypeRepository.findById(notifyUserRequestDto.getRoomTypeId())
                .orElseThrow(() -> new ObjectNotFoundException("Room Id given is invalid", "Exception"));
        NotifyUser notifyUser = NotifyUser.builder()
                .userEmail(notifyUserRequestDto.getUserEmail())
                .startDate(startDate)
                .roomTypeId(roomType)
                .build();
        notifyUserRepository.save(notifyUser);
        return new ResponseEntity<>("notify user details stored successfully", HttpStatus.OK);
    }
}