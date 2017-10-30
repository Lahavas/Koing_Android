package com.tourwith.koing.Model;

import com.google.firebase.database.ServerValue;

/**
 * Created by hanhb on 2017-10-10.
 */

public class Tour {

    public Object timestamp; // 등록일
    public Long end_timestamp; //여행 시작일
    public Long start_timestamp; // 여행 종료일

    String tour_type; //투어 타입
    String area;
    String uid;
    String key;

    String lang1 = "";
    String lang2 = "";
    String lang3 = "";

    public Tour() {
        timestamp = ServerValue.TIMESTAMP;
    }

    public Tour(String uid, String area, String tour_type) {
        timestamp = ServerValue.TIMESTAMP;
        this.uid = uid;

        this.area = area;
        this.tour_type = tour_type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public Long getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(Long end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

    public Long getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(Long start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    public String getTour_type() {
        return tour_type;
    }

    public void setTour_type(String tour_type) {
        this.tour_type = tour_type;
    }

    public String getLang1() {
        return lang1;
    }

    public void setLang1(String lang1) {
        this.lang1 = lang1;
    }

    public String getLang2() {
        return lang2;
    }

    public void setLang2(String lang2) {
        this.lang2 = lang2;
    }

    public String getLang3() {
        return lang3;
    }

    public void setLang3(String lang3) {
        this.lang3 = lang3;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
