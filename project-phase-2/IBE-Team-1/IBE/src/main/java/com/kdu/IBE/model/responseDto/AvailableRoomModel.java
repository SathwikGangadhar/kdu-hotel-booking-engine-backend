package com.kdu.IBE.model.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailableRoomModel {
    public Long roomTypeId;
    public String roomTypeName;
    public Long singleBed;
    public Long maxCapacity;
    public Long doubleBed;
    public Long areaInSquareFeet;
    public Integer availableRoomCount;
    public Double roomRate;
}
