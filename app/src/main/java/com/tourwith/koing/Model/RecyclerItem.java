package com.tourwith.koing.Model;

/**
 * Created by Munak on 2017. 9. 24..
 */

public class RecyclerItem {

    private int image;
    private String title;

    public RecyclerItem(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
