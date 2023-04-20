package com.kdu.IBE.model.requestDto;

import lombok.Data;

@Data
public class NotifyUserRequestDto {
    private String userEmail;
    private String StartDate;
    private Long roomTypeId;
}
