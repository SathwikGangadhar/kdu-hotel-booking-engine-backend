package com.kdu.IBE.model.requestDto;

import lombok.Data;
import javax.validation.Valid;
@Data
public class BookingModel {
    private BookingDetailsModel bookingDetailsModel;
    @Valid
    private UserInfoModel userInfoModel;
}