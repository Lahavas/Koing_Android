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

    //ViewPager vp;
    FrameLayout container2;
    TextView tab_first, tab_second, tab_third, tab_fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //vp = (ViewPager) findViewById(R.id.vp);
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

        /*
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setOffscreenPageLimit(2);
        vp.setCurrentItem(0);

        tab_first.setOnClickListener(movePageListener);
        tab_first.setTag(0);
        tab_second.setOnClickListener(movePageListener);
        tab_second.setTag(1);
        tab_third.setOnClickListener(movePageListener);
        tab_third.setTag(2);

        tab_first.setSelected(true);

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                int i = 0;
                while(i<3)
                {
                    if(position==i)
                    {
                        ll.findViewWithTag(i).setSelected(true);
                    }
                    else
                    {
                        ll.findViewWithTag(i).setSelected(false);
                    }
                    i++;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        */
    }

    /*

    View.OnClickListener movePageListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            int tag = (int) v.getTag();

            int i = 0;
            while(i<3)
            {
                if(tag==i)
                {
                    ll.findViewWithTag(i).setSelected(true);
                }
                else
                {
                    ll.findViewWithTag(i).setSelected(false);
                }
                i++;
            }

            vp.setCurrentItem(tag);
        }
    }



    private class pagerAdapter extends FragmentStatePagerAdapter
    {
        public pagerAdapter(android.support.v4.app.FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position)
        {
            switch(position)
            {
                case 0:
                    return new StartFragment();
                case 1:
                    return new MessageFragment();
                case 2:
                    return new TourInfoFragment();
                default:
                    return null;
            }
        }
        @Override
        public int getCount()
        {
            return 3;
        }
    }
    */
}
