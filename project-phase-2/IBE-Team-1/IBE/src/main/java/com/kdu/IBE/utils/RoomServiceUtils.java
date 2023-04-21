package com.kdu.IBE.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.requestDto.FiltersModel;
import com.kdu.IBE.model.responseDto.AvailableRoomModel;
import com.kdu.IBE.model.responseDto.RoomRateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoomServiceUtils {
    @Autowired
    public RoomServiceFilters roomServiceFilters;

    @Autowired
    public RoomServiceSort roomServiceSort;

    /**
     * @param startDate
     * @param endDate
     * @param propertyId
     * @param skipValue
     * @return
     */
    public String getAvailableRoomDetailsQuery(String startDate, String endDate, String propertyId, Integer skipValue) {
        return "query MyQuery {\n" +
                "  listRoomAvailabilities(where: {AND: {booking_id: {equals: 0}, date: {gte: \"" + startDate + "\", lte: \"" + endDate + "\"}, property_id: {equals: " + propertyId + "}}},skip: " + Integer.toString(skipValue) + ", take: 1000000) {\n" +
                "    room_id\n" +
                "    room {\n" +
                "      room_type_id\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    /**
     * @param startDate
     * @param endDate
     * @param roomTypeListString
     * @param roomTypeArrayLength
     * @param daysBetween
     * @return
     */
    public String getRoomRatesQuery(String startDate, String endDate, String roomTypeListString, Integer roomTypeArrayLength, long daysBetween) {
        return "query MyQuery3 {\n" +
                "  listRoomRateRoomTypeMappings(where: {room_rate: {date: {gte: \"" + startDate + "\", lte: \"" + endDate + "\"}}, room_type: {room_type_id: {in: " + roomTypeListString + "}}}, skip: 0, take: " + Long.toString(roomTypeArrayLength * daysBetween) + ") {\n" +
                "    room_type_id\n" +
                "    room_rate {\n" +
                "      basic_nightly_rate\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    /**
     * @param roomTypeListString
     * @param roomTypeArrayLength
     * @return
     */
    public String getRoomTypeQuery(String roomTypeListString, Integer roomTypeArrayLength) {
        return "query MyQuery2 {\n" +
                "  listRoomTypes(where: {room_type_id: {in: " + roomTypeListString + "}}, skip: 0, take: " + Integer.toString(roomTypeArrayLength) + ") {\n" +
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
     * @param roomType
     * @param startDate
     * @param endDate
     * @return
     */
    public String getRoomRatePerDataQuery(Long roomType, String startDate, String endDate) {
        /**
         * calculating the count of the days of stay to apply it to the take of the graph ql query
         */
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDateForCount = LocalDate.parse(startDate.substring(0, 10), formatter);
        LocalDate endDateForCount = LocalDate.parse(endDate.substring(0, 10), formatter);
        long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount) + 1;
        String take = Long.toString(daysBetween);
        return "query MyQuery2 {\n" +
                "  listRoomRateRoomTypeMappings(where: {room_rate: {date: {gte: \"" + startDate +"T00:00:00.000Z"+ "\", lte: \"" + endDate +"T00:00:00.000Z"+ "\"}}, room_type: {room_type_id: {equals: " + roomType.toString() + "}}}, skip: 0, take: " + take + ") {\n" +
                "    room_rate {\n" +
                "      basic_nightly_rate\n" +
                "      date\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    /**
     * @param availableRoomsList
     * @param roomsMap
     * @param daysBetween
     * @param roomTypesMap
     */
    public void roomCountFetcher(JsonNode availableRoomsList, HashMap<Integer, Integer> roomsMap, long daysBetween, HashMap<Integer, Integer> roomTypesMap) {
        for (JsonNode availableRoom : availableRoomsList) {
            Integer roomId = availableRoom.get("room_id").asInt();
            Integer roomsCount = roomsMap.get(roomId);
            if (roomsCount == null) {
                roomsMap.put(roomId, 1);
            } else {
                roomsMap.put(roomId, roomsCount + 1);
            }
            if (roomsMap.get(roomId) == daysBetween) {
                Integer roomTypeId = availableRoom.get("room").get("room_type_id").asInt();
                Integer roomTypeCount = roomTypesMap.get(roomTypeId);
                if (roomTypeCount == null) {
                    roomTypesMap.put(roomTypeId, 1);
                } else {
                    roomTypesMap.put(roomTypeId, roomTypeCount + 1);
                }
            }
        }
    }

    /**
     * @param roomTypesMap
     * @param minNumberOfRooms
     * @param roomTypeArray
     */
    public void roomTypeListSetter(HashMap<Integer, Integer> roomTypesMap, Integer minNumberOfRooms, List<Integer> roomTypeArray) {
        for (Map.Entry<Integer, Integer> entry : roomTypesMap.entrySet()) {
            if (entry.getValue() >= minNumberOfRooms) {
                roomTypeArray.add(entry.getKey());
            }
        }
    }

    /**
     * @param roomRateList
     * @param roomTypeRateMap
     */

    public void roomRateMapSetter(JsonNode roomRateList, HashMap<Integer, Double> roomTypeRateMap) {

        for (JsonNode roomRate : roomRateList) {
            Double roomRates = roomTypeRateMap.get(roomRate.get("room_type_id").asInt());
            if (roomRates == null) {
                roomTypeRateMap.put(roomRate.get("room_type_id").asInt(), roomRate.get("room_rate").get("basic_nightly_rate").asDouble());
            } else {
                roomTypeRateMap.put(roomRate.get("room_type_id").asInt(), roomRates + roomRate.get("room_rate").get("basic_nightly_rate").asDouble());
            }
        }
    }


    /**
     * @param roomTypeRateMap
     * @param daysBetween
     */
    public void roomTypeRateAverageSetter(HashMap<Integer, Double> roomTypeRateMap, long daysBetween) {
        for (Map.Entry<Integer, Double> entry : roomTypeRateMap.entrySet()) {
            roomTypeRateMap.put(entry.getKey(), entry.getValue() / daysBetween);
        }
    }

    /**
     * @param roomTypeList
     * @param roomTypesMap
     * @param roomTypeRateMap
     * @param availableRoomModelList
     */
    public void roomAvailabilityListSetter(JsonNode roomTypeList, HashMap<Integer, Integer> roomTypesMap, HashMap<Integer, Double> roomTypeRateMap, List<AvailableRoomModel> availableRoomModelList) {
        for (JsonNode roomType : roomTypeList) {
            AvailableRoomModel availableRoomModel = AvailableRoomModel.builder()
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
     * @param filters
     * @param availableRoomModelList
     */
    public void getFiltersApply(FiltersModel filters, List<AvailableRoomModel> availableRoomModelList) {
        if (filters.getRoomName().getIsNeeded() > 0) {
            roomServiceFilters.getRoomNameFilter(availableRoomModelList, filters.getRoomName().getValue());
        }
        if (filters.getBedType().getIsNeeded() > 0) {
            roomServiceFilters.getBedTypeFilter(availableRoomModelList, filters.getBedType().getValue());
        }
        if (filters.getMaxCapacity().getIsNeeded() > 0) {
            roomServiceFilters.getMaxCapacityFilter(availableRoomModelList, filters.getMaxCapacity().getValue());
        }
        if (filters.getArea().getIsNeeded() > 0) {
            roomServiceFilters.getAreaFilter(availableRoomModelList, filters.getArea().getValue());
        }
        if (filters.getRate().getIsNeeded() > 0) {
            roomServiceFilters.getRateFilters(availableRoomModelList, filters.getRate().getValue());
        }
    }

    /**
     * @param sortState
     * @param availableRoomModelList
     */
    public void getSortApply(Integer sortState, List<AvailableRoomModel> availableRoomModelList) {
        switch (Math.abs(sortState)) {
            case 1:
                roomServiceSort.sortAvailableListOnRate(availableRoomModelList, sortState);
                break;
            case 2:
                roomServiceSort.sortAvailableListOnArea(availableRoomModelList, sortState);
                break;
            default:
                // code block
        }
    }

    /**
     * @param subTotal
     * @param taxValue
     * @param surchargesValue
     * @return
     */
    public double getTaxesAndSurchargesAmount(double subTotal, double taxValue, double surchargesValue) {
        return subTotal * (taxValue + surchargesValue);
    }

    /**
     * @param subTotal
     * @param vatValue
     * @return
     */
    public double getVatAmount(double subTotal, double vatValue) {
        return subTotal * (vatValue);
    }

    /**
     * @param subTotal
     * @param dueNowValue
     * @return
     */
    public double getDueNowAmount(double subTotal, double dueNowValue) {
        return subTotal * dueNowValue;
    }

    /**
     * @param roomRateArray
     * @param subTotal
     * @param roomRateModelList
     * @param roomCount
     * @return
     */
    public double getSubTotalAndBuildRoomRateModelList(JsonNode roomRateArray, double subTotal, List<RoomRateModel> roomRateModelList, Integer roomCount) {
        for (JsonNode roomRate : roomRateArray) {
            /**
             * calculating the subtotal
             */
            subTotal += roomRate.get("room_rate").get("basic_nightly_rate").asDouble();

            RoomRateModel roomRateModel = RoomRateModel.builder()
                    .basicNightlyRate(roomRate.get("room_rate").get("basic_nightly_rate").asDouble())
                    .date(roomRate.get("room_rate").get("date").toString())
                    .build();
            roomRateModelList.add(roomRateModel);
        }
        subTotal *= roomCount;
        return subTotal;
    }
}
