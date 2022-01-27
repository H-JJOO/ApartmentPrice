package com.koreait.ap.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApartmentInfoDto {
    private int locationcode;
    private List<ApartmentInfoEntity> apartmentInfoList;
}
