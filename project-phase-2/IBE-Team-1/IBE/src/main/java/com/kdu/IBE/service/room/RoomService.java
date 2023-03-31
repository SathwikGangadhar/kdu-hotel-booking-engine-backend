package com.kdu.IBE.service.room;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.recieveModel.FilterSort;
import com.kdu.IBE.model.recieveModel.FiltersModel;
import com.kdu.IBE.model.returnDto.AvailableRoomModel;
import com.kdu.IBE.model.returnDto.AvailableRoomModelResponse;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import com.kdu.IBE.utils.RoomServiceUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
@Service
public class RoomService implements IRoomService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;
    @Autowired
    public RoomServiceUtils roomServiceUtils;

    @Override
    public ResponseEntity<?> getRoomTypes(FilterSort filterSort, BindingResult result, String propertyId, String startDate, String endDate, String skip, String take , String minNoOfRooms) {
        if(result.hasErrors()){
            throw  new ObjectNotFoundException("Request Body passed is invalid","Invalid");
        }
        Map<String, Object> requestBody = new HashMap<>();
        List<Integer> roomTypeArray=new ArrayList<>();
        Integer minNumberOfRooms=Integer.parseInt(minNoOfRooms);
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
        Integer skipValue=Integer.parseInt(skip);
        Integer startIndex=Integer.parseInt(skip);
        Integer endIndex=(startIndex+Integer.parseInt(take));
        Integer availableListCount;
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
         * getting the roomrates values from the graphQl api
         */
        String roomTypeListString=roomTypeArray.toString();
        Integer roomTypeArrayLength=roomTypeArray.size();
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
       roomServiceUtils.roomTypeRateAverageSetter(roomTypeRateMap,minNumberOfRooms );

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
}
