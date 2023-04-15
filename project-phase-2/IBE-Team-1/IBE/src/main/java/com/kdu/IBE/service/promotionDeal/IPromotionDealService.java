package com.kdu.IBE.service.promotionDeal;

import com.kdu.IBE.model.responseDto.PromotionDealModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPromotionDealService {
    ResponseEntity<List<PromotionDealModel>> getAllPromotionDeals(String startDate, String endDate);
}
