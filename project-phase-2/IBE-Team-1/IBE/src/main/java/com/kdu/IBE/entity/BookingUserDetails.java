package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_user_info")
public class BookingUserDetails {
    @Id
    @JoinColumn(name="traveller_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long travellerId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="booking_id")
    private Booking bookingId;
    @NotNull
    private String travellerFirstName;
    private String travellerMiddleName;
    private String travellerLastName;
    @NotNull
    private String travellerPhoneNumber;
    private String travellerAlternatePhone;
    @NotNull
    private String travellerEmail;
    private String travellerAlternateEmail;
    @NotNull
    private String billingFirstName;
    private String billingMiddleName;
    private String billingLastName;
    @NotNull
    private String mailingAddress;
    private String alternateMailingAddress;
    @NotNull
    private String billingEmail;
    private String billingAlternateEmail;
    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private String state;
    @NotNull
    private String zip;
    @NotNull
    private String billingPhoneNumber;
    private String billingAlternatePhone;
    @NotNull
    private String cardNumber;
    @NotNull
    private String expiryMonth;
    @NotNull
    private String expiryYear;
    private Boolean isSendOffers;
    private Long roomTypeId;
}
