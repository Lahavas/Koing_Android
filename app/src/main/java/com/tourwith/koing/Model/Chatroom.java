package com.tourwith.koing.Model;

/**
 * Created by hanhb on 2017-09-26.
 */

public class Chatroom {

    String mUID; //내 uid
    String oUID; //상대 uid
    String key;

    public Chatroom(){}

    public Chatroom(String mUID, String oUID, String key) {
        this.mUID = mUID;
        this.oUID = oUID;
        this.key = key;
    }
    public Chatroom(String oUID, String key) {
        this.oUID = oUID;
        this.key = key;
    }

    public String getmUID() {
        return mUID;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }

    public String getoUID() {
        return oUID;
    }

    public void setoUID(String oUID) {
        this.oUID = oUID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
