package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notify_user" , uniqueConstraints = @UniqueConstraint(columnNames = {"user_email", "room_type_id"}))
public class NotifyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_email")
    private String userEmail;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_type_id")
    private RoomType roomTypeId;
    private Integer requiredRoomCount;
}