package com.tourwith.koing.Firebase;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.ViewPager.ViewPagerAdapter;
import com.tourwith.koing.ViewPager.ViewPagerClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanhb on 2017-10-11.
 */

public class FirebaseTour {

    private FirebaseDatabase database;
    private DatabaseReference tourRef;

    private ViewPager viewPager;
    private List<Tour> tourList = new ArrayList<Tour>();
    private Activity activity;
    private Context context;
    private ViewPagerClickListener listener;


    public FirebaseTour() {
        database = FirebaseDatabase.getInstance();
        tourRef = database.getReference().child("tour");

    }

    public FirebaseTour(ViewPager viewPager, Activity activity, Context context, ViewPagerClickListener listener) {
        database = FirebaseDatabase.getInstance();
        tourRef = database.getReference().child("tour");
        this.activity = activity;
        this.context = context;
        this.viewPager = viewPager;
        this.listener = listener;

        tourRef.addListenerForSingleValueEvent(new TourEventListener()); //리스너를 추가하여 리사이클러뷰 갱신

    }

    private class TourEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            getTours(dataSnapshot);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    }

    private void getTours(DataSnapshot dataSnapshot) {
        tourList.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            Tour vo = ds.getValue(Tour.class);
            tourList.add(vo);
        }


        //어댑터에 리스트 추가

        //MessageAdapter adapter = new MessageAdapter(context, list, chatroom.getmUID(), chatroom.getoUID(), oProfileImgData);
        //adapter.setList(list);
        //recyclerView.setLayoutManager(manager);
        //recyclerView.setAdapter(adapter);


        viewPager.setAdapter(new ViewPagerAdapter(activity.getLayoutInflater(),context, listener, tourList.size(), tourList));


    }

    //리프레쉬 : 리사이클러 뷰 갱신
    public void refresh(){
        tourRef.addListenerForSingleValueEvent(new TourEventListener());
    }

    public void writeTour(Tour tour){
        DatabaseReference specifiedTourRef = tourRef.push();
        tour.setKey(specifiedTourRef.getKey());
        specifiedTourRef.setValue(tour);

        //User의 투어 리스트에 추가
        DatabaseReference toursWithinUserRef = database.getReference().child("user").child(tour.getUid()).child("tours").push();
        toursWithinUserRef.setValue(tour.getKey());

    }


}
