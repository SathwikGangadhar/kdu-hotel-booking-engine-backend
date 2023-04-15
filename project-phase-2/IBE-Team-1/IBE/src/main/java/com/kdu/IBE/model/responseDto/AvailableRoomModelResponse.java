package com.kdu.IBE.model.responseDto;

import lombok.Data;
import java.util.List;

@Data
public class AvailableRoomModelResponse {
    List<AvailableRoomModel> availableRoomModelList;
    Integer availableResponseCount;
}
