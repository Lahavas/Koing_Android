package com.tourwith.koing.Model;

import com.google.firebase.database.ServerValue;

/**
 * Created by hanhb on 2017-09-28.
 */

public class Message {

    String content;
    String uid;
    boolean share;
    public Object timestamp;
    String pictureUrl;
    public Message() {

    }

    public Message(String content, String uid , boolean share) {
        this.content = content;
        this.uid = uid;
        this.share = share;
        timestamp = ServerValue.TIMESTAMP;
    }

    public Message(String content, String uid , boolean share, String pictureUrl) {
        this.content = content;
        this.uid = uid;
        this.share = share;
        timestamp = ServerValue.TIMESTAMP;
        this.pictureUrl = pictureUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getshare() {
        return share;
    }

    public void setshare(boolean share) {
        this.share = share;
    }


}
