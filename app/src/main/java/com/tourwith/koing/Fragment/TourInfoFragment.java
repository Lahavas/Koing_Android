package com.tourwith.koing.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tourwith.koing.CardSlider.CardSliderLayoutManager;
import com.tourwith.koing.CardSlider.CardSnapHelper;
import com.tourwith.koing.CardSlider.SliderAdapter;
import com.tourwith.koing.LikesRecyclerView.LikesAdapter;
import com.tourwith.koing.Model.TourInfoItem;
import com.tourwith.koing.Model.TourInfoResponse;
import com.tourwith.koing.R;
import com.tourwith.koing.TourAPI.MatchParsingSource;
import com.tourwith.koing.TourAPI.TourInfoRetrofitService;

import java.net.URLDecoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class TourInfoFragment extends Fragment {

    private static final int TOUR_INFO_FILTER_ACTIVITY = 0;

    private Retrofit retrofit;
    private TourInfoRetrofitService retrofitService;

    private RecyclerView recyclerView;

    private String baseUrl;
    private TextView textView3;
    private String korKey;
    private String engKey;
    private String decodedKorKey;
    private String decodedEngKey;
    private List<TourInfoItem> mItems;
    private View view;

    private TextView country1TextView;
    private TextView country2TextView;
    private int countryOffset1;
    private int countryOffset2;
    private long countryAnimDuration;

    private TextView title1TextView;
    private TextView title2TextView;
    private int titleOffset1;
    private int titleOffset2;
    private long titleAnimDuration;

    private TextView addr1TextView;
    private TextView addr2TextView;
    private int addrOffset1;
    private int addrOffset2;
    private long addrAnimDuration;

    private ImageButton tour_info_heart;
    private ImageButton tourInfoFilter;

    private RecyclerView tour_info_likes_recyclerview;
    private SliderAdapter sliderAdapter;
    private LikesAdapter likesAdapter;

    private String[] countries;

    private String[] titles;

    private String[] addrs;

    private CardSliderLayoutManager layoutManger;
    private int currentPosition;

    public TourInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tour_info, container, false);

        countries = new String[24];
        titles = new String[24];
        addrs = new String[24];


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        country1TextView = (TextView) view.findViewById(R.id.tv_country_1);
        country2TextView = (TextView) view.findViewById(R.id.tv_country_2);
        countryAnimDuration = view.getResources().getInteger(R.integer.labels_animation_duration);
        countryOffset1 = view.getResources().getDimensionPixelSize(R.dimen.left_offset);
        countryOffset2 = view.getResources().getDimensionPixelSize(R.dimen.card_width);


        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country2TextView.setAlpha(0f);

        titleAnimDuration = view.getResources().getInteger(R.integer.labels_animation_duration);
        titleOffset1 = view.getResources().getDimensionPixelSize(R.dimen.left_offset2);
        titleOffset2 = view.getResources().getDimensionPixelSize(R.dimen.card_width);
        title1TextView = (TextView) view.findViewById(R.id.tv_title_1);
        title2TextView = (TextView) view.findViewById(R.id.tv_title_2);

        title1TextView.setX(titleOffset1);
        title2TextView.setX(titleOffset2);
        title2TextView.setAlpha(0f);

        addrAnimDuration = view.getResources().getInteger(R.integer.labels_animation_duration);
        addrOffset1 = view.getResources().getDimensionPixelSize(R.dimen.left_offset2);
        addrOffset2 = view.getResources().getDimensionPixelSize(R.dimen.card_width);
        addr1TextView = (TextView) view.findViewById(R.id.tv_addr_1);
        addr2TextView = (TextView) view.findViewById(R.id.tv_addr_2);

        addr1TextView.setX(addrOffset1);
        addr2TextView.setX(addrOffset2);
        addr2TextView.setAlpha(0f);

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        new CardSnapHelper().attachToRecyclerView(recyclerView);

        tour_info_likes_recyclerview = (RecyclerView) view.findViewById(R.id.tour_info_likes_recyclerview);
        tour_info_likes_recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager tour_info_layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        tour_info_likes_recyclerview.setLayoutManager(tour_info_layoutManager);



        tour_info_heart = (ImageButton) view.findViewById(R.id.tour_info_heart);

        tour_info_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tour_info_heart.setBackgroundResource(R.drawable.ic_heart_t);
            }
        });

        baseUrl = getString(R.string.retrofitBaseURL);
        korKey = getString(R.string.tourAPIKeyKor);
        engKey = getString(R.string.tourAPIKeyEng);
        try {
            decodedKorKey = URLDecoder.decode(korKey, "UTF-8");
            decodedEngKey = URLDecoder.decode(engKey, "UTF-8");
        }catch(Exception e){

        }

        tourInfoFilter = (ImageButton)view.findViewById(R.id.tour_info_filter);
        tourInfoFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TourInfoFilterActivity.class);
                startActivityForResult(intent,TOUR_INFO_FILTER_ACTIVITY);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        retrofitService = retrofit.create(TourInfoRetrofitService.class);
        //영문 관광정보 받아오기

        Call<TourInfoResponse> call = retrofitService.
                getEngTripInfo(decodedEngKey,
                        "78",
                        "1",
                        "","","","",
                        "Y",
                        "AND",
                        "Koing",
                        "P",
                        "24",
                        "1"
                );


        call.enqueue(new Callback<TourInfoResponse>() {
            @Override
            public void onResponse(Call<TourInfoResponse> call, Response<TourInfoResponse> response) {
                TourInfoResponse r = response.body();
                if(r.getBody().getItems().getItem() != null) {
                    mItems = r.getBody().getItems().getItem();

                    int ii = 0;
                    for(TourInfoItem item : mItems){
                        countries[ii] = MatchParsingSource.getQueryNum(item.getAreacode());
                        titles[ii] = item.getTitle();
                        addrs[ii] = item.getAddr1();
                        ii++;
                    }

                    sliderAdapter = new SliderAdapter(24, null, getContext(), mItems);
                    recyclerView.setAdapter(sliderAdapter);


                    /* country */

                    country1TextView.setText(countries[0]);

                    /* title */
                    title1TextView.setText(titles[0]);

                    /* addr */

                    addr1TextView.setText(addrs[0]);

                    likesAdapter = new LikesAdapter();
                    tour_info_likes_recyclerview.setAdapter(likesAdapter);
                }

            }

            @Override
            public void onFailure(Call<TourInfoResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "정보받아오기 실패" , Toast.LENGTH_SHORT).show();
            }
        });

        /*
        //영문 현재 위치 근처 관광정보 받아오기
        double latitude=37.568477;
        double longitude=126.981106;
        GpsInfoService gps = new GpsInfoService(getContext());
        if(gps.isGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }
        String lat = String.valueOf(latitude);
        String lon = String.valueOf(longitude);

        */


