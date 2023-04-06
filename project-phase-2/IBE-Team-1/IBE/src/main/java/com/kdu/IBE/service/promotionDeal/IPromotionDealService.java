package com.kdu.IBE.service.promotionDeal;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IPromotionDealService {
    ResponseEntity<?> getAllPromotionDeals(String startDate, String endDate);
}
