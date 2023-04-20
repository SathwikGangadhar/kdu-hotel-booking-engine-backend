package com.kdu.IBE.model.requestDto;

import lombok.Data;

@Data
public class NotifyUserRequestDto {
    private String userEmail;
    private String startDate;
    private String endDate;
    private Long roomTypeId;
    private Integer roomCount;
}
