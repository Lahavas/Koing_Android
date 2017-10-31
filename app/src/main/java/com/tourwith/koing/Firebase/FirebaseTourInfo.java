package com.tourwith.koing.Firebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by hanhb on 2017-10-31.
 */

public class FirebaseTourInfo {

    FirebaseDatabase database;
    DatabaseReference tourInfoRef;

    private RecyclerView recyclerView;
    List<String> list;
    Context context;

    public FirebaseTourInfo(Context context, RecyclerView recyclerView) {
        database = FirebaseDatabase.getInstance();
        tourInfoRef = database.getReference().child("tour_info");
        this.context = context;
        this.recyclerView = recyclerView;

    }

    public void getLikes(String contentId, final TextView numOfLikesText){
        DatabaseReference specificTourInfoRef = tourInfoRef.child(contentId);
        list.clear();

        specificTourInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    count++;
                    list.add(ds.getValue(String.class));

                }
                numOfLikesText.setText("Likes " + count);

                //리사이클러뷰에 추가
                // list 어댑터에 추가
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void doLike(final String uid, String contentId){

        final DatabaseReference specificTourInfoRef = tourInfoRef.child(contentId);
        specificTourInfoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getValue(String.class).equals(uid)){ //중복이면 좋아요 제거
                        specificTourInfoRef.child(ds.getKey()).removeValue();
                        return;
                    }
                }
                specificTourInfoRef.push().setValue(uid);
                //refresh
                refresh();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void refresh() {

    }

}
