package com.tourwith.koing.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tourwith.koing.Adapter.HomeRecyclerAdapter;
import com.tourwith.koing.CardSlider.CardSnapHelper;
import com.tourwith.koing.CardSlider.SliderAdapter;
import com.tourwith.koing.Model.RecyclerItem;
import com.tourwith.koing.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class HomeFragment extends Fragment {

    List<RecyclerItem> items = new ArrayList<>();

    public HomeFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        RecyclerItem[] item = new RecyclerItem[5];
        item[0] = new RecyclerItem(1,"#1");
        item[1] = new RecyclerItem(2,"#2");
        item[2] = new RecyclerItem(3,"#3");
        item[3] = new RecyclerItem(4,"#4");
        item[4] = new RecyclerItem(5,"#5");

        for(int i=0;i<5;i++) {
            items.add(item[i]);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        int[] pics = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};

        SliderAdapter sliderAdapter = new SliderAdapter(pics, 20, null);


        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(sliderAdapter);

        /*
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);
        */

        //RecyclerView.Adapter adapter = new HomeRecyclerAdapter(view.getContext(), items);
        //recyclerView.setAdapter(adapter);

        /*
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        */
        new CardSnapHelper().attachToRecyclerView(recyclerView);

        return view;
    }

}
