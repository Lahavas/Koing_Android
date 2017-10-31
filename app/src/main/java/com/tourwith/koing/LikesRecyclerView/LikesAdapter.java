package com.tourwith.koing.LikesRecyclerView;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 10. 30..
 */

public class LikesAdapter extends RecyclerView.Adapter<LikesViewHolder> {

    public LikesAdapter(/*int count, View.OnClickListener listener, Context context, List<TourInfoItem> items*/) {
        /*
        this.count = count;
        this.listener = listener;
        this.context = context;
        this.items = items;
        */
    }


    @Override
    public LikesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_likes_view, parent, false);

        return new LikesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LikesViewHolder holder, int position) {


        /*
        * tour info view likes people image rounding
        */
        holder.imageView.setBackground(new ShapeDrawable(new OvalShape()));
        holder.imageView.setClipToOutline(true);
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
