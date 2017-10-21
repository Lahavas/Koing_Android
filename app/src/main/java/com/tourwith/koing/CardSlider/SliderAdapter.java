package com.tourwith.koing.CardSlider;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.Model.TourInfoItem;
import com.tourwith.koing.R;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderCardViewHolder> {

    private final int count;
    private final View.OnClickListener listener;

    Context context;
    List<TourInfoItem> items;

    public SliderAdapter(int count, View.OnClickListener listener, Context context, List<TourInfoItem> items) {
        this.count = count;
        this.listener = listener;
        this.context = context;
        this.items = items;
    }

    @Override
    public SliderCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_card_view, parent, false);

        return new SliderCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderCardViewHolder holder, int position) {

        TourInfoItem tourInfoItem = items.get(position);
        Glide.with(context).load(tourInfoItem.getFirstimage()).override(1000,600).into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return count;
    }

}
