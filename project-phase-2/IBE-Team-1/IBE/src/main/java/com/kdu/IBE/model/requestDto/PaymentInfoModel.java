package com.kdu.IBE.model.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class PaymentInfoModel {
    @NotNull
    private String cardNumber;
    @NotNull
    @Pattern(
            regexp = "^\\d{2}$",
            message = "Please enter a valid two-digit expiry year"
    )
    private String expiryMonth;
    @NotNull
    @Pattern(
            regexp = "^\\d{2}$",
            message = "Please enter a valid two-digit expiry year"
    )
    private String expiryYear;
}
