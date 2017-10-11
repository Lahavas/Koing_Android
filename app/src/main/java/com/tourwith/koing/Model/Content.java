package com.tourwith.koing.Model;

/**
 * Created by hanhb on 2017-09-15.
 */

@Deprecated
public class Content {
    private String uid;
    private String key;
    private String title;
    private String content;

    public Content(){}

    public Content(String uid, String key, String title, String content) {
        this.uid = uid;
        this.key = key;
        this.title = title;
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
