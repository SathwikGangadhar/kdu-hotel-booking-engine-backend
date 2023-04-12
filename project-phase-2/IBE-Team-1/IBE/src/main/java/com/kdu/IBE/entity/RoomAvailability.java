package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomAvailability {
    @Id
    private Long availabilityId;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room roomId;
    private Long bookingId;
}

