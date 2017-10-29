package com.tourwith.koing.Model;

import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by hanhb on 2017-10-10.
 */

public class Tour {

    public Object timestamp; // 등록일
    public Object end_timestamp; //여행 시작일
    public Object start_timestamp; // 여행 종료일
    String introduction; //자기소개
    Map<String, String> tour_type;
    String uid;
    String key;

    public Tour() {

    }


    public Tour(Object end_timestamp, Object start_timestamp, String introduction, Map<String, String> tour_type, String uid) {
        timestamp = ServerValue.TIMESTAMP;
        this.end_timestamp = end_timestamp;
        this.start_timestamp = start_timestamp;
        this.introduction = introduction;
        this.tour_type = tour_type;
        this.uid = uid;
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

    public Object getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(Object end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

    public Object getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(Object start_timestamp) {
        this.start_timestamp = start_timestamp;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Map<String, String> getTour_type() {
        return tour_type;
    }

    public void setTour_type(Map<String, String> tour_type) {
        this.tour_type = tour_type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
