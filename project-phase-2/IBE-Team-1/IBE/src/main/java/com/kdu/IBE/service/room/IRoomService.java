package com.kdu.IBE.service.room;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.recieveModel.FilterSort;
import com.kdu.IBE.model.returnDto.RoomRateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public interface IRoomService {
    ResponseEntity<?> getRoomTypes(FilterSort filterSort, BindingResult result, String propertyId, String startDate, String endDate, String skip, String take, String min);
     ResponseEntity<List<RoomRateModel>> getRoomRatePerDate(String roomTypeId, String startDate , String endDate);
    }
