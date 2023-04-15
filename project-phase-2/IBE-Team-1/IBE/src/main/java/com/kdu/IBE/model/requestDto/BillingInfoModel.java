package com.kdu.IBE.model.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BillingInfoModel {
    @NotNull
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    private String mailingAddress;
    private String alternateMailingAddress;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zip;
    @NotNull
    private String phone;
    private String alternatePhone;
    @NotNull
    private String email;
    private String alternateEmail;
}
