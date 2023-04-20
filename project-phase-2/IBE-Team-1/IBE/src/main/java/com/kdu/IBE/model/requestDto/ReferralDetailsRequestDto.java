package com.kdu.IBE.model.requestDto;

import lombok.Data;
@Data
public class ReferralDetailsRequestDto {
    private String userEmail;
    private Double userDiscount;
    private String referralUserEmail;
    private Double referralUserDiscount;
}
