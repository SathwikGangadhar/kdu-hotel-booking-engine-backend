package com.kdu.IBE.service.constumeDeal;

import com.kdu.IBE.entity.CustomDeal;
import com.kdu.IBE.model.returnDto.CustomDealModel;
import org.springframework.http.ResponseEntity;

public interface ICostumeDealService {
    public ResponseEntity<CustomDealModel> getPromoCodeDetails(String promoCode , String roomType);
}
