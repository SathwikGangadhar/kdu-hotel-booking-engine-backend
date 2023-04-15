package com.kdu.IBE.model.requestDto;

import lombok.Data;
@Data
public class PaymentInfoModel {
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
}
