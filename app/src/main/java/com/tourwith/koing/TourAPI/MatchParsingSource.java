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
        keyWordToQueryNum.put("1","Seoul");

        keyWordToQueryNum.put("인천","2");
        keyWordToQueryNum.put("Incheon","2");
        keyWordToQueryNum.put("2","Incheon");

        keyWordToQueryNum.put("대전","3");
        keyWordToQueryNum.put("Daejeon","3");
        keyWordToQueryNum.put("3","Daejeon");

        keyWordToQueryNum.put("대구","4");
        keyWordToQueryNum.put("Daegu","4");
        keyWordToQueryNum.put("4","Daegu");

        keyWordToQueryNum.put("광주","5");
        keyWordToQueryNum.put("Gwangju","5");
        keyWordToQueryNum.put("5","Gwangju");

        keyWordToQueryNum.put("부산","6");
        keyWordToQueryNum.put("Busan","6");
        keyWordToQueryNum.put("6","Busan");

        keyWordToQueryNum.put("울산","7");
        keyWordToQueryNum.put("Ulsan","7");
        keyWordToQueryNum.put("7","Ulsan");

        keyWordToQueryNum.put("세종특별자치시","8");
        keyWordToQueryNum.put("Sejong","8");
        keyWordToQueryNum.put("8","Sejong");

        keyWordToQueryNum.put("경기도","31");
        keyWordToQueryNum.put("Gyeonggi","31");
        keyWordToQueryNum.put("31","Gyeonggi");

        keyWordToQueryNum.put("강원도","32");
        keyWordToQueryNum.put("Gangwon","32");
        keyWordToQueryNum.put("32","Gangwon");

        keyWordToQueryNum.put("충북","33");
        keyWordToQueryNum.put("Chungbuk","33");
        keyWordToQueryNum.put("33","Chungbuk");

        keyWordToQueryNum.put("충남","34");
        keyWordToQueryNum.put("Chungnam","34");
        keyWordToQueryNum.put("34","Chungnam");

        keyWordToQueryNum.put("경북","35");
        keyWordToQueryNum.put("Gyeongbuk","35");
        keyWordToQueryNum.put("35","Gyeongbuk");

        keyWordToQueryNum.put("경남","36");
        keyWordToQueryNum.put("Gyeongnam","36");
        keyWordToQueryNum.put("36","Gyeongnam");

        keyWordToQueryNum.put("전북","37");
        keyWordToQueryNum.put("Jeonbuk","37");
        keyWordToQueryNum.put("37","Jeonbuk");

        keyWordToQueryNum.put("전남","38");
        keyWordToQueryNum.put("Jeonnam","38");
        keyWordToQueryNum.put("38","Jeonnam");

        keyWordToQueryNum.put("제주도","39");
        keyWordToQueryNum.put("Jeju","39");
        keyWordToQueryNum.put("39","Jeju");
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
