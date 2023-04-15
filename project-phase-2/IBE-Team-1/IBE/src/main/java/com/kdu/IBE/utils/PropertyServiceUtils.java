package com.kdu.IBE.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.kdu.IBE.model.responseDto.PropertyReturnModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyServiceUtils {
    public String getCountPropertyQuery(){
        return "query MyQuery {\n" +
                "  countProperties\n" +
                "}";
    }
    public String getPropertyQuery(String tenantId,Integer skip,Integer take){
        return "query MyQuery {\n" +
                "  listProperties(where: {tenant_id: {equals: " + tenantId + "}}, skip: " + Integer.toString(skip) + ", take: " + Integer.toString(take) + ") {\n" +
                "    property_id\n" +
                "    property_name\n" +
                "  }\n" +
                "}";
    }
    public void setPropertyDto(JsonNode propertyList, List<PropertyReturnModel> propertyReturnModelList){
        for (JsonNode property :propertyList){
            PropertyReturnModel propertyModel= PropertyReturnModel.builder().propertyId(property.get("property_id").asLong()).propertyName(property.get("property_name").toString()).build();
            propertyReturnModelList.add(propertyModel);
        }
    }



}
