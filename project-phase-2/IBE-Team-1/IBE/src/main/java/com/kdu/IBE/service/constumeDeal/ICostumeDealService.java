package com.kdu.IBE.service.constumeDeal;

import org.springframework.http.ResponseEntity;

public interface ICostumeDealService {
    public ResponseEntity<?> getPromoCodeDetails(String promoCode , String roomType);
}
