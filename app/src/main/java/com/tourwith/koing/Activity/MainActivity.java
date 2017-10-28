package com.tourwith.koing.Activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourwith.koing.Fragment.HomeFragment;
import com.tourwith.koing.Fragment.MessageFragment;
import com.tourwith.koing.Fragment.MyPageFragment;
import com.tourwith.koing.Fragment.TourInfoFragment;
import com.tourwith.koing.R;

public class MainActivity extends AppCompatActivity {

    FrameLayout container2;
    ImageView tab_home, tab_tour, tab_message, tab_profile;
    Fragment homeFragment;
    Fragment messageFragment;
    Fragment mypageFragment;
    Fragment tourinfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    1 );
        }

        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        mypageFragment = new MyPageFragment();
        tourinfoFragment = new TourInfoFragment();

        container2 = (FrameLayout) findViewById(R.id.container2);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, homeFragment)
                    .commit();
        }

        tab_home = (ImageView) findViewById(R.id.tab_home);
        tab_tour = (ImageView) findViewById(R.id.tab_tour);
        tab_message = (ImageView) findViewById(R.id.tab_message);
        tab_profile = (ImageView) findViewById(R.id.tab_profile);

        tab_home.setBackgroundResource(R.drawable.btn_tap_home_t);

        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, homeFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_t);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_n);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_n);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_n);
            }
        });

        tab_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, tourinfoFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_n);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_t);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_n);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_n);
            }
        });

        tab_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, messageFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_n);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_n);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_t);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_n);
            }
        });

        tab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, mypageFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_n);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_n);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_n);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_t);
            }
        });

    }
}
