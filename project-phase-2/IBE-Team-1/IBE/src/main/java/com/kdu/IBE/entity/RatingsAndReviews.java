package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ratings_and_reviews")
public class RatingsAndReviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @NotNull
    @JoinColumn(name="room_type_id")
    private RoomType roomType;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    @NotNull
    private Double ratings;
    private String reviews;
}
