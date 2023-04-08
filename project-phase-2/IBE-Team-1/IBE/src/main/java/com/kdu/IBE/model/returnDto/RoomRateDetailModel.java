package com.kdu.IBE.model.returnDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
public class RoomRateDetailModel {
    private List<RoomRateModel> roomRateModelList;
    private double subTotal;
    private double taxesAndSurchargesAmount;
    private double vatAmount;
    private double grandTotal;
    private double dueNow;
    private double dueAtResort;
}
