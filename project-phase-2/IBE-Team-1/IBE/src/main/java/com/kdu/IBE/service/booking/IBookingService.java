package com.kdu.IBE.service.booking;

import com.kdu.IBE.model.requestDto.BookingModel;
import com.kdu.IBE.model.requestDto.BookingResponse;
import com.kdu.IBE.model.responseDto.RoomBookedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IBookingService {
    public ResponseEntity<BookingResponse> bookRoom(BookingModel bookingModel, BindingResult result);

}
