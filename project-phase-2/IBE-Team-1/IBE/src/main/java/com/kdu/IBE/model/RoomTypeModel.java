package com.kdu.IBE.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeModel {
    private Long room_type_id;
    private Long property_id;
}
