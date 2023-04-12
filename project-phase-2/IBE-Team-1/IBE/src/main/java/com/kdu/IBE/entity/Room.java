package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    private Long roomId;
    @ManyToOne
    @JoinColumn(name = "room_type_id")
    private RoomType roomTypeId;
}
