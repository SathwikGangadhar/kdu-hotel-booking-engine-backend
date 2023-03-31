package com.kdu.IBE.service.room;

import com.kdu.IBE.model.recieveModel.FilterSort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface IRoomService {
    ResponseEntity<?> getRoomTypes(FilterSort filterSort, BindingResult result, String propertyId, String startDate, String endDate, String skip, String take, String min);

    }
