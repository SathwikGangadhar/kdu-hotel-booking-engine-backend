package com.kdu.IBE.service.promotionDeal;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.returnDto.PromotionDealModel;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import com.kdu.IBE.utils.PromotionDealServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PromotionDealService implements IPromotionDealService {
    @Autowired
    public GraphQlWebClient graphQlWebClient;
    @Autowired
    private PromotionDealServiceUtil promotionDealServiceUtil;
    JsonNode jsonNode;

    @Override
    public ResponseEntity<?> getAllPromotionDeals(String startDate, String endDate) {
        Map<String, Object> availablePromotionDealsMap = new HashMap<>();
        JsonNode jsonNode;
        availablePromotionDealsMap.put("query",
                promotionDealServiceUtil.getPromotionDealQuery(startDate, endDate));
        jsonNode = graphQlWebClient.getGraphQlResponse(availablePromotionDealsMap);
        JsonNode availablePromotionDealsJsonList = jsonNode.get("data").get("listPromotions");
        List<PromotionDealModel> availablePromotionDeals = new ArrayList<>();
        promotionDealServiceUtil.promotionDealsListSetter(availablePromotionDealsJsonList, availablePromotionDealsMap, availablePromotionDeals);
        List<PromotionDealModel> filteredAvailablePromotionDeals = promotionDealServiceUtil.getFilteredAvailablePromotionDeals(availablePromotionDeals, startDate, endDate);
        filteredAvailablePromotionDeals = filteredAvailablePromotionDeals
                .stream()
                .sorted(Comparator.comparing(PromotionDealModel::getPromotionDealPriceFactor).reversed())
                .collect(Collectors.toList());
        return new ResponseEntity<List<PromotionDealModel>>(filteredAvailablePromotionDeals, HttpStatus.OK);
    }
}
