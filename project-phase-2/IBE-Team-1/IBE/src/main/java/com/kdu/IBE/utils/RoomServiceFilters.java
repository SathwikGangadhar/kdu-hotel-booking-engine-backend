package com.kdu.IBE.utils;

import com.kdu.IBE.model.responseDto.AvailableRoomModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RoomServiceFilters {
    /**
     * @param availableRoomModelList
     * @param valueList
     */
    public void getRoomNameFilter(List<AvailableRoomModel> availableRoomModelList,List<String> valueList){
        List<AvailableRoomModel> filterAllList=new ArrayList<>();
        for(String value:valueList) {
            List<AvailableRoomModel> filteredList;
            filteredList = availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.roomTypeName.contains(value.toUpperCase())).collect(Collectors.toList());
            filterAllList.addAll(filteredList);
        }
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filterAllList);
    }

    /**
     * @param availableRoomModelList
     * @param valueList
     */
    public void getBedTypeFilter(List<AvailableRoomModel> availableRoomModelList,List<String> valueList){
        List<AvailableRoomModel> filterAllList=new ArrayList<>();
        Map<Long,Integer> availableRoomModelMap=new HashMap<>();
        for(String value:valueList) {
            if(value.equalsIgnoreCase("King bed")){
                List<AvailableRoomModel> filteredList;
                filteredList=availableRoomModelList.stream().filter(availableRoomModel -> (availableRoomModel.doubleBed>0)).collect(Collectors.toList());
                filterAllList.addAll(filteredList);
            } else if (value.equalsIgnoreCase("Queen bed")) {
                List<AvailableRoomModel> filteredList;
                filteredList=availableRoomModelList.stream().filter(availableRoomModel -> (availableRoomModel.singleBed>0)).collect(Collectors.toList());

                filterAllList.addAll(filteredList);
            }
        }
        availableRoomModelList.clear();
        for(AvailableRoomModel availableRoomModel:filterAllList){
            if(availableRoomModelMap.get(availableRoomModel.getRoomTypeId())==null){
                availableRoomModelList.add(availableRoomModel);
                availableRoomModelMap.put(availableRoomModel.getRoomTypeId(), Integer.valueOf(1));
            }
        }
    }

    /**
     * @param availableRoomModelList
     * @param value
     */
    public void getMaxCapacityFilter(List<AvailableRoomModel> availableRoomModelList,Integer value){
        List<AvailableRoomModel> filteredList;
        filteredList= availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.maxCapacity>=value).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }

    /**
     * @param availableRoomModelList
     * @param value
     */
    public void getAreaFilter(List<AvailableRoomModel> availableRoomModelList, Double value){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.areaInSquareFeet>=value).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }

    /**
     * @param availableRoomModelList
     * @param rate
     */
    public void getRateFilters(List<AvailableRoomModel> availableRoomModelList,Double rate){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.roomRate<=rate).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }

    /**
     * @param availableRoomModelList
     * @param value
     * @param maxCapacity
     */
    public  void getBedCountAndMaxCapacityFilters(List<AvailableRoomModel> availableRoomModelList,int value,int maxCapacity){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.singleBed+availableRoomModel.doubleBed>=value && availableRoomModel.maxCapacity>=maxCapacity).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }
}
