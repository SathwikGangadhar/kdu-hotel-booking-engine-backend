package com.kdu.IBE.model.recieveModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltersModel {
    private FiltersDescriptionModel<String> roomName;
    private FiltersDescriptionModel<String> bedType;
    private  FiltersDescriptionModel<Integer> maxCapacity;
    private FiltersDescriptionModel<Double> area;
    private FiltersDescriptionModel<Double> rate;
    private FiltersDescriptionModel<Integer> bedCounts;

}
