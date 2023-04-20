package com.kdu.IBE.model.requestDto;

import lombok.Data;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotNull;

@Data
public class BillingInfoModel {
    @NotNull
    @Size(min = 1, max = 30, message = "Length should be between 1 and 30 characters")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    @Size(min = 4, max = 60, message = "Length should be between 4 and 60 characters")
    private String mailingAddress;
    private String alternateMailingAddress;
    @NotNull
    private String country;
    @NotNull
    @Size(min = 1, message = "Length should be at least 1 character")
    private String city;
    @NotNull
    private String state;
    @NotNull
    @Pattern(regexp = "^[0-9]{5,6}$", message = "Zip code must be a 5-6 digit number")
    private String zip;
    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid 10-digit phone number")
    private String phone;
    private String alternatePhone;
    @NotNull
    @Email(message = "Please enter a valid email")
    private String email;
    private String alternateEmail;
}
