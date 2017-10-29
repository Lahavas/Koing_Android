package com.tourwith.koing.ViewPager;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 10. 28..
 */

public class ViewPagerHolder {
    RelativeLayout home_contain;

    CardView home_cv;

    ImageView home_person_iv;
    ImageView home_flag_iv;

    TextView home_name;
    TextView home_flag;
    TextView home_language;

    TextView home_tourist_type_title;
    TextView home_tourist_type;

    TextView home_trip_period_title;
    TextView home_trip_period;

    TextView home_description;

    ImageButton home_send_bt;

    public ViewPagerHolder(View itemView) {
        home_contain = (RelativeLayout) itemView.findViewById(R.id.home_contain);

        home_cv = (CardView) itemView.findViewById(R.id.home_cv);

        home_person_iv = (ImageView) itemView.findViewById(R.id.home_person_iv);
        home_flag_iv  = (ImageView) itemView.findViewById(R.id.home_flag_iv);

        home_name = (TextView) itemView.findViewById(R.id.home_name);
        home_flag = (TextView) itemView.findViewById(R.id.home_flag);
        home_language = (TextView) itemView.findViewById(R.id.home_language);

        home_tourist_type_title = (TextView) itemView.findViewById(R.id.home_tourist_type_title);
        home_tourist_type = (TextView) itemView.findViewById(R.id.home_tourist_type);

        home_trip_period_title = (TextView) itemView.findViewById(R.id.home_trip_period_title);
        home_trip_period = (TextView) itemView.findViewById(R.id.home_trip_period);

        home_description = (TextView) itemView.findViewById(R.id.home_description);

        home_send_bt = (ImageButton) itemView.findViewById(R.id.home_send_bt);
    }
}
