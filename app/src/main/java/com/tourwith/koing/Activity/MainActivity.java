package com.tourwith.koing.Activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tourwith.koing.Fragment.HomeFragment;
import com.tourwith.koing.Fragment.MessageFragment;
import com.tourwith.koing.Fragment.MyPageFragment;
import com.tourwith.koing.Fragment.TourInfoFragment;
import com.tourwith.koing.R;

public class MainActivity extends AppCompatActivity {

    FrameLayout container2;
    TextView tab_first, tab_second, tab_third, tab_fourth;
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

        tab_first = (TextView) findViewById(R.id.tab_first);
        tab_second = (TextView) findViewById(R.id.tab_second);
        tab_third = (TextView) findViewById(R.id.tab_third);
        tab_fourth = (TextView) findViewById(R.id.tab_fourth);

        tab_first.setSelected(true);

        tab_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, homeFragment)
                        .commit();

                tab_first.setSelected(true);
                tab_second.setSelected(false);
                tab_third.setSelected(false);
                tab_fourth.setSelected(false);
            }
        });

        tab_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, messageFragment)
                        .commit();
                tab_first.setSelected(false);
                tab_second.setSelected(true);
                tab_third.setSelected(false);
                tab_fourth.setSelected(false);
            }
        });

        tab_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, tourinfoFragment)
                        .commit();
                tab_first.setSelected(false);
                tab_second.setSelected(false);
                tab_third.setSelected(true);
                tab_fourth.setSelected(false);
            }
        });

        tab_fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, mypageFragment)
                        .commit();
                tab_first.setSelected(false);
                tab_second.setSelected(false);
                tab_third.setSelected(false);
                tab_fourth.setSelected(true);
            }
        });
    }
}
