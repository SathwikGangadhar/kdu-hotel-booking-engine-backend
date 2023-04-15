package com.kdu.IBE.service.tenant;

import com.kdu.IBE.model.responseDto.PropertyReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class TenantServiceTest {
    @Autowired
    ITenantService tenantService;


//    @Test
    public void testForProperties(){
        ResponseEntity<List<PropertyReturnModel>> response =tenantService.getTenantProperties("1");
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}