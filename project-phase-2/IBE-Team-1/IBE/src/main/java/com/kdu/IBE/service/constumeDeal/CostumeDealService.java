package com.kdu.IBE.service.constumeDeal;

import com.kdu.IBE.entity.CustomDeal;
import com.kdu.IBE.model.returnDto.CustomDealModel;
import com.kdu.IBE.repo.CustomDealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class CostumeDealService implements ICostumeDealService{
    @Autowired
    CustomDealRepository customDealRepository;
    public ResponseEntity<?> getPromoCodeDetails(String promoCode , String roomType){
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        Long roomTypeIdValue=Long.parseLong(roomType);

        /**
         * found the current date when the booking is made to check whether the promo code is valid or not
         */
        String currentDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        CustomDeal customDeal =customDealRepository.findByPromoCodeEqualsAndRoomTypeIdEqualsAndStartDateEqualsAndEndDateEquals(promoCode,roomTypeIdValue,currentDate);
        if(customDeal==null){
            return new ResponseEntity<>("No data", HttpStatus.OK);
        }
        CustomDealModel customDealModel=CustomDealModel.builder()
                .dealId(customDeal.getDealId())
                .price_factor(customDeal.getPrice_factor())
                .promotionTitle(customDeal.getPromotionTitle())
                .promotionDescription(customDeal.getPromotionDescription())
                .build();

        return new ResponseEntity<CustomDealModel>(customDealModel, HttpStatus.OK);
    }
    }












