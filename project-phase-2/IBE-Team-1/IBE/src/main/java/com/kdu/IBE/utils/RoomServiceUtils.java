package com.kdu.IBE.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.recieveModel.FiltersModel;
import com.kdu.IBE.model.returnDto.AvailableRoomModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomServiceUtils {
    @Autowired
    public RoomServiceFilters roomServiceFilters;

    public String getAvailableRoomDetailsQuery(String startDate ,String endDate, String propertyId,Integer skipValue){
        return "query MyQuery {\n" +
                "  listRoomAvailabilities(where: {AND: {booking_id: {equals: 0}, date: {gte: \""+startDate+"\", lte: \""+endDate+"\"}, property_id: {equals: "+propertyId+"}}},skip: "+Integer.toString(skipValue)+", take: 1000000) {\n" +
                "    room_id\n" +
                "    room {\n" +
                "      room_type_id\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public String getRoomRatesQuery(String startDate ,String endDate, String roomTypeListString ,Integer roomTypeArrayLength ,long daysBetween){
        return "query MyQuery3 {\n" +
                "  listRoomRateRoomTypeMappings(where: {room_rate: {date: {gte: \""+startDate+"\", lte: \""+endDate+"\"}}, room_type: {room_type_id: {in: "+roomTypeListString+"}}}, skip: 0, take: "+Long.toString(roomTypeArrayLength*daysBetween)+") {\n" +
                "    room_type_id\n" +
                "    room_rate {\n" +
                "      basic_nightly_rate\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    public String getRoomTypeQuery(String roomTypeListString,Integer roomTypeArrayLength){
        return "query MyQuery2 {\n" +
                "  listRoomTypes(where: {room_type_id: {in: "+roomTypeListString+"}}, skip: 0, take: "+Integer.toString( roomTypeArrayLength)+") {\n" +
                "    room_type_id\n" +
                "    room_type_name\n" +
                "    single_bed\n" +
                "    max_capacity\n" +
                "    double_bed\n" +
                "    area_in_square_feet\n" +
                "  }\n" +
                "}";
    }

    /**
     * map to fetch the room count and to fetch the count of the rooms in room type
     */
     public void roomCountFetcher(JsonNode availableRoomsList, HashMap<Integer,Integer> roomsMap,long daysBetween,HashMap<Integer,Integer> roomTypesMap){
         for(JsonNode availableRoom :availableRoomsList){
             Integer roomId=availableRoom.get("room_id").asInt();
             Integer roomsCount=roomsMap.get(roomId);
             if(roomsCount==null){
                 roomsMap.put(roomId,1);
             }
             else{
                 roomsMap.put(roomId,roomsCount+1);
             }
             if(roomsMap.get(roomId)==daysBetween){
                 Integer roomTypeId=availableRoom.get("room").get("room_type_id").asInt();
                 Integer roomTypeCount=roomTypesMap.get(roomTypeId);
                 if(roomTypeCount==null){
                     roomTypesMap.put(roomTypeId,1);
                 }
                 else{
                     roomTypesMap.put(roomTypeId,roomTypeCount+1);
                 }
             }
         }
     }

     public void roomTypeListSetter(HashMap<Integer,Integer> roomTypesMap , Integer minNumberOfRooms, List<Integer> roomTypeArray){
         for (Map.Entry<Integer,Integer> entry : roomTypesMap.entrySet()){
             if(entry.getValue()>=minNumberOfRooms){
                 roomTypeArray.add(entry.getKey());
             }
         }
     }

     public void roomRateMapSetter(JsonNode roomRateList, HashMap<Integer,Double> roomTypeRateMap ){

         for(JsonNode roomRate:roomRateList){
             Double roomRates=roomRate.get("room_rate").get("basic_nightly_rate").asDouble();
             if(roomRates==null){
                 roomTypeRateMap.put(roomRate.get("room_type_id").asInt(),roomRate.get("room_rate").get("basic_nightly_rate").asDouble());
             }
             else{
                 roomTypeRateMap.put(roomRate.get("room_type_id").asInt(),roomRates+roomRate.get("room_rate").get("basic_nightly_rate").asDouble());
             }
         }
     }


     public void roomTypeRateAverageSetter( HashMap<Integer,Double> roomTypeRateMap,Integer minNumberOfRooms ){
         for (Map.Entry<Integer,Double> entry : roomTypeRateMap.entrySet()){
             roomTypeRateMap.put(entry.getKey(),entry.getValue()/minNumberOfRooms);
         }
     }

     public void roomAvailabilityListSetter(JsonNode roomTypeList,HashMap<Integer,Integer> roomTypesMap,HashMap<Integer,Double> roomTypeRateMap,List<AvailableRoomModel> availableRoomModelList){
         for(JsonNode roomType:roomTypeList){
             AvailableRoomModel availableRoomModel= AvailableRoomModel.builder()
                     .roomTypeId(roomType.get("room_type_id").asLong())
                     .roomTypeName(roomType.get("room_type_name").toString())
                     .singleBed(roomType.get("single_bed").asLong())
                     .maxCapacity(roomType.get("max_capacity").asLong())
                     .doubleBed(roomType.get("double_bed").asLong())
                     .areaInSquareFeet(roomType.get("area_in_square_feet").asLong())
                     .availableRoomCount(roomTypesMap.get(roomType.get("room_type_id").asInt()))
                     .roomRate(roomTypeRateMap.get(roomType.get("room_type_id").asInt()))
                     .build();
             availableRoomModelList.add(availableRoomModel);
         }

     }

    /**
     *
     * @param filters
     * @param availableRoomModelList
     */
     public void getFiltersApply(FiltersModel filters,List<AvailableRoomModel> availableRoomModelList){
         if(filters.getRoomName().getIsNeeded()>0){
             roomServiceFilters.getRoomNameFilter(availableRoomModelList,filters.getRoomName().getValue());
         }
         if(filters.getBedType().getIsNeeded()>0){
             roomServiceFilters.getBedTypeFilter(availableRoomModelList,filters.getBedType().getValue());
         }
         if(filters.getMaxCapacity().getIsNeeded()>0) {
             roomServiceFilters.getMaxCapacityFilter(availableRoomModelList, filters.getMaxCapacity().getValue());
         }
         if(filters.getArea().getIsNeeded()>0){
             roomServiceFilters.getAreaFilter(availableRoomModelList,filters.getArea().getValue());
         }
         if(filters.getRate().getIsNeeded()>0){
             roomServiceFilters.getRateFilters(availableRoomModelList,filters.getRate().getValue());
         }
         if(filters.getBedCounts().getIsNeeded()>0){
             roomServiceFilters.getBedCountFilters(availableRoomModelList,filters.getBedCounts().getValue());
         }
     }
}
