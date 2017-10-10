package com.tourwith.koing;

/**
 * Created by Munak on 2017. 9. 24..
 */

public class RecyclerItem {
    int image;
    String title;

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

    RecyclerItem(int image, String title) {
        this.image = image;
        this.title = title;
    }
}
