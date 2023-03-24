package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "room_type")
public class RoomType {
    @Id
    private Long roomTypeId;
    @ManyToOne
    @JoinColumn(name = "property_table")
    private PropertyTable property;
}