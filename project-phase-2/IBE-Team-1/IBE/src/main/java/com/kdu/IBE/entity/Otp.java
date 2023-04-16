package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="otp")
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long bookingId;
    private Integer otp;
    @CreationTimestamp
    private LocalDateTime time;
}
