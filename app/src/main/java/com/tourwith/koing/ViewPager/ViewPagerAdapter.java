package com.tourwith.koing.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Activity.UserInformationActivity;
import com.tourwith.koing.Firebase.FirebaseChatroom;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Firebase.FirebaseTour;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.R;

import java.util.List;

/**
 * Created by Munak on 2017. 10. 28..
 */

public class ViewPagerAdapter extends PagerAdapter {
    LayoutInflater inflater;
    Context context;
    //FragmentManager fm;
    //Activity activity;
    ViewPagerClickListener mListener;
    int totalcount;
    List<Tour> tourList;

    //전달받은 inflater 멤버변수에 전달
    public ViewPagerAdapter(LayoutInflater inflater, Context context, ViewPagerClickListener listener, int totalcount, List<Tour> tourList) {
        this.inflater = inflater;
        this.context = context;
        this.mListener = listener;
        this.totalcount = totalcount;
        this.tourList = tourList;
    }

    public ViewPagerAdapter() {

    }

    //Pager Adapter가 가지고 있는 뷰 개
    @Override
    public int getCount() {
        return totalcount;
    }

    //ViewPager가 현재 보여질 Item(View객체)를 생성할 필요가 있는 때 자동으로 호출
    //쉽게 말해, 스크롤을 통해 현재 보여져야 하는 View를 만들어냄.
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : ViewPager가 보여줄 View의 위치(가장 처음부터 0,1,2,3...)
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        // TODO Auto-generated method stub
        View view = null;

        view = inflater.inflate(R.layout.item_child_view, null);
        final ViewPagerHolder viewPagerHolder = new ViewPagerHolder(view);


        //viewPagerHolder.home_cv.setMaxCardElevation(getPixelsFromDPs(10));

        /*
        * home view people image rounding
        */
        viewPagerHolder.home_person_iv.setBackground(new ShapeDrawable(new OvalShape()));
        viewPagerHolder.home_person_iv.setClipToOutline(true);
        viewPagerHolder.home_person_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        /*
        * home view flag image rounding
        */
        viewPagerHolder.home_flag_iv.setBackground(new ShapeDrawable(new OvalShape()));
        viewPagerHolder.home_flag_iv.setClipToOutline(true);
        viewPagerHolder.home_flag_iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        /* tour info start */
        final Tour tour = tourList.get(position);

        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUser(context, tour.getUid(), viewPagerHolder.home_name, viewPagerHolder.home_flag, viewPagerHolder.home_main_lang, viewPagerHolder.home_description, viewPagerHolder.home_person_iv, viewPagerHolder.home_flag_iv);

        FirebaseTour firebaseTour = new FirebaseTour();
        firebaseTour.getTourOfTripcard(tour.getKey(), tour.getUid(), context, viewPagerHolder.home_sub_lang1, viewPagerHolder.home_sub_lang2, viewPagerHolder.home_trip_period, viewPagerHolder.home_tourist_type, null);



        //viewPagerHolder.home_tourist_type.setText(tour.getTour_type());

        /* period */
        /*
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        calStart.setTimeInMillis(tour.getStart_timestamp());
        calEnd.setTimeInMillis(tour.getEnd_timestamp());

        viewPagerHolder.home_trip_period.setText(fmt.format(calStart.getTime()) + " ~ " + fmt.format(calEnd.getTime()));
        */

        /* tour info end */

        viewPagerHolder.home_person_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserInformationActivity.class);
                //(MainActivity)context).uid, tour.getUid()
                intent.putExtra("mUID", ((MainActivity)context).uid);
                intent.putExtra("oUID", tour.getUid());
                ((MainActivity) context).startActivityForResult(intent,3000);
            }
        });

        viewPagerHolder.home_send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseChatroom firebaseChatroom = new FirebaseChatroom(context);
                firebaseChatroom.writeChatroom(((MainActivity)context).uid, tour.getUid());



            }
        });

        //ViewPager에 만들어 낸 View 추가
        container.addView(view);

        //Image가 세팅된 View를 리턴
        return view;
    }

    //화면에 보이지 않은 View는파괴를 해서 메모리를 관리함.
    //첫번째 파라미터 : ViewPager
    //두번째 파라미터 : 파괴될 View의 인덱스(가장 처음부터 0,1,2,3...)
    //세번째 파라미터 : 파괴될 객체(더 이상 보이지 않은 View 객체)
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub

        //ViewPager에서 보이지 않는 View는 제거
        //세번째 파라미터가 View 객체 이지만 데이터 타입이 Object여서 형변환 실시
        container.removeView((View)object);

    }

    //instantiateItem() 메소드에서 리턴된 Ojbect가 View가  맞는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View v, Object obj) {

        // TODO Auto-generated method stub
        return v == obj;

    }

    protected int getPixelsFromDPs(int dps){
        Resources r = context.getResources();
        int  px = (int) (TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dps, r.getDisplayMetrics()));
        return px;
    }

}
