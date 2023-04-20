package com.kdu.IBE.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "referral")
public class Referral {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long referralId;
    @Column(unique=true)
    private String userEmail;
    @Column(unique=true)
    private String referralCode;
}

