package com.kdu.IBE.model.returnDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PromotionDealModel {
    private Long promotionDealId;
    private String promotionDealTitle;
    private String promotionDealDescription;
    private Double promotionDealPriceFactor;
}
