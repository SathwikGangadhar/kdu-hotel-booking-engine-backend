package com.kdu.IBE.utils;

import com.kdu.IBE.model.responseDto.AvailableRoomModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Component
public class RoomServiceSort {
    /**
     * @param availableRoomModelList
     * @param isDescending
     */
    public void sortAvailableListOnRate(List<AvailableRoomModel> availableRoomModelList, Integer isDescending){
        Collections.sort(availableRoomModelList, Comparator.comparingDouble(AvailableRoomModel::getRoomRate));
        if(isDescending<0){
            Collections.reverse(availableRoomModelList);
        }
    }

    /**
     * @param availableRoomModelList
     * @param isDescending
     */
    public void sortAvailableListOnArea(List<AvailableRoomModel> availableRoomModelList,Integer isDescending){
        Collections.sort(availableRoomModelList,Comparator.comparingLong(AvailableRoomModel::getAreaInSquareFeet));
        if(isDescending<0){
            Collections.reverse(availableRoomModelList);
        }
    }
}
