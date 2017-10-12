package com.tourwith.koing.TourAPI;

/**
 * Created by MSI on 2017-10-09.
 */

public class MatchParsingSource {
    public static String getNumOfSource(String src){
        if(src.equals("문화시설")) return "14";
        else if(src.equals("관광지")) return "12";
        else if(src.equals("축제공연행사")) return "15";
        else if(src.equals("여행코스")) return "25";
        else if(src.equals("레포츠")) return "28";
        else if(src.equals("숙박")) return "32";
        else if(src.equals("쇼핑")) return "38";
        else if(src.equals("음식점")) return "39";
        else if(src.equals("서울")) return "1";
        else if(src.equals("인천")) return "2";
        else if(src.equals("대전")) return "3";
        else if(src.equals("대구")) return "4";
        else if(src.equals("광주")) return "5";
        else if(src.equals("부산")) return "6";
        else if(src.equals("울산")) return "7";
        else if(src.equals("세종특별자치시")) return "8";
        else if(src.equals("경기도")) return "31";
        else if(src.equals("강원도")) return "32";
        else if(src.equals("충북")) return "33";
        else if(src.equals("충남")) return "34";
        else if(src.equals("경북")) return "35";
        else if(src.equals("경남")) return "36";
        else if(src.equals("전북")) return "37";
        else if(src.equals("전남")) return "38";
        else if(src.equals("제주도")) return "39";
        else return "";
        //contentTypeId=
        //문화시설(14)관광지(12)축제공연행사(15)여행코스(25)레포츠(28)숙박(32)쇼핑(38)음식점(39)
        //areaCode=
        //서울(1)인천(2)대전(3)대구(4)광주(5)부산(6)울산(7)세종특별자치시(8)경기도(31)
        // 강원도(32)충북(33)충남(34)경북(35)경남(36)전북(37)전남(38)제주도(39)
    }
}
