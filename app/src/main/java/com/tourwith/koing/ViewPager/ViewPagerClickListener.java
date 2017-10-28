package com.tourwith.koing.ViewPager;

/**
 * Created by Munak on 2017. 10. 28..
 */

public interface ViewPagerClickListener {

    /**
     * Called when a card is clicked
     * @param holder The ViewHolder for the clicked card
     * @param position The position in the grid of the card that was clicked
     */
    void onCardClicked(ViewPagerHolder holder, int position);
}
