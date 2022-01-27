package com.koreait.ap;

import com.koreait.ap.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApMapper {
    int insApartmentInfo(ApartmentInfoEntity entity);
    int insApartmentInfoForeach(ApartmentInfoDto dto);
    List<ApartmentInfoVo> selApartmentInfoList(SearchDto dto);
    List<LocationCodeEntity> selLocationList();
    LocationCodeEntity selLocation(SearchDto dto);
}
