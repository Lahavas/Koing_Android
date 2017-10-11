package com.tourwith.koing.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tourwith.koing.Fragment.HomeFragment;
import com.tourwith.koing.Fragment.MessageFragment;
import com.tourwith.koing.Fragment.MyPageFragment;
import com.tourwith.koing.R;
import com.tourwith.koing.Fragment.TourInfoFragment;

public class MainActivity extends AppCompatActivity {

    FrameLayout container2;
    TextView tab_first, tab_second, tab_third, tab_fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container2 = (FrameLayout) findViewById(R.id.container2);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, new HomeFragment())
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
                        .replace(R.id.container2, new HomeFragment())
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
                        .replace(R.id.container2, new MessageFragment())
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
                        .replace(R.id.container2, new TourInfoFragment())
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
                        .replace(R.id.container2, new MyPageFragment())
                        .commit();
                tab_first.setSelected(false);
                tab_second.setSelected(false);
                tab_third.setSelected(false);
                tab_fourth.setSelected(true);
            }
        });
    }
}
