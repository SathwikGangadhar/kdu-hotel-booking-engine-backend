package com.kdu.IBE.service.promotionDeal;

import com.kdu.IBE.model.returnDto.PromotionDealModel;
import com.kdu.IBE.utils.PromotionDealServiceUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PromotionDealTest {

    @Autowired
    public IPromotionDealService promotionDealService;
    @Autowired
    public PromotionDealServiceUtil promotionDealServiceUtil;

    /**
     * testing whether all promotionDeals are fetched or not for the given dates
     */
    @Test
    public void testGetAllPromotionDeals(){
        // Call the method with some test data
        ResponseEntity<?> response = promotionDealService.getAllPromotionDeals("2023-04-08", "2023-04-23");
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PromotionDealModel> deals = (List<PromotionDealModel>) response.getBody();
        assertNotNull(deals);
        assertEquals(6, deals.size());
        }

    /**
     * testing that long weekend promotion deal is fetched or not for the defined dates
     */
    @Test
    public void testLongWeekendPromotions(){
        // Call the method with some test data
        ResponseEntity<?> response = promotionDealService.getAllPromotionDeals("2023-04-08", "2023-04-23");
        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PromotionDealModel> deals = (List<PromotionDealModel>) response.getBody();
        assertNotNull(deals);
        // assertEquals(6, deals.size());
        PromotionDealModel deal = deals.get(0);
        assertEquals(3, deal.getPromotionDealId());
        assertEquals("\"Long weekend discount\"", deal.getPromotionDealTitle());
        assertEquals(0.75, deal.getPromotionDealPriceFactor(), 0.001); // Use delta for floating-point comparisons
    }

    /**
     * testing the number of weekends
     */
    @Test
    public void testNumberOfWeekends(){
        //Call the method with some test data
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        LocalDate startDateForCount = LocalDate.parse("2023-04-14".substring(0, 10), formatter);
        LocalDate endDateForCount = LocalDate.parse("2023-04-17".substring(0, 10), formatter);
        long weekends = promotionDealServiceUtil.countWeekdaysAndWeekends(startDateForCount, endDateForCount, true);
        assertThat(2).isEqualTo(weekends);
    }
}
