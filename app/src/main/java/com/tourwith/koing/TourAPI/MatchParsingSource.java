package com.tourwith.koing.TourAPI;

import java.util.HashMap;

/**
 * Created by MSI on 2017-10-09.
 */

public class MatchParsingSource {
    static HashMap<String, String> keyWordToQueryNum;
    static{
        keyWordToQueryNum = new HashMap<String, String>();
        keyWordToQueryNum.put("문화시설","14");
        keyWordToQueryNum.put("관광지","12");
        keyWordToQueryNum.put("축제공연행사","15");
        keyWordToQueryNum.put("여행코스","25");
        keyWordToQueryNum.put("레포츠","28");
        keyWordToQueryNum.put("숙박","32");
        keyWordToQueryNum.put("쇼핑","38");
        keyWordToQueryNum.put("음식점","39");

        keyWordToQueryNum.put("Tourist Attractions","76");
        keyWordToQueryNum.put("Cultural Facilities","78");
        keyWordToQueryNum.put("Festivals/Events/Performances","85");
        keyWordToQueryNum.put("Leisure/Sports","75");
        keyWordToQueryNum.put("Accommodation","80");
        keyWordToQueryNum.put("Shopping","79");
        keyWordToQueryNum.put("Dining","82");
        keyWordToQueryNum.put("Transportaion","77");

        keyWordToQueryNum.put("서울","1");
        keyWordToQueryNum.put("Seoul","1");
        keyWordToQueryNum.put("인천","2");
        keyWordToQueryNum.put("Incheon","2");
        keyWordToQueryNum.put("대전","3");
        keyWordToQueryNum.put("Daejeon","3");
        keyWordToQueryNum.put("대구","4");
        keyWordToQueryNum.put("Daegu","4");
        keyWordToQueryNum.put("광주","5");
        keyWordToQueryNum.put("Gwangju","5");
        keyWordToQueryNum.put("부산","6");
        keyWordToQueryNum.put("Busan","6");
        keyWordToQueryNum.put("울산","7");
        keyWordToQueryNum.put("Ulsan","7");
        keyWordToQueryNum.put("세종특별자치시","8");
        keyWordToQueryNum.put("Sejong","8");
        keyWordToQueryNum.put("경기도","31");
        keyWordToQueryNum.put("Gyeonggi-do","31");
        keyWordToQueryNum.put("강원도","32");
        keyWordToQueryNum.put("Gangwon-do","32");
        keyWordToQueryNum.put("충북","33");
        keyWordToQueryNum.put("Chungcheongbuk-do","33");
        keyWordToQueryNum.put("충남","34");
        keyWordToQueryNum.put("Chungcheongnam-do","34");
        keyWordToQueryNum.put("경북","35");
        keyWordToQueryNum.put("Gyeongsangbuk-do","35");
        keyWordToQueryNum.put("경남","36");
        keyWordToQueryNum.put("Gyeongsangnam-do","36");
        keyWordToQueryNum.put("전북","37");
        keyWordToQueryNum.put("Jeollabuk-do","37");
        keyWordToQueryNum.put("전남","38");
        keyWordToQueryNum.put("Jeollanam-do","38");
        keyWordToQueryNum.put("제주도","39");
        keyWordToQueryNum.put("Jeju-do","39");
    }

    public static String getQueryNum(String keyWord){
        String queryNum = keyWordToQueryNum.get(keyWord);
        if(queryNum==null)
            return "";
        else
            return queryNum;
    }
    //contentTypeId=
    //문화시설(14)관광지(12)축제공연행사(15)여행코스(25)레포츠(28)숙박(32)쇼핑(38)음식점(39)
    //areaCode=
    //서울(1)인천(2)대전(3)대구(4)광주(5)부산(6)울산(7)세종특별자치시(8)경기도(31)
    // 강원도(32)충북(33)충남(34)경북(35)경남(36)전북(37)전남(38)제주도(39)

}
