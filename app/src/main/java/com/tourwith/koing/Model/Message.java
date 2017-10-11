package com.tourwith.koing.Model;

import com.google.firebase.database.ServerValue;

/**
 * Created by hanhb on 2017-09-28.
 */

public class Message {

    String content;
    String uid;
    boolean isRead;
    public Object timestamp;
    public Message() {

    }

    public Message(String content, String uid , boolean isRead) {
        this.content = content;
        this.uid = uid;
        this.isRead = isRead;
        timestamp = ServerValue.TIMESTAMP;
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

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }


}
