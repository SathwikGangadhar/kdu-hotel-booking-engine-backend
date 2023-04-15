package com.kdu.IBE.model.requestDto;

import com.kdu.IBE.model.responseDto.RoomBookedModel;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Builder
@Data
public class BookingResponse {
    private Long bookingId;
    private List<RoomBookedModel> roomList;
}
