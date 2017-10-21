package com.tourwith.koing.CardSlider;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.tourwith.koing.R;

public class SliderCardViewHolder extends RecyclerView.ViewHolder {
    public ImageView itemImage;

    public SliderCardViewHolder(View itemView) {
        super(itemView);

        itemImage = (ImageView) itemView.findViewById(R.id.image);
        itemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

}