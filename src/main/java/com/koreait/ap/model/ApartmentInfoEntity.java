package com.koreait.ap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApartmentInfoEntity {
    private int iapart;
    @JsonProperty("거래금액") private String dealamount;
    @JsonProperty("건축년도") private String buildyear;
    @JsonProperty("년") private String dealyear;
    @JsonProperty("월") private String dealmonth;
    @JsonProperty("일") private String dealday;
    @JsonProperty("법정동") private String dong;
    @JsonProperty("아파트") private String apartmentname;
    @JsonProperty("전용면적") private float areaforexclusiveuse;
    @JsonProperty("지번") private String jibun;
    @JsonProperty("층") private int floor;
    private int locationcode;
    @JsonProperty("도로명시군구코드") private String excd;

    //두번째 방법
    public void setDealamount(String dealamount) {
        this.dealamount = dealamount.replaceAll(",", "").trim();//trim 양쪽 빈칸 없앰
    }
    //세번째 방법은 쿼리문에 REPLACE(TRIM) 사용 SELECT
    //            REPLACE(TRIM(#{dealamount}), ',', '') , #{buildyear}, #{dealyear}, #{dealmonth}, #{dealday}
    //             , #{dong}, #{apartmentname}, #{areaforexclusiveuse}, #{jibun}, #{floor}
    //             , `code`, #{excd}
}