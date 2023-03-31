package com.kdu.IBE.utils;

import com.kdu.IBE.model.returnDto.AvailableRoomModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomServiceFilters {
    public void getRoomNameFilter(List<AvailableRoomModel> availableRoomModelList,String value){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel ->availableRoomModel.roomTypeName.contains(value.toUpperCase())).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }
    public void getBedTypeFilter(List<AvailableRoomModel> availableRoomModelList,String value){
        if(value.equalsIgnoreCase("king")){
            List<AvailableRoomModel> filteredList;
            filteredList=availableRoomModelList.stream().filter(availableRoomModel -> (availableRoomModel.doubleBed>0)).collect(Collectors.toList());
            availableRoomModelList.clear();
            availableRoomModelList.addAll(filteredList);
        } else if (value.equalsIgnoreCase("queen")) {
            List<AvailableRoomModel> filteredList;
            filteredList=availableRoomModelList.stream().filter(availableRoomModel -> (availableRoomModel.singleBed>0)).collect(Collectors.toList());
            availableRoomModelList.clear();
            availableRoomModelList.addAll(filteredList);
        }
    }

    public void getMaxCapacityFilter(List<AvailableRoomModel> availableRoomModelList,Integer value){
        List<AvailableRoomModel> filteredList;
        filteredList= availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.maxCapacity>=value).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }

    public void getAreaFilter(List<AvailableRoomModel> availableRoomModelList, Double value){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.areaInSquareFeet>=value).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }

    public void getRateFilters(List<AvailableRoomModel> availableRoomModelList,Double rate){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.roomRate<=rate).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }
    public  void getBedCountFilters(List<AvailableRoomModel> availableRoomModelList,Integer value){
        List<AvailableRoomModel> filteredList;
        filteredList=availableRoomModelList.stream().filter(availableRoomModel -> availableRoomModel.singleBed+availableRoomModel.doubleBed>=value).collect(Collectors.toList());
        availableRoomModelList.clear();
        availableRoomModelList.addAll(filteredList);
    }
}
