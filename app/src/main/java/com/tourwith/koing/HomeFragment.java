package com.tourwith.koing;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class HomeFragment extends Fragment implements HomeCardClickListener {

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
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(layoutManager);


        RecyclerView.Adapter adapter = new HomeRecyclerAdapter(view.getContext(), items, this);
        recyclerView.setAdapter(adapter);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);


        return view;
    }

    @Override
    public void onCardClicked(HomeCardViewHolder holder, int position) {
/*
        DetailFragment cardDetails = DetailFragment.newInstance();

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cardDetails.setSharedElementEnterTransition(new DetailsTransition());
            cardDetails.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            cardDetails.setSharedElementReturnTransition(new DetailsTransition());
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.image, "kittenImage")
                .replace(R.id.container2, cardDetails)
                .addToBackStack(null)
                .commit();
                */
    }

}
