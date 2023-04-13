package com.kdu.IBE.model.recieveModel;

import lombok.Data;
@Data
public class BookingDetails {
   private Long roomTypeId;
   private Integer numberOfRooms;
   private String startDate;
   private String endDate;
}
