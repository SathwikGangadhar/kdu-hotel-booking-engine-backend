package com.kdu.IBE.model.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookingDetailsModel {
   @NotNull
   private Long roomTypeId;
   @NotNull
   private Integer numberOfRooms;
   @NotNull
   private String startDate;
   @NotNull
   private String endDate;
   private String roomImage;
   private Integer adultCount;
   private Integer childCount;
   private Double dealPrice;
   private String dealTitle;
   private String dealDescription;
   private Double averagePrice;
   private Double subTotal;
   private Double taxPrice;
   private Double vatPrice;
   private Double totalAmount;
}