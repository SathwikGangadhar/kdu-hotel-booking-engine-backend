package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "booking_details")
public class BookingDetails {
    @Id
    @JoinColumn(name="traveller_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="booking_id")
    private Booking bookingId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double dealPrice;
    private String dealTitle;
    private String dealDescription;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id")
    private RoomType roomTypeId;
    private String roomImage;
    @NotNull
    private Integer adultCount;
    private Integer childCount;
    private Double averagePrice;
    private Double subTotal;
    private Double taxPrice;
    private Double vatPrice;
    private Double totalAmount;
}
