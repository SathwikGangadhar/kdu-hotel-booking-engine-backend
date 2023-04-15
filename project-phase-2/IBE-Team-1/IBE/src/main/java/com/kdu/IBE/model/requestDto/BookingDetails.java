package com.kdu.IBE.model.requestDto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookingDetails {
   @NotNull
   private Long roomTypeId;
   @NotNull
   private Integer numberOfRooms;
   @NotNull
   private String startDate;
   @NotNull
   private String endDate;
}
