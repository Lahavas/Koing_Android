package com.tourwith.koing.TourAPI;

import com.tourwith.koing.Model.TourInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by MSI on 2017-09-23.
 */

public interface TourInfoRetrofitService {

    @GET("rest/KorService/areaBasedList")
    Call<TourInfoResponse> getKorTripInfo(
            @Query("ServiceKey") String key,
            @Query("contentTypeId") String contentTypeId,
            @Query("areaCode") String areaCode,
            @Query("sigunguCode") String sigunguCode,
            @Query("cat1") String cat1,
            @Query("cat2") String cat2,
            @Query("cat3") String cat3,
            @Query("listYN") String listYN,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("arrange") String arrange,
            @Query("numOfRows") String numOfRows,
            @Query("pageNo") String pageNo
    );

    @GET("rest/EngService/areaBasedList")
    Call<TourInfoResponse> getEngTripInfo(
            @Query("ServiceKey") String key,
            @Query("contentTypeId") String contentTypeId,
            @Query("areaCode") String areaCode,
            @Query("sigunguCode") String sigunguCode,
            @Query("cat1") String cat1,
            @Query("cat2") String cat2,
            @Query("cat3") String cat3,
            @Query("listYN") String listYN,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("arrange") String arrange,
            @Query("numOfRows") String numOfRows,
            @Query("pageNo") String pageNo
    );

    @GET("rest/KorService/locationBasedList")
    Call<TourInfoResponse> getKorTripInfo2(
            @Query("ServiceKey") String key,
            @Query("contentTypeId") String contentTypeId,
            @Query("mapX") String mapX,
            @Query("mapY") String mapY,
            @Query("radius") String radius,
            @Query("listYN") String listYN,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("arrange") String arrange,
            @Query("numOfRows") String numOfRows,
            @Query("pageNo") String pageNo
    );

    @GET("rest/EngService/locationBasedList")
    Call<TourInfoResponse> getEngTripInfo2(
            @Query("ServiceKey") String key,
            @Query("contentTypeId") String contentTypeId,
            @Query("mapX") String mapX,
            @Query("mapY") String mapY,
            @Query("radius") String radius,
            @Query("listYN") String listYN,
            @Query("MobileOS") String MobileOS,
            @Query("MobileApp") String MobileApp,
            @Query("arrange") String arrange,
            @Query("numOfRows") String numOfRows,
            @Query("pageNo") String pageNo
    );
}


//http://api.visitkorea.or.kr/openapi/service/rest/EngService/locationBasedList?
// ServiceKey=인증키&
// contentTypeId=78&
// mapX=126.981106&
// mapY=37.568477&
// radius=2000&
// listYN=Y&
// MobileOS=ETC&
// MobileApp=TourAPI3.0_Guide&
// arrange=P&
// numOfRows=12&
// pageNo=1






//http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?
// ServiceKey=인증키&
// contentTypeId=&
// mapX=126.981106&
// mapY=37.568477&
// radius=2000&
// listYN=Y&
// MobileOS=ETC&
// MobileApp=TourAPI3.0_Guide&
// arrange=A&
// numOfRows=12&
// pageNo=1


