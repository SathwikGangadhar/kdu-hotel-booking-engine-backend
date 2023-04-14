package com.kdu.IBE.service.booking;

import com.kdu.IBE.model.recieveModel.BookingDetails;
import com.kdu.IBE.model.recieveModel.BookingModel;
import com.kdu.IBE.model.recieveModel.TravellerInfoModel;
import com.kdu.IBE.model.recieveModel.UserInfoModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IBookingService {
    public ResponseEntity<?> bookRoom(BookingModel bookingModel, BindingResult result);

}
