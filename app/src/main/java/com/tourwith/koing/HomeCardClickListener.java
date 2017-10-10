package com.tourwith.koing;

/**
 * Created by Munak on 2017. 10. 4..
 */

public interface HomeCardClickListener {
    /**
     * Called when a kitten is clicked
     * @param holder The ViewHolder for the clicked Card
     * @param position The position in the grid of the Card that was clicked
     */
    void onCardClicked(HomeCardViewHolder holder, int position);
}
