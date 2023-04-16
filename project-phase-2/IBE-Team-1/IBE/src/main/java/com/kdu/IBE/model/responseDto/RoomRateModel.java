package com.kdu.IBE.model.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomRateModel {
    private Double basicNightlyRate;
    private String date;
}
