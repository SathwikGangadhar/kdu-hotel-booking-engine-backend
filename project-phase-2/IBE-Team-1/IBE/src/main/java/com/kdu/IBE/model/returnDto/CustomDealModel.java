package com.kdu.IBE.model.returnDto;

import com.kdu.IBE.entity.RoomType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomDealModel {
    private Long dealId;
    private  Double price_factor;
    private String promotionTitle;
    private String promotionDescription;
}







