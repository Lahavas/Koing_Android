package com.tourwith.koing.Firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tourwith.koing.Activity.TripCardActivity;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.ViewPager.ViewPagerAdapter;
import com.tourwith.koing.ViewPager.ViewPagerClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hanhb on 2017-10-11.
 */

public class FirebaseTour {

    private FirebaseDatabase database;
    private DatabaseReference tourRef;

    private ViewPager viewPager;
    private List<Tour> tourList = new ArrayList<Tour>();
    private Activity activity, tripActivity;
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

    public FirebaseTour(Activity tripActivity) {
        database = FirebaseDatabase.getInstance();
        tourRef = database.getReference().child("tour");

        this.tripActivity = tripActivity;

    }

    public void getTourOfTripcard(String key, final TextView trip_sub_lang1, final TextView trip_sub_lang2, final TextView trip_trip_period, final TextView trip_tourist_type, final TextView trip_area_text) {

        tourRef.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Tour tour = dataSnapshot.getValue(Tour.class);

                /* period */
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

                Calendar calStart = Calendar.getInstance();
                Calendar calEnd = Calendar.getInstance();

                calStart.setTimeInMillis(tour.getStart_timestamp());
                calEnd.setTimeInMillis(tour.getEnd_timestamp());

                trip_trip_period.setText(fmt.format(calStart.getTime()) + " ~ " + fmt.format(calEnd.getTime()));

                if(!tour.getLang1().equals(""))
                    trip_sub_lang1.setText(" > " + tour.getLang1());
                trip_sub_lang2.setText(tour.getLang2());

                trip_tourist_type.setText(tour.getTour_type());

                if(trip_area_text != null) {
                    trip_area_text.setText(tour.getArea());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





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

        viewPager.setAdapter(new ViewPagerAdapter(activity.getLayoutInflater(),context, listener, tourList.size(), tourList));

    }

    public void filter(final Tour filter){
        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tourList.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Tour vo = ds.getValue(Tour.class);
                    boolean addToList = true;

                    //filtering
                    if(!filter.getNationality().equals(vo.getNationality()))
                        addToList = false;
                    if(!filter.getArea().equals(vo.getArea()))
                        addToList = false;
                    if(!filter.getTour_type().equals("All type") && !filter.getTour_type().equals(vo.getTour_type()))
                        addToList = false;
                    if(!(filter.getLang1().equals(vo.getLang1()) || filter.getLang1().equals(vo.getLang2())
                            || (!filter.getLang2().equals("") && filter.getLang2().equals(filter.getLang1()))
                            || (!filter.getLang2().equals("") && filter.getLang2().equals(filter.getLang2())))){
                        addToList = false;
                    }

                    if((filter.getEnd_timestamp()+43200000l) < vo.getStart_timestamp() || vo.getEnd_timestamp() < (filter.getStart_timestamp()-43200000l))
                        addToList = false;

                    if(addToList)
                        tourList.add(vo);

                }

                viewPager.setAdapter(new ViewPagerAdapter(activity.getLayoutInflater(),context, listener, tourList.size(), tourList));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }


    //리프레쉬 : 리사이클러 뷰 갱신
    public void refresh(){
        tourRef.addListenerForSingleValueEvent(new TourEventListener());
    }

    public void getToursOfUser(final Activity currentActivity, final String mUid, final String uid, final LinearLayout[] layouts, final TextView[] areaTexts, final TextView[] typeTexts, final TextView[] langTexts){

        DatabaseReference userRef = database.getReference().child("user").child(uid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                String mainLang = dataSnapshot.child("mainLang").getValue(String.class);
                for(final DataSnapshot ds : dataSnapshot.child("tours").getChildren()){

                    langTexts[i].setText(mainLang);
                    FirebaseTour firebaseTour = new FirebaseTour();
                    firebaseTour.getTour(ds.getValue(String.class), areaTexts[i], typeTexts[i], langTexts[i]);

                    layouts[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(currentActivity, TripCardActivity.class);
                            intent.putExtra("tripuid",uid);
                            intent.putExtra("tripkey",ds.getValue(String.class));
                            intent.putExtra("mUid", mUid);
                            currentActivity.startActivityForResult(intent, 2001);
                        }
                    });

                    layouts[i].setVisibility(View.VISIBLE);
                    i++;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getTour(String key, final TextView areaText, final TextView typeText, final TextView langText){

        DatabaseReference specificTourRef = database.getReference().child("tour").child(key);

        specificTourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                areaText.setText(dataSnapshot.child("area").getValue(String.class));
                typeText.setText(dataSnapshot.child("tour_type").getValue(String.class));
                if(langText !=null )langText.append(" > "+dataSnapshot.child("lang1").getValue(String.class) + " " + dataSnapshot.child("lang2").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void writeTour(final Tour tour, final Activity activity){
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Processing ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        DatabaseReference userRef = database.getReference().child("user").child(tour.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = 0;
                DataSnapshot tourSnapshot = dataSnapshot.child("tours");
                for(DataSnapshot ds : tourSnapshot.getChildren()) {
                    count++;
                }

                progressDialog.dismiss();
                if(count>=3){
                    MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.CARD_MAX_INVALID);
                    messageDialogFragment.setActivity(activity);
                    messageDialogFragment.show(activity.getFragmentManager(), "");

                } else {
                    DatabaseReference specifiedTourRef = tourRef.push();
                    tour.setKey(specifiedTourRef.getKey());
                    tour.setNationality(dataSnapshot.child("nationality").getValue(String.class));
                    specifiedTourRef.setValue(tour);

                    //User의 투어 리스트에 추가
                    DatabaseReference toursWithinUserRef = database.getReference().child("user").child(tour.getUid()).child("tours").push();
                    toursWithinUserRef.setValue(tour.getKey());

                    activity.setResult(1000);
                    activity.finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    public void destroyTour(final String key, String uid, final Activity activity, final MessageDialogFragment messageDialogFragment){

        final DatabaseReference specificTourRef = tourRef.child(key);
        final DatabaseReference specificToursOfUserRef = database.getReference().child("user").child(uid).child("tours");

        specificToursOfUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    if(ds.getValue(String.class).equals(key)){
                        specificTourRef.removeValue();
                        specificToursOfUserRef.child(ds.getKey()).removeValue();
                        messageDialogFragment.dismiss();
                        activity.setResult(3000);
                        activity.finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
