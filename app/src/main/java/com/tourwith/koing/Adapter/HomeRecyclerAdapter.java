package com.tourwith.koing.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tourwith.koing.Adapter.ViewHolder.HomeCardViewHolder;
import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.R;

import java.util.List;

/**
 * Created by Munak on 2017. 9. 24..
 */

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeCardViewHolder> {

    private List<RecyclerItem> items;

    public HomeRecyclerAdapter(List<RecyclerItem> items) {
        this.items = items;
    }

    @Override
    public HomeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false);

        return new HomeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeCardViewHolder holder, int position) {
        RecyclerItem item = this.items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
