package com.kdu.IBE.model.returnDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyReturnModel {
    private Long propertyId;
    private String propertyName;
}
