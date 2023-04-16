package com.kdu.IBE.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class RatingsAndReviewUtils {
    public String getRatingBookingIdQuery(String skip,String formattedDate){
        return "query MyQuery {\n" +
                "  listBookings(skip: "+skip+", take: 10000000, where: {property_id: {equals: 1}, check_out_date: {lt: \""+formattedDate+"\"}}) {\n" +
                "    booking_id\n" +
                "  }\n" +
                "}";
    }
    public String getCurrentDate(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = dateFormat.format(now);
        return formattedDate;
    }
}
