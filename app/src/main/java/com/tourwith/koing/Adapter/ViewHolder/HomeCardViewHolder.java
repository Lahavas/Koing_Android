package com.tourwith.koing.Adapter.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 10. 4..
 */

public class HomeCardViewHolder extends RecyclerView.ViewHolder {

    private RecyclerItem item;

    private ImageView imageView;
    private TextView titleTextView;
    private CardView cardView;

    public HomeCardViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.image);
        titleTextView = (TextView)itemView.findViewById(R.id.title);
        cardView = (CardView)itemView.findViewById(R.id.card_view);
    }

    public void bind(RecyclerItem item) {

        this.item = item;

        titleTextView.setText(this.item.getTitle());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
