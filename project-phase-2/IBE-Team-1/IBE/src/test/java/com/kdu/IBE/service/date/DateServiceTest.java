package com.kdu.IBE.service.date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class DateServiceTest {
    @Autowired
    public IDateService dateService;

    @Test
    public void testMinRateForDate(){
        ResponseEntity<Map<String, Integer>> response= dateService.getMinDate();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().get("2023-03-28")).isEqualTo(50);
    }
}


