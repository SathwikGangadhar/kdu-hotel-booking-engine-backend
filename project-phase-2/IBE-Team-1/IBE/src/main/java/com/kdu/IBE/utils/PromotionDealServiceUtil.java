package com.kdu.IBE.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.returnDto.PromotionDealModel;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PromotionDealServiceUtil {
    public String getPromotionDealQuery(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDateForCount = LocalDate.parse(startDate.substring(0, 10), formatter);
        LocalDate endDateForCount = LocalDate.parse(endDate.substring(0, 10), formatter);
        long daysBetween = ChronoUnit.DAYS.between(startDateForCount, endDateForCount) + 1;
        String skip = Long.toString(0);
        String take = Long.toString(10);
        return "query MyQuery { " +
                "listPromotions(where: {is_deactivated: {equals: false}, minimum_days_of_stay: {lte: " + Long.toString(daysBetween) + "}}, skip: " + skip + ", take: " + take + ") { " +
                "price_factor " +
                "promotion_description " +
                "promotion_id " +
                "promotion_title " +
                "} " +
                "}";
    }

    public void promotionDealsListSetter(JsonNode availablePromotionDealsJsonList, Map<String, Object> availablePromotionDealsMap, List<PromotionDealModel> availablePromotionDeals) {
        for (JsonNode promotionDeal : availablePromotionDealsJsonList) {
            PromotionDealModel promotionDealModel = PromotionDealModel.builder()
                    .promotionDealId(promotionDeal.get("promotion_id").asLong())
                    .promotionDealTitle(String.valueOf(promotionDeal.get("promotion_title")))
                    .promotionDealDescription(String.valueOf(promotionDeal.get("promotion_description")))
                    .promotionDealPriceFactor(promotionDeal.get("price_factor").asDouble())
                    .build();
            availablePromotionDeals.add(promotionDealModel);
        }
    }

    public List<PromotionDealModel> getFilteredAvailablePromotionDeals(List<PromotionDealModel> availablePromotionDeals, String startDate, String endDate) {
        List<PromotionDealModel> filteredAvailablePromotionDeals;
        filteredAvailablePromotionDeals = availablePromotionDeals.stream().filter(deal -> deal.getPromotionDealTitle().contains("weekend")) // filter based on the promotion weekend title
                .filter(deal -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                    LocalDate startDateForCount = LocalDate.parse(startDate.substring(0, 10), formatter);
                    LocalDate endDateForCount = LocalDate.parse(endDate.substring(0, 10), formatter);
                    long weekdays = countWeekdaysAndWeekends(startDateForCount, endDateForCount, false);
                    long weekends = countWeekdaysAndWeekends(startDateForCount, endDateForCount, true);
                    return ((weekends > 1 && (weekdays >= 1 || weekdays == 0)) ); // filter based on the number of weekdays and weekends
                })
                .collect(Collectors.toList());
        return filteredAvailablePromotionDeals;
    }

    public long countWeekdaysAndWeekends(LocalDate startDate, LocalDate endDate, boolean countWeekends) {
        long weekdays = 0;
        long weekends = 0;
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                weekdays++;
            } else {
                weekends++;
            }
            currentDate = currentDate.plus(1, ChronoUnit.DAYS);
        }

        return countWeekends ? weekends : weekdays;
    }
}
