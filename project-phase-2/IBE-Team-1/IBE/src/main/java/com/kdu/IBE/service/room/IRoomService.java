package com.kdu.IBE.service.room;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IRoomService {
    public ResponseEntity<?> getRoomTypes(String propertyId, String startDate, String endDate, String skip, String take);
    public Long getRoomCount(String roomTypeId);
}
