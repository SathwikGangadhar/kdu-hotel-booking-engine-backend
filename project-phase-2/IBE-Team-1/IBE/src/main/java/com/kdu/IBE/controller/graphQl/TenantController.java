package com.kdu.IBE.controller.graphQl;

import com.kdu.IBE.constants.EndPointConstants;
import com.kdu.IBE.model.recieveModel.FilterSort;
import com.kdu.IBE.service.constumeDeal.ICostumeDealService;
import com.kdu.IBE.service.room.IRoomService;
import com.kdu.IBE.service.tenant.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(EndPointConstants.TENANT_REQUEST_MAPPING)
public class TenantController {
    @Autowired
    ITenantService tenantService;
    @Autowired
    public IRoomService roomService;

    @Autowired
    ICostumeDealService costumeDealService;
    @GetMapping(EndPointConstants.GET_TENANT_PROPERTIES)
    ResponseEntity<?> getTenantProperties(@RequestParam(name = "tenant_id") String tenantId) {
        return tenantService.getTenantProperties(tenantId);
    }
    @PostMapping(EndPointConstants.GET_ROOMS)
    ResponseEntity<?> getRoomTypes(@Valid @RequestBody FilterSort filterSort, BindingResult result, @RequestParam(name="property_id") String propertyId, @RequestParam(name="start_date") String startDate , @RequestParam(name="end_date") String endDate, @RequestParam(name="skip")String skip, @RequestParam(name="take")String take , @RequestParam(name="min_no_of_rooms") String minNoOfRooms,@RequestParam(name="min_no_of_beds") String minNoOfBeds,@RequestParam(name="max_capacity") String maxCapacity){
        return roomService.getRoomTypes(filterSort,result,propertyId, startDate , endDate, skip, take, minNoOfRooms,minNoOfBeds,maxCapacity);
    }

    @GetMapping(EndPointConstants.GET_PROMO_CODE_DETAILS)
    ResponseEntity<?> getPromoCodeDetails(@RequestParam(name="promo_code") String promoCode ,@RequestParam(name = "room_type") String roomType) {
        return costumeDealService.getPromoCodeDetails(promoCode, roomType);
    }

    @GetMapping(EndPointConstants.GET_ROOM_RATE_PER_DATE)
    ResponseEntity<?> getRoomRatePerDate(@RequestParam(name="room_type_id") String roomTypeId, @RequestParam(name="start_date") String startDate , @RequestParam(name="end_date") String endDate){
        return roomService.getRoomRatePerDate(roomTypeId,startDate,endDate);
    }
}


