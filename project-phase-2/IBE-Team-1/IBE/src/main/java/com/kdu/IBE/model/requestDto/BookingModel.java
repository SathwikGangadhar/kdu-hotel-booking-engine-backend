package com.kdu.IBE.model.requestDto;

import lombok.Data;

@Data
public class BookingModel {
    private BookingDetails bookingDetails;
    private UserInfoModel userInfoModel;
}
