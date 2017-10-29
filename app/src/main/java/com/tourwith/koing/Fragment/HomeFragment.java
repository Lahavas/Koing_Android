package com.tourwith.koing.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.R;
import com.tourwith.koing.ViewPager.ViewPagerClickListener;
import com.tourwith.koing.ViewPager.ViewPagerHolder;
import com.tourwith.koing.ViewPager.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class HomeFragment extends Fragment implements ViewPagerClickListener {

    List<RecyclerItem> items = new ArrayList<>();
    ViewPager imageViewPager;
    Context context;
    ArrayList<String> imageArr;
    int baseItemViewHeight = 900;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        context = getContext();

        imageViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        imageViewPager.setPageMargin((int) getResources().getDimension(R.dimen.home_card_page_margin));
        imageViewPager.setClipToPadding(false);
        imageViewPager.setPadding((int) getResources().getDimension(R.dimen.home_card_padding_left), 0, (int) getResources().getDimension(R.dimen.home_card_padding_right), 0);

        imageViewPager.setAdapter(new ViewPagerAdapter(getActivity().getLayoutInflater(),getContext(),this, 5));

        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(context, "position : " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //imageViewPager.setPageTransformer(true, new MagnifyingAnimPageTransInterface(200, baseItemViewHeight));


        return view;
    }

    @Override
    public void onCardClicked(ViewPagerHolder holder, int position) {

    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
