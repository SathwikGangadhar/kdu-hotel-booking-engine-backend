package com.kdu.IBE.service.date;

import com.kdu.IBE.service.graphQl.GraphQlWebClient;
import org.hibernate.ObjectNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 *
 */
@Service
public class DateService implements IDateService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;

    /**
     * Fetches the minimum room rates for all the date
     */
    @Override
    public ResponseEntity<Map<String, Integer>> getMinDate(){
        Map<String, Integer> minRatesByDate = new HashMap<>();

        int skip=0;
        final int take=10000;
        /**
         * all the minimum dates are selected for each date in the hashmap
         * infinite loop because to fetch all the data of the grapfQL api
         */
        while(true){
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("query","query MyQuery { " +
                    "listRoomRates(skip: "+Integer.toString(skip)+", take: "+Integer.toString(take)+", where: {room_types: {some: {room_type: {property_id: {equals: 1}}}}}) { " +
                    "date " +
                    "basic_nightly_rate " +
                    "} " +
                    "}"
            );
            skip+=10000;
            String bodyString;
            try {
                WebClient.ResponseSpec response = graphQlWebClient.requestBodySpec
                        .body(BodyInserters.fromValue(requestBody))
                        .accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
                        .acceptCharset(StandardCharsets.UTF_8)
                        .retrieve();

                bodyString = response.bodyToMono(String.class).block();

            }catch (Exception e){
                throw new ObjectNotFoundException("user not present","");
            }
            JSONObject json = new JSONObject(bodyString);

            JSONArray listRoomRates = json.getJSONObject("data").getJSONArray("listRoomRates");


            if( listRoomRates.length()==0){
                break;
            }
            /**
             * fetches the minimum room rate for all particular date using hashmap
             */

            IntStream.range(0, listRoomRates.length())
                    .forEach(listIndex -> {
                        JSONObject roomRate = listRoomRates.getJSONObject(listIndex);
                        String date = roomRate.getString("date");
                        int rate = roomRate.getInt("basic_nightly_rate");
                        /**
                         * if the data is not all ready entered then initialize it with a data
                         */
                        if(minRatesByDate.get(date)==null){
                            minRatesByDate.put(date,rate);
                        }
                        /**
                         * if the data is initialized then get the minimum out of that data and update the hashmap
                         */
                        else{
                            minRatesByDate.put(date,Math.min(minRatesByDate.get(date),rate));
                        }
                    });
        }
        return new ResponseEntity<Map<String, Integer>>(minRatesByDate, HttpStatus.OK);
    }
}
