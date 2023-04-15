package com.kdu.IBE.service.room;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.requestDto.FilterSort;
import com.kdu.IBE.model.responseDto.AvailableRoomModel;
import com.kdu.IBE.model.responseDto.AvailableRoomModelResponse;
import com.kdu.IBE.model.responseDto.RoomRateDetailModel;
import com.kdu.IBE.model.responseDto.RoomRateModel;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import com.kdu.IBE.utils.RoomServiceFilters;
import com.kdu.IBE.utils.RoomServiceUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RoomService implements IRoomService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;
    @Autowired
    public RoomServiceUtils roomServiceUtils;
    @Autowired
    public RoomServiceFilters roomServiceFilters;
    @Override
    public ResponseEntity<AvailableRoomModelResponse > getRoomTypes(FilterSort filterSort, BindingResult result, String propertyId, String startDate, String endDate, String skip, String take , String minNoOfRooms,String minNoOfBeds,String maxCapacity) {
        if(result.hasErrors()){
            throw  new ObjectNotFoundException("Request Body passed is invalid","Invalid");
        }
        Map<String, Object> requestBody = new HashMap<>();
        List<Integer> roomTypeArray=new ArrayList<>();
        int minNumberOfRooms=Integer.parseInt(minNoOfRooms);
        int minNumberOfBeds= Integer.parseInt(minNoOfBeds);
        int maxCapacityRequired=Integer.parseInt(maxCapacity);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDateForCount = LocalDate.parse(startDate.substring(0,10), formatter);
        LocalDate endDateForCount = LocalDate.parse(endDate.substring(0,10), formatter);
        long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount)+1;
        HashMap<Integer,Integer> roomsMap=new HashMap<>();//HashMap to find the room if it is available on the full range of date
        HashMap<Integer,Integer> roomTypesMap=new HashMap<>();//HashMap to find the room if it is available on the full range of date
        HashMap<Integer,Double> roomTypeRateMap=new HashMap<>();//HashMap to find the room if it is available on the full range of date
        List<AvailableRoomModel> availableRoomModelList=new ArrayList<>();
        List<AvailableRoomModel> finalAvailableRoomModelList=new ArrayList<>();
        AvailableRoomModelResponse availableRoomModelResponse=new AvailableRoomModelResponse();
        JsonNode jsonNode;
        int skipValue=Integer.parseInt(skip);
        int startIndex=Integer.parseInt(skip);
        int endIndex=(startIndex+Integer.parseInt(take));
        int availableListCount;
        while(true){
            requestBody.put("query",
            roomServiceUtils.getAvailableRoomDetailsQuery(startDate ,endDate,propertyId,skipValue)
            );

            skipValue+=1000000;
            jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
            JsonNode availableRoomsList=jsonNode.get("data").get("listRoomAvailabilities");
            if(availableRoomsList==null || availableRoomsList.size()==0){
                break;
            }
            /**
             * map to fetch the room count and to fetch the count of the rooms in room type
             */
            roomServiceUtils.roomCountFetcher(availableRoomsList, roomsMap,daysBetween,roomTypesMap);
        }

        /**
         * set the array if room types which satisfies the conditions
         */
        roomServiceUtils.roomTypeListSetter(roomTypesMap , minNumberOfRooms, roomTypeArray);

        /**
         * getting the room rates values from the graphQl api
         */
        String roomTypeListString=roomTypeArray.toString();
        int roomTypeArrayLength=roomTypeArray.size();
        requestBody.put("query", roomServiceUtils.getRoomRatesQuery(startDate ,endDate, roomTypeListString ,roomTypeArrayLength ,daysBetween)
        );

        jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
        JsonNode roomRateList=jsonNode.get("data").get("listRoomRateRoomTypeMappings");

        /**
         * mapping the room rates according to the room type id
         */

        roomServiceUtils.roomRateMapSetter(roomRateList,roomTypeRateMap );

        /**
         * to set the rates to the average
         */
        roomServiceUtils.roomTypeRateAverageSetter(roomTypeRateMap,daysBetween );

        /**
         * get the room type details
         */

        requestBody.put("query", roomServiceUtils.getRoomTypeQuery(roomTypeListString,roomTypeArrayLength)
        );
        jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
        JsonNode roomTypeList=jsonNode.get("data").get("listRoomTypes");

        /**
         * to set list of the dto made to store the list of the map
         */
        roomServiceUtils.roomAvailabilityListSetter( roomTypeList,roomTypesMap,roomTypeRateMap,availableRoomModelList);

        /**
         * applying filters
         */
        roomServiceUtils.getFiltersApply(filterSort.getFiltersModel(),availableRoomModelList);

        /**
         * applying sort
         */
        roomServiceUtils.getSortApply(filterSort.getSortState(),availableRoomModelList);

        /**
         * applying bed count filter and max capacity
         * it is a compulsory filter
         */
        roomServiceFilters.getBedCountAndMaxCapacityFilters(availableRoomModelList,minNumberOfBeds,maxCapacityRequired);

        /**
         * pagination in backend
         */
        availableListCount=availableRoomModelList.size();
        endIndex=Math.min(endIndex,availableListCount);
        for(int index=startIndex;index<endIndex;index++){
            finalAvailableRoomModelList.add(availableRoomModelList.get(index));
        }
        availableRoomModelResponse.setAvailableRoomModelList(finalAvailableRoomModelList);
        availableRoomModelResponse.setAvailableResponseCount(availableListCount);

        return new ResponseEntity<AvailableRoomModelResponse>(availableRoomModelResponse, HttpStatus.OK);
    }


    /**
     * to fetch the rate of the rooms across all the days needed by the user
     * @param roomTypeId
     * @param startDate
     * @param endDate
     * @return
     */
    public ResponseEntity<RoomRateDetailModel > getRoomRatePerDate(String roomTypeId, String startDate , String endDate, String tax , String surcharges, String vat, String dueNow,String numberOfRooms){

        Map<String, Object> requestBody = new HashMap<>();
        List<RoomRateModel> roomRateModelList=new ArrayList<>();
        double taxValue=Double.parseDouble(tax);
        double surchargesValue=Double.parseDouble(surcharges);
        double vatValue=Double.parseDouble(vat);
        double dueNowValue=Double.parseDouble(dueNow);
        double subTotal=0;
        double taxesAndSurchargesAmount=0;
        double vatAmount=0;
        double grandTotal=0;
        double dueNowAmount=0;
        double dueAtResortAmount=0;
        int roomCount=Integer.parseInt(numberOfRooms);
        requestBody.put("query",
                roomServiceUtils.getRoomRatePerDataQuery(roomTypeId,startDate,endDate)
        );
        /**
         * getting the graphql response
         */
        JsonNode jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
        JsonNode roomRateArray=jsonNode.get("data").get("listRoomRateRoomTypeMappings");

        /**
         * mapping the results to the DTO
         */

        for(JsonNode roomRate:roomRateArray){
            /**
             * calculating the sub total
             */
            subTotal+=roomRate.get("room_rate").get("basic_nightly_rate").asDouble();

            RoomRateModel roomRateModel=RoomRateModel.builder()
                    .basicNightlyRate(roomRate.get("room_rate").get("basic_nightly_rate").asDouble())
                    .date(roomRate.get("room_rate").get("date").toString())
                    .build();
            roomRateModelList.add(roomRateModel);
        }
        subTotal*=roomCount;

        /**
         * getting the amount of tax
         */
        taxesAndSurchargesAmount= roomServiceUtils.getTaxesAndSurchargesAmount(subTotal,taxValue,surchargesValue);
        /**
         * getting the amount of vat
         */
        vatAmount= roomServiceUtils.getVatAmount(subTotal,vatValue);
        /**
         * Grand total calculation
         */
        grandTotal=subTotal+taxesAndSurchargesAmount+vatAmount;
        /**
         * Deu Now amount and due at resort
         */
        dueNowAmount= roomServiceUtils.getDueNowAmount(grandTotal,dueNowValue);
        dueAtResortAmount=grandTotal-dueNowAmount;
        RoomRateDetailModel roomRateDetailModel=RoomRateDetailModel.builder()
                .roomRateModelList(roomRateModelList)
                .subTotal(subTotal)
                .taxesAndSurchargesAmount(taxesAndSurchargesAmount)
                .vatAmount(vatAmount)
                .grandTotal(grandTotal)
                .dueNow(dueNowAmount)
                .dueAtResort(dueAtResortAmount)
                .build();
        return new ResponseEntity<>(roomRateDetailModel,HttpStatus.OK);
    }
    }
