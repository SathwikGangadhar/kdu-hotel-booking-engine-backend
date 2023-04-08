package com.kdu.IBE.service.room;

import com.kdu.IBE.model.recieveModel.FilterSort;
import com.kdu.IBE.model.returnDto.RoomRateDetailModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IRoomService {
    ResponseEntity<?> getRoomTypes(FilterSort filterSort, BindingResult result, String propertyId, String startDate, String endDate, String skip, String take, String minNoOfRooms,String minNoOfBeds,String maxCapacity);
    ResponseEntity<RoomRateDetailModel> getRoomRatePerDate(String roomTypeId, String startDate , String endDate, String tax , String surcharges, String vat, String dueNow);
 }
