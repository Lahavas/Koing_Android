package com.tourwith.koing.LikesRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 10. 30..
 */

public class LikesViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public LikesViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.tour_info_likes_peoples_iv);
    }
}
