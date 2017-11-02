package com.tourwith.koing.Activity;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Firebase.FirebaseTour;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 11. 1..
 */

public class TripCardActivity extends AppCompatActivity {

    /* trip card view varialbes*/
    TextView trip_area_text;

    ImageButton trip_close_bt;

    ImageView trip_person_iv;
    ImageView trip_flag_iv;

    TextView trip_name;
    TextView trip_flag;

    TextView trip_main_lang;
    TextView trip_sub_lang1;
    TextView trip_sub_lang2;

    TextView trip_tourist_type;
    TextView trip_trip_period;

    TextView trip_description;

    ImageButton trip_edit_bt;

    ImageButton trip_delete_bt;

    /* uid and key variables */
    String mUid;
    String uid;
    String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit_child_view);

        /* hide actionbar */
        getSupportActionBar().hide();


        /* find view id */
        findView();


        /* get uid and key */
        uid = getIntent().getStringExtra("tripuid");
        key = getIntent().getStringExtra("tripkey");
        mUid = getIntent().getStringExtra("mUid");

        /* set view content */
        setView();

        /* close button */
        trip_close_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /* view find part */
    private void findView() {
        trip_area_text = (TextView) findViewById(R.id.trip_area_text);

        trip_close_bt = (ImageButton) findViewById(R.id.trip_close_bt);

        trip_person_iv = (ImageView) findViewById(R.id.trip_person_iv);
        trip_person_iv.setBackground(new ShapeDrawable(new OvalShape()));
        trip_person_iv.setClipToOutline(true);
        trip_person_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        trip_flag_iv = (ImageView) findViewById(R.id.trip_flag_iv);
        trip_flag_iv.setBackground(new ShapeDrawable(new OvalShape()));
        trip_flag_iv.setClipToOutline(true);
        trip_flag_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        trip_name = (TextView) findViewById(R.id.trip_name);
        trip_flag = (TextView) findViewById(R.id.trip_flag);

        trip_main_lang = (TextView) findViewById(R.id.trip_main_lang);
        trip_sub_lang1 = (TextView) findViewById(R.id.trip_sub_lang1);
        trip_sub_lang2 = (TextView) findViewById(R.id.trip_sub_lang2);


        trip_tourist_type = (TextView) findViewById(R.id.trip_tourist_type);
        trip_trip_period = (TextView) findViewById(R.id.trip_trip_period);

        trip_description = (TextView) findViewById(R.id.trip_description);

        trip_edit_bt = (ImageButton) findViewById(R.id.trip_edit_bt);

        trip_delete_bt = (ImageButton) findViewById(R.id.trip_delete_bt);
    }

    /* view set part */
    private void setView() {
        if(!mUid.equals(uid)){
            trip_delete_bt.setVisibility(View.GONE);
            trip_edit_bt.setVisibility(View.GONE);
        }

        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUser(this, uid, trip_name, trip_flag, trip_main_lang, trip_description, trip_person_iv, trip_flag_iv);

        final FirebaseTour firebaseTour = new FirebaseTour();
        firebaseTour.getTourOfTripcard(key, trip_sub_lang1, trip_sub_lang2, trip_trip_period, trip_tourist_type, trip_area_text);



        trip_delete_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.CHECK_DESTROY);
                messageDialogFragment.setFirebaseTour(uid, key, firebaseTour, TripCardActivity.this);
                messageDialogFragment.show(getFragmentManager(), "");
            }
        });

        trip_edit_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripCardActivity.this, TourEditActivity.class);
                intent.putExtra("edituid",uid);
                intent.putExtra("editkey",key);
                startActivity(intent);
            }
        });

    }
}
