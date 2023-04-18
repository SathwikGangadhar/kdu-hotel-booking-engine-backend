package com.kdu.IBE.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRateModel {
    private Long basicNightlyRate;
    private String date;
    private  Long room_rate_id;
}
