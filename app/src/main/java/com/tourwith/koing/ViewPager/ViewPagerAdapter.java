package com.tourwith.koing.ViewPager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v4.view.PagerAdapter;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Firebase.FirebaseChatroom;
import com.tourwith.koing.Firebase.FirebasePicture;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Munak on 2017. 10. 28..
 */

public class ViewPagerAdapter extends PagerAdapter {
    LayoutInflater inflater;
    Context context;
    //FragmentManager fm;
    //Activity activity;
    final ViewPagerClickListener mListener;
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

        //새로운 View 객체를 Layoutinflater를 이용해서 생성
        //만들어질 View의 설계는 res폴더>>layout폴더>>viewpater_childview.xml 레이아웃 파일 사용
        view = inflater.inflate(R.layout.item_child_view, null);
        final ViewPagerHolder viewPagerHolder = new ViewPagerHolder(view);

        //만들어진 View안에 있는 ImageView 객체 참조
        //위에서 inflated 되어 만들어진 view로부터 findViewById()를 해야 하는 것에 주의.


        //ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
        //현재 position에 해당하는 이미지를 setting
        //img.setImageResource(R.drawable.gametitle_01 + position);
        //viewPagerHolder.tv1.setText("name #" + position);
        //ll.setBackgroundColor(Color.rgb(0,0,255) + position * Color.rgb(50,0,0));
        //viewPagerHolder.cv.setCardBackgroundColor(Color.rgb(3,67,223) + position * Color.rgb(30,0,0));

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

        FirebasePicture firebasePicture = new FirebasePicture(context);
        firebasePicture.downLoadProfileImage(tour.getUid(), FirebasePicture.ORIGINAL, viewPagerHolder.home_person_iv);

        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUserInfo(tour.getUid(), viewPagerHolder.home_name, viewPagerHolder.home_flag, viewPagerHolder.home_language, viewPagerHolder.home_description);

        viewPagerHolder.home_tourist_type.setText(tour.getTour_type());

        /* period */
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();

        calStart.setTimeInMillis(tour.getStart_timestamp());
        calEnd.setTimeInMillis(tour.getEnd_timestamp());

        viewPagerHolder.home_trip_period.setText(fmt.format(calStart.getTime()) + " ~ " + fmt.format(calEnd.getTime()));


        /* tour info end */

        viewPagerHolder.home_send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseChatroom firebaseChatroom = new FirebaseChatroom(context);
                firebaseChatroom.writeChatroom(((MainActivity)context).uid, tour.getUid());

                //Toast.makeText(view.getContext(), position + "g : " + cv.getCardBackgroundColor(), Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(view.getContext(), DetailActivity.class);
                //view.getContext().startActivity(intent);
/*
                StartFragment firstFragment = new StartFragment();
                DetailFragment detailFragment = new DetailFragment();

                // Note that we need the API version check here because the actual transition classes (e.g. Fade)
                // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
                // ARE available in the support library (though they don't do anything on API < 21)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Transition changeTransform = TransitionInflater.from(context).inflateTransition(R.transition.change_image_transform);
                    Transition explodeTransform = TransitionInflater.from(context).inflateTransition(android.R.transition.explode);
                    Transition fadeTransform = TransitionInflater.from(context).inflateTransition(android.R.transition.fade);
                    */

/*
                Pair[] pair = new Pair[2];
                pair[0] = new Pair<View, String>(tv1, "text_name");
                pair[1] = new Pair<View, String>(iv, "img_profile");
*/
/*
                    firstFragment.setSharedElementReturnTransition(changeTransform);
                    detailFragment.setEnterTransition(new Fade());
                    firstFragment.setExitTransition(new Fade());

                    detailFragment.setSharedElementEnterTransition(changeTransform);
                    detailFragment.setEnterTransition(fadeTransform);

                }

                    FragmentTransaction ft = fm.beginTransaction()
                            .replace(R.id.container, detailFragment)
                            .addToBackStack(null)
                            .addSharedElement(iv, "img_profile")
                            .addSharedElement(tv1, "text_name");

                    // Apply the transaction
                    ft.commit();
*/
/*
                    // Setup exit transition on first fragment
                    //firstFragment.setSharedElementReturnTransition(changeTransform);
                    //firstFragment.setExitTransition(explodeTransform);

                    // Setup enter transition on second fragment
                    detailFragment.setSharedElementEnterTransition(new DetailsTransition());
                    detailFragment.setEnterTransition(new Fade());
                    firstFragment.setExitTransition(new Fade());
                    detailFragment.setSharedElementReturnTransition(new DetailsTransition());

                    // Find the shared element (in Fragment A)
                    //ImageView ivProfile = (ImageView) findViewById(R.id.ivProfile);

                    // Add second fragment by replacing first
                    FragmentTransaction ft = fm.beginTransaction()
                            .replace(R.id.container, detailFragment)
                            .addToBackStack("transaction")
                            .addSharedElement(iv, "img_profile");
                    // Apply the transaction
                    ft.commit();

*/


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
