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

/**
 *
 */
@Service
public class DateService implements IDateService{
    @Autowired
    public GraphQlWebClient graphQlWebClient;

    /**
     * @return
     */
    @Override
    public ResponseEntity<Map<String, Integer>> getMinDate(){
        Map<String, Integer> minRatesByDate = new HashMap<>();

        int skip=0;
        int take=1000;
        //all the minimum dates are selected for each date in the hashmap
        while(true){
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("query","query MyQuery { " +
                    "listRoomRates(skip: "+Integer.toString(skip)+", take: "+Integer.toString(take)+") { " +
                    "date " +
                    "basic_nightly_rate " +
                    "} " +
                    "}"
            );
            skip+=1000;
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

            for(int listIndex=0;listIndex<listRoomRates.length();listIndex++){
                JSONObject roomRate = listRoomRates.getJSONObject(listIndex);
                String date = roomRate.getString("date").substring(0, 10);
                int rate = roomRate.getInt("basic_nightly_rate");

                if(minRatesByDate.get(date)==null){
                    minRatesByDate.put(date,rate);
                }
                else{
                    minRatesByDate.put(date,Math.min(minRatesByDate.get(date),rate));
                }
            }

        }
        return new ResponseEntity<Map<String, Integer>>(minRatesByDate, HttpStatus.OK);
    }
}
