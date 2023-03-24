package com.kdu.IBE.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tenant_table")
public class TenantTable {
    @Id
    private Long tenant_id;
}
