package com.kdu.IBE.service.room;

import com.kdu.IBE.model.requestDto.FilterSort;
import com.kdu.IBE.model.responseDto.AvailableRoomModelResponse;
import com.kdu.IBE.model.responseDto.RoomRateDetailModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IRoomService {
    ResponseEntity<AvailableRoomModelResponse> getRoomTypes(FilterSort filterSort, BindingResult result, String propertyId, String startDate, String endDate, String skip, String take, String minNoOfRooms, String minNoOfBeds, String maxCapacity);
    ResponseEntity<RoomRateDetailModel> getRoomRatePerDate(String roomTypeId, String startDate , String endDate, String tax , String surcharges, String vat, String dueNow);
 }
