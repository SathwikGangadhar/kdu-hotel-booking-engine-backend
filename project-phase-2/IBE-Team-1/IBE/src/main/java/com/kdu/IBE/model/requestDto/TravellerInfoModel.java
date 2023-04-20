package com.kdu.IBE.model.requestDto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
public class TravellerInfoModel {
    @NotNull
    @Size(min = 1, max = 30, message = "Min length 1, Max length 30")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull
    @Pattern(regexp = "^\\d{10}$", message = "Please enter a valid 10-digit phone number")
    private String phone;
    private String alternatePhone;
    @NotNull
    @Email(message = "Please enter a valid email")
    private String email;
    private String alternateEmail;
}