/*
        Call<TourInfoResponse> call = retrofitService.getEngTripInfo2(decodedEngKey,
                MatchParsingSource.getQueryNum("Cultural Facilities"),
                lon,//mapX
                lat,//mapY
                "2000",
                "Y",
                "AND",
                "Koing",
                "P",
                "24",
                "1"
        );

*/


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case TOUR_INFO_FILTER_ACTIVITY:
                if(resultCode == RESULT_OK){
                    String area = data.getStringExtra("area");
                    String tourType = data.getStringExtra("tourType");

                    Call<TourInfoResponse> call = retrofitService.
                            getEngTripInfo(decodedEngKey,
                                    MatchParsingSource.getQueryNum(tourType),
                                    MatchParsingSource.getQueryNum(area),
                                    "","","","",
                                    "Y",
                                    "AND",
                                    "Koing",
                                    "P",
                                    "24",
                                    "1"
                            );

                    call.enqueue(new Callback<TourInfoResponse>() {
                        @Override
                        public void onResponse(Call<TourInfoResponse> call, Response<TourInfoResponse> response) {
                            TourInfoResponse r = response.body();
                            if(r.getBody().getItems().getItem() != null) {
                                mItems = r.getBody().getItems().getItem();

                                int ii = 0;
                                for(TourInfoItem item : mItems){
                                    countries[ii] = MatchParsingSource.getQueryNum(item.getAreacode());
                                    titles[ii] = item.getTitle();
                                    addrs[ii] = item.getAddr1();
                                    ii++;
                                }

                                sliderAdapter = new SliderAdapter(24, null, getContext(), mItems);
                                recyclerView.setAdapter(sliderAdapter);


                    /* country */

                                country1TextView.setText(countries[0]);

                    /* title */
                                title1TextView.setText(titles[0]);

                    /* addr */

                                addr1TextView.setText(addrs[0]);

                                likesAdapter = new LikesAdapter();
                                tour_info_likes_recyclerview.setAdapter(likesAdapter);
                            }

                        }

                        @Override
                        public void onFailure(Call<TourInfoResponse> call, Throwable t) {

                            Toast.makeText(getActivity(), "정보받아오기 실패" , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
        }
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }

        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {
        final boolean left2right = pos < currentPosition;

        setCountryText(countries[pos % countries.length], left2right);
        setTitleText(titles[pos % titles.length], left2right);
        setAddrText(addrs[pos % addrs.length], left2right);

        currentPosition = pos;
    }

    private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (country1TextView.getAlpha() > country2TextView.getAlpha()) {
            visibleText = country1TextView;
            invisibleText = country2TextView;
        } else {
            visibleText = country2TextView;
            invisibleText = country1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }

    private void setTitleText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (title1TextView.getAlpha() > title2TextView.getAlpha()) {
            visibleText = title1TextView;
            invisibleText = title2TextView;
        } else {
            visibleText = title2TextView;
            invisibleText = title1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = titleOffset2;
        } else {
            invisibleText.setX(titleOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", titleOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(titleAnimDuration);
        animSet.start();
    }

    private void setAddrText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (addr1TextView.getAlpha() > addr2TextView.getAlpha()) {
            visibleText = addr1TextView;
            invisibleText = addr2TextView;
        } else {
            visibleText = addr2TextView;
            invisibleText = addr1TextView;
        }

        final int vOffset;
        if (left2right) {
            invisibleText.setX(0);
            vOffset = addrOffset2;
        } else {
            invisibleText.setX(addrOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", addrOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(addrAnimDuration);
        animSet.start();
    }
}
