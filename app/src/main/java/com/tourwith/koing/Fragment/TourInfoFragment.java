package com.tourwith.koing.Fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Activity.ShareActivity;
import com.tourwith.koing.Activity.TourInfoFilterActivity;
import com.tourwith.koing.CardSlider.CardSliderLayoutManager;
import com.tourwith.koing.CardSlider.CardSnapHelper;
import com.tourwith.koing.CardSlider.SliderAdapter;
import com.tourwith.koing.Dialog.GpsSetDialog;
import com.tourwith.koing.Firebase.FirebaseTourInfo;
import com.tourwith.koing.LikesRecyclerView.LikesAdapter;
import com.tourwith.koing.Model.TourInfoItem;
import com.tourwith.koing.Model.TourInfoResponse;
import com.tourwith.koing.R;
import com.tourwith.koing.TourAPI.GpsInfoService;
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
import static android.content.Context.LOCATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class TourInfoFragment extends Fragment {

    private static final int TOUR_INFO_FILTER_ACTIVITY = 0;

    private Retrofit retrofit;
    private TourInfoRetrofitService retrofitService;

    private RecyclerView recyclerView;

    private String area;
    private String tourType;

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
    private ImageButton tourInfoLocation;
    private ImageButton tourInfoShare;

    private ImageView tourTypeAllImageView;
    private ImageView tourTypeDiningImageView;
    private ImageView tourTypeLeisureImageView;
    private ImageView tourTypeEventsImageView;
    private ImageView tourTypeShoppingImageView;
    private ImageView tourTypeAttractionsImageView;
    private ImageView tourTypeCulturalImageView;

    private RecyclerView tour_info_likes_recyclerview;
    private SliderAdapter sliderAdapter;
    private LikesAdapter likesAdapter;

    private TextView tour_info_likes_count;

    private FirebaseTourInfo firebaseTourInfo;

    private MainActivity activity;

    private String[] countries;

    private String[] titles;

    private String[] addrs;

    private String[] contentids;

    private String[] pictureUrls;

    private CardSliderLayoutManager layoutManger;
    private int currentPosition;

    private Call<TourInfoResponse> call;

    public TourInfoFragment() {

    }

    public TourInfoFragment(MainActivity mainActivity) {
        this.activity = mainActivity;
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

        tourTypeAllImageView = (ImageView)view.findViewById(R.id.tour_type_all_image_view);
        tourTypeDiningImageView = (ImageView)view.findViewById(R.id.tour_type_dining_image_view);
        tourTypeLeisureImageView = (ImageView)view.findViewById(R.id.tour_type_leisure_image_view);
        tourTypeEventsImageView = (ImageView)view.findViewById(R.id.tour_type_events_image_view);
        tourTypeShoppingImageView = (ImageView)view.findViewById(R.id.tour_type_shopping_image_view);
        tourTypeAttractionsImageView = (ImageView)view.findViewById(R.id.tour_type_attraction_image_view);
        tourTypeCulturalImageView = (ImageView)view.findViewById(R.id.tour_type_cultural_image_view);

        countries = new String[24];
        titles = new String[24];
        addrs = new String[24];
        contentids = new String[24];
        pictureUrls = new String[24];

        /* tour_info_country */
        country1TextView = (TextView) view.findViewById(R.id.tv_country_1);
        country2TextView = (TextView) view.findViewById(R.id.tv_country_2);
        countryAnimDuration = view.getResources().getInteger(R.integer.labels_animation_duration);
        countryOffset1 = view.getResources().getDimensionPixelSize(R.dimen.left_offset);
        countryOffset2 = view.getResources().getDimensionPixelSize(R.dimen.card_width);

        country1TextView.setX(countryOffset1);
        country2TextView.setX(countryOffset2);
        country2TextView.setAlpha(0f);

        /* tour_info_title */
        titleAnimDuration = view.getResources().getInteger(R.integer.labels_animation_duration);
        titleOffset1 = view.getResources().getDimensionPixelSize(R.dimen.left_offset2);
        titleOffset2 = view.getResources().getDimensionPixelSize(R.dimen.card_width);
        title1TextView = (TextView) view.findViewById(R.id.tv_title_1);
        title2TextView = (TextView) view.findViewById(R.id.tv_title_2);

        title1TextView.setX(titleOffset1);
        title2TextView.setX(titleOffset2);
        title2TextView.setAlpha(0f);

        /* tour_info_address */
        addrAnimDuration = view.getResources().getInteger(R.integer.labels_animation_duration);
        addrOffset1 = view.getResources().getDimensionPixelSize(R.dimen.left_offset2);
        addrOffset2 = view.getResources().getDimensionPixelSize(R.dimen.card_width);
        addr1TextView = (TextView) view.findViewById(R.id.tv_addr_1);
        addr2TextView = (TextView) view.findViewById(R.id.tv_addr_2);

        addr1TextView.setX(addrOffset1);
        addr2TextView.setX(addrOffset2);
        addr2TextView.setAlpha(0f);



        /* tour_info_likes_recyclerview */
        tour_info_likes_recyclerview = (RecyclerView) view.findViewById(R.id.tour_info_likes_recyclerview);
        tour_info_likes_recyclerview.setHasFixedSize(true);
        RecyclerView.LayoutManager tour_info_layoutManager = new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL, false);
        tour_info_likes_recyclerview.setLayoutManager(tour_info_layoutManager);

        tour_info_likes_count = (TextView) view.findViewById(R.id.tour_info_likes_count);

        firebaseTourInfo = new FirebaseTourInfo(getContext(), tour_info_likes_recyclerview);

        tour_info_heart = (ImageButton) view.findViewById(R.id.tour_info_heart);
        tourInfoLocation = (ImageButton)view.findViewById(R.id.tour_info_location);
        tour_info_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseTourInfo.doLike(activity.uid ,contentids[currentPosition], tour_info_heart, tour_info_likes_count);

            }
        });

        tourInfoShare = (ImageButton) view.findViewById(R.id.tour_info_share);
        tourInfoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //공유할 사람 선택하는 ShareActivity로
                if(countries == null || countries[currentPosition]==null || countries[currentPosition].equals(""))
                    return;

                Intent intent = new Intent(getContext(), ShareActivity.class);
                intent.putExtra("uid", activity.uid).putExtra("country", countries[currentPosition])
                        .putExtra("title", titles[currentPosition]).putExtra("addr", addrs[currentPosition])
                        .putExtra("url", pictureUrls[currentPosition]);

                startActivity(intent);

            }
        });


        view.findViewById(R.id.tour_info_likes_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseTourInfo.getLikes(contentids[currentPosition], tour_info_likes_count);
            }
        });

        tourInfoFilter = (ImageButton)view.findViewById(R.id.tour_info_filter);

        baseUrl = getString(R.string.retrofitBaseURL);
        korKey = getString(R.string.tourAPIKeyKor);
        engKey = getString(R.string.tourAPIKeyEng);
        try {
            decodedKorKey = URLDecoder.decode(korKey, "UTF-8");
            decodedEngKey = URLDecoder.decode(engKey, "UTF-8");
        }catch(Exception e){

        }

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        retrofitService = retrofit.create(TourInfoRetrofitService.class);


        /* default 화면 */
        area = "";
        tourType = "";
        callGetEngTripInfoByArea(area,tourType);

        /* tour_info_cardslider */
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        setViewForTripInfo(true);

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });



        tourInfoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

                // GPS 정보 가져오기
                Boolean isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // 현재 네트워크 상태 값 알아오기
                Boolean isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {//gps 설정이 안된 경우
                    GpsSetDialog dialog = new GpsSetDialog(getContext());
                    dialog.show();
                }else{//gps 설정이 된 경우
                    callGetEngTripInfoByLocation(tourType);
                    setViewForTripInfo(false);
                }
            }
        });


        tourInfoFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TourInfoFilterActivity.class);
                startActivityForResult(intent,TOUR_INFO_FILTER_ACTIVITY);
            }
        });




        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case TOUR_INFO_FILTER_ACTIVITY:
                if(resultCode == RESULT_OK){
                    area = data.getStringExtra("area");
                    tourType = data.getStringExtra("tourType");

                    if(!area.equals("Current Position")) {
                        callGetEngTripInfoByArea(area,tourType);
                    }
                    else{//현재 위치에서 정보를 가저오는 경우
                        callGetEngTripInfoByLocation(tourType);
                    }
                    setViewForTripInfo(false);
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

        firebaseTourInfo.getLikes(contentids[pos % contentids.length],tour_info_likes_count);
        firebaseTourInfo.getMyLike(activity.uid, contentids[pos % contentids.length],tour_info_heart);

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

    private void callGetEngTripInfoByArea(String area, String tourType){
        call = retrofitService.
                getEngTripInfo(decodedEngKey,
                        MatchParsingSource.getQueryNum(tourType),
                        MatchParsingSource.getQueryNum(area),
                        "", "", "", "",
                        "Y",
                        "AND",
                        "Koing",
                        "P",
                        "24",
                        "1"
                );
    }
    private void callGetEngTripInfoByLocation(String tourType){
        double latitude=37.568477;
        double longitude=126.981106;
        GpsInfoService gps = new GpsInfoService(getContext());
        if(gps.isGetLocation()){ //gps가 설정되어있는 경우
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            String lat = String.valueOf(latitude);
            String lon = String.valueOf(longitude);

            call = retrofitService.getEngTripInfo2(decodedEngKey,
                    MatchParsingSource.getQueryNum(tourType),
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

            Toast.makeText(getActivity(), "현재 위치로 설정됩니다", Toast.LENGTH_SHORT).show();
        }else{ //gps가 설정되어있지 않은 경우
            GpsSetDialog dialog = new GpsSetDialog(getContext());
            dialog.show();
        }
    }

    private void setViewForTripInfo(final Boolean first) {
        call.enqueue(new Callback<TourInfoResponse>() {
            @Override
            public void onResponse(Call<TourInfoResponse> call, Response<TourInfoResponse> response) {
                TourInfoResponse r = response.body();
                if (r.getBody().getItems().getItem() != null) {
                    mItems = r.getBody().getItems().getItem();

                    int ii = 0;
                    for (TourInfoItem item : mItems) {
                        countries[ii] = MatchParsingSource.getQueryNum(item.getAreacode());
                        titles[ii] = item.getTitle();
                        addrs[ii] = item.getAddr1();
                        contentids[ii] = item.getContentid();
                        pictureUrls[ii] = item.getFirstimage();
                        ii++;
                        if(ii==24)
                            break;
                    }
                    if(first) {
                        sliderAdapter = new SliderAdapter(ii, null, getContext(), mItems);
                        recyclerView.setAdapter(sliderAdapter);
                    }else{
                        sliderAdapter.addAll(mItems, ii);
                        sliderAdapter.notifyDataSetChanged();
                    }
                    /* country */
                    country1TextView.setText(countries[0]);

                    /* title */
                    title1TextView.setText(titles[0]);

                    /* addr */
                    addr1TextView.setText(addrs[0]);

                    /* likes */
                    firebaseTourInfo.getLikes(contentids[0],tour_info_likes_count);

                    firebaseTourInfo.getMyLike(activity.uid, contentids[0],tour_info_heart);

                    setTourTypeImageView(tourType);
                }
            }

            @Override
            public void onFailure(Call<TourInfoResponse> call, Throwable t) {

                Toast.makeText(getActivity(), "정보받아오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTourTypeImageView(String tourType){
        tourTypeAllImageView.setVisibility(View.INVISIBLE);
        tourTypeAttractionsImageView.setVisibility(View.INVISIBLE);
        tourTypeCulturalImageView.setVisibility(View.INVISIBLE);
        tourTypeEventsImageView.setVisibility(View.INVISIBLE);
        tourTypeLeisureImageView.setVisibility(View.INVISIBLE);
        tourTypeShoppingImageView.setVisibility(View.INVISIBLE);
        tourTypeDiningImageView.setVisibility(View.INVISIBLE);
        if(tourType.equals("All type") || tourType.equals("")){
            tourTypeAllImageView.setVisibility(View.VISIBLE);
        }else if(tourType.equals("Tourist Attractions")){
            tourTypeAttractionsImageView.setVisibility(View.VISIBLE);
        }else if(tourType.equals("Cultural Facilities")){
            tourTypeCulturalImageView.setVisibility(View.VISIBLE);
        }else if(tourType.equals("Events")){
            tourTypeEventsImageView.setVisibility(View.VISIBLE);
        }else if(tourType.equals("Leisure")){
            tourTypeLeisureImageView.setVisibility(View.VISIBLE);
        }else if(tourType.equals("Shopping")){
            tourTypeShoppingImageView.setVisibility(View.VISIBLE);
        }else if(tourType.equals("Dining")){
            tourTypeDiningImageView.setVisibility(View.VISIBLE);
        }
    }

}
