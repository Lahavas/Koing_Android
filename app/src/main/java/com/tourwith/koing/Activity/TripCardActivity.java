package com.tourwith.koing.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView trip_language;

    TextView trip_tourist_type;
    TextView trip_trip_period;

    TextView trip_description;

    ImageButton trip_edit_bt;

    ImageButton trip_delete_bt;

    /* uid variables */
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


        /* get uid */
        uid = getIntent().getStringExtra("tripuid");
        key = getIntent().getStringExtra("tripkey");

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
        trip_flag_iv = (ImageView) findViewById(R.id.trip_flag_iv);

        trip_name = (TextView) findViewById(R.id.trip_name);
        trip_flag = (TextView) findViewById(R.id.trip_flag);
        trip_language = (TextView) findViewById(R.id.trip_language);

        trip_tourist_type = (TextView) findViewById(R.id.trip_tourist_type);
        trip_trip_period = (TextView) findViewById(R.id.trip_trip_period);

        trip_description = (TextView) findViewById(R.id.trip_description);

        trip_edit_bt = (ImageButton) findViewById(R.id.trip_edit_bt);

        trip_delete_bt = (ImageButton) findViewById(R.id.trip_delete_bt);
    }

    /* view set part */
    private void setView() {

    }
}
