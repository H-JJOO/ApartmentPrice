package com.koreait.ap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koreait.ap.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApService {
    @Autowired private ApMapper mapper;

    public List<LocationCodeEntity> selLocationList() {
        return mapper.selLocationList();
    }

    public List<ApartmentInfoVo> getData(SearchDto dto) {

        List<ApartmentInfoVo> dbList = mapper.selApartmentInfoList(dto);
        if (dbList.size() > 0) {
            return dbList;
        }

        String lawdCd = dto.getExcd();
        String dealYm = String.format("%s%02d", dto.getYear(), dto.getMonth());
        System.out.println("dealym : " + dealYm);

        String serviceKey = "Y2UOCkD8Ilv2gViPGV33ddNTTQfRi92i8mRzUeQX+NgSiNTO3gp9hJZX4J6u8uXucMM6RdRBoGxMn6XHfsEzNA==";
        String url = "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";

        //URL 생성
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("LAWD_CD", lawdCd)
                .queryParam("DEAL_YMD", dealYm)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 5000)
                .build(false);

        RestTemplate rest = new RestTemplate();
        rest.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        ResponseEntity<String> responseEntity = rest.exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
        String result = responseEntity.getBody();
        //System.out.println("result : " + result);

        //컴포팅 도움, FAIL_ON_UNKNOWN_PROPERTIES : 알수없는 properties 가 발생했을때 (전체값 안받을때) 에러발생을 막아준다.
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);//오브젝트 맵퍼 객체화
        JsonNode jsonNode = null;//객체로 바꾸기 전 작업, 문자열을 노드로 바꿔주기 위해
        ApartmentInfoEntity[] list = null;
        List<ApartmentInfoEntity> list2 = null;
        try {
            jsonNode = om.readTree(result);
            //list = om.treeToValue(jsonNode.path("response").path("body").path("items").path("item"), ApartmentInfoEntity[].class);//리턴타입을 List 로 하고 (Node => Value)
            list2 = om.convertValue(jsonNode.path("response").path("body").path("items").path("item"), new TypeReference<List<ApartmentInfoEntity>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list2.size() == 0) { return new ArrayList<ApartmentInfoVo>(); }

        LocationCodeEntity codeEntity = mapper.selLocation(dto);

        ApartmentInfoDto apartDto = new ApartmentInfoDto();
        apartDto.setLocationcode(codeEntity.getCode());
        apartDto.setApartmentInfoList(list2);
        mapper.insApartmentInfoForeach(apartDto);

        return mapper.selApartmentInfoList(dto);

//        for(ApartmentInfoEntity item : list2) {
//            //문자열에 , 없에는 첫번째 방법
////            String dealAmaount = item.getDealamount();
////            dealAmaount = dealAmaount.replaceAll(",", "");
////            item.setDealamount(dealAmaount);
//            mapper.insApartmentInfo(item);
//        }
    }
}
