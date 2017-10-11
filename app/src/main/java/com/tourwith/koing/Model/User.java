package com.tourwith.koing.Model;

import com.google.firebase.database.ServerValue;

/**
 * Created by hanhb on 2017-10-10.
 */

public class User {

    String mainLang;
    String nationality;
    String nickname;
    Object timestamp;

    public User() {
    }

    public User(String mainLang, String nationality, String nickname) {
        timestamp = ServerValue.TIMESTAMP;
        this.mainLang = mainLang;
        this.nationality = nationality;
        this.nickname = nickname;
    }

    public String getMainLang() {
        return mainLang;
    }

    public void setMainLang(String mainLang) {
        this.mainLang = mainLang;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
