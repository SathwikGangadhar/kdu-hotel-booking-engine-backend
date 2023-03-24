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
@Table(name = "property_table")
public class Property {
    @Id
    private Long property_id;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenantTable;
}
