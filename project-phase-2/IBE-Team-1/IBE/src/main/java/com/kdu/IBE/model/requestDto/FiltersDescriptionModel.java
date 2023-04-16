package com.kdu.IBE.model.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltersDescriptionModel<T> {
    private int isNeeded;
    private T value;
}
