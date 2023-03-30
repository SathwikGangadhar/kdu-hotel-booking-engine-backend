package com.kdu.IBE.controller.graphQl;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.service.room.IRoomService;
import com.kdu.IBE.service.tenant.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(EndPointConstants.TENANT_REQUEST_MAPPING)
public class TenantController {
    @Autowired
    ITenantService tenantService;
    @Autowired
    public IRoomService roomService;
    @GetMapping(EndPointConstants.GET_TENANT_PROPERTIES)
    ResponseEntity<?> getTenantProperties(@RequestParam(name = "tenant_id") String tenantId) {
        return tenantService.getTenantProperties(tenantId);
    }
    @GetMapping(EndPointConstants.GET_ROOMS)
    ResponseEntity<?> getRoomTypes(@RequestParam(name="property_id") String propertyId,@RequestParam(name="start_date") String startDate ,@RequestParam(name="end_date") String endDate, @RequestParam(name="skip")String skip,@RequestParam(name="take")String take ,@RequestParam(name="min_no_of_rooms") String minNoOfRooms){
        return roomService.getRoomTypes(propertyId, startDate , endDate, skip, take, minNoOfRooms);
    }

}