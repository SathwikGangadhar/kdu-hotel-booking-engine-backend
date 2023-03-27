package com.kdu.IBE.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRoomModel {
    private Long roomTypeId;
    private String roomTypeName;
    private Long singleBed;
    private Long maxCapacity;
    private Long doubleBed;
    private Long areaInSquareFeet;
    private Long availableRoomCount;
}
