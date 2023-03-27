package com.kdu.IBE.service.room;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.AvailableRoomModel;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomService implements IRoomService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;

    public ResponseEntity<?> getRoomTypes(String propertyId, String startDate, String endDate, String skip, String take) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", "query MyQuery {\n" +
                "  listRoomTypes(where: {property_id: {equals: " + propertyId + "}, room: {some: {room_available: {some: {booking_id: {equals: 0}, date: {gte: " + startDate + ", lte: " + endDate + "}}}}}}, skip: " + skip + ", take: " + take + ") {\n" +
                "    room_type_id\n" +
                "    room_type_name\n" +
                "    single_bed\n" +
                "    max_capacity\n" +
                "    double_bed\n" +
                "    area_in_square_feet\n" +
                "  }\n" +
                "}"
        );
        JsonNode jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);

        List<AvailableRoomModel> availableRoomModelList = new ArrayList<>();
        JsonNode responseList = jsonNode.get("data").get("listRoomTypes");
        for (JsonNode roomTypeDetails : responseList) {
            Long availableRoomCount = getRoomCount(roomTypeDetails.get("room_type_id").toString());
            AvailableRoomModel availableRoomModel = new AvailableRoomModel(roomTypeDetails.get("room_type_id").asLong(), roomTypeDetails.get("room_type_name").toString(), roomTypeDetails.get("single_bed").asLong(), roomTypeDetails.get("max_capacity").asLong(), roomTypeDetails.get("double_bed").asLong(), roomTypeDetails.get("area_in_square_feet").asLong(), availableRoomCount);
           availableRoomModelList.add(availableRoomModel);
        }
        return new ResponseEntity< List<AvailableRoomModel> >(availableRoomModelList, HttpStatus.OK);
    }
    public Long getRoomCount(String roomTypeId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", "query MyQuery {\n" +
                "  countRooms(where: {room_type_id: {equals: " + roomTypeId + "}, room_available: {some: {booking_id: {equals: 0}}}})\n" +
                "}"
        );
        JsonNode jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
        return jsonNode.get("data").get("countRooms").asLong();
    }
}
