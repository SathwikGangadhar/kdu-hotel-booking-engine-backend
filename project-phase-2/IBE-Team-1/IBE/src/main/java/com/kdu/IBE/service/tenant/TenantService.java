package com.kdu.IBE.service.tenant;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.exception.GraphQlApiException;
import com.kdu.IBE.model.responseDto.PropertyReturnModel;
import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import com.kdu.IBE.utils.PropertyServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class TenantService implements ITenantService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;

    @Autowired
    public PropertyServiceUtils propertyServiceUtils;

    public ResponseEntity<List<PropertyReturnModel>> getTenantProperties(String tenantId) {
        Map<String, Integer> minRatesByDate = new HashMap<>();
        int skip = 0;
        int take = 0;
        /**
         * getting the count of the
         */
        Map<String, Object> requestBodyPropertyCount = new HashMap<>();
        requestBodyPropertyCount.put("query", propertyServiceUtils.getCountPropertyQuery()
        );
        JsonNode jsonNode=graphQlWebClient.getGraphQlResponse(requestBodyPropertyCount);
        take = jsonNode.get("data").get("countProperties").asInt();

        if(take<1){
            throw new GraphQlApiException("Room count is less than the 1");
        }

        /**
         * getting properties of tenant from the property table
         *
         */

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("query", propertyServiceUtils.getPropertyQuery( tenantId,skip, take)
        );

       jsonNode=graphQlWebClient.getGraphQlResponse(requestBody);
       List<PropertyReturnModel> propertyReturnModelList=new ArrayList<>();
       JsonNode propertyList=jsonNode.get("data").get("listProperties");

        /**
         * setting the property dto to the response
         */
        propertyServiceUtils.setPropertyDto(propertyList, propertyReturnModelList);

        return new ResponseEntity<List<PropertyReturnModel>>(propertyReturnModelList, HttpStatus.OK);
    }
}
