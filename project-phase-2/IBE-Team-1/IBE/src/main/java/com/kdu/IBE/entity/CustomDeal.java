package com.kdu.IBE.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="custom_deal")
public class CustomDeal {
    @Id
    private Long dealId;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="room_type_id")
    private RoomType roomType;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private  Double price_factor;
    @NotNull
    private String promotionTitle;
    @NotNull
    private String promotionDescription;
    @NotNull
    @Column(unique = true)
    private String promoCode;
}