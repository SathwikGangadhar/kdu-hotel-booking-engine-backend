package com.kdu.IBE.model.responseDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Builder
@Data
public class BookingDetailsResponse {
    private Long bookingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double dealPrice;
    private Double dealTitle;
    private Double dealDescription;
    private Long roomTypeId;
    private String roomImage;
    private Double averagePrice;
    private Double subTotal;
    private Double taxPrice;
    private Double vatPrice;
    private Double totalAmount;
}
