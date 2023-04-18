package com.kdu.IBE.utils;

import org.springframework.stereotype.Component;
@Component
public class DateServiceUtils {
    /**
     * @param skip
     * @param take
     * @return
     */
    public String getMinRatePerDateQuery(Integer skip,Integer take){
        return "query MyQuery { " +
                "listRoomRates(skip: "+Integer.toString(skip)+", take: "+Integer.toString(take)+", where: {room_types: {some: {room_type: {property_id: {equals: 1}}}}}) { " +
                "date " +
                "basic_nightly_rate " +
                "} " +
                "}";
    }
}
